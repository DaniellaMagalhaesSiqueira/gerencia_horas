package gerencia.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gerencia.modelo.DiasSemana;
import gerencia.modelo.Funcionario;
import gerencia.util.AlertUtil;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class FuncionarioService {

//	static final Logger logger = LogManager.getLogger(FuncionarioService.class.getName());

	public FuncionarioService() {
		try {
			Class.forName("org.sqlite.JDBC");// force Java ClassLoader to load class
			DriverManager.registerDriver(new org.sqlite.JDBC());// register class with DriverManager
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("FuncionarioService construtor -> " + e.getMessage());
		}

	}

	public int inserir(Funcionario funcionario) {
		int id = 0;
		StringBuilder query = new StringBuilder("INSERT INTO " + funcionario.getTabela());
		query.append("(nomeFuncionario, cargaHoraria, cor");
		query.append(") VALUES (");
		query.append("'" + funcionario.getNomeFuncionario() + "',");
		query.append("" + funcionario.getCargaHorariaSemanal() + ",");
		query.append("'" + funcionario.getCor() + "');");
		
		try (Connection conn = Database.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			 int affectedRows = pstmt.executeUpdate();
			 if(affectedRows > 0) {
				 id = pstmt.getGeneratedKeys().getInt(1);
			 }
		} catch (SQLException e) {
			System.out.println("Inserindo funcionario service -> " + e.getMessage());
			return -1;
		}
		if(funcionario.getDiasSemanas().isEmpty() || funcionario.getDiasSemanas() == null) {
			AlertUtil.showAlert("Dias da Semana", "A Seleção de dias da semana está vazia", 
					"Por favor, selecione os dias da semana em que o funcionário trabalha", AlertType.WARNING);
		}else {	
			inserirDiaSemana(id, funcionario.getDiasSemanas());
		}
		return id;
	}
	
	public int inserirDiaSemana(int id, List<DiasSemana> dias) {
		StringBuilder query = new StringBuilder("INSERT INTO funcionario_semana ");
		query.append("(idFuncionario, diasemana) VALUES ");
		for(int i = 0; i < dias.size(); i++) {
		query.append("(");
		query.append(id + ",");
		query.append("'" + dias.get(i).name() + "'),");
		}
		query.replace(query.length() - 1, query.length(), ";");
		try (Connection conn = Database.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows > 0) {
				id = pstmt.getGeneratedKeys().getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Inserindo Dias Semana service -> " + e.getMessage());
			return -1;
		}
		return id;
	}
	
	public int excluir(int id) {
		excluirDiasSemana(id);
		 String sql = "DELETE FROM funcionario WHERE idFuncionario = ?";
		 try (Connection conn = Database.connect()) {
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, id);
	            return pstmt.executeUpdate();

	        } catch (SQLException e) {
	        	System.out.println("Excluindo funcionario service -> " + e.getMessage()  + "\nCausa: "+  e.getCause());
	            return -1;
	        }
	}
	
	public int alterar(Funcionario funcionario) {
		 String sql = "UPDATE funcionario SET ";
		 	sql += "nomeFuncionario = '" + funcionario.getNomeFuncionario() + "',";
			sql += "cargaHoraria = " + funcionario.getCargaHorariaSemanal() + ",";
			sql += "cor = '" + funcionario.getCor() + "'";
			sql += " WHERE idFuncionario = " + funcionario.getIdFuncionario();

			
		 try (Connection conn = Database.connect()) {
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.executeUpdate();

	        } catch (SQLException e) {
	        	System.out.println("Alterando funcionario service -> " + e.getMessage() + "\nCausa: "+ e.getCause());
	        	return -1;
	        }
		 return alterarDiasSemana(funcionario);
		 	
	}
	
	public int alterarDiasSemana(Funcionario funcionario) {
		if(excluirDiasSemana(funcionario.getIdFuncionario()) > 0) {
			if(inserirDiaSemana(funcionario.getIdFuncionario(), funcionario.getDiasSemanas()) > 0) {
				return 1;
			}
		}
		return -1;
	}
	

	public int excluirDiasSemana(int id) {
		 String sql = "DELETE FROM funcionario_semana WHERE idFuncionario = ?";
		 try (Connection conn = Database.connect()) {
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, id);
	            return pstmt.executeUpdate();

	        } catch (SQLException e) {
	        	System.out.println("Excluindo funcionario service -> " + e.getMessage()  + "\nCausa: "+  e.getCause());
	            return -1;
	        }
	}

	
	public List<Funcionario> todos(){
		List<Funcionario> fs = new ArrayList<>();
		try (Connection connection = Database.connect()) {
	        PreparedStatement statement = connection.prepareStatement("Select * from funcionario");
	        ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
				funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
				funcionario.setCargaHorariaSemanal(rs.getInt("cargaHoraria"));
				funcionario.setCor(rs.getString("cor"));
				funcionario.setDiasSemanas(obterDias(funcionario.getIdFuncionario()));
				fs.add(funcionario);
			}
		} catch (SQLException e) {
			System.out.println("Consulta todos -> " + e.getMessage());
			e.printStackTrace();
		}
		return fs;
	}
	
	public List<DiasSemana> obterDias(int id){
		List<DiasSemana> dias = new ArrayList<>();		
		try (Connection connection = Database.connect()) {
	        PreparedStatement statement = connection.prepareStatement("Select * from funcionario_semana "
	        		+ "WHERE idFuncionario = " + id + ";");
	        ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				DiasSemana d = DiasSemana.valueOf(DiasSemana.class, rs.getString("diasemana"));
				dias.add(d);
			}
		} catch (SQLException e) {
			System.out.println("Consulta todos -> " + e.getMessage());
			e.printStackTrace();
		}
		
		
		return dias;
		
	}
	public Funcionario obterUm(int id){
		Funcionario f = new Funcionario();
		try (Connection connection = Database.connect()) {
			PreparedStatement statement = connection.prepareStatement("Select * from funcionario "
					+ "WHERE idFuncionario = " + id + ";");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				f.setIdFuncionario(id);
				f.setNomeFuncionario(rs.getString("nomeFuncionario"));
				f.setCargaHorariaSemanal(rs.getInt("cargaHoraria"));
				f.setCor(rs.getString("cor"));
				f.setDiasSemanas(obterDias(id));
				return f;
			}
		} catch (SQLException e) {
			System.out.println("Consulta todos -> " + e.getMessage());
			e.printStackTrace();
		}
		
		
		return f;
		
	}

