module Client {
    requires javafx.fxml;
    requires javafx.controls;
    opens Client to javafx.graphics;
    exports Client;
}