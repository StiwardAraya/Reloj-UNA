package cr.ac.una.relojuna.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;

public class BeanValidationHelper {

    private final ValidatorFactory FACTORY;
    private final Validator VALIDATOR;
    private ResourceBundle bundle;

    public BeanValidationHelper(ResourceBundle bundle) {
        FACTORY = Validation.buildDefaultValidatorFactory();
        VALIDATOR = FACTORY.getValidator();
        this.bundle = bundle;
    }

    public boolean validate(Object dto) {
        Set<ConstraintViolation<Object>> violaciones = VALIDATOR.validate(dto);
        if (!violaciones.isEmpty()) {
            String mensaje = violaciones.stream()
                    .map(this::resolverMensaje)
                    .collect(Collectors.joining("\n"));

            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("bean.validation.error.title"), mensaje);

            return false;
        }

        return true;
    }

    public void updateBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private String resolverMensaje(ConstraintViolation<?> violacion) {
        String mensaje = "";

        try {
            mensaje = bundle.getString(violacion.getMessageTemplate());
        } catch (MissingResourceException ex) {
            Logger.getLogger(BeanValidationHelper.class.getName()).log(Level.SEVERE, "Exception ocurred while resolving a validation message at LoginPruebaController.resolverMensaje", ex);
        }

        for (var entry : violacion.getConstraintDescriptor().getAttributes().entrySet()) {
            mensaje = mensaje.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }

        return mensaje;
    }
}
