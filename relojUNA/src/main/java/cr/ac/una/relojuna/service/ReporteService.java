/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import cr.ac.una.relojuna.ws.SOAPResponse;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tames
 */
public class ReporteService {

    private static final Logger LOG =
            Logger.getLogger(ReporteService.class.getName());

    public Respuesta consultarResumen(LocalDate desde,LocalDate hasta,String folioEmpleado) {
        Respuesta validacion = validarFechas(desde, hasta);
        if (validacion != null) {
            return validacion;
        }
        try {
            RelojUNASOAP port = crearPuerto();//aqui obtenemos al objeto,para llamar los metodos del WS desde aqui
            SOAPResponse resultado = port.consultarResumen(desde.toString(),hasta.toString(),normalizarFolio(folioEmpleado));
            if (!resultado.isExito()) {
                return new Respuesta(false,resultado.getMensajeUsuario(),resultado.getMensajeTecnico());
            }
            return new Respuesta(true,resultado.getMensajeUsuario(),resultado.getMensajeTecnico(),"ResumenMarcas",resultado.getResultado());
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al consultar el resumen", ex);
            return new Respuesta(false,"No se pudo consultar el resumen. " + "Verifique la conexión con el servidor.",ex.toString());
        }
    }


    public Respuesta obtenerJornadas(LocalDate desde,LocalDate hasta,String folioEmpleado) {
        Respuesta validacion = validarFechas(desde, hasta);
        if (validacion != null) {
            return validacion;
        }
        try {RelojUNASOAP port = crearPuerto();
            SOAPResponse resultado = port.obtenerJornadas(desde.toString(),hasta.toString(),normalizarFolio(folioEmpleado));
            if (!resultado.isExito()) {
                return new Respuesta(false,resultado.getMensajeUsuario(),resultado.getMensajeTecnico());
            }
            return new Respuesta(true,resultado.getMensajeUsuario(),resultado.getMensajeTecnico(),"Jornadas",resultado.getResultado());
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al obtener las jornadas", ex);
            return new Respuesta(false,"No se pudieron obtener las jornadas. " + "Verifique la conexión con el servidor.",ex.toString());
        }
    }


    private RelojUNASOAP crearPuerto() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        return service.getRelojUNASOAPPort();
    }


    private String normalizarFolio(String folioEmpleado) {
        return folioEmpleado == null || folioEmpleado.isBlank() ? null : folioEmpleado.trim();
    }


    private Respuesta validarFechas(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            return new Respuesta(false,"Debe seleccionar la fecha inicial y la fecha final.","ReporteService: fechas incompletas");
        }
        if (hasta.isBefore(desde)) {
            return new Respuesta(false,"La fecha final no puede ser anterior a la inicial.","ReporteService: rango de fechas inválido");
        }
        return null;
    }
}