package cr.ac.una.relojunaws.service;

import cr.ac.una.relojunaws.model.Empleado;
import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import cr.ac.una.relojunaws.model.dto.EmpleadoListDTO;
import cr.ac.una.relojunaws.util.Respuesta;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class EmpleadoService {

    @PersistenceContext(unitName = "WSRelojUNAPU")
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(EmpleadoService.class.getName());

    public Respuesta autenticarEmpleado(String folio, String clave) {
        try {
            Query qry = em.createNamedQuery("Empleado.authenticate", Empleado.class);
            qry.setParameter("folio", folio);
            qry.setParameter("clave", clave);
            Empleado empleado = (Empleado) qry.getSingleResult();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDTO(empleado));
        } catch (NoResultException ex) {
            return new Respuesta(false, "login.auth.notfound", "Empleado.authenticate NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Resultado no único en Empleado.authenticate", ex);
            return new Respuesta(false, "login.auth.multiple", "Empleado.authenticate NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en Empleado.authenticate", ex);
            return new Respuesta(false, "login.auth.error", "Empleado.authenticate Exception");
        }
    }

    public Respuesta getEmpleado(Long id) {
        try {
            Query qry = em.createNamedQuery("Empleado.findById", Empleado.class);
            qry.setParameter("id", id);
            Empleado empleado = (Empleado) qry.getSingleResult();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDTO(empleado));
        } catch (NoResultException ex) {
            return new Respuesta(false, "empleados.get.notfound", "Empleado.getEmpleado NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Resultado no único en Empleado.getEmpleado", ex);
            return new Respuesta(false, "empleados.get.multiple", "Empleado.getEmpleado NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en Empleado.getEmpleado", ex);
            return new Respuesta(false, "empleados.get.error", "Empleado.getEmpleado Exception");
        }
    }

    public Respuesta getEmpleadoIdFolio(Long id, String folio) {
        try {
            Query qry = em.createNamedQuery("Empleado.findByIdFolio", Empleado.class);
            qry.setParameter("id", id);
            qry.setParameter("folio", folio);
            Empleado empleado = (Empleado) qry.getSingleResult();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDTO(empleado));
        } catch (NoResultException ex) {
            return new Respuesta(false, "empleados.get.notfound", "Empleado.getEmpleadoIdFolio NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Resultado no único en Empleado.getEmpleadoIdFolio", ex);
            return new Respuesta(false, "empleados.get.multiple", "Empleado.getEmpleadoIdFolio NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en Empleado.getEmpleadoIdFolio", ex);
            return new Respuesta(false, "empleados.get.error", "Empleado.getEmpleadoIdFolio Exception");
        }
    }

    public Respuesta getEmpleados() {
        try {
            Query qry = em.createNamedQuery("Empleado.findAll", Empleado.class);
            List<Empleado> empleados = (List<Empleado>) qry.getResultList();
            List<EmpleadoDTO> empleadosDTO = empleados.stream()
                    .map(e -> new EmpleadoDTO(e))
                    .toList();
            EmpleadoListDTO dtoList = new EmpleadoListDTO(empleadosDTO);
            return new Respuesta(true, "", "", dtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "empleados.getlist.notfound", "Empleado.getEmpleadosActivos NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en Empleado.getEmpleadosActivos", ex);
            return new Respuesta(false, "empleados.getlist.error", "Empleado.getEmpleadosActivos Exception");
        }
    }

    public Respuesta getEmpleadosByFilters(String correo, String cedula, String nombre, String primerApellido, String segundoApellido) {
        try {
            Query qry = em.createNamedQuery("Empleado.findByFilters", Empleado.class);
            qry.setParameter("correo", correo);
            qry.setParameter("cedula", cedula);
            qry.setParameter("nombre", nombre);
            qry.setParameter("primerApellido", primerApellido);
            qry.setParameter("segundoApellido", segundoApellido);
            List<Empleado> empleados = (List<Empleado>) qry.getResultList();
            List<EmpleadoDTO> empleadosDTO = empleados.stream()
                    .map(e -> new EmpleadoDTO(e))
                    .toList();
            EmpleadoListDTO dtoList = new EmpleadoListDTO(empleadosDTO);
            return new Respuesta(true, "", "", dtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "empleados.getlist.notfound", "Empleado.getEmpleadosByFilters NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en Empleado.getEmpleadosActivos", ex);
            return new Respuesta(false, "empleados.getlist.error", "Empleado.getEmpleadosByFilters Exception");
        }
    }

    public Respuesta getEmpleadosActivos() {
        try {
            Query qry = em.createNamedQuery("Empleado.findAll", Empleado.class);
            List<Empleado> empleados = (List<Empleado>) qry.getResultList();
            List<EmpleadoDTO> empleadosDTO = empleados.stream()
                    .filter(e -> e.getActivo().equals("A"))
                    .map(e -> new EmpleadoDTO(e))
                    .toList();
            EmpleadoListDTO dtoList = new EmpleadoListDTO(empleadosDTO);
            return new Respuesta(true, "", "", dtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "empleados.getlist.notfound", "Empleado.getEmpleadosActivos NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error en Empleado.getEmpleadosActivos", ex);
            return new Respuesta(false, "empleados.getlist.error", "Empleado.getEmpleadosActivos Exception");
        }
    }

    public Respuesta guardarEmpleado(EmpleadoDTO dto) {
        try {
            Empleado empleado;
            if (dto.getId() != null && dto.getId() > 0) {
                empleado = em.find(Empleado.class, dto.getId());
                if (empleado == null) {
                    return new Respuesta(false, "empleados.update.notfound", "guardarEmpleado NoResultException");
                }
                empleado.actualizar(dto);
                em.merge(empleado);
            } else {
                String folioGenerado = generarFolio(dto.getPrimerApellido());
                dto.setFolio(folioGenerado);
                empleado = new Empleado(dto);
                em.persist(empleado);
            }
            em.flush();
            return new Respuesta(true, "empleado.guardar.exito", "", "Empleado", new EmpleadoDTO(empleado));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al guardar " + dto.getId(), ex);
            return new Respuesta(false, "empleados.guardar.error", "Empleado.guardarEmpleado Exception" + ex.getMessage());
        }
    }

    public Respuesta eliminarEmpleado(Long id) {
        try {
            if (id == null || id <= 0) {
                return new Respuesta(false, "empleados.delete.nullid", "eliminarEmpleado NoResultException");
            }
            Empleado empleado = em.find(Empleado.class, id);
            if (empleado == null) {
                return new Respuesta(false, "empleados.delete.notfound", "eliminarEmpleado NoResultException");
            }
            try {
                em.remove(empleado);
                em.flush();
                return new Respuesta(true, "empleado.eliminar.exito", "");
            } catch (Exception ex) {
                if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                    em.clear();
                    Empleado empleadoDesactivar = em.find(Empleado.class, id);
                    empleadoDesactivar.setActivo("I");
                    em.merge(empleadoDesactivar);
                    em.flush();
                    return new Respuesta(true, "empleado.eliminar.desactivado",
                            "El empleado tiene relaciones con otros registros, se desactivó en su lugar.");
                }
                throw ex;
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el empleado.", ex);
            return new Respuesta(false, "empleados.delete.error", "eliminarEmpleado " + ex.getMessage());
        }
    }

    //Helpr
    private String generarFolio(String primerApellido) {
        int maxNumero = 0;
        String letra = primerApellido.trim().substring(0, 1).toUpperCase();
        try {
            Query qry = em.createNamedQuery("Empleado.findFolios", Empleado.class);
            List<String> folios = qry.getResultList();
            maxNumero = folios.stream()
                    .filter(folio -> folio != null && folio.length() >= 6)
                    .mapToInt(this::parseNumeroFolio)
                    .max()
                    .orElse(0);
        } catch (PersistenceException ex) {
            LOG.log(Level.SEVERE, "Error al consultar folios existentes", ex);
            throw new RuntimeException("No se pudo generar el folio: error al consultar folios existentes", ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error inesperado al generar folio", ex);
            throw new RuntimeException("No se pudo generar el folio", ex);
        }
        int siguienteNumero = maxNumero + 1;
        return letra + String.format("%05d", siguienteNumero);
    }

    private int parseNumeroFolio(String folio) {
        try {
            return Integer.parseInt(folio.substring(1));
        } catch (NumberFormatException ex) {
            LOG.log(Level.WARNING, "Folio con formato inesperado, se ignora: " + folio, ex);
            return 0;
        }
    }
}
