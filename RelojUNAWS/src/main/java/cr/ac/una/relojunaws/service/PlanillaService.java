package cr.ac.una.relojunaws.service;

import cr.ac.una.relojunaws.model.DetallePlanilla;
import cr.ac.una.relojunaws.model.Empleado;
import cr.ac.una.relojunaws.model.JornadaPOJO;
import cr.ac.una.relojunaws.model.Marca;
import cr.ac.una.relojunaws.model.Planilla;
import cr.ac.una.relojunaws.model.dto.ResumenDetallePlanillaDTO;
import cr.ac.una.relojunaws.model.dto.ResumenPlanillaDTO;
import cr.ac.una.relojunaws.util.Respuesta;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PlanillaService {

    @PersistenceContext(unitName = "WSRelojUNAPU")
    private EntityManager em;

    @EJB
    private MarcaService marcaService;

    private static final Logger LOG = Logger.getLogger(PlanillaService.class.getName());

    public Respuesta calcularPlanilla(int anio, int mes) {
        // TODO:
        // 1. Validar anio/mes (rango válido de mes 1-12, anio razonable).
        // 2. Obtener rango de fechas del mes (obtenerRangoDelMes).
        // 3. Obtener las marcas del periodo (obtenerMarcasDelMes).
        // 4. Agrupar marcas por empleado.
        // 5. Por cada empleado, construir sus jornadas y calcular horas, incluyendo
        //    horas nocturnas (calcularDetalleEmpleado).
        // 6. Armar el ResumenPlanillaDTO general a partir de los detalles
        //    (construirResumenGeneral), fijando anio y mes en el DTO resultante.
        // 7. Retornar Respuesta(true, ..., resumen).
        return null;
    }

    public Respuesta generarPlanilla(ResumenPlanillaDTO resumenPlanilla) {
        // TODO:
        // 1. Validar que resumenPlanilla no sea nulo y traiga anio, mes y detalles.
        // 2. Validar condición 1: mesHaFinalizado(resumenPlanilla.getAnio(), resumenPlanilla.getMes())
        //    -> si no, retornar error.
        // 3. Validar condición 2: existenInconsistenciasEnElMes(desde, hasta)
        //    -> si sí, retornar error.
        // 4. Validar condición 3: existePlanillaEnMesAnio(anio, mes) -> si sí, retornar error.
        // 5. Si todo pasa: crear entidad Planilla (crearEntidadPlanilla) y persistirla + flush
        //    (para obtener su id).
        // 6. Por cada ResumenDetallePlanillaDTO en resumenPlanilla.getDetallesPlanilla():
        //    crear entidad DetallePlanilla (crearEntidadDetallePlanilla), incluyendo
        //    horas nocturnas, asociada a la Planilla recién creada, y persistirla.
        // 7. em.flush().
        // 8. Retornar Respuesta(true, ...) con la planilla guardada.
        return null;
    }

    //helpers
    private LocalDate[] obtenerRangoDelMes(int anio, int mes) {
        // Debe retornar { primerDiaDelMes, ultimoDiaDelMes }.
        LocalDate primerDia = LocalDate.of(anio, mes, 1);
        LocalDate ultimoDia = primerDia.withDayOfMonth(primerDia.lengthOfMonth());
        return new LocalDate[]{primerDia, ultimoDia};
    }

    private List<Marca> obtenerMarcasDelMes(LocalDate desde, LocalDate hasta) {
        // Trae todas las marcas cuyo fecha_hora cae entre desde y hasta.
        // Ver nota sobre Marca.findByRangoFechas en la respuesta.
        Query qry = em.createNamedQuery("Marca.findByRangoFechas", Marca.class);
        qry.setParameter("desde", desde.atStartOfDay());
        qry.setParameter("hasta", hasta.atTime(23, 59, 59));
        return (List<Marca>) qry.getResultList();
    }

    private ResumenDetallePlanillaDTO calcularDetalleEmpleado(Empleado empleado, List<Marca> marcasEmpleado) {
        // Debe construir las jornadas del empleado (misma lógica que
        // MarcaService.construirJornadas), sumar horas ordinarias/extras/dobles/nocturnas,
        // y calcular el total a pagar en función de salario_hora.
        List<Marca> ordenadas = marcasEmpleado.stream()
            .sorted(Comparator.comparing(Marca::getFechaHora))
            .toList();
    List<JornadaPOJO> jornadas = construirJornadas(ordenadas); // helper privado igual al de MarcaService

    double horasOrdinarias = 0, horasExtras = 0, horasDobles = 0, horasNocturnas = 0;

    for (JornadaPOJO jornada : jornadas) {
        if (!jornada.isCompleta()) continue;

        double horas = jornada.getHorasTrabajadas().toMinutes() / 60.0;

        if (jornada.isEsDiaLibre()) {
            horasDobles += horas;
        } else if (horas <= 8) {
            horasOrdinarias += horas;
        } else {
            horasOrdinarias += 8;
            horasExtras += horas - 8;
        }
        horasNocturnas += calcularHorasNocturnas(jornada);
    }

        BigDecimal salario = empleado.getSalarioHora();
        BigDecimal total = salario.multiply(BigDecimal.valueOf(horasOrdinarias))
                .add(salario.multiply(BigDecimal.valueOf(horasExtras)).multiply(BigDecimal.valueOf(1.5)))
                .add(salario.multiply(BigDecimal.valueOf(horasDobles)).multiply(BigDecimal.valueOf(2.0)))
                .add(salario.multiply(BigDecimal.valueOf(horasNocturnas)).multiply(BigDecimal.valueOf(1.35)));

        ResumenDetallePlanillaDTO detalle = new ResumenDetallePlanillaDTO();
        detalle.setFolioEmpleado(empleado.getFolio());
        detalle.setNombreCompletoEmpleado(empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido());
        detalle.setSalarioHoraEmpleado(salario);
        detalle.setHorasOrdinarias(horasOrdinarias);
        detalle.setHorasExtras(horasExtras);
        detalle.setHorasDobles(horasDobles);
        detalle.setTotalHoras(horasOrdinarias + horasExtras + horasDobles);
        detalle.setTotalAPagar(total.setScale(2, RoundingMode.HALF_UP));
        
        return detalle;
    }
    
    private List<JornadaPOJO> construirJornadas(List<Marca> marcasOrdenadas) {
        List<JornadaPOJO> jornadas = new ArrayList<>();
        if (marcasOrdenadas == null || marcasOrdenadas.isEmpty()) {
            return jornadas;
        }

        int i = 0;
        while (i < marcasOrdenadas.size()) {
            Marca actual = marcasOrdenadas.get(i);

            if (actual.getTipo().equalsIgnoreCase("E")) {
                Marca siguiente = (i + 1 < marcasOrdenadas.size()) ? marcasOrdenadas.get(i + 1) : null;
                if (siguiente != null && siguiente.getTipo().equalsIgnoreCase("S")) {
                    jornadas.add(emparejarEntradaSalida(actual, siguiente));
                    i += 2;
                } else {
                    jornadas.add(emparejarEntradaSalida(actual, null));
                    i++;
                }
            } else {
                jornadas.add(emparejarEntradaSalida(null, actual));
                i++;
            }
        }
        return jornadas;
    }
    
    private JornadaPOJO emparejarEntradaSalida(Marca entrada, Marca salida) {
        Empleado empleado = (entrada != null) ? entrada.getEmpleado() : salida.getEmpleado();
        LocalDate fecha = (entrada != null)
                ? entrada.getFechaHora().toLocalDate()
                : salida.getFechaHora().toLocalDate();
        Duration horas = (entrada != null && salida != null)
                ? Duration.between(entrada.getFechaHora(), salida.getFechaHora())
                : null;
        Boolean esDiaLibre = fecha.getDayOfWeek() == DayOfWeek.SUNDAY;
        return new JornadaPOJO(empleado, fecha, entrada, salida, horas, esDiaLibre);
    }
    
    private double calcularHorasNocturnas(JornadaPOJO jornada) {
        LocalDateTime entrada = jornada.getEntrada().getFechaHora();
        LocalDateTime salida = jornada.getSalida().getFechaHora();
        // cuenta minutos entre entrada-salida que caen entre 22:00 y 06:00
        double minutos = 0;
        LocalDateTime cursor = entrada;
        while (cursor.isBefore(salida)) {
            int hora = cursor.getHour();
            if (hora >= 22 || hora < 6) minutos++;
            cursor = cursor.plusMinutes(1);
        }
        return minutos / 60.0;
    }

    private ResumenPlanillaDTO construirResumenGeneral(int anio, int mes, List<ResumenDetallePlanillaDTO> detalles) {
        // Debe sumar cantEmpleados, cantHorasOrdinarias, cantHorasExtras, cantHorasNocturnas
        // y totalAPagar a partir de la lista de detalles por empleado, y fijar anio/mes.
        return null;
    }

    private boolean mesHaFinalizado(int anio, int mes) {
        // true solo si el último día de ese mes/año ya pasó respecto a "hoy".
        return false;
    }

    private boolean existenInconsistenciasEnElMes(LocalDate desde, LocalDate hasta) {
        // Puede apoyarse en marcaService.obtenerMarcasInconsistentes(desde, hasta, null)
        // y revisar si la lista resultante viene vacía o no.
        return false;
    }

    private boolean existePlanillaEnMesAnio(int anio, int mes) {
        // Debe consultar si ya existe una Planilla para ese mes/año.
        // Ver Named Query sugerida: Planilla.findByMesAnio.
        return false;
    }

    private Planilla crearEntidadPlanilla(ResumenPlanillaDTO resumenPlanilla) {
        // Mapea ResumenPlanillaDTO -> entidad Planilla
        // (mes = resumenPlanilla.getMes(), anio = resumenPlanilla.getAnio(),
        //  fecha_generacion = hoy, total_pagado = resumenPlanilla.getTotalAPagar()).
        return null;
    }

    private DetallePlanilla crearEntidadDetallePlanilla(ResumenDetallePlanillaDTO detalleDTO, Planilla planilla) {
        // Mapea ResumenDetallePlanillaDTO -> entidad Detalle_Planilla, incluyendo
        // el nuevo total de horas nocturnas. Debe resolver el Empleado por folio
        // (detalleDTO.getFolioEmpleado()) y asociarlo junto con la Planilla recién creada.
        return null;
    }
}
