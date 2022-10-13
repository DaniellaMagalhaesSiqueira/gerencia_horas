package gerencia.modelo;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

//@Entity
public class Funcionario {

	private Integer idFuncionario;
	
	private String nomeFuncionario;

	private List<DiasSemana> diasSemanas;

	private String cor;
	
	private Integer cargaHorariaSemanal;
	
	private ColorPicker colorPicker;
	
	private Color color;
	
	private final String tabela = "funcionario";
	
	
	public Funcionario() {
		
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int id) {
		this.idFuncionario = id;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	
	public String getPrimeiroNome() {
		String [] nome = nomeFuncionario.split(" ");
		return nome[0];
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public List<DiasSemana> getDiasSemanas() {
		return diasSemanas;
	}

	public void setDiasSemanas(List<DiasSemana> diasSemanas) {
		this.diasSemanas = diasSemanas;
	}


	public Integer getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(Integer cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}
	

	public String getTabela() {
		return this.tabela;
	}
	
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
		
	public ColorPicker getColorPicker() {
		String [] cor = this.getCor().split(",");
		double [] cores = new double [4];
		for(int i = 0; i < cor.length; i++) {
			cores[i] = Double.parseDouble(cor[i]);
		}
		ColorPicker cp = new ColorPicker(new Color(cores[0], cores[1], cores[2], cores[3]));
		return cp;
	}
	
	public ColorPicker setColorPicker(ColorPicker colorPiker) {
		return this.colorPicker;
	}
	
	public String getStrSemanas() {
		String str = "";
		if(!getDiasSemanas().isEmpty()) {			
			for(DiasSemana d: this.getDiasSemanas()) {
				str += d.toString() + " - ";
			}
			str = str.substring(0, str.length() - 3);
		}
		return str;
	}
	
	public void setColor(Color cor) {
		String str = "";
		str += cor.getRed() + "," + cor.getGreen() + "," + cor.getBlue() + "," + cor.getOpacity();
		this.setCor(str);
		this.color = cor;
	}
	
	public Color getColor() {
		String [] str = this.getCor().split(",");
		double [] c = new double [4];
		for(int i = 0; i< str.length; i++) {
			c[i] = Double.parseDouble(str[i]);
		}
		return new Color(c[0], c[1], c[2], c[3]);
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Funcionario [idFuncionario=");
		builder.append(idFuncionario);
		builder.append(", nomeFuncionario=");
		builder.append(nomeFuncionario);
		builder.append(", diasSemanas=");
		builder.append(diasSemanas);
		builder.append(", cor=");
		builder.append(cor);
		builder.append(", cargaHorariaSemanal=");
		builder.append(cargaHorariaSemanal);
		builder.append(", tabela=");
		builder.append(tabela);
		builder.append("]");
		return builder.toString();
	}

	
}
