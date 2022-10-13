package gerencia.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.ResourceBundle;

import gerencia.modelo.DiasSemana;
import gerencia.modelo.Funcionario;
import gerencia.service.FuncionarioService;
import gerencia.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CalendarioController implements Initializable {

	@FXML
	private VBox vb01;
	@FXML
	private VBox vb02;
	@FXML
	private VBox vb03;
	@FXML
	private VBox vb04;
	@FXML
	private VBox vb05;
	@FXML
	private VBox vb06;
	@FXML
	private VBox vb07;
	@FXML
	private VBox vb08;
	@FXML
	private VBox vb09;
	@FXML
	private VBox vb10;
	@FXML
	private VBox vb11;
	@FXML
	private VBox vb12;
	@FXML
	private VBox vb13;
	@FXML
	private VBox vb14;
	@FXML
	private VBox vb15;
	@FXML
	private VBox vb16;
	@FXML
	private VBox vb17;
	@FXML
	private VBox vb18;
	@FXML
	private VBox vb19;
	@FXML
	private VBox vb20;
	@FXML
	private VBox vb21;
	@FXML
	private VBox vb22;
	@FXML
	private VBox vb23;
	@FXML
	private VBox vb24;
	@FXML
	private VBox vb25;
	@FXML
	private VBox vb26;
	@FXML
	private VBox vb27;
	@FXML
	private VBox vb28;
	@FXML
	private VBox vb29;
	@FXML
	private VBox vb30;
	@FXML
	private VBox vb31;
	
	@FXML
	private VBox teste;

	@FXML
	private TextField anoField;
	
	@FXML
	private TextField mesField;
	
	@FXML
	private Label alerta;
	
	private FuncionarioService service = new FuncionarioService();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int mesAtual = DataUtil.mes();
		int anoAtual = DataUtil.ano();
		carregarCalendario(mesAtual, anoAtual);
		
	}
	
	private Queue<VBox> criarFilaVbox() {
		Queue<VBox> vboxs = new LinkedList<VBox>() {{
			add(vb01);add(vb02);add(vb03);add(vb04);add(vb05);add(vb06);add(vb07);add(vb08);add(vb09);add(vb10);add(vb11);
			add(vb12);add(vb13);add(vb14);add(vb15);add(vb16);add(vb17);add(vb18);add(vb19);add(vb20);add(vb21);add(vb22);
			add(vb23);add(vb24);add(vb25);add(vb26);add(vb27);add(vb28);add(vb29);add(vb30);add(vb31);
		}};
		
		for(VBox vb: vboxs) {
			HBox hb = (HBox) vb.getChildren().get(0);
			HBox hb1 = (HBox) vb.getChildren().get(1);
			HBox hb2 = (HBox) vb.getChildren().get(2);
			HBox hb3 = (HBox) vb.getChildren().get(3);
			hb.getChildren().clear();
			hb1.getChildren().clear();
			hb2.getChildren().clear();
			hb3.getChildren().clear();
		}
		return vboxs;
	}

	public void carregarCalendario(int mes, int ano) {
		alerta.setTextFill(Color.BLACK);
		alerta.setText(DataUtil.nomeMes(mes, ano));
		
		List<Funcionario> funcionarios = service.todos();
		mes = mes != 0 ? mes : Calendar.MONTH;
		ano = ano != 0 ? ano : Calendar.YEAR;
		List<Date> datas = DataUtil.pegarDatasDoMes(mes, ano);
		Queue<VBox> vboxs = criarFilaVbox();
		
		Map<Integer, Integer> dias = DataUtil.mapDataSemana(mes, ano);
		Map<Integer, List<Funcionario>> funcDias = new HashMap<>();
		for(Entry<Integer, Integer> d: dias.entrySet()) {
			List<Funcionario> lista = new ArrayList<>();
			for(Funcionario f: funcionarios) {
				for(DiasSemana semana: f.getDiasSemanas()) {
					if((semana.ordinal() + 1) == d.getValue()) {
						lista.add(f);
					}
				}
			}
			funcDias.put(d.getKey(), lista);
		}
		for(Entry<Integer, List<Funcionario>> l: funcDias.entrySet()) {
			int dia = l.getKey();
			
			DiasSemana semana = DataUtil.saberDiaDaSemana(dia, mes, ano);
			String data = DataUtil.dataStr(dia, mes+1, ano);
			
			preencherVbox(vboxs.poll(), semana, data, l.getValue());
		}

	}
	
	private void preencherVbox(VBox  vb, DiasSemana diaSemana, String data, List<Funcionario> funcs) {
		HBox hb = (HBox) vb.getChildren().get(0);
		
		hb.getChildren().add(new Label(data + " - "+ diaSemana.toString()));
		
		HBox hb1 = (HBox) vb.getChildren().get(1);
		HBox hb2 = (HBox) vb.getChildren().get(2);
		HBox hb3 = (HBox) vb.getChildren().get(3);
		
		for(int i = 0; i < funcs.size(); i++) {
			if(i <= 3) {				
				Label lb = new Label(funcs.get(i).getPrimeiroNome() + " ");
				Paint pt = funcs.get(i).getColor();
				lb.setTextFill(pt);
				hb1.getChildren().add(lb);
			}
			if(i > 3 && i <= 6) {
				Label lb = new Label(funcs.get(i).getPrimeiroNome() + " ");
				Paint pt = funcs.get(i).getColor();
				lb.setTextFill(pt);
				hb2.getChildren().add(lb);
			}
			if(i > 6) {
				Label lb = new Label(funcs.get(i).getPrimeiroNome() + " ");
				Paint pt = funcs.get(i).getColor();
				lb.setTextFill(pt);
				hb3.getChildren().add(lb);
			}
		}
	}

	@FXML
	public void clicarConsultar(ActionEvent event) {
		if(!anoField.getText().isEmpty() || !mesField.getText().isEmpty()) {	
			carregarCalendario(Integer.parseInt(mesField.getText()) - 1, Integer.parseInt(anoField.getText()));
		}else {
			alerta.setTextFill(Color.RED);
			alerta.setText("Digite o ano e o mês para consultar.");
		}
	}

	public void verificarNumerico(String oldValue, String newValue) {
		String numbers = "0123456789";
		for(char a: newValue.toCharArray()) {
			if(!numbers.contains(a+"")) {
				anoField.setText("");
				alerta.setTextFill(Color.RED);
				alerta.setText("Digite apenas números!");
			}else {
				alerta.setText("");					
			}
		}
		
	}
	public void carregarObservables() {
		anoField.textProperty().addListener((observable, oldValue, newValue) -> {
			verificarNumerico(oldValue, newValue);
			if(newValue.length() > 4) {
				anoField.setText(oldValue);
			}
		});
		
		mesField.textProperty().addListener((observable, oldValue, newValue) -> {
			verificarNumerico(oldValue, newValue);
			int digitado = Integer.parseInt(newValue);
			if(digitado > 12 && digitado < 1) {
				anoField.setText("");
				alerta.setTextFill(Color.RED);
				alerta.setText("Digite um mês válido!");
			}
		});
		
	}

}
