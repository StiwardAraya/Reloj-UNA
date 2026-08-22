package cr.ac.una.relojuna.model;

import cr.ac.una.relojuna.ws.LoginRequestDTO;
import javafx.beans.property.SimpleStringProperty;

public class LoginViewModel {

    private final SimpleStringProperty folio = new SimpleStringProperty("");
    private final SimpleStringProperty clave = new SimpleStringProperty("");

    public SimpleStringProperty folioProperty() {
        return folio;
    }

    public SimpleStringProperty claveProperty() {
        return clave;
    }

    public String getFolio() {
        return folio.get();
    }

    public void setFolio(String folio) {
        this.folio.set(folio);
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.set(clave);
    }

    public LoginRequestDTO toDto() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setFolio(this.getFolio());
        loginRequest.setClave(this.getClave());
        return loginRequest;
    }

    public void fromDto(LoginRequestDTO dto) {
        this.folio.set(dto.getFolio());
        this.clave.set(dto.getClave());
    }
}
