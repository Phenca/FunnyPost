module funnypost.mvc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.json;
    requires mysql.connector.java;

    opens funnypost.mvc to javafx.fxml;
    opens funnypost.mvc.entities to javafx.base;

    exports funnypost.mvc;
}