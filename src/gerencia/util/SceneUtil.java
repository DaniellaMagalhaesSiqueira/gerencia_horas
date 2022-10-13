package gerencia.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneUtil {

	public static Stage atual(ActionEvent event) {
		
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
		
	}
	
	
}