//	public int ultimo() {
//		List<Funcionario> fs = todos();
//		int ul = 0;
//		for(Funcionario f: fs) {
//			if(f.getIdFuncionario() > ul) {
//				ul = f.getIdFuncionario();
//			}
//		}
//		
//		return ul;
//	}
//	
//	public int ultimoTabelaSemana() {
//		int id = 0;
//		try (Connection connection = Database.connect()) {
//	        PreparedStatement statement = connection.prepareStatement("Select * from funcionario");
//	        ResultSet rs = statement.executeQuery();
//			while (rs.next()) {
//				if(id < rs.getInt("idFuncionarioDiaSemana")) {					
//					id = rs.getInt("idFuncionarioDiaSemana");
//				}
//			}
//		} catch (SQLException e) {
//			System.out.println("Consulta todos tabela dias da semana -> " + e.getMessage());
//			e.printStackTrace();
//		}
//		return id;
//	}
	
	
	public void consultarFuncionario(String consulta) {
		
		 try (Connection conn = Database.connect()) {
	            PreparedStatement pstmt = conn.prepareStatement(consulta);
	            ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Funcionario funcionario = new Funcionario();
					funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
					funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
					funcionario.setCargaHorariaSemanal(rs.getInt("cargaHoraria"));
					System.out.println(funcionario.toString());
				}

	        } catch (SQLException e) {
	        	System.out.println("Excluindo funcionario service -> " + e.getMessage()  + "\nCausa: "+  e.getCause());
	        }
	}
}
