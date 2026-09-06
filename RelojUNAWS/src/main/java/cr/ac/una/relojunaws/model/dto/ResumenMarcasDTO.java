package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement(name = "resumenMarcas")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResumenMarcasDTO", propOrder = {
    "cantidadEmpleados", "totalMarcas", "totalHorasTrabajadas", "totalMinutosTrabajados"
})
public class ResumenMarcasDTO implements Serializable {

    @XmlElement(name = "cantidadEmpleados")
    private Long cantidadEmpleados;

    @XmlElement(name = "totalMarcas")
    private Long totalMarcas;

    @XmlElement(name = "totalHorasTrabajadas")
    private Long totalHorasTrabajadas;

    @XmlElement(name = "totalMinutosTrabajados")
    private Integer totalMinutosTrabajados;

    public ResumenMarcasDTO() {
    }

    public ResumenMarcasDTO(Long cantidadEmpleados, Long totalMarcas, Long totalHorasTrabajadas, Integer totalMinutosTrabajados) {
        this.cantidadEmpleados = cantidadEmpleados;
        this.totalMarcas = totalMarcas;
        this.totalHorasTrabajadas = totalHorasTrabajadas;
        this.totalMinutosTrabajados = totalMinutosTrabajados;
    }

    public Long getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public void setCantidadEmpleados(Long cantidadEmpleados) {
        this.cantidadEmpleados = cantidadEmpleados;
    }

    public Long getTotalMarcas() {
        return totalMarcas;
    }

    public void setTotalMarcas(Long totalMarcas) {
        this.totalMarcas = totalMarcas;
    }

    public Long getTotalHorasTrabajadas() {
        return totalHorasTrabajadas;
    }

    public void setTotalHorasTrabajadas(Long totalHorasTrabajadas) {
        this.totalHorasTrabajadas = totalHorasTrabajadas;
    }

    public Integer getTotalMinutosTrabajados() {
        return totalMinutosTrabajados;
    }

    public void setTotalMinutosTrabajados(Integer totalMinutosTrabajados) {
        this.totalMinutosTrabajados = totalMinutosTrabajados;
    }

    @Override
    public String toString() {
        return "ResumenMarcasDTO{" + "cantidadEmpleados=" + cantidadEmpleados
                + ", totalMarcas=" + totalMarcas
                + ", totalHorasTrabajadas=" + totalHorasTrabajadas
                + "h " + totalMinutosTrabajados + "m}";
    }
}
