module cr.ac.una.relojuna {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires MaterialFX;
    requires java.logging;
    requires java.base;
    requires jakarta.annotation;
    requires org.glassfish.jaxb.runtime;
    requires jakarta.validation;
    requires jakarta.xml.ws;
    requires org.jvnet.staxex;
    requires org.jvnet.mimepull;

    opens cr.ac.una.relojuna to javafx.fxml;
    opens cr.ac.una.relojuna.controller to javafx.fxml;
    opens cr.ac.una.relojuna.model;
    opens cr.ac.una.relojuna.ws;

    exports cr.ac.una.relojuna;
    exports cr.ac.una.relojuna.controller;
    exports cr.ac.una.relojuna.util;
    exports cr.ac.una.relojuna.service;
    exports cr.ac.una.relojuna.model;
    exports cr.ac.una.relojuna.ws;
}
