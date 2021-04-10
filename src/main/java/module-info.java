module org.mvj {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.utcn.magazin;
    exports com.utcn.magazin.controller;

    opens fxml;
}