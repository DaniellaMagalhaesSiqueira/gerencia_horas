package gerencia.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import gerencia.app.Main;
import gerencia.modelo.DiasSemana;
import gerencia.modelo.Funcionario;
import gerencia.service.FuncionarioService;
import gerencia.util.AlertUtil;
import gerencia.util.ColorTableCell;
import gerencia.util.DataUtil;
import gerencia.util.EventoUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CadastroController implements Initializable{
	
	private List<DiasSemana> lista = new ArrayList<>();
	
	@FXML
	private TextField nome;
	
	@FXML
	private ComboBox<String> comboCarga;
	private ObservableList<String> obsCarga;
	
	@FXML
	CheckComboBox<String> checkComboBox;

	
	@FXML
	private Button cadastrar;
	
	@FXML
	private ColorPicker color;
	
	@FXML 
	private HBox hbox;
	
	@FXML
	private TableView<Funcionario> table;
	
	@FXML
	private TableColumn<Funcionario, Integer> colunaCodigo;
	
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	
	@FXML
	private TableColumn<Funcionario, String> colunaDias;
	
	@FXML
	private TableColumn<Funcionario, Integer> colunaCh;

	@FXML
	private TableColumn<Funcionario, Color> colunaCor;
	
	@FXML
	private TableColumn<Funcionario, Funcionario> colunaExcluir;
	
	
	private Funcionario funcionario;
	
	private FuncionarioService service = new FuncionarioService();
	
//	private String mensagem;

	private final Locale BR = new Locale("pt-BR");
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarCombo();
		criarCheckComboBox();
		hbox.getChildren().add(1, checkComboBox);
		carregarTabela();
		

	}
	
	
	
	public void carregarCombo() {
		obsCarga = FXCollections.observableArrayList("40 Horas", "30 Horas","20 Horas");
		comboCarga.setItems(obsCarga);
		comboCarga.getSelectionModel().selectFirst();
		

	}
	
	
	 private CheckComboBox<String> criarCheckComboBox() {
		 ObservableList<String> dias = FXCollections.observableArrayList(
		        "DOMINGO","SEGUNDA","TERÇA","QUARTA","QUINTA","SEXTA","SÁBADO");
		 checkComboBox = new CheckComboBox<>(dias);
		 checkComboBox.getCheckModel().getCheckedItems().addListener(new InvalidationListener() {
	      @Override
	      public void invalidated(Observable observable) {
	        System.out.printf("\nSelected items: %s", getSelectedItems(checkComboBox));
	      }
	    });
	    return checkComboBox;
	  }
	 
	private List<String> getSelectedItems(CheckComboBox<String> checkComboBox) {
		    return checkComboBox.getCheckModel().getCheckedItems();
	 }
	
	@FXML
	public void clicarCadastrar(ActionEvent event) {
		if(nome.getText().isBlank()) {
			AlertUtil.showAlert("Campo obrigatório vazio", "", "O campo nome é obrigatório", AlertType.WARNING);
			return;
		}
		
		for(String valor: getSelectedItems(checkComboBox)) {
			for(DiasSemana d: DiasSemana.values()) {
				if(valor.equals(d.toString())) {					
					lista.add(d);
				}
			}
		}
		
		if(lista.isEmpty()) {
			AlertUtil.showAlert("Campo obrigatório vazio", "", 
				"Escolha os dias da semana que o funcionário está registrado para trabalhar.", AlertType.WARNING);
			return;
		}

		this.funcionario = new Funcionario();
		funcionario.setDiasSemanas(lista);
		funcionario.setNomeFuncionario(nome.getText());
		String str = comboCarga.getSelectionModel().getSelectedItem() == null || comboCarga.getSelectionModel().getSelectedItem() == "" ?
				"40" : comboCarga.getSelectionModel().getSelectedItem();
		str = str.substring(0,2);
		int ch = Integer.parseInt(str);
		funcionario.setCargaHorariaSemanal(ch);
		Color cor = color.getValue();
		String cores = cor.getRed() + "," + cor.getGreen() + "," + cor.getBlue() + "," + cor.getOpacity() + "";		
		funcionario.setCor(cores);

		
		try {
			int result = service.inserir(funcionario);
			if(result < 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			AlertUtil.showAlert("Erro ao acessar banco", "", 
					"Houve um erro ao acessar o banco de dados. Não foi possível incluir o funcionário", AlertType.WARNING);
			e.printStackTrace();
				return;
		}
		limparDados();
		carregarCombo();
		carregarTabela();

	}
	
	public void carregarTabela() {
		List<Funcionario> fs = service.todos();

		
		ObservableList<Funcionario> obss = FXCollections.observableArrayList(fs);
		table.setItems(obss);
		table.setEditable(true);
		colunaCodigo.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("idFuncionario"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nomeFuncionario"));
		colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());
		colunaDias.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("strSemanas"));
		colunaDias.setCellFactory(TextFieldTableCell.forTableColumn());
		colunaCor.setCellValueFactory(new PropertyValueFactory<Funcionario, Color>("color"));
		colunaCor.setCellFactory(ColorTableCell::new);
		colunaCh.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("cargaHorariaSemanal"));
		colunaCh.setEditable(true);
		colunaCh.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

			@Override
			public String toString(Integer object) {
				return object + " Horas";
			}

			@Override
			public Integer fromString(String string) {
				int inteiro = Integer.parseInt(string.substring(0, 2));
				return inteiro;
			}
		}));
		
		EventoUtil.initButtons((TableColumn<Funcionario, Funcionario>) 
		colunaExcluir, 30, "Excluir", "svg-gray", (Funcionario funcionario, ActionEvent event) -> {
			System.out.println("Você clicou para excluir as informações de: " + funcionario.getNomeFuncionario());
			service.excluir(funcionario.getIdFuncionario());
			carregarTabela();
		});


		Stage cena = (Stage) Main.getMainScene().getWindow();
		table.prefHeightProperty().bind(cena.heightProperty());
	}
	
	@FXML
	public void onNomeChanged(TableColumn.CellEditEvent<Funcionario, String> funcionarioStrCellEditEvent) {
		Funcionario f = table.getSelectionModel().getSelectedItem();
		f.setNomeFuncionario(funcionarioStrCellEditEvent.getNewValue());
		service.alterar(f);
		carregarTabela();
	}
	
	@FXML
	public void onDiasChanged(TableColumn.CellEditEvent<Funcionario, String> funcionarioStrCellEditEvent) {
		String [] dias = funcionarioStrCellEditEvent.getNewValue().split(" - ");
		List<DiasSemana> funcDias = new ArrayList<>();
		for(String d: dias) {
			DiasSemana dia = DataUtil.saberDiaDaSemana(d.trim().toUpperCase(BR));
			if(dia != null) {
				funcDias.add(dia);
			}
		}
		if(!funcDias.isEmpty()) {
			Funcionario f = table.getSelectionModel().getSelectedItem();
			f.setDiasSemanas(funcDias);
			service.alterar(f);
		}
		carregarTabela();
	}
	
	@FXML
	public void onChChanged(TableColumn.CellEditEvent<Funcionario, Integer> event) {
		Integer novo = event.getNewValue();
		Funcionario f = table.getSelectionModel().getSelectedItem();
		f.setCargaHorariaSemanal(novo);
		service.alterar(f);
		carregarTabela();
	}
	
	@FXML
	public void clicarAtualizar() {
		carregarTabela();
	}
	
	public void limparDados() {
		nome.setText("");
		comboCarga.getSelectionModel().select(0);
		hbox.getChildren().remove(1);
		criarCheckComboBox();
		hbox.getChildren().add(1, checkComboBox);
		color.setValue(Color.WHITE);
		carregarTabela();
	}
	
}

