package gerencia.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gerencia.app.Main;
import gerencia.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;




public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemCadastrar;
	
	@FXML
	private MenuItem menuItemConsultar;

	@FXML
	private MenuItem menuItemAjuda;
	
	@FXML
	private MenuItem menuItemCalcular;
	

	@FXML
	public void onMenuItemCadastrarAction() {
		loadView("formularioCadastro.fxml");	
	}
	
	
	@FXML
	public void onMenuItemCalcularAction() {
		loadView("calchoras.fxml");	
	
	}
	
	
	@FXML
	public void onMenuItemAjudaAction() {
		loadView("AjudaView.fxml");	
	}
	
	
	@FXML
	public void onMenuItemCalendario() {
		loadView("calendario.fxml");	
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			//pegando referência da cena principal
			Scene mainScene = Main.getMainScene();
			//Pegando referência do vbox da janela principal
			VBox mainvbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//guardando referência para o menu
			Node mainMenu = mainvbox.getChildren().get(0);
			//limpando o mainvbox
			mainvbox.getChildren().clear();
			//adicionando o menu novamente
			mainvbox.getChildren().add(mainMenu);
			//adicionando o vbox da nova tela e seus filhos
			mainvbox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException e) {
			AlertUtil.showAlert("IOException", "Erro ao carregar a página", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}

}
