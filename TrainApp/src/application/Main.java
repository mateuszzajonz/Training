package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {

	@FXML
	ImageView img;
	
	@Override
	public void start(Stage primaryStage) {

		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
	        Parent root = loader.load();
	        Controller myController = loader.getController();

			Scene scene = new Scene(root,400,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
