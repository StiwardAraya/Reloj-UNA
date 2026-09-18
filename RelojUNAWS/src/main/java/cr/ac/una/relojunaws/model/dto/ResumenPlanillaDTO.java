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
    "mes", "ano", "cantEmpleados", "cantHorasOrdinarias", "cantHorasExtras",
    "cantHorasNocturnas", "totalAPagar", "detallesPlanilla"
})
public class ResumenPlanillaDTO {

    @XmlElement(name = "mes")
    private Integer mes;

    @XmlElement(name = "ano")
    private Integer ano;

    @XmlElement(name = "cantEmpleados")
    private Integer cantEmpleados;

    @XmlElement(name = "cantHorasOrdinarias")
    private Double cantHorasOrdinarias;

    @XmlElement(name = "cantHorasExtras")
    private Double cantHorasExtras;

    @XmlElement(name = "cantHorasNocturnas")
    private Double cantHorasNocturnas;

    @XmlElement(name = "totalAPagar")
    private BigDecimal totalAPagar;

    @XmlElement(name = "detallesPlanilla")
    private List<ResumenDetallePlanillaDTO> detallesPlanilla;

    public ResumenPlanillaDTO() {
    }

    public ResumenPlanillaDTO(Integer mes, Integer ano, Integer cantEmpleados, Double cantHorasOrdinarias, Double cantHorasExtras, Double cantHorasNocturnas, BigDecimal totalAPagar) {
        this.mes = mes;
        this.ano = ano;
        this.cantEmpleados = cantEmpleados;
        this.cantHorasOrdinarias = cantHorasOrdinarias;
        this.cantHorasExtras = cantHorasExtras;
        this.cantHorasNocturnas = cantHorasNocturnas;
        this.totalAPagar = totalAPagar;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(Integer cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    public Double getCantHorasOrdinarias() {
        return cantHorasOrdinarias;
    }

    public void setCantHorasOrdinarias(Double cantHorasOrdinarias) {
        this.cantHorasOrdinarias = cantHorasOrdinarias;
    }

    public Double getCantHorasExtras() {
        return cantHorasExtras;
    }

    public void setCantHorasExtras(Double cantHorasExtras) {
        this.cantHorasExtras = cantHorasExtras;
    }

    public Double getCantHorasNocturnas() {
        return cantHorasNocturnas;
    }

    public void setCantHorasNocturnas(Double cantHorasNocturnas) {
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
