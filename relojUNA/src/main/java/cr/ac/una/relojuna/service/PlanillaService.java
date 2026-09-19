package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import cr.ac.una.relojuna.ws.ResumenPlanillaDTO;
import cr.ac.una.relojuna.ws.SOAPResponse;

public class PlanillaService {

    private final RelojUNASOAP port;

    public PlanillaService() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        this.port = service.getRelojUNASOAPPort();
    }

    public Respuesta calcularPlanilla(int anio, int mes) {
        SOAPResponse resultado = port.calcularPlanilla(anio, mes);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "ResumenPlanilla", resultado.getResultado());
    }

    public Respuesta generarPlanilla(ResumenPlanillaDTO resumen) {
        SOAPResponse resultado = port.generarPlanilla(resumen);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "ResumenPlanilla", resultado.getResultado());
    }
    
}
