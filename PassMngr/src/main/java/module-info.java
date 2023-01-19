module PassMngr {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires java.sql;
	requires java.xml;
	requires com.google.common;
	requires org.junit.jupiter.api;
	
	opens gui to javafx.fxml;

	exports gui;
    exports Main;
}
