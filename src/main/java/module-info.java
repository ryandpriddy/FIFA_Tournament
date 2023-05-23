module edu.ucdenver.pa1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    //requires org.glavo.materialfx.adapter;

    opens edu.ucdenver.app to javafx.fxml;
    exports edu.ucdenver.app;
    exports edu.ucdenver.server;
    exports edu.ucdenver.tournament;
}