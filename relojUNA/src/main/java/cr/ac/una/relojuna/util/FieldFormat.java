package cr.ac.una.relojuna.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;

public final class FieldFormat {

    private FieldFormat() {
        // Evita instancias
    }

    public static TextFormatter<String> formatoSoloNumeros() {
        return formatoSoloNumeros(-1);
    }

    public static TextFormatter<String> formatoSoloNumeros(int maxLongitud) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.isEmpty()) {
                return change;
            }
            if (!nuevoTexto.matches("\\d*")) {
                return null;
            }
            if (maxLongitud > 0 && nuevoTexto.length() > maxLongitud) {
                return null;
            }
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> formatoSoloLetras(int maxLongitud) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.isEmpty()) {
                return change;
            }
            if (!nuevoTexto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                return null;
            }
            if (maxLongitud > 0 && nuevoTexto.length() > maxLongitud) {
                return null;
            }
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> formatoAlfanumerico(int maxLongitud) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.isEmpty()) {
                return change;
            }
            if (!nuevoTexto.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]*")) {
                return null;
            }
            if (maxLongitud > 0 && nuevoTexto.length() > maxLongitud) {
                return null;
            }
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> formatoLimiteCaracteres(int maxLongitud) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.length() > maxLongitud) {
                return null;
            }
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> formatoCorreoElectronico(int maxLongitud) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.isEmpty()) {
                return change;
            }
            if (!nuevoTexto.matches("[a-zA-Z0-9@._+-]*")) {
                return null;
            }
            if (maxLongitud > 0 && nuevoTexto.length() > maxLongitud) {
                return null;
            }
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    private static final Pattern PATRON_CORREO
            = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    public static boolean validarCorreoElectronico(String correo) {
        return correo != null && PATRON_CORREO.matcher(correo).matches();
    }

    public static TextFormatter<String> formatoTelefonoCR() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            if (!change.isContentChange()) {
                return change;
            }

            String soloDigitos = change.getControlNewText().replaceAll("[^0-9]", "");
            if (soloDigitos.length() > 8) {
                soloDigitos = soloDigitos.substring(0, 8);
            }

            String formateado = formatearTelefono(soloDigitos);

            change.setRange(0, change.getControlText().length());
            change.setText(formateado);
            change.setCaretPosition(formateado.length());
            change.setAnchor(formateado.length());
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    private static String formatearTelefono(String digitos) {
        if (digitos.length() <= 4) {
            return digitos;
        }
        return digitos.substring(0, 4) + "-" + digitos.substring(4);
    }

    public static TextFormatter<String> formatoDineroColones() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "CR"));
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00", simbolos);

        UnaryOperator<TextFormatter.Change> filtro = change -> {
            if (!change.isContentChange()) {
                return change;
            }

            String soloDigitos = change.getControlNewText().replaceAll("[^0-9]", "");
            if (soloDigitos.isEmpty()) {
                change.setRange(0, change.getControlText().length());
                change.setText("");
                return change;
            }
            if (soloDigitos.length() > 15) {
                soloDigitos = soloDigitos.substring(soloDigitos.length() - 15);
            }

            double valor = Long.parseLong(soloDigitos) / 100.0;
            String textoFormateado = "₡ " + formatoMoneda.format(valor);

            change.setRange(0, change.getControlText().length());
            change.setText(textoFormateado);
            change.setCaretPosition(textoFormateado.length());
            change.setAnchor(textoFormateado.length());
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    public static double obtenerValorColones(String textoFormateado) {
        String soloDigitos = textoFormateado.replaceAll("[^0-9]", "");
        if (soloDigitos.isEmpty()) {
            return 0.0;
        }
        return Long.parseLong(soloDigitos) / 100.0;
    }

    public static TextFormatter<String> formatoDecimal(int maxEnteros, int maxDecimales) {
        String regex = "\\d{0," + maxEnteros + "}(\\.\\d{0," + maxDecimales + "})?";
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.isEmpty()) {
                return change;
            }
            if (nuevoTexto.matches(regex)) {
                return change;
            }
            return null;
        };
        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> formatoMayusculas() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            change.setText(change.getText().toUpperCase(new Locale("es", "CR")));
            return change;
        };
        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> formatoCedulaCR() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            if (!change.isContentChange()) {
                return change;
            }

            String soloDigitos = change.getControlNewText().replaceAll("[^0-9]", "");
            if (soloDigitos.length() > 9) {
                soloDigitos = soloDigitos.substring(0, 9);
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < soloDigitos.length(); i++) {
                if (i == 1 || i == 5) {
                    sb.append("-");
                }
                sb.append(soloDigitos.charAt(i));
            }
            String formateado = sb.toString();

            change.setRange(0, change.getControlText().length());
            change.setText(formateado);
            change.setCaretPosition(formateado.length());
            change.setAnchor(formateado.length());
            return change;
        };
        return new TextFormatter<>(filtro);
    }
}
