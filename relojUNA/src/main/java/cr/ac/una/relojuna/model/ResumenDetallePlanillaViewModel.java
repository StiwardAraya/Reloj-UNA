package cr.ac.una.relojuna.model;

import cr.ac.una.relojuna.ws.ResumenDetallePlanillaDTO;
import java.math.BigDecimal;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResumenDetallePlanillaViewModel {

    private final StringProperty folioEmpleado = new SimpleStringProperty("");
    private final StringProperty nombreCompletoEmpleado = new SimpleStringProperty("");
    private final StringProperty salarioHoraEmpleado = new SimpleStringProperty("");
    private final StringProperty horasOrdinarias = new SimpleStringProperty("");
    private final StringProperty horasExtras = new SimpleStringProperty("");
    private final StringProperty horasDobles = new SimpleStringProperty("");
    private final StringProperty totalHoras = new SimpleStringProperty("");
    private final StringProperty totalAPagar = new SimpleStringProperty("");

    public StringProperty folioProperty() {
        return folioEmpleado;
    }

    public StringProperty nombreCompletoEmpleadoProperty() {
        return nombreCompletoEmpleado;
    }

    public StringProperty salarioHoraEmpleadoProperty() {
        return salarioHoraEmpleado;
    }

    public StringProperty horasOrdinariasProperty() {
        return horasOrdinarias;
    }

    public StringProperty horasExtrasProperty() {
        return horasExtras;
    }

    public StringProperty horasDoblesProperty() {
        return horasDobles;
    }

    public StringProperty totalHorasProperty() {
        return totalHoras;
    }

    public StringProperty totalAPagarProperty() {
        return totalAPagar;
    }

    public void fromDTO(ResumenDetallePlanillaDTO dto) {
        this.folioEmpleado.set(dto.getFolioEmpleado());
        this.nombreCompletoEmpleado.set(dto.getNombreCompletoEmpleado());
        this.salarioHoraEmpleado.set(dto.getSalarioHoraEmpleado().toString());
        this.horasOrdinarias.set(dto.getHorasOrdinarias().toString());
        this.horasExtras.set(dto.getHorasExtras().toString());
        this.horasDobles.set(dto.getHorasDobles().toString());
        this.totalHoras.set(dto.getTotalHoras().toString());
        this.totalAPagar.set(dto.getTotalAPagar().toString());
    }
    
    public ResumenDetallePlanillaDTO toDTO() {
        ResumenDetallePlanillaDTO dto = new ResumenDetallePlanillaDTO();
        dto.setFolioEmpleado(this.folioEmpleado.get());
        dto.setNombreCompletoEmpleado(this.nombreCompletoEmpleado.get());
        dto.setSalarioHoraEmpleado(new BigDecimal(this.salarioHoraEmpleado.get()));
        dto.setHorasOrdinarias(Integer.valueOf(this.horasOrdinarias.get()));
        dto.setHorasExtras(Integer.valueOf(this.horasExtras.get()));
        dto.setHorasDobles(Integer.valueOf(this.horasDobles.get()));
        dto.setTotalHoras(Integer.valueOf(this.totalHoras.get()));
        dto.setTotalAPagar(new BigDecimal(this.totalAPagar.get()));
        return dto;
    }
}
