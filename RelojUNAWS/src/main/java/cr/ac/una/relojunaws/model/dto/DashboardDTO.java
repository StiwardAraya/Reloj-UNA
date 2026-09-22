package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "dashboard")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DashboardDTO", propOrder = {
    "totalEntradas", "asocieEntradasSemanaAnterior", "totalSalidas",
    "asocieSalidasSemanaAnterior", "totalMovimientos", "totalInconsistencias",
    "asocieEntreMarcas", "topEmpleados"
})
public class DashboardDTO {

    @XmlElement(name = "totalEntradas")
    private Integer totalEntradas;

    @XmlElement(name = "asocieEntradasSemanaAnterior")
    private Double asocieEntradasSemanaAnterior;

    @XmlElement(name = "totalSalidas")
    private Integer totalSalidas;

    @XmlElement(name = "asocieSalidasSemanaAnterior")
    private Double asocieSalidasSemanaAnterior;

    @XmlElement(name = "totalMovimientos")
    private Integer totalMovimientos;

    @XmlElement(name = "totalInconsistencias")
    private Integer totalInconsistencias;

    @XmlElement(name = "asocieEntreMarcas")
    private Double asocieEntreMarcas;

    @XmlElement(name = "topEmpleados")
    private List<String> topEmpleados;

    public DashboardDTO() {
    }

    public DashboardDTO(Integer totalEntradas, Double asocieEntradasSemanaAnterior, Integer totalSalidas, Double asocieSalidasSemanaAnterior, Integer totalMovimientos, Integer totalInconsistencias, Double asocieEntreMarcas, List<String> topEmpleados) {
        this.totalEntradas = totalEntradas;
        this.asocieEntradasSemanaAnterior = asocieEntradasSemanaAnterior;
        this.totalSalidas = totalSalidas;
        this.asocieSalidasSemanaAnterior = asocieSalidasSemanaAnterior;
        this.totalMovimientos = totalMovimientos;
        this.totalInconsistencias = totalInconsistencias;
        this.asocieEntreMarcas = asocieEntreMarcas;
        this.topEmpleados = topEmpleados;
    }

    public Integer getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(Integer totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public Double getAsocieEntradasSemanaAnterior() {
        return asocieEntradasSemanaAnterior;
    }

    public void setAsocieEntradasSemanaAnterior(Double asocieEntradasSemanaAnterior) {
        this.asocieEntradasSemanaAnterior = asocieEntradasSemanaAnterior;
    }

    public Integer getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(Integer totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public Double getAsocieSalidasSemanaAnterior() {
        return asocieSalidasSemanaAnterior;
    }

    public void setAsocieSalidasSemanaAnterior(Double asocieSalidasSemanaAnterior) {
        this.asocieSalidasSemanaAnterior = asocieSalidasSemanaAnterior;
    }

    public Integer getTotalMovimientos() {
        return totalMovimientos;
    }

    public void setTotalMovimientos(Integer totalMovimientos) {
        this.totalMovimientos = totalMovimientos;
    }

    public Integer getTotalInconsistencias() {
        return totalInconsistencias;
    }

    public void setTotalInconsistencias(Integer totalInconsistencias) {
        this.totalInconsistencias = totalInconsistencias;
    }

    public Double getAsocieEntreMarcas() {
        return asocieEntreMarcas;
    }

    public void setAsocieEntreMarcas(Double asocieEntreMarcas) {
        this.asocieEntreMarcas = asocieEntreMarcas;
    }

    public List<String> getTopEmpleados() {
        return topEmpleados;
    }

    public void setTopEmpleados(List<String> topEmpleados) {
        this.topEmpleados = topEmpleados;
    }

    @Override
    public String toString() {
        return "DashboardDTO{" + "totalEntradas=" + totalEntradas + ", asocieEntradasSemanaAnterior=" + asocieEntradasSemanaAnterior + ", totalSalidas=" + totalSalidas + ", asocieSalidasSemanaAnterior=" + asocieSalidasSemanaAnterior + ", totalMovimientos=" + totalMovimientos + ", totalInconsistencias=" + totalInconsistencias + ", asocieEntreMarcas=" + asocieEntreMarcas + ", topEmpleados=" + topEmpleados + '}';
    }

}
