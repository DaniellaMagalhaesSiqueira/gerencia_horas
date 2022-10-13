package gerencia.app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application{

	private static Scene mainScene;
	private static Stage ps;
	
	@Override
	public void start(Stage primaryStage) {
		ps = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainView.fxml"));
			ScrollPane scrollpane = loader.load();
			
			scrollpane.setFitToHeight(true);
			scrollpane.setFitToWidth(true);
			mainScene = new Scene(scrollpane);
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Carga Horária Estendida");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static Stage gerPrimaryStage() {
		return ps;
	}
	
	
	public static void main(String[] args) {
		

		
		launch(args);
	}
	

}
