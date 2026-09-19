package cr.ac.una.relojunaws.model.dto;

import java.math.BigDecimal;

public class ResumenDetallePlanillaDTO {

    private String folioEmpleado;
    private String nombreCompletoEmpleado;
    private BigDecimal salarioHoraEmpleado;
    private Double horasOrdinarias;
    private Double horasExtras;
    private Double horasDobles;
    private Double horasNocturnas;
    private Double totalHoras;
    private BigDecimal totalAPagar;

    public ResumenDetallePlanillaDTO() {
    }

    public ResumenDetallePlanillaDTO(DetallePlanillaDTO dpDTO, EmpleadoDTO eDTO) {
        this.folioEmpleado = eDTO.getFolio();
        this.nombreCompletoEmpleado = eDTO.getNombre()
                .concat(" ")
                .concat(eDTO.getPrimerApellido())
                .concat(" ")
                .concat(eDTO.getSegundoApellido());
        this.salarioHoraEmpleado = eDTO.getSalarioHora();
        this.horasOrdinarias = dpDTO.getTotalHorasOrdinarias();
        this.horasExtras = dpDTO.getTotalHorasExtras();
        this.horasDobles = dpDTO.getTotalHorasDobles();
        this.horasNocturnas = dpDTO.getTotalHorasNocturnas();
        this.totalHoras = this.horasOrdinarias + this.horasDobles + this.horasExtras;
        this.totalAPagar = dpDTO.getTotalAPagar();
    }

    public String getFolioEmpleado() {
        return folioEmpleado;
    }

    public void setFolioEmpleado(String folioEmpleado) {
        this.folioEmpleado = folioEmpleado;
    }

    public String getNombreCompletoEmpleado() {
        return nombreCompletoEmpleado;
    }

    public void setNombreCompletoEmpleado(String nombreCompletoEmpleado) {
        this.nombreCompletoEmpleado = nombreCompletoEmpleado;
    }

    public BigDecimal getSalarioHoraEmpleado() {
        return salarioHoraEmpleado;
    }

    public void setSalarioHoraEmpleado(BigDecimal salarioHoraEmpleado) {
        this.salarioHoraEmpleado = salarioHoraEmpleado;
    }

    public Double getHorasOrdinarias() {
        return horasOrdinarias;
    }

    public void setHorasOrdinarias(Double horasOrdinarias) {
        this.horasOrdinarias = horasOrdinarias;
    }

    public Double getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Double horasExtras) {
        this.horasExtras = horasExtras;
    }

    public Double getHorasDobles() {
        return horasDobles;
    }

    public void setHorasDobles(Double horasDobles) {
        this.horasDobles = horasDobles;
    }

    public Double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Double totalHoras) {
        this.totalHoras = totalHoras;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Double getHorasNocturnas() {
        return horasNocturnas;
    }

    public void setHorasNocturnas(Double horasNocturnas) {
        this.horasNocturnas = horasNocturnas;
    }

    @Override
    public String toString() {
        return "ResumenDetallePlanillaDTO{" + "folioEmpleado=" + folioEmpleado + ", nombreCompletoEmpleado=" + nombreCompletoEmpleado + ", salarioHoraEmpleado=" + salarioHoraEmpleado + ", horasOrdinarias=" + horasOrdinarias + ", horasExtras=" + horasExtras + ", horasDobles=" + horasDobles + ", totalHoras=" + totalHoras + ", totalAPagar=" + totalAPagar + '}';
    }

}
