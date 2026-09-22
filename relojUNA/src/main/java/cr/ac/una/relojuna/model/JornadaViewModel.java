package cr.ac.una.relojuna.model;

import cr.ac.una.relojuna.ws.ResumenJornadaDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JornadaViewModel {

    private final StringProperty fecha = new SimpleStringProperty("");
    private final StringProperty entrada = new SimpleStringProperty("");
    private final StringProperty salida = new SimpleStringProperty("");
    private final StringProperty horasTrabajadas = new SimpleStringProperty("");
    private final StringProperty diaLibre = new SimpleStringProperty("");
    private final StringProperty estado = new SimpleStringProperty("");

    public void fromDTO(ResumenJornadaDTO dto) {
        this.fecha.set(dto.getFecha() != null ? dto.getFecha() : "");
        this.entrada.set(dto.getHoraEntrada() != null ? dto.getHoraEntrada() : "");
        this.salida.set(dto.getHoraSalida() != null ? dto.getHoraSalida() : "");
        this.horasTrabajadas.set(dto.getHorasTrabajadas() != null ? dto.getHorasTrabajadas().toString() : "");
        this.diaLibre.set(Boolean.TRUE.equals(dto.isEsDiaLibre()) ? "Sí" : "No");
        this.estado.set(Boolean.TRUE.equals(dto.isEstado()) ? "Completa" : "Incompleta");
    }

    public StringProperty fechaProperty() { return fecha; }
    public StringProperty entradaProperty() { return entrada; }
    public StringProperty salidaProperty() { return salida; }
    public StringProperty horasTrabajadasProperty() { return horasTrabajadas; }
    public StringProperty diaLibreProperty() { return diaLibre; }
    public StringProperty estadoProperty() { return estado; }
}
