package cr.ac.una.relojuna.util;

public enum NotificationColor {

    SUCCESS("bg-green-500"),
    WARNING("bg-yellow-500"),
    INFO("bg-blue-500"),
    ERROR("bg-red-500");

    private final String cssClass;

    NotificationColor(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
