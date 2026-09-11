package cr.ac.una.relojuna.model;

import cr.ac.una.relojuna.util.TipoMarca;
import cr.ac.una.relojuna.ws.MarcaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MarcaViewModel {

    private final MarcaDTO original;
    private final StringProperty folio = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> hora = new SimpleObjectProperty<>();
    private final ObjectProperty<TipoMarca> tipo = new SimpleObjectProperty<>();
    private final BooleanProperty inconsistente = new SimpleBooleanProperty();
    private final BooleanProperty editando = new SimpleBooleanProperty();
    private final boolean nueva;

    public MarcaViewModel(MarcaDTO dto) {
        this.original = dto;
        this.nueva = (dto == null);
        if (dto != null) {
            LocalDateTime fechaHoraParsed = LocalDateTime.parse(dto.getFechaHora());
            folio.set(dto.getFolioEmpleado());
            fecha.set(fechaHoraParsed.toLocalDate());
            hora.set(fechaHoraParsed.toLocalTime());
            tipo.set(TipoMarca.fromCodigo(dto.getTipo()));
            //IMPLEMENT: inconsistente.set(dto.isInconsistente());
        }
    }

    public boolean isNueva() {
        return nueva;
    }

    public MarcaDTO getOriginal() {
        return original;
    }

    public StringProperty folioProperty() {
        return folio;
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }

    public ObjectProperty<LocalTime> horaProperty() {
        return hora;
    }

    public ObjectProperty<TipoMarca> tipoProperty() {
        return tipo;
    }

    public BooleanProperty inconsistenteProperty() {
        return inconsistente;
    }

    public BooleanProperty editandoProperty() {
        return editando;
    }

}
