package asktechforum.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import asktechforum.util.ConnectionUtil;

/**
 * Repositorio de dados para o objeto Voto
 */
public class RepositorioVoto {
	private Connection connection = null;
	
	/**
	 * Construtor vazio
	 */
	public RepositorioVoto() {
	}
	
	/**
	 * Metodo responsavel por inserir um voto
	 * @param idUsuario- Id do usuario autor do voto
	 * @param idResposta- Id da resposta que recebera o voto
	 * @throws SQLException - Excecao caso ocorra na insercao
	 */
	public void inserirVoto(int idUsuario, int idResposta) throws SQLException {
		String sql = "insert into voto(idUsuario, idResposta)values( ?, ? )";
		PreparedStatement preparedStatement = null;
		
		try {
			this.connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idUsuario);
			preparedStatement.setInt(2, idResposta);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			this.connection.close();
		}
	}
	
	/**
	 * Metodo responsavel por deletar um voto
	 * @param idUsuario- Id do usuario autor do voto
	 * @param idResposta- Id da resposta que recebeu o voto
	 * @throws SQLException - Excecao caso ocorra na delecao
	 */
	public void deletarVoto(int idUsuario, int idResposta) throws SQLException {
		PreparedStatement preparedStatement = null;
        try {
    		this.connection = ConnectionUtil.getConnection();
            preparedStatement = this.connection
                    .prepareStatement("delete from voto where idUsuario=? and idResposta=?");

			preparedStatement.setInt(1, idUsuario);
			preparedStatement.setInt(2, idResposta);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            this.connection.close();
        }
	}
	
	/**
	 * Metodo responsavel por consultar voto
	 * @param idUsuario- Id do usuario autor do voto
	 * @param idResposta- Id da resposta que recebeu o voto
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public Boolean consultarVoto(int idUsuario, int idResposta) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Boolean flag = true;
		try {
			this.connection = ConnectionUtil.getConnection();
			preparedStatement = this.connection
					.prepareStatement("select * from voto where idUsuario=? and idResposta=?");

			preparedStatement.setInt(1, idUsuario);
			preparedStatement.setInt(2, idResposta);
			rs = preparedStatement.executeQuery();
			
			if(rs.next() == false) {
				flag = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            preparedStatement.close();
            rs.close();
            this.connection.close();
        }
		
		return flag;
	}
	
}