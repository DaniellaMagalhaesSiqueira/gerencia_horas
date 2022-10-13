package gerencia.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import gerencia.modelo.DAO;
import gerencia.util.AlertUtil;
import gerencia.util.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ConsultaController implements Initializable{
	
	@FXML
	private TextField nome;
	
	@FXML
	private TextField anoField;
	
	@FXML
	private TextField mesField;
	
	@FXML
	private TextArea result;
	
	@FXML
	private CheckBox anoAtualCheck;
	
	@FXML
	private Button calcular;
	
	private boolean anoAtual;
	
	private Map<Integer, String> meses = new HashMap<Integer, String>(){{
	put(0, "Janeiro"); put(1, "Fevereiro");put(2,"Março");put(3, "Abril");
	put(4, "Maio"); put(5, "Junho"); put(6, "Julho");put(7, "Agosto");put(8, "Setembro");
	put(9, "Outubro"); put(10, "Novembro"); put(11, "Dezembro");
	}};


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		anoAtualCheck.selectedProperty().set(true);
		anoField.setText(DataUtil.pegarAnoAtual() + "");

	}
	
	public void clicarAnoAtual() {
		this.anoAtual = (boolean) anoAtualCheck.selectedProperty().getValue();
		this.anoField.setText(DataUtil.pegarAnoAtual()+"");
	}

	
	public void clicarConsultar(ActionEvent event) {
		
		if(nome.getText().isBlank()) {
			AlertUtil.showAlert("Campo necessário para consulta", null, "É necessário informar um nome para a pesquisa na base de dados",
					AlertType.WARNING);
		}
		int mes = Integer.parseInt(mesField.getText());


	}
		
}
	
