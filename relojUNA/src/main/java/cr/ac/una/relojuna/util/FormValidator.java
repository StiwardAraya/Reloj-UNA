package cr.ac.una.relojuna.util;

import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;

public final class FormValidator {

    private static final String REQUIRED_SELECTOR = ".required-field";
    private static final PseudoClass INVALID = PseudoClass.getPseudoClass(".field-invalid");
    private static final Map<Class<?>, Predicate<Node>> CHECKERS = new LinkedHashMap<>();

    static {
        register(TextInputControl.class, n
                -> isBlank(((TextInputControl) n).getText()));

        register(ComboBoxBase.class, n
                -> ((ComboBoxBase) n).getValue() == null);

        register(CheckBox.class, n -> !((CheckBox) n).isSelected());
    }

    private FormValidator() {
    }

    public static <T extends Node> void register(Class<T> type, Predicate<Node> emptyChecker) {
        CHECKERS.put(type, emptyChecker);
    }

    public record ValidationResult(List<String> missingFields, String mensaje) {

        public boolean isValid() {
            return missingFields.isEmpty();
        }

        public String toMessage() {
            return mensaje + "\n\n- "
                    + String.join("\n- ", missingFields);
        }
    }

    public static ValidationResult validate(Parent root, ResourceBundle idioma) {
        List<Node> requeridos = new ArrayList<>(root.lookupAll(REQUIRED_SELECTOR));
        List<String> faltantes = new ArrayList<>();

        for (Node node : requeridos) {
            boolean vacio = isEmpty(node);
            node.pseudoClassStateChanged(INVALID, vacio);
            if (vacio) {
                faltantes.add(labelOf(node));
            }
        }

        return new ValidationResult(faltantes, idioma.getString("validation.missing.fields"));
    }

    public static void clearMarks(Parent root) {
        root.lookupAll(REQUIRED_SELECTOR).forEach(n -> n.pseudoClassStateChanged(INVALID, false));
    }

    private static boolean isEmpty(Node node) {
        return CHECKERS.entrySet().stream()
                .filter(n -> n.getKey().isInstance(node))
                .findFirst()
                .map(n -> n.getValue().test(node))
                .orElseThrow(() -> new IllegalStateException(
                "No hay checker registrado para el tipo "
                + node.getClass().getSimpleName()
                + ". Usa FormValidator.register(...) para agregarle sorpote."));
    }

    private static String labelOf(Node node) {
        if (node.getUserData() instanceof String label) {
            return label;
        }
        if (node instanceof MFXTextField tf && tf.getFloatingText() != null) {
            return tf.getFloatingText();
        }
        if (node instanceof TextInputControl tic && tic.getPromptText() != null) {
            return tic.getPromptText();
        }
        return node.getId();
    }

    private static String humanize(String fxId) {
        String sinPrefijo = fxId.replaceFirst("^(txt|cb|dp|chk|lst)", "");
        String conEspacios = sinPrefijo.replaceAll("([a-z])([A-Z])", "$1 $2");
        return conEspacios.substring(0, 1).toUpperCase() + conEspacios.substring(1);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
