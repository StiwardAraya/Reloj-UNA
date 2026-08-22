package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmpleadoListDTO")
public class EmpleadoListDTO implements Serializable {

    @XmlElement(name = "empleados")
    private List<EmpleadoDTO> empleados;

    public EmpleadoListDTO() {
    }

    public EmpleadoListDTO(List<EmpleadoDTO> empleados) {
        this.empleados = empleados;
    }

    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoDTO> empleados) {
        this.empleados = empleados;
    }

}
