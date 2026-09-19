package cr.ac.una.relojunaws.service;

import cr.ac.una.relojunaws.model.DetallePlanilla;
import cr.ac.una.relojunaws.model.Empleado;
import cr.ac.una.relojunaws.model.Marca;
import cr.ac.una.relojunaws.model.Planilla;
import cr.ac.una.relojunaws.model.dto.ResumenDetallePlanillaDTO;
import cr.ac.una.relojunaws.model.dto.ResumenPlanillaDTO;
import cr.ac.una.relojunaws.util.Respuesta;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
@LocalBean
public class PlanillaService {

    @PersistenceContext(unitName = "WSRelojUNAPU")
    private EntityManager em;

    @EJB
    private MarcaService marcaService;

    private static final Logger LOG = Logger.getLogger(PlanillaService.class.getName());

    private static final Double HORAS_ORDINARIAS_POR_JORNADA = 8.0;
    private static final Double FACTOR_HORA_EXTRA = 1.5;
    private static final Double FACTOR_HORA_DOBLE = 2.0;
    private static final Double PORCENTAJE_RECARGO_NOCTURNO = 0.15;
    private static final Integer HORA_INICIO_NOCTURNA = 19;
    private static final Integer HORA_FIN_NOCTURNA = 5;

    public Respuesta calcularPlanilla(int anio, int mes) {
        try {
            if (mes < 1 || mes > 12) {
                return new Respuesta(false, "planilla.calcular.mesinvalido", "calcularPlanilla mes fuera de rango: " + mes);
            }

            LocalDate[] rango = obtenerRangoDelMes(anio, mes);
            List<Marca> marcasDelMes = obtenerMarcasDelMes(rango[0], rango[1]);

            Map<Empleado, List<Marca>> porEmpleado = marcasDelMes.stream()
                    .collect(Collectors.groupingBy(Marca::getEmpleado));

            List<ResumenDetallePlanillaDTO> detalles = porEmpleado.entrySet().stream()
                    .map(entry -> {
                        List<Marca> ordenadas = entry.getValue().stream()
                                .sorted(Comparator.comparing(Marca::getFechaHora))
                                .toList();
                        return calcularDetalleEmpleado(entry.getKey(), ordenadas);
                    })
                    .sorted(Comparator.comparing(ResumenDetallePlanillaDTO::getFolioEmpleado))
                    .toList();

            Boolean generada = existePlanillaEnMesAnio(anio, mes);
            ResumenPlanillaDTO resumen = construirResumenGeneral(anio, mes, detalles, generada);

            return new Respuesta(true, "", "", "ResumenPlanilla", resumen);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en calcularPlanilla", ex);
            return new Respuesta(false, "planilla.calcular.error", "calcularPlanilla Exception " + ex.getMessage());
        }
    }

    public Respuesta generarPlanilla(ResumenPlanillaDTO resumenPlanilla) {
        try {
            if (resumenPlanilla == null || resumenPlanilla.getAno() == null || resumenPlanilla.getMes() == null) {
                return new Respuesta(false, "planilla.generar.datosrequeridos", "generarPlanilla resumen, ano o mes nulos");
            }

            if (resumenPlanilla.getDetallesPlanilla() == null || resumenPlanilla.getDetallesPlanilla().isEmpty()) {
                return new Respuesta(false, "planilla.generar.detallesrequeridos", "generarPlanilla sin detalles");
            }

            int anio = resumenPlanilla.getAno();
            int mes = resumenPlanilla.getMes();

            if (!mesHaFinalizado(anio, mes)) {
                return new Respuesta(false, "planilla.generar.mesnofinalizado",
                        "generarPlanilla el mes " + mes + "/" + anio + " aún no ha finalizado");
            }

            LocalDate[] rango = obtenerRangoDelMes(anio, mes);
            List<Marca> marcasDelMes = obtenerMarcasDelMes(rango[0], rango[1]);

            if (existenInconsistenciasEnElMes(marcasDelMes)) {
                return new Respuesta(false, "planilla.generar.inconsistencias",
                        "generarPlanilla existen marcas inconsistentes en " + mes + "/" + anio);
            }

            if (existePlanillaEnMesAnio(anio, mes)) {
                return new Respuesta(false, "planilla.generar.yaexiste",
                        "generarPlanilla ya existe una planilla para " + mes + "/" + anio);
            }

            Planilla planilla = crearEntidadPlanilla(resumenPlanilla);
            em.persist(planilla);
            em.flush();

            for (ResumenDetallePlanillaDTO detalleDTO : resumenPlanilla.getDetallesPlanilla()) {
                DetallePlanilla detalle = crearEntidadDetallePlanilla(detalleDTO, planilla);
                em.persist(detalle);
            }
            em.flush();

            return new Respuesta(true, "planilla.generar.exito", "Planilla generada", "Planilla", resumenPlanilla);
        } catch (NoResultException ex) {
            return new Respuesta(false, "planilla.generar.empleadonotfound", "generarPlanilla empleado no encontrado: " + ex.getMessage());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en generarPlanilla", ex);
            return new Respuesta(false, "planilla.generar.error", "generarPlanilla Exception " + ex.getMessage());
        }
    }

