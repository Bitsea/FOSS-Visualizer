module com.fossvisualizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    exports com.fossvisualizer.application;
    opens com.fossvisualizer.application to javafx.fxml;
}