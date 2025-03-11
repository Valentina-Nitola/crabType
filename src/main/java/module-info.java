module crabtype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens crabtype to javafx.fxml;
    exports crabtype;
}