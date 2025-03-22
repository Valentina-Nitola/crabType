module crabtype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens crabtype.view to javafx.graphics, javafx.fxml;
    opens crabtype.controller to javafx.fxml;

    exports crabtype.controller;
    exports crabtype.view;
}