    //helpers
    private LocalDate[] obtenerRangoDelMes(int anio, int mes) {
        YearMonth yearMonth = YearMonth.of(anio, mes);
        return new LocalDate[]{yearMonth.atDay(1), yearMonth.atEndOfMonth()};
    }

    private List<Marca> obtenerMarcasDelMes(LocalDate desde, LocalDate hasta) {
        Query qry = em.createNamedQuery("Marca.findByRangoFechas", Marca.class);
        qry.setParameter("desde", desde.atStartOfDay());
        qry.setParameter("hasta", hasta.atTime(23, 59, 59));
        return (List<Marca>) qry.getResultList();
    }

    private ResumenDetallePlanillaDTO calcularDetalleEmpleado(Empleado empleado, List<Marca> marcasEmpleado) {
        List<Marca[]> jornadas = construirParesEntradaSalida(marcasEmpleado);

        double horasOrdinarias = 0;
        double horasExtras = 0;
        double horasDobles = 0;
        double horasNocturnas = 0;

        for (Marca[] par : jornadas) {
            Marca entrada = par[0];
            Marca salida = par[1];

            if (entrada == null || salida == null) {
                continue;
            }

            LocalDateTime horaEntrada = entrada.getFechaHora();
            LocalDateTime horaSalida = salida.getFechaHora();

            Double horasJornada = Duration.between(horaEntrada, horaSalida).toMinutes() / 60.0;
            Boolean esDiaLibre = horaEntrada.getDayOfWeek() == DayOfWeek.SUNDAY;

            if (esDiaLibre) {
                horasDobles += horasJornada;
            } else if (horasJornada > HORAS_ORDINARIAS_POR_JORNADA) {
                horasOrdinarias += HORAS_ORDINARIAS_POR_JORNADA;
                horasExtras += horasJornada - HORAS_ORDINARIAS_POR_JORNADA;
            } else {
                horasOrdinarias += horasJornada;
            }

            horasNocturnas += calcularHorasNocturnas(horaEntrada, horaSalida);
        }

        BigDecimal totalAPagar = calcularPagoEmpleado(empleado, horasOrdinarias, horasExtras, horasDobles, horasNocturnas);
        ResumenDetallePlanillaDTO detalle = new ResumenDetallePlanillaDTO();
        detalle.setFolioEmpleado(empleado.getFolio());
        detalle.setNombreCompletoEmpleado(
                empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido());
        detalle.setSalarioHoraEmpleado(empleado.getSalarioHora());
        detalle.setHorasOrdinarias(horasOrdinarias);
        detalle.setHorasExtras(horasExtras);
        detalle.setHorasDobles(horasDobles);
        detalle.setHorasNocturnas(horasNocturnas);
        detalle.setTotalHoras(horasOrdinarias + horasExtras + horasDobles);
        detalle.setTotalAPagar(totalAPagar);
        return detalle;
    }

    private List<Marca[]> construirParesEntradaSalida(List<Marca> marcasOrdenadas) {
        List<Marca[]> pares = new ArrayList<>();
        int i = 0;
        while (i < marcasOrdenadas.size()) {
            Marca marcaActual = marcasOrdenadas.get(i);

            if (marcaActual.getTipo().equals("E")) {
                Marca siguiente = (i + 1 < marcasOrdenadas.size()) ? marcasOrdenadas.get(i + 1) : null;
                if (siguiente != null && siguiente.getTipo().equalsIgnoreCase("S")) {
                    pares.add(new Marca[]{marcaActual, siguiente});
                    i += 2;
                } else {
                    pares.add(new Marca[]{marcaActual, null});
                    i++;
                }
            } else {
                pares.add(new Marca[]{null, marcaActual});
                i++;
            }
        }
        return pares;
    }

    private double calcularHorasNocturnas(LocalDateTime entrada, LocalDateTime salida) {
        Double minutosNocturnos = 0.0;
        LocalDate dia = entrada.toLocalDate();
        LocalDate diaFinal = salida.toLocalDate();

        while (!dia.isAfter(diaFinal)) {
            LocalDateTime inicioNocturno = dia.atTime(HORA_INICIO_NOCTURNA, 0);
            LocalDateTime finNocturno = dia.plusDays(1).atTime(HORA_FIN_NOCTURNA, 0);

            LocalDateTime inicioSolape = entrada.isAfter(inicioNocturno) ? entrada : inicioNocturno;
            LocalDateTime finSolape = salida.isBefore(finNocturno) ? salida : finNocturno;

            if (inicioSolape.isBefore(finSolape)) {
                minutosNocturnos += Duration.between(inicioSolape, finSolape).toMinutes();
            }
            dia = dia.plusDays(1);
        }

        return minutosNocturnos / 60.0;
    }

