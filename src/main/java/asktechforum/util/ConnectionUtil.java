package asktechforum.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Representa a conexao com o banco de dados
 *
 */
public class ConnectionUtil {

	private static Connection connection = null;
	
	/**
	 * Metodo responsavel por criar uma conexao com o banco de dados
	 * @return - uma conexao disponivel para uso
	 */
	public static Connection getConnection() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/asktechforum";
			String user = "root";
			String password = "root";
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}