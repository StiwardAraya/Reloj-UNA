/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
import cr.ac.una.relojunaws.model.dto.MarcaDTO;
import cr.ac.una.relojunaws.model.dto.MarcaListDTO;
import cr.ac.una.relojunaws.model.dto.ResumenMarcasDTO;
import cr.ac.una.relojunaws.service.MarcaService;
import cr.ac.una.relojunaws.util.SOAPResponse;
import jakarta.ejb.EJB;
import jakarta.jws.WebService;
import java.time.LocalDate;

/**
 *
 * @author Tames
 */
@WebService(
        endpointInterface = "cr.ac.una.relojunaws.controller.MarcaSOAP",
        serviceName = "MarcaSOAP",
        targetNamespace = "http://controller.una.ac.cr/marcas"
)
public class MarcaController implements MarcaSOAP {

    @EJB
    private MarcaService marceService;

    @Override
    public SOAPResponse<MarcaDTO> registrarMarca(String folio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SOAPResponse<MarcaListDTO> obtenerPorFechas(LocalDate desde, LocalDate hasta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SOAPResponse<MarcaDTO> guardarMarca(MarcaDTO marca) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SOAPResponse<MarcaDTO> eliminarMarca(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SOAPResponse<MarcaListDTO> obtenerMarcasInconsistentes(LocalDate desde, LocalDate hasta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SOAPResponse<ResumenMarcasDTO> consultarResumen(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SOAPResponse<JornadaListDTO> obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
