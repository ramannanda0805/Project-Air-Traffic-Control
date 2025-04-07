module AirTrafficControl {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.airtrafficcontrol;  // 👈 Export your package
    opens com.airtrafficcontrol to javafx.graphics, javafx.fxml;  // 👈 Open for JavaFX reflection
}
