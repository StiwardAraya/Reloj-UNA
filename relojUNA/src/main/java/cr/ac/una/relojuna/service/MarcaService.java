package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.MarcaDTO;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import java.time.LocalDate;

public class MarcaService {

    private final RelojUNASOAP port;

    public MarcaService() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        this.port = service.getRelojUNASOAPPort();
    }

    public Respuesta registrarMarca(String folio) {
        // TODO: Implementar
        return null;
    }

    public Respuesta obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        // TODO: Implementar
        return null;
    }

    public Respuesta guardarMarca(MarcaDTO dto) {
        // TODO: Implementar
        return null;
    }

    public Respuesta eliminarMarca(Long id) {
        // TODO: Implementar
        return null;
    }

    public Respuesta obtenerMarcasInconsistentes(LocalDate desde, LocalDate hasta) {
        // TODO: Implementar
        return null;
    }

    public Respuesta obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        // TODO: Implementar
        return null;
    }
}
