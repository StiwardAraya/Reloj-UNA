package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import cr.ac.una.relojunaws.model.dto.EmpleadoListDTO;
import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
import cr.ac.una.relojunaws.model.dto.LoginRequestDTO;
import cr.ac.una.relojunaws.model.dto.MarcaDTO;
import cr.ac.una.relojunaws.model.dto.MarcaListDTO;
import cr.ac.una.relojunaws.model.dto.ResumenMarcasDTO;
import cr.ac.una.relojunaws.service.EmpleadoService;
import cr.ac.una.relojunaws.service.MarcaService;
import cr.ac.una.relojunaws.util.Respuesta;
import cr.ac.una.relojunaws.util.SOAPResponse;
import jakarta.ejb.EJB;
import jakarta.jws.WebService;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.relojunaws.model.dto.ArchivoDTO;
import cr.ac.una.relojunaws.service.ReporteService;
import cr.ac.una.relojunaws.util.ArchivoResponse;


@WebService(
        endpointInterface = "cr.ac.una.relojunaws.controller.RelojUNASOAP",
        serviceName = "RelojUNASOAPService",
        portName = "RelojUNASOAPPort",
        targetNamespace = "http://controller.una.ac.cr/relojuna"
)
public class RelojUNAController implements RelojUNASOAP {

    @EJB
    private EmpleadoService empleadoService;
    @EJB
    private MarcaService marcaService;
    @EJB
    private ReporteService reporteService;

    private static final Logger LOG = Logger.getLogger(RelojUNAController.class.getName());

    @Override
    public SOAPResponse<EmpleadoDTO> autenticarEmpleado(LoginRequestDTO loginRequest) {
        if (loginRequest == null || loginRequest.getFolio() == null || loginRequest.getFolio().isBlank()
                || loginRequest.getClave() == null || loginRequest.getClave().isBlank()) {
            return SOAPResponse.error("Debe indicar el folio y la clave.", "autenticarEmpleado: datos incompletos");
        }
        return ejecutar("autenticarEmpleado", () -> empleadoService.autenticarEmpleado(loginRequest.getFolio(),
                loginRequest.getClave()), respuesta -> (EmpleadoDTO) respuesta.getResultado("Empleado"));
    }

    @Override
    public SOAPResponse<EmpleadoDTO> getEmpleado(String id) {
        return ejecutar("getEmpleado", () -> empleadoService.getEmpleado(Long.valueOf(id)), respuesta -> (EmpleadoDTO) respuesta.getResultado("Empleado"));
    }

    @Override
    public SOAPResponse<EmpleadoDTO> getEmpleadoIdFolio(String id, String folio) {
        return ejecutar("getEmpleadoIdFolio", () -> {
            Long idEmpleado = (id == null || id.isBlank()) ? 0L : Long.valueOf(id);
            return empleadoService.getEmpleadoIdFolio(idEmpleado, folio);
        }, respuesta -> (EmpleadoDTO) respuesta.getResultado("Empleado"));
    }

    @Override
    public SOAPResponse<EmpleadoListDTO> getEmpleados() {
        return ejecutar("getEmpleados", () -> empleadoService.getEmpleados(), respuesta -> (EmpleadoListDTO) respuesta.getResultado());
    }

    @Override
    public SOAPResponse<EmpleadoListDTO> getEmpleadosByFilters(String correo, String cedula, String nombre, String primerApellido, String segundoApellido) {
        return ejecutar("getEmpleadosByFilters", () -> empleadoService
                .getEmpleadosByFilters(correo, cedula, nombre, primerApellido, segundoApellido), respuesta -> (EmpleadoListDTO) respuesta.getResultado());
    }

    @Override
    public SOAPResponse<EmpleadoListDTO> getEmpleadosActivos() {
        return ejecutar("getEmpleadosActivos", () -> empleadoService.getEmpleadosActivos(), respuesta -> (EmpleadoListDTO) respuesta.getResultado());
    }

    @Override
    public SOAPResponse<EmpleadoDTO> guardarEmpleado(EmpleadoDTO empleado) {
        if (empleado == null) {
            return SOAPResponse.error("Debe indicar los datos del empleado.", "guardarEmpleado: empleado nulo");
        }
        return ejecutar("guardarEmpleado", () -> empleadoService.guardarEmpleado(empleado),
                respuesta -> (EmpleadoDTO) respuesta.getResultado("Empleado"));
    }

    @Override
    public SOAPResponse<EmpleadoDTO> eliminarEmpleado(String id) {
        return ejecutar("eliminarEmpleado", () -> empleadoService.eliminarEmpleado(Long.valueOf(id)), respuesta -> null);
    }

