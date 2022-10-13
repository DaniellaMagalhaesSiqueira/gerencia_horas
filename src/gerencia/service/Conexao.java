package gerencia.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

	private static Connection conexao;

	static {

		try {

			Class.forName("org.sqlite.JDBC");// force Java ClassLoader to load class
			DriverManager.registerDriver(new org.sqlite.JDBC());// register class with DriverManager

			conexao = DriverManager.getConnection("jdbc:sqlite:gerencia.db");

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Bloco estático de conexão ->" + e.getMessage());
		}
	}

	public Conexao() {

	}


	public static ResultSet consulta(String query) {
//		logger.info("Iniciando query");
		try (Connection conn = Database.connect()){
			PreparedStatement statement = conexao.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			statement.executeUpdate();
			return rs;
		} catch (Exception e) {
//			logger.error("Não foi possível realizar a query.\nFalha: " + e.getMessage() + "\n"+ mensagem);
			System.out.println("Consulta com ResultSet -> " + e.getMessage());
			return null;
		}
	}

}
