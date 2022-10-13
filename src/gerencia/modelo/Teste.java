package gerencia.modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gerencia.service.FuncionarioService;

//EventoUtil.initButtons((TableColumn<Funcionario, Funcionario>) 
//colunaEditar, 30, "Editar", "svg-gray", (Funcionario funcionario, ActionEvent event) -> {
//System.out.println("Você clicou para editar as informações de: " + funcionario.getNomeFuncionario());
////TODO Aqui vai toda a lógica para editar a pessoa
//});
//
//
//EventoUtil.initButtons((TableColumn<Funcionario, Funcionario>) 
//colunaExcluir, 30, "Excluir", "svg-gray", (Funcionario funcionario, ActionEvent event) -> {
//	System.out.println("Você clicou para editar as informações de: " + funcionario.getNomeFuncionario());
//	//TODO Aqui vai toda a lógica para editar a pessoa
//});
//
//
//
//Image imagem = new Image(getClass().getResourceAsStream("/assets/img_1.png"));
//imagemView.setImage(imagem);
//
//Image imagem2 = new Image(getClass().getResourceAsStream("/assets/img2.png"));
//imagemView2.setImage(imagem2);

public class Teste {

	public static void main(String[] args) throws SQLException {
		List<Funcionario> fs = new ArrayList<>();
		Funcionario f1 = new Funcionario();
//		f1.setIdFuncionario(2);
//		f1.setNomeFuncionario("Alterado do 2");
//		f1.setCargaHorariaSemanal(40);
		f1.setNomeFuncionario("Aterado");
		f1.setCargaHorariaSemanal(30);
		f1.setCor("4.1,0.2,0.1,3.3");
		List<DiasSemana> dias = new ArrayList<DiasSemana>() {{
//			add(DiasSemana.QA);
//			add(DiasSemana.SA);
		}};
		f1.setDiasSemanas(dias);
		f1.setIdFuncionario(3);
		

		FuncionarioService service = new FuncionarioService();
		
		fs = service.todos();
		System.out.println(fs);
//		DiasSemana d = DiasSemana.valueOf("QI");
//		System.out.println(d);
//		System.out.println(d.name());
//		service.alterarDiasSemana(f1);
//		System.out.println(service.alterar(f1));
		
		
//		service.consultar("SELECT * FROM funcionario");
//		System.out.println(service.inserir(f1));
//		System.out.println(service.alterar( f1));
//		System.out.println(service.excluir(3));


		
		
	/*
	  	 try {

		        Class.forName("org.sqlite.JDBC");//force Java ClassLoader to load class
		        DriverManager.registerDriver(new org.sqlite.JDBC());//register class with DriverManager
		        Connection connection = DriverManager.getConnection("/gerencia/repositorio/gerencia.db");
		        
		        
		      
		    } catch (ClassNotFoundException | SQLException classNotFoundException) {
		       
		    }
		 List<Funcionario> fs = new ArrayList<>();
		 String query = "SELECT * FROM funcionario";
		    try (Connection connection = DriverManager.getConnection("/gerencia/repositorio/gerencia.db");) {
		        PreparedStatement statement = connection.prepareStatement(query);
		        ResultSet rs = statement.executeQuery();
		        funcionarios.clear();
		        while (rs.next()) {
		        	rs.getString(f1.getNomeFunc());
		        	rs.getInt(f1.getCargaHorariaSemanal());
		        	
		            fs.add(f1);
		        }
		    } catch (SQLException e) {
		      
		        fs.clear();
		    }
		    ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList(fs);
		    for(Funcionario f: fs) {
		    	System.out.println(f.getNomeFunc() + "-> nome -> ");
		    }	 */

	}

}
