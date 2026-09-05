package cr.ac.una.relojunaws.service;

import cr.ac.una.relojunaws.model.Empleado;
import cr.ac.una.relojunaws.model.JornadaPOJO;
import cr.ac.una.relojunaws.model.Marca;
import cr.ac.una.relojunaws.model.dto.MarcaDTO;
import cr.ac.una.relojunaws.util.Respuesta;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class MarcaService {

    @PersistenceContext(unitName = "WSRelojUNAPU")
    private EntityManager em;

    private static final long HORAS_LIMITE_JORNADA = 16;
    private static final long SEGUNDOS_MINIMOS_ENTRE_MARCAS = 5;

    private static final Logger LOG = Logger.getLogger(MarcaService.class.getName());

    public Respuesta registrarMarca(String folio) {
        try {
            if (folio.isBlank() || folio.isEmpty()) {
                return new Respuesta(false, "marca.registrar.idrequerido", "registrarMarca sin folio");
            }

            Query qry = em.createNamedQuery("Empleado.findByFolio", Empleado.class);
            qry.setParameter("folio", folio);
            Empleado empleado = (Empleado) qry.getSingleResult();
            validarEmpleadoActivo(empleado);

            LocalDateTime momento = LocalDateTime.now();
            validarNoMarcaDuplicadaInmediata(empleado, momento);

            String tipo = determinarTipoMarca(empleado, momento);
            Marca marca = new Marca();
            marca.setEmpleado(empleado);
            marca.setFechaHora(momento);
            marca.setTipo(tipo);

            em.persist(marca);
            em.flush();
            return new Respuesta(true, "marca.registrar.exito", "Marca registrada");
        } catch (IllegalArgumentException ex) {
            return new Respuesta(false, "marca.registrar.notfound", "registrarMarca " + ex.getMessage());
        } catch (IllegalStateException ex) {
            return new Respuesta(false, "marca.registrar.invalido", "registrarMarca " + ex.getMessage());
        } catch (NoResultException ex) {
            return new Respuesta(false, "marca.registrar.notfound", "registrarMarca folio no encontrado: " + folio);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en registrarMarca", ex);
            return new Respuesta(false, "marca.registrar.error", "registrarMarca Exception " + ex.getMessage());
        }
    }

    public Respuesta obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        // TODO: Implementar metodo publico
        return null;
    }

    public Respuesta guardarMarca(MarcaDTO dto) {
        // TODO: Implementar metodo publico
        return null;
    }

    public Respuesta eliminarMarca(Long id) {
        // TODO: Implementar metodo publico
        return null;
    }

    public Respuesta obtenerMarcasInconsistentes(LocalDate desde, LocalDate hasta) {
        // TODO: Implementar metodo publico
        return null;
    }

    public Respuesta consultarResumen(LocalDate desde, LocalDate hasta, Long idEmpleado) {
        // TODO: Implementar metodo publico
        return null;
    }

    public Respuesta obtenerJornadas(LocalDate desde, LocalDate hasta, Long idEmpleado) {
        // TODO: Implementar metodo publico
        return null;
    }

    // Helpers
    private Marca obtenerUltimaMarca(String folio) {
        try {
            Marca marca;
            Query qry = em.createNamedQuery("Marca.findByEmpleado", Marca.class);
            qry.setParameter("folio", folio);
            marca = (Marca) qry.setMaxResults(1).getSingleResult();
            return marca;
        } catch (Exception ex) {
            return null;
        }
    }

    private String determinarTipoMarca(Empleado empleado, LocalDateTime momento) {
        Marca ultimaMarca = obtenerUltimaMarca(empleado.getFolio());
        if (ultimaMarca == null) {
            return "E";
        }

        if (ultimaMarca.getTipo().equalsIgnoreCase("S")) {
            return "E";
        }

        Long horasTranscurridas = Duration.between(ultimaMarca.getFechaHora(), momento).toHours();
        return horasTranscurridas > HORAS_LIMITE_JORNADA ? "E" : "S";
    }

    private void validarEmpleadoActivo(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no existe.");
        }
        if (!empleado.getActivo().equalsIgnoreCase("A")) {
            throw new IllegalStateException("El empleado se encuentra inactivo y no puede registrar marcas.");
        }
    }

    private void validarNoMarcaDuplicadaInmediata(Empleado empleado, LocalDateTime momento) {
        Marca ultimaMarca = obtenerUltimaMarca(empleado.getFolio());
        if (ultimaMarca == null) {
            return;
        }
        Long segundosTranscurridos = Duration.between(ultimaMarca.getFechaHora(), momento).toSeconds();
        if (segundosTranscurridos < SEGUNDOS_MINIMOS_ENTRE_MARCAS) {
            throw new IllegalStateException("Ya se registró una marca hace instantes. Evite marcar varias veces seguidas.");
        }
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

    private Boolean esMarcaInconsistente(Marca actual, Marca anterior) {
        if (anterior == null) {
            return actual.getTipo().equalsIgnoreCase("S");
        }
        return anterior.getTipo().equalsIgnoreCase(actual.getTipo());
    }

    private Long contarEmpleadosDistintos(List<Marca> marcas) {
        return marcas.stream()
                .map(Marca::getEmpleado)
                .distinct()
                .count();
    }

    private Duration sumarHorasTrabajadas(List<JornadaPOJO> jornadas) {
        return jornadas.stream()
                .filter(JornadaPOJO::isCompleta)
                .map(JornadaPOJO::getHorasTrabajadas)
                .reduce(Duration.ZERO, Duration::plus);
    }
}
