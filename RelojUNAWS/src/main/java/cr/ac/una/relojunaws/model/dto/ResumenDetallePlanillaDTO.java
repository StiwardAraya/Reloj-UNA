package cr.ac.una.relojunaws.model.dto;

import java.math.BigDecimal;

public class ResumenDetallePlanillaDTO {

    private String folioEmpleado;
    private String nombreCompletoEmpleado;
    private BigDecimal salarioHoraEmpleado;
    private Integer horasOrdinarias;
    private Integer horasExtras;
    private Integer horasDobles;
    private Integer totalHoras;
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
        // this.horasDobles = dpDTO.getTotalHorasDobles();
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

    public Integer getHorasOrdinarias() {
        return horasOrdinarias;
    }

    public void setHorasOrdinarias(Integer horasOrdinarias) {
        this.horasOrdinarias = horasOrdinarias;
    }

    public Integer getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Integer horasExtras) {
        this.horasExtras = horasExtras;
    }

    public Integer getHorasDobles() {
        return horasDobles;
    }

    public void setHorasDobles(Integer horasDobles) {
        this.horasDobles = horasDobles;
    }

    public Integer getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Integer totalHoras) {
        this.totalHoras = totalHoras;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    @Override
    public String toString() {
        return "ResumenDetallePlanillaDTO{" + "folioEmpleado=" + folioEmpleado + ", nombreCompletoEmpleado=" + nombreCompletoEmpleado + ", salarioHoraEmpleado=" + salarioHoraEmpleado + ", horasOrdinarias=" + horasOrdinarias + ", horasExtras=" + horasExtras + ", horasDobles=" + horasDobles + ", totalHoras=" + totalHoras + ", totalAPagar=" + totalAPagar + '}';
    }

}
