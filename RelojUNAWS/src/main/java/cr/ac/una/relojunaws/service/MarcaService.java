package cr.ac.una.relojunaws.service;

import cr.ac.una.relojunaws.model.Empleado;
import cr.ac.una.relojunaws.model.JornadaPOJO;
import cr.ac.una.relojunaws.model.Marca;
import cr.ac.una.relojunaws.model.dto.JornadaDTO;
import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
import cr.ac.una.relojunaws.model.dto.MarcaDTO;
import cr.ac.una.relojunaws.model.dto.MarcaListDTO;
import cr.ac.una.relojunaws.model.dto.ResumenMarcasDTO;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
            if (folio.isBlank()) {
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
            //return new Respuesta(true, "marca.registrar.exito", "Marca registrada");
            
            // con esto el controlador puede devolver la marca, recien registrada mediante getResultado("Marca")
            return new Respuesta(true,"marca.registrar.exito","Marca registrada", "Marca",new MarcaDTO(marca));        
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
        try {
            if (desde == null || hasta == null) {
                return new Respuesta(false, "marca.obtener.fechasrequeridas", "obtenerPorFechas fechas nulas");
            }
            if (hasta.isBefore(desde)) {
                return new Respuesta(false, "marca.obtener.rangoinvalido", "obtenerPorFechas hasta anterior a desde");
            }

            Query qry = em.createNamedQuery("Marca.findAll", Marca.class);
            List<Marca> todasLasMarcas = (List<Marca>) qry.getResultList();

            List<MarcaDTO> marcasDTO = todasLasMarcas.stream()
                    .filter(marcaEnRango(desde, hasta))
                    .sorted(Comparator.comparing(Marca::getFechaHora))
                    .map(MarcaDTO::new)
                    .toList();

            MarcaListDTO dtoList = new MarcaListDTO(marcasDTO);
            return new Respuesta(true, "", "", dtoList);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en obtenerPorFechas", ex);
            return new Respuesta(false, "marca.obtener.error", "obtenerPorFechas Exception " + ex.getMessage());
        }
    }

    public Respuesta guardarMarca(MarcaDTO dto) {
        try {
            if (dto.getFolioEmpleado() == null) {
                return new Respuesta(false, "marca.guardar.idempleadorequerido", "guardarMarca sin folio");
            }

            if (dto.getTipo() == null || (!dto.getTipo().equals("E") && !dto.getTipo().equals("S"))) {
                return new Respuesta(false, "marca.guardar.tipoinvalido", "guardarMarca tipo invalido: " + dto.getTipo());
            }

            Query qry = em.createNamedQuery("Empleado.findByFolio", Empleado.class);
            qry.setParameter("folio", dto.getFolioEmpleado());
            Empleado empleado = (Empleado) qry.getSingleResult();

            Marca marca;
            if (dto.getId() != null && dto.getId() > 0) {
                marca = em.find(Marca.class, dto.getId());
                if (marca == null) {
                    return new Respuesta(false, "marca.guardar.notfound", "guardarMarca NoResultException");
                }
                marca.actualizar(dto);
                marca.setEmpleado(empleado);
                em.merge(marca);
            } else {
                marca = new Marca(dto);
                marca.setEmpleado(empleado);
                em.persist(marca);
            }
            em.flush();
            return new Respuesta(true, "marca.guardar.exito", "", "Marca", new MarcaDTO(marca));
        } catch (NoResultException ex) {
            return new Respuesta(false, "marca.guardar.empleadonotfound", "guardarMarca folio no encontrado: " + dto.getFolioEmpleado());
        } catch (jakarta.persistence.OptimisticLockException ex) {
            return new Respuesta(false, "marca.guardar.conflicto", "guardarMarca OptimisticLockException " + ex.getMessage());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en guardarMarca", ex);
            return new Respuesta(false, "marca.guardar.error", "guardarMarca Exception " + ex.getMessage());
        }
    }

    public Respuesta eliminarMarca(Long id) {
        try {
            if (id == null || id <= 0) {
                return new Respuesta(false, "marca.eliminar.nullid", "eliminarMarca id invalido");
            }

            Marca marca = em.find(Marca.class, id);
            if (marca == null) {
                return new Respuesta(false, "marca.eliminar.notfound", "eliminarMarca NoResultException");
            }

            em.remove(marca);
            em.flush();
            return new Respuesta(true, "marca.eliminar.exito", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en eliminarMarca", ex);
            return new Respuesta(false, "marca.eliminar.error", "eliminarMarca Exception " + ex.getMessage());
        }
    }

    public Respuesta obtenerMarcasInconsistentes(LocalDate desde, LocalDate hasta) {
        try {
            if (desde == null || hasta == null) {
                return new Respuesta(false, "marca.obtener.fechasrequeridas", "obtenerMarcasInconsistentes fechas nulas");
            }
            if (hasta.isBefore(desde)) {
                return new Respuesta(false, "marca.obtener.rangoinvalido", "obtenerMarcasInconsistentes hasta anterior a desde");
            }

            Query qry = em.createNamedQuery("Marca.findAll", Marca.class);
            List<Marca> todasLasMarcas = (List<Marca>) qry.getResultList();

            Map<Empleado, List<Marca>> porEmpleado = todasLasMarcas.stream()
                    .collect(Collectors.groupingBy(Marca::getEmpleado));

            List<Marca> inconsistentes = porEmpleado.values().stream()
                    .flatMap(marcasDelEmpleado -> {
                        List<Marca> ordenadas = marcasDelEmpleado.stream()
                                .sorted(Comparator.comparing(Marca::getFechaHora))
                                .toList();
                        return marcarInconsistentesDeUnEmpleado(ordenadas).stream();
                    })
                    .filter(marcaEnRango(desde, hasta))
                    .sorted(Comparator.comparing(Marca::getFechaHora))
                    .toList();

            List<MarcaDTO> inconsistentesDTO = inconsistentes.stream()
                    .map(MarcaDTO::new)
                    .toList();

            MarcaListDTO dtoList = new MarcaListDTO(inconsistentesDTO);
            return new Respuesta(true, "", "", dtoList);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en obtenerMarcasInconsistentes", ex);
            return new Respuesta(false, "marca.inconsistentes.error", "obtenerMarcasInconsistentes Exception " + ex.getMessage());
        }
    }

    public Respuesta consultarResumen(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            if (desde == null || hasta == null) {
                return new Respuesta(false, "marca.obtener.fechasrequeridas", "obtenerMarcasInconsistentes fechas nulas");
            }
            if (hasta.isBefore(desde)) {
                return new Respuesta(false, "marca.obtener.rangoinvalido", "obtenerMarcasInconsistentes hasta anterior a desde");
            }

            Query qry = em.createNamedQuery("Marca.findAll", Marca.class);
            List<Marca> todasLasMarcas = qry.getResultList();

            List<Marca> marcasFiltradas = todasLasMarcas.stream()
                    .filter(marcaEnRango(desde, hasta))
                    //.filter(m -> folioEmpleado == null || m.getEmpleado().getFolio().equalsIgnoreCase(folioEmpleado))
                    .filter(m -> folioEmpleado == null || folioEmpleado.isBlank()|| m.getEmpleado().getFolio().equalsIgnoreCase(folioEmpleado.trim()))
                    .toList();

            Long cantidadEmpleados = contarEmpleadosDistintos(marcasFiltradas);
            //Long totalMarcas = (long) marcasFiltradas.size();
            Long totalMarcas = marcasFiltradas.stream().count();//conteo mediante streams, tal como pide el punto 7

            Map<Empleado, List<Marca>> porEmpleado = marcasFiltradas.stream()
                    .collect(Collectors.groupingBy(Marca::getEmpleado));

            List<JornadaPOJO> jornadas = porEmpleado.values().stream()
                    .flatMap(marcasDelEmpleado -> {
                        List<Marca> ordenadas = marcasDelEmpleado.stream()
                                .sorted(Comparator.comparing(Marca::getFechaHora))
                                .toList();
                        return construirJornadas(ordenadas).stream();
                    })
                    .toList();

            Duration totalHorasTrabajadas = sumarHorasTrabajadas(jornadas);

            ResumenMarcasDTO resumen = new ResumenMarcasDTO(
                    cantidadEmpleados,
                    totalMarcas,
                    totalHorasTrabajadas.toHours(),
                    totalHorasTrabajadas.toMinutesPart()
            );

            return new Respuesta(true, "", "", "ResumenMarcas", resumen);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en consultarResumen", ex);
            return new Respuesta(false, "marca.resumen.error", "consultarResumen Exception " + ex.getMessage());
        }
    }

    public Respuesta obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            if (desde == null || hasta == null) {
                return new Respuesta(false, "marca.obtener.fechasrequeridas", "obtenerMarcasInconsistentes fechas nulas");
            }
            if (hasta.isBefore(desde)) {
                return new Respuesta(false, "marca.obtener.rangoinvalido", "obtenerMarcasInconsistentes hasta anterior a desde");
            }

            Query qry = em.createNamedQuery("Marca.findAll", Marca.class);
            List<Marca> todasLasMarcas = qry.getResultList();

            List<Marca> marcasFiltradas = todasLasMarcas.stream()
                    .filter(marcaEnRango(desde, hasta))
                    //.filter(m -> folioEmpleado.isBlank() || m.getEmpleado().getFolio().equals(folioEmpleado))
                    .filter(m -> folioEmpleado == null || folioEmpleado.isBlank()|| m.getEmpleado().getFolio().equalsIgnoreCase(folioEmpleado.trim()))
                    .toList();

            Map<Empleado, List<Marca>> porEmpleado = marcasFiltradas.stream()
                    .collect(Collectors.groupingBy(Marca::getEmpleado));

            List<JornadaDTO> jornadasDTO = porEmpleado.values().stream()
                    .flatMap(marcasDelEmpleado -> {
                        List<Marca> ordenadas = marcasDelEmpleado.stream()
                                .sorted(Comparator.comparing(Marca::getFechaHora))
                                .toList();
                        return construirJornadas(ordenadas).stream();
                    })
                    .sorted(Comparator.comparing(JornadaPOJO::getFecha))
                    .map(JornadaDTO::new)
                    .toList();

            JornadaListDTO dtoList = new JornadaListDTO(jornadasDTO);
            return new Respuesta(true, "", "", dtoList);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en obtenerJornadas", ex);
            return new Respuesta(false, "marca.jornadas.error", "obtenerJornadas Exception " + ex.getMessage());
        }
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

    private List<Marca> marcarInconsistentesDeUnEmpleado(List<Marca> marcasOrdenadas) {
        List<Marca> resultado = new ArrayList<>();
        for (int i = 0; i < marcasOrdenadas.size(); i++) {
            Marca actual = marcasOrdenadas.get(i);
            Marca anterior = (i > 0) ? marcasOrdenadas.get(i - 1) : null;
            if (esMarcaInconsistente(actual, anterior)) {
                resultado.add(actual);
            }
        }
        return resultado;
    }

    private Predicate<Marca> marcaEnRango(LocalDate desde, LocalDate hasta) {
        return marca -> {
            LocalDate fecha = marca.getFechaHora().toLocalDate();
            return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
        };
    }
}
