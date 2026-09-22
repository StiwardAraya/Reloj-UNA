package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "resumenDetallesEmpleado")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResumenDetallesEmpleadoDTO", propOrder = {"empleadoDto", "resumenDetalleDto", "jornadas"})
public class ResumenDetallesEmpleadoDTO {

    @XmlElement(name = "empleadoDto")
    private EmpleadoDTO empleadoDto;

    @XmlElement(name = "resumenDetalleDto")
    private ResumenDetallePlanillaDTO resumenDetalleDto;

    @XmlElement(name = "jornadas")
    private List<ResumenJornadaDTO> jornadas;

    public ResumenDetallesEmpleadoDTO() {
    }

    public ResumenDetallesEmpleadoDTO(EmpleadoDTO empleadoDto, ResumenDetallePlanillaDTO resumenDetalleDto, List<ResumenJornadaDTO> jornadas) {
        this.empleadoDto = empleadoDto;
        this.resumenDetalleDto = resumenDetalleDto;
        this.jornadas = jornadas;
    }

    public EmpleadoDTO getEmpleadoDto() {
        return empleadoDto;
    }

    public void setEmpleadoDto(EmpleadoDTO empleadoDto) {
        this.empleadoDto = empleadoDto;
    }

    public ResumenDetallePlanillaDTO getResumenDetalleDto() {
        return resumenDetalleDto;
    }

    public void setResumenDetalleDto(ResumenDetallePlanillaDTO resumenDetalleDto) {
        this.resumenDetalleDto = resumenDetalleDto;
    }

    public List<ResumenJornadaDTO> getJornadas() {
        return jornadas;
    }

    public void setJornadas(List<ResumenJornadaDTO> jornadas) {
        this.jornadas = jornadas;
    }

}
