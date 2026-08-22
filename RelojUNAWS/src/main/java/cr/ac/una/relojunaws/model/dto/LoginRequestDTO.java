package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@XmlRootElement(name = "LoginRequest", namespace = "empleados/login")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginRequestDTO", propOrder = {"folio", "clave"})
public class LoginRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "folio", required = true)
    @NotBlank(message = "bv.folio.required")
    @Size(max = 6, message = "bv.folio.size")
    private String folio;

    @XmlElement(name = "clave", required = true)
    @NotBlank(message = "bv.clave.required")
    @Size(max = 16, message = "bv.clave.size")
    private String clave;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String folio, String clave) {
        this.folio = folio;
        this.clave = clave;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" + "folio=" + folio + ", clave=*** }";
    }

}