    private BigDecimal calcularPagoEmpleado(Empleado empleado, double horasOrdinarias, double horasExtras,
            double horasDobles, double horasNocturnas) {
        BigDecimal salarioHora = empleado.getSalarioHora();

        BigDecimal pagoOrdinario = salarioHora.multiply(BigDecimal.valueOf(horasOrdinarias));
        BigDecimal pagoExtra = salarioHora
                .multiply(BigDecimal.valueOf(FACTOR_HORA_EXTRA))
                .multiply(BigDecimal.valueOf(horasExtras));
        BigDecimal pagoDoble = salarioHora
                .multiply(BigDecimal.valueOf(FACTOR_HORA_DOBLE))
                .multiply(BigDecimal.valueOf(horasDobles));
        BigDecimal recargoNocturno = salarioHora
                .multiply(BigDecimal.valueOf(PORCENTAJE_RECARGO_NOCTURNO))
                .multiply(BigDecimal.valueOf(horasNocturnas));

        return pagoOrdinario.add(pagoExtra).add(pagoDoble).add(recargoNocturno)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private ResumenPlanillaDTO construirResumenGeneral(int anio, int mes, List<ResumenDetallePlanillaDTO> detalles, boolean generada) {
        Integer cantEmpleados = detalles.size();
        Double totalOrdinarias = detalles.stream().mapToDouble(ResumenDetallePlanillaDTO::getHorasOrdinarias).sum();
        Double totalExtras = detalles.stream().mapToDouble(ResumenDetallePlanillaDTO::getHorasExtras).sum();
        Double totalNocturnas = detalles.stream().mapToDouble(ResumenDetallePlanillaDTO::getHorasNocturnas).sum();
        BigDecimal totalAPagar = detalles.stream()
                .map(ResumenDetallePlanillaDTO::getTotalAPagar)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ResumenPlanillaDTO resumen = new ResumenPlanillaDTO(
                mes, anio, cantEmpleados, totalOrdinarias, totalExtras, totalNocturnas, totalAPagar, generada);
        resumen.setDetallesPlanilla(detalles);
        return resumen;
    }

    private Boolean mesHaFinalizado(int anio, int mes) {
        LocalDate ultimoDiaDelMes = YearMonth.of(anio, mes).atEndOfMonth();
        return LocalDate.now().isAfter(ultimoDiaDelMes);
    }

    private boolean existenInconsistenciasEnElMes(List<Marca> marcasDelMes) {
        Map<Empleado, List<Marca>> porEmpleado = marcasDelMes.stream()
                .collect(Collectors.groupingBy(Marca::getEmpleado));

        for (List<Marca> marcasEmpleado : porEmpleado.values()) {
            List<Marca> ordenadas = marcasEmpleado.stream()
                    .sorted(Comparator.comparing(Marca::getFechaHora))
                    .toList();

            for (int i = 0; i < ordenadas.size(); i++) {
                Marca actual = ordenadas.get(i);
                Marca anterior = (i > 0) ? ordenadas.get(i - 1) : null;

                boolean esInconsistente = (anterior == null)
                        ? actual.getTipo().equalsIgnoreCase("S")
                        : anterior.getTipo().equalsIgnoreCase(actual.getTipo());

                if (esInconsistente) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existePlanillaEnMesAnio(int anio, int mes) {
        try {
            Query qry = em.createNamedQuery("Planilla.findByMesAnio", Planilla.class);
            qry.setParameter("anio", anio);
            qry.setParameter("mes", mes);
            qry.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    private Planilla crearEntidadPlanilla(ResumenPlanillaDTO resumenPlanilla) {
        Planilla planilla = new Planilla();
        planilla.setMes(resumenPlanilla.getMes());
        planilla.setAnio(resumenPlanilla.getAno());
        planilla.setFechaGeneracion(LocalDate.now());
        planilla.setTotalPagado(resumenPlanilla.getTotalAPagar());
        return planilla;
    }

    private DetallePlanilla crearEntidadDetallePlanilla(ResumenDetallePlanillaDTO detalleDTO, Planilla planilla) {
        Query qry = em.createNamedQuery("Empleado.findByFolio", Empleado.class);
        qry.setParameter("folio", detalleDTO.getFolioEmpleado());
        Empleado empleado = (Empleado) qry.getSingleResult();

        DetallePlanilla detalle = new DetallePlanilla();
        detalle.setEmpleado(empleado);
        detalle.setPlanilla(planilla);
        detalle.setTotalHorasOrdinarias(detalleDTO.getHorasOrdinarias());
        detalle.setTotalHorasExtras(detalleDTO.getHorasExtras());
        detalle.setTotalHorasDobles(detalleDTO.getHorasDobles());
        detalle.setTotalHorasNocturnas(detalleDTO.getHorasNocturnas());
        detalle.setTotalAPagar(detalleDTO.getTotalAPagar());
        return detalle;
    }
}
