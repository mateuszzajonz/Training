package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class Main extends Application {

	
	@Override
	public void start(Stage primaryStage){


		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
	        Parent root = loader.load();
	        Controller myController = loader.getController();
			Scene scene = new Scene(root,400,700);
			primaryStage.setScene(scene);
			primaryStage.show();
			myController.comboBox.getItems().addAll(
					"Mê¿czyzna",
					"Kobieta"
		);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
