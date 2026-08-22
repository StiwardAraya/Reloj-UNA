package cr.ac.una.relojuna.util;

import java.util.Locale;

public class IdiomaItem {

    private final String nombre;
    private final Locale locale;

    public IdiomaItem(String nombre, Locale locale) {
        this.nombre = nombre;
        this.locale = locale;
    }

    public String getNombre() {
        return nombre;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