    @Override
    public SOAPResponse<MarcaDTO> registrarMarca(String folio) {
        if (folio == null || folio.isBlank()) {
            return SOAPResponse.error("Debe indicar el folio del empleado.", "registrarMarca: folio vacío");
        }
        return ejecutar("registrarMarca", () -> marcaService.registrarMarca(folio), respuesta -> (MarcaDTO) respuesta.getResultado("Marca"));
    }

    @Override
    public SOAPResponse<MarcaListDTO> obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        return ejecutar("obtenerPorFechas", () -> marcaService.obtenerPorFechas(desde, hasta),
                respuesta -> (MarcaListDTO) respuesta.getResultado());
    }

    @Override
    public SOAPResponse<MarcaDTO> guardarMarca(MarcaDTO marca) {
        if (marca == null) {
            return SOAPResponse.error("Debe indicar los datos de la marca.", "guardarMarca: marca nula");
        }
        return ejecutar("guardarMarca", () -> marcaService.guardarMarca(marca), respuesta -> (MarcaDTO) respuesta.getResultado("Marca"));
    }

    @Override
    public SOAPResponse<MarcaDTO> eliminarMarca(Long id) {
        return ejecutar("eliminarMarca", () -> marcaService.eliminarMarca(id), respuesta -> null);
    }

    @Override
    public SOAPResponse<MarcaListDTO> obtenerMarcasInconsistentes(LocalDate desde, LocalDate hasta) {
        return ejecutar("obtenerMarcasInconsistentes", () -> marcaService.obtenerMarcasInconsistentes(desde, hasta), respuesta -> (MarcaListDTO) respuesta.getResultado());
    }

    @Override
    public SOAPResponse<ResumenMarcasDTO> consultarResumen(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        return ejecutar("consultarResumen", () -> marcaService.consultarResumen(desde, hasta, folioEmpleado),
                respuesta -> (ResumenMarcasDTO) respuesta.getResultado("ResumenMarcas")
        );
    }

    @Override
    public SOAPResponse<JornadaListDTO> obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        return ejecutar("obtenerJornadas", () -> marcaService.obtenerJornadas(desde, hasta, folioEmpleado),
                respuesta -> (JornadaListDTO) respuesta.getResultado());
    }

    private <T> SOAPResponse<T> ejecutar(String operacion, Supplier<Respuesta> llamadaServicio, Function<Respuesta, T> obtenerResultado) {
        try {
            Respuesta respuesta = llamadaServicio.get();
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito(obtenerResultado.apply(respuesta), respuesta.getMensaje(), respuesta.getMensajeInterno());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "RelojUNAController." + operacion, ex);
            return SOAPResponse.error("No se pudo completar la operación.", "RelojUNAController." + operacion + ": " + ex.getClass().getSimpleName());
        }
    }

    @Override
    public ArchivoResponse generarExcelMarcas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            Respuesta respuesta = reporteService.generarExcelMarcas(desde, hasta, folioEmpleado);
            if (!respuesta.getEstado()) {
                return ArchivoResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            ArchivoDTO archivo = (ArchivoDTO) respuesta.getResultado("Archivo");
            return ArchivoResponse.exito(archivo, respuesta.getMensaje(), respuesta.getMensajeInterno());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error en generarExcelMarcas", ex);
            return ArchivoResponse.error("reporte.excel.error", ex.getMessage());
        }
    }

    @Override
    public ArchivoResponse generarReporteEmpleados() {
        try {
            Respuesta respuesta = reporteService.generarReporteEmpleados();
            if (!respuesta.getEstado()) {
                return ArchivoResponse.error( respuesta.getMensaje(), respuesta.getMensajeInterno() );
            } 
            ArchivoDTO archivo = (ArchivoDTO) respuesta.getResultado("Archivo");
            return ArchivoResponse.exito( archivo, respuesta.getMensaje(), respuesta.getMensajeInterno() );
        } catch (Exception ex) {
            LOG.log( Level.SEVERE, "Error en generarReporteEmpleados", ex );
            return ArchivoResponse.error( "reporte.empleados.error", ex.getMessage() );
        }
    }

    @Override
    public ArchivoResponse generarReporteMarcas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try{
            Respuesta respuesta = reporteService.generarReporteMarcas(desde, hasta, folioEmpleado);
            if(!respuesta.getEstado())
                return ArchivoResponse.error(respuesta.getMensaje(),respuesta.getMensajeInterno());
            ArchivoDTO archivo = (ArchivoDTO) respuesta.getResultado("Archivo");
            return ArchivoResponse.exito( archivo, respuesta.getMensaje(), respuesta.getMensajeInterno() ); 
        }catch (Exception ex) {
            LOG.log( Level.SEVERE, "Error en generarReporteMarcas", ex );
            return ArchivoResponse.error( "reporte.marcas.error", ex.getMessage() );
        }
    }

}
