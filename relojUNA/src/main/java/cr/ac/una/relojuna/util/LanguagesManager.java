package cr.ac.una.relojuna.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Gestor de idiomas de la aplicación.
 */
public class LanguagesManager {

    private static final String BUNDLE_BASE_NAME
            = "cr.ac.una.relojuna.languages.messages";

    private static Locale currentLocale;
    private static ResourceBundle currentBundle;

    private static final List<LanguageChangeListener> listeners = new ArrayList<>();

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static ResourceBundle getCurrentBundle() {
        return currentBundle;
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        currentBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
        notifyLanguageChange();
    }

    public static void addLanguageChangeListener(LanguageChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static void removeLanguageChangeListener(LanguageChangeListener listener) {
        listeners.remove(listener);
    }

    public static void clearListeners() {
        listeners.clear();
    }

    private static void notifyLanguageChange() {
        List<LanguageChangeListener> snapshot = new ArrayList<>(listeners);
        for (LanguageChangeListener listener : snapshot) {
            listener.onLanguageChanged(currentBundle);
        }
    }
}
