module TrainApp {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	requires sqlite.jdbc;
	
	opens application to javafx.graphics, javafx.fxml;
}
