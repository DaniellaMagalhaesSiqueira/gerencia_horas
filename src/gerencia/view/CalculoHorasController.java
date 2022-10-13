package gerencia.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gerencia.service.Ponto;
import gerencia.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CalculoHorasController implements Initializable{
	
	@FXML
	private TextArea ta;
	
	@FXML
	private Label horasTrabalhadas;
	
	@FXML
	private Label horasDevidas;
	
	@FXML
	private Label bancoHoras;
	
	@FXML
	private Label mensagem;
	
	@FXML
	private ComboBox<String> comboCh;
	
	@FXML
	private TextField feriadosField;
	
	@FXML
	private TextField horaEntrada;
	
	
	@FXML
	private TextField horaSaida;
	
	
	@FXML
	private TextField minEntrada;
	
	
	@FXML
	private TextField minSaida;

	
	private ObservableList<PieChart.Data> pieChartData;
	@FXML
	private PieChart pieChart;
	
//	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
//			   new PieChart.Data("Iphone 5S", 13), 
//			   new PieChart.Data("Samsung Grand", 25), 
//			   new PieChart.Data("MOTO G", 10), 
//			   new PieChart.Data("Nokia Lumia", 22)); 
	//Creating a Pie chart 
//	PieChart pieChart = new PieChart(pieChartData);
//	pieChart.setData(pieChartData);
	//Setting the title of the Pie chart 
//	pieChart.setTitle("Mobile Sales");
	//Setting the labels of the pie chart visible  
//	pieChart.setLabelsVisible(true);
//

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarCombos();
		registrarObservables();

	     
	}
	
	private void carregarCombos() {
		
		List<String> cargas = new ArrayList<>() {{
			add("40 Horas"); add("30 Horas"); add("20 Horas");
		}};
		ObservableList<String> obsCh = FXCollections.observableArrayList(cargas);
		comboCh.setItems(obsCh);
	}
	
	
	public void calcular(ActionEvent event) {
		horasTrabalhadas.setText("");
		horasDevidas.setText("");
		bancoHoras.setText("");
		if(ta.getText().isEmpty()) {
			AlertUtil.showAlert("Texto Vazio", null, "Insira as informações na caixa de texto", AlertType.WARNING);
		}
		String padraoEntrada = "", padraoSaida = "";
		if(!horaEntrada.getText().isEmpty() || !minEntrada.getText().isEmpty()) {			
			padraoEntrada = horaEntrada.getText() + ":" + minEntrada.getText() + ":00"; 
		}
		if(!horaSaida.getText().isEmpty() || !minSaida.getText().isEmpty()) {		
			padraoSaida = horaSaida.getText() + ":" + minSaida.getText() + ":00"; 
		}
		System.out.println("Padrao Entrada: "+ padraoEntrada);
		System.out.println("Padrao Saída: "+ padraoSaida);
		int ch = 40;
		int feriados = 0;
		try {
			String chStr = comboCh.getSelectionModel().getSelectedItem();
			ch = chStr == null ? 40 : Integer.parseInt(chStr.substring(0,2));
			feriados = feriadosField.getText().isEmpty() ? 0 : Integer.parseInt(feriadosField.getText());
		} catch (NumberFormatException e) {
			AlertUtil.showAlert("Ops!", null, "Falha nossa", AlertType.ERROR);
			e.printStackTrace();
		}
		
		
		Ponto ponto = new Ponto(ta.getText(), padraoEntrada, padraoSaida, ch, feriados);
		ponto.lerTexto();
		
		horasTrabalhadas.setText("Horas Trabalhadas " + ponto.horasTrabalhadas());
		horasDevidas.setText("Horas Devidas: " + ponto.horasDevidas());
		String bh = ponto.bancoHoras();
		if(bh.substring(0,1).equals("-")) {
			bancoHoras.setTextFill(Color.RED);
		}
		bancoHoras.setText("Banco de Horas " + ponto.bancoHoras());
		
		double [] porcentagem = ponto.cumpridas();
		String resto = "";
		String banco = "";
		if(porcentagem[1] < 0) {
			banco = "Faltantes";
			resto = "";
		}else {
			banco = "Excedente";
		}
//		if(resto.isEmpty()) {
			
//			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
//				new PieChart.Data("Trabalhadas", porcentagem[0]), 
//				new PieChart.Data(banco, porcentagem[1])
//			);
//		}else {
//			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
//				new PieChart.Data("Trabalhadas", porcentagem[0]), 
//				new PieChart.Data("Excedentes", porcentagem[1]),
//				new PieChart.Data("Restante", porcentagem[3])
//			);
//		}
//		pieChart.setLegendSide(Side.LEFT);
//		pieChart.setData(pieChartData);
//
//		pieChart.setTitle("Horas do Mês");
//		
//		final Label caption = new Label("");
//		caption.setTextFill(Color.BLACK);
//		caption.setStyle("-fx-font: 24 arial;");
//		
//		for (final PieChart.Data data : pieChart.getData()) {
//		    data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED,
//		        new EventHandler<MouseEvent>() {
//		            @Override public void handle(MouseEvent e) {
//		                caption.setTranslateX(e.getSceneX());
//		                caption.setTranslateY(e.getSceneY());
//		                caption.setText(String.valueOf(data.getPieValue()) + "%");
//		             }
//		        });
//		}
		
	}
	
	
	private void registrarObservables() {
		feriadosField.textProperty().addListener((observable, oldValue, newValue) -> {
			String numbers = "0123456789";
			for(char a: newValue.toCharArray()) {
				if(!numbers.contains(a+"")) {
					feriadosField.setText("");
					mensagem.setText("Digite apenas números!");
				}else {
					mensagem.setText("");					
				}
			}
		});
		
		horaSaida.textProperty().addListener((observable, oldValue, newValue) -> {
			String numbers = "0123456789";
			for(char a: newValue.toCharArray()) {
				if(!numbers.contains(a+"")) {
					feriadosField.setText("");
					mensagem.setText("Digite apenas números!");
				}else {
					mensagem.setText("");					
				}
			}
			if(newValue.length() > 2) {
				feriadosField.setText(oldValue);
			}
			
			
		});
		horaEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
			String numbers = "0123456789";
			for(char a: newValue.toCharArray()) {
				if(!numbers.contains(a+"")) {
					feriadosField.setText("");
					mensagem.setText("Digite apenas números!");
				}else {
					mensagem.setText("");					
				}
			}
			if(newValue.length() > 2) {
				feriadosField.setText(oldValue);
			}
			
			
		});
		minSaida.textProperty().addListener((observable, oldValue, newValue) -> {
			String numbers = "0123456789";
			for(char a: newValue.toCharArray()) {
				if(!numbers.contains(a+"")) {
					feriadosField.setText("");
					mensagem.setText("Digite apenas números!");
				}else {
					mensagem.setText("");					
				}
			}
			if(newValue.length() > 2) {
				feriadosField.setText(oldValue);
			}
			
			
		});
		minEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
			String numbers = "0123456789";
			for(char a: newValue.toCharArray()) {
				if(!numbers.contains(a+"")) {
					feriadosField.setText("");
					mensagem.setText("Digite apenas números!");
				}else {
					mensagem.setText("");					
				}
			}
			if(newValue.length() > 2) {
				feriadosField.setText(oldValue);
			}
			
			
		});
	}
	
	public void limpar(ActionEvent event) {

		
		comboCh.getSelectionModel().clearSelection();
		ta.clear();
		horasTrabalhadas.setText("");
		horasDevidas.setText("");
		bancoHoras.setText("");
		mensagem.setText("");
		horaEntrada.setText("");
		horaSaida.setText("");
		minEntrada.setText("");
		minSaida.setText("");
		feriadosField.clear();
		
		
	}

}
