package gerencia.modelo;

public enum DiasSemana {

	
	DO("DOMINGO"), SE("SEGUNDA"), TE("TERÇA"), QA("QUARTA"), QI("QUINTA"),
	SX("SEXTA"), SA("SÁBADO");
	
	private String nome;

	private DiasSemana(String nome){
		this.nome = nome;
	}
	
	@Override
	public String  toString() {
		return this.nome;
	}
	
}
