package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement(name = "resumenPlanilla")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResumenPlanillaDTO", propOrder = {
    "cantEmpleados", "cantHorasOrdinarias", "cantHorasExtras",
    "cantHorasNocturnas", "totalAPagar", "detallesPlanilla"
})
public class ResumenPlanillaDTO {

    @XmlElement(name = "cantEmpleados")
    private Integer cantEmpleados;

    @XmlElement(name = "cantHorasOrdinarias")
    private Integer cantHorasOrdinarias;

    @XmlElement(name = "cantHorasExtras")
    private Integer cantHorasExtras;

    @XmlElement(name = "cantHorasNocturnas")
    private Integer cantHorasNocturnas;

    @XmlElement(name = "totalAPagar")
    private BigDecimal totalAPagar;

    @XmlElement(name = "detallesPlanilla")
    private List<ResumenDetallePlanillaDTO> detallesPlanilla;

    public ResumenPlanillaDTO() {
    }

    public ResumenPlanillaDTO(Integer cantEmpleados, Integer cantHorasOrdinarias, Integer cantHorasExtras, Integer cantHorasNocturnas, BigDecimal totalAPagar) {
        this.cantEmpleados = cantEmpleados;
        this.cantHorasOrdinarias = cantHorasOrdinarias;
        this.cantHorasExtras = cantHorasExtras;
        this.cantHorasNocturnas = cantHorasNocturnas;
        this.totalAPagar = totalAPagar;
    }

    public Integer getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(Integer cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    public Integer getCantHorasOrdinarias() {
        return cantHorasOrdinarias;
    }

    public void setCantHorasOrdinarias(Integer cantHorasOrdinarias) {
        this.cantHorasOrdinarias = cantHorasOrdinarias;
    }

    public Integer getCantHorasExtras() {
        return cantHorasExtras;
    }

    public void setCantHorasExtras(Integer cantHorasExtras) {
        this.cantHorasExtras = cantHorasExtras;
    }

    public Integer getCantHorasNocturnas() {
        return cantHorasNocturnas;
    }

    public void setCantHorasNocturnas(Integer cantHorasNocturnas) {
        this.cantHorasNocturnas = cantHorasNocturnas;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public List<ResumenDetallePlanillaDTO> getDetallesPlanilla() {
        return detallesPlanilla;
    }

    public void setDetallesPlanilla(List<ResumenDetallePlanillaDTO> detallesPlanilla) {
        this.detallesPlanilla = detallesPlanilla;
    }

    @Override
    public String toString() {
        return "ResumenPlanillaDTO{" + "cantEmpleados=" + cantEmpleados + ", cantHorasOrdinarias=" + cantHorasOrdinarias + ", cantHorasExtras=" + cantHorasExtras + ", cantHorasNocturnas=" + cantHorasNocturnas + ", totalAPagar=" + totalAPagar + ", detallesPlanilla=" + detallesPlanilla + '}';
    }

}
