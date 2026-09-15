package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.MarcaDTO;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import cr.ac.una.relojuna.ws.SOAPResponse;
import java.time.LocalDate;

public class MarcaService {

    private final RelojUNASOAP port;

    public MarcaService() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        this.port = service.getRelojUNASOAPPort();
    }

    public Respuesta registrarMarca(String folio) {
        SOAPResponse resultado = port.registrarMarca(folio);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Marca", resultado.getResultado());
    }

    public Respuesta obtenerPorFechas(LocalDate desde, LocalDate hasta, String folio) {
        SOAPResponse resultado = port.obtenerPorFechas(desde.toString(), hasta.toString(), folio);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Marcas", resultado.getResultado());

    }

    public Respuesta guardarMarca(MarcaDTO dto) {
        SOAPResponse resultado = port.guardarMarca(dto);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Marca", resultado.getResultado());
    }

    public Respuesta eliminarMarca(Long id) {
        SOAPResponse resultado = port.eliminarMarca(id);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Marca", resultado.getResultado());
    }

    public Respuesta obtenerMarcasInconsistentes(LocalDate desde, LocalDate hasta) {
        SOAPResponse resultado = port.obtenerMarcasInconsistentes(desde.toString(), hasta.toString());
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Marcas", resultado.getResultado());
    }

    public Respuesta obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        SOAPResponse resultado = port.obtenerJornadas(desde.toString(), hasta.toString(), folioEmpleado);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Jornadas", resultado.getResultado());
    }
}
