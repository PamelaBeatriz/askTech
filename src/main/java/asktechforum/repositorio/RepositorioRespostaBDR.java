package asktechforum.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Pergunta;
import asktechforum.dominio.Resposta;
import asktechforum.dominio.Usuario;
import asktechforum.interfaces.RepositorioResposta;
import asktechforum.util.ConnectionUtil;

/**
 * Repositorio de dados para o objeto Resposta
 */
public class RepositorioRespostaBDR implements RepositorioResposta {

	private Connection con = null;

	/**
	 * Construtor vazio
	 */
	public RepositorioRespostaBDR() {
	}
	
	/**
	 * Metodo responsavel por inserir uma resposta
	 * @param Resposta a ser inserida
	 * @return String com status da acao
	 * @throws SQLException - Excecao caso ocorra na insercao
	 */
	public String inserirResposta(Resposta resposta) throws SQLException {
		String retorno = "cadastroSucesso";
		String sql = "insert into RESPOSTA(descricao, idUsuario, idPergunta, data, hora)values(?,?,?,?,?)";
		PreparedStatement stmt = null;
		
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int index = 0;
			stmt.setString(++index, resposta.getDescricao());
			stmt.setInt(++index, resposta.getIdUsuario());
			stmt.setInt(++index, resposta.getIdPergunta());
			stmt.setDate(++index, resposta.getData());
			stmt.setTime(++index, resposta.getHora());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			this.con.close();
		}
		return retorno;
	}
	
	/**
	 * Metodo utilizado para deletar uma resposta
	 * @param id - Id da resposta a ser deletada
	 * @throws SQLException - Excecao caso ocorra na delecao
	 */
	public void deletarResposta(int id) throws SQLException {
		String sql = "delete from RESPOSTA where idResposta = " + id;
		PreparedStatement stmt = null;
		
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			this.con.close();
		}
	}

	/**
	 * Metodo responsavel por consultar resposta por id
	 * @param id - Id da resposta
	 * @return Resposta encontrada
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public Resposta consultarRespostaPorId(int id) throws SQLException {
		Resposta resposta = new Resposta();

		String sql = "select * from RESPOSTA where idResposta = " + id;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				resposta.setData(rs.getDate("data"));
				resposta.setDescricao(rs.getString("descricao"));
				resposta.setHora(rs.getTime("hora"));
				resposta.setIdPergunta(rs.getInt("idPergunta"));
				resposta.setIdResposta(rs.getInt("idResposta"));
				resposta.setIdUsuario(rs.getInt("idUsuario"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return resposta;
	}

	/**
	 * Metodo responsavel por consultar respostas atraves de um id de usuario
	 * @return Lista de respostas encontradas
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public ArrayList<Resposta> consultarRespostaPorIdUsuario(int id)
			throws SQLException {

		ArrayList<Resposta> resposta = new ArrayList<Resposta>();

		String sql = "select * from RESPOSTA where idUsuario = " + id
				+ " order by data, hora";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Resposta r = new Resposta();
				r.setData(rs.getDate("data"));
				r.setDescricao(rs.getString("descricao"));
				r.setHora(rs.getTime("hora"));
				r.setIdPergunta(rs.getInt("idPergunta"));
				r.setIdResposta(rs.getInt("idResposta"));
				r.setIdUsuario(rs.getInt("idUsuario"));
				resposta.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return resposta;
	}
	
	/**
	 * Metodo responsavel por consultar todas as respostas
	 * @return Lista com todas as respostas encontradas
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public ArrayList<Resposta> consultarTodasRespostas() throws SQLException {
		ArrayList<Resposta> resposta = new ArrayList<Resposta>();

		String sql = "select * from Resposta order by data, hora";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Resposta r = new Resposta();
				r.setData(rs.getDate("data"));
				r.setDescricao(rs.getString("descricao"));
				r.setHora(rs.getTime("hora"));
				r.setIdPergunta(rs.getInt("idPergunta"));
				r.setIdResposta(rs.getInt("idResposta"));
				r.setIdUsuario(rs.getInt("idUsuario"));
				resposta.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return resposta;
	}
	
	/**
	 * Metodo responsavel por consultar respostas atraves do id de uma pergunta
	 * @param id- IdPergunta
	 * @return Lista de respostas encontradas
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public ArrayList<Resposta> consultarRespostaPorPergunta(int id)
			throws SQLException {
		ArrayList<Resposta> resposta = new ArrayList<Resposta>();		
		
		String sql = "SELECT u.nome, r.idResposta, r.descricao, r.idUsuario, r.idPergunta, r.data, r.hora , r.votos FROM usuario u, resposta r	" +
				"WHERE idPergunta=" + id + " and u.idUsuario = r.idUsuario order by data, hora ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Resposta r = new Resposta();
				r.setData(rs.getDate("data"));
				r.setDescricao(rs.getString("descricao"));
				r.setHora(rs.getTime("hora"));
				r.setIdPergunta(rs.getInt("idPergunta"));
				r.setIdResposta(rs.getInt("idResposta"));
				r.setIdUsuario(rs.getInt("idUsuario"));
				r.setNomeUsuario(rs.getString("nome"));
				r.setVotos(rs.getInt("votos"));
				resposta.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return resposta;
	}
	
	/**
	 * Metodo responsavel por adicionar um voto em uma resposta
	 * @param id - Id da resposta
	 * @throws SQLException - Excecao caso ocorra na adicao do voto
	 */
	public void adcionarVotoResposta(int id) throws SQLException{
		String sql = "update resposta set votos = votos + 1 where idResposta = ?";
		PreparedStatement stmt = null;
		
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);
			
			int index = 0;
			stmt.setInt(++index, id);	

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			this.con.close();
		}
	}
	
	/**
	 * Metodo responsavel por remover um voto de uma resposta
	 * @param id - Id da resposta
	 * @throws SQLException - Excecao caso ocorra na remocao
	 */
	public void removerVotoResposta(int id) throws SQLException{
		String sql = "update resposta set votos = votos - 1 where idResposta = ?";
		PreparedStatement stmt = null;
		
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);
			
			int index = 0;
			stmt.setInt(++index, id);	

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			this.con.close();
		}
	}

	/**
	 * Metodo responsavel por consultar todos os usuarios que responderam um determinada pergunta 
	 * @param id da pergunta
	 * @return Lista de usuarios que responderam a pergunta
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public ArrayList<Usuario> consultarContribuintesPergunta(int id)
			throws SQLException {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();		
				
		String sql =  "SELECT distinct u.email, u.nome, r.idUsuario, p.titulo FROM usuario u, resposta r, pergunta p " +
		"WHERE p.idPergunta = r.idPergunta 	and p.idPergunta= "+ id +" and u.idUsuario = r.idUsuario ;";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Usuario u = new Usuario();
				Pergunta p = new Pergunta();
				
				u.setIdUsuario(rs.getInt("idUsuario"));
				u.setNome(rs.getString("nome"));
				u.setEmail(rs.getString("email"));
				p.setTitulo(rs.getString("titulo"));
				
				u.setPergunta(p);
				usuarios.add(u);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return usuarios;
	}

	/**
	 * Metodo responsavel por consultar uma usuario autor de uma pergunta 
	 * @param id da pergunta
	 * @return Usuario - autor da pergunta
	 * @throws SQLException - Excecao caso ocorra na consulta
	 */
	public Usuario consultarAutorPergunta(int id)
	 			throws SQLException {
	 		Usuario usuario = new Usuario();
	 		Pergunta pergunta = new Pergunta();
	 		
	 		String sql = "SELECT  u.email, u.nome, u.idUsuario,p.titulo FROM usuario u, pergunta p " +
	 				"WHERE p.idUsuario = u.idUsuario and p.idPergunta = " + id;
	 	
	 		
	 		PreparedStatement stmt = null;
	 	ResultSet rs = null;
	 
	 		try {
	 			this.con = ConnectionUtil.getConnection();
	 			stmt = con.prepareStatement(sql);
	 
	 			rs = stmt.executeQuery();
	 			
	 			if(rs.next()){
	 				
	 				usuario.setIdUsuario(rs.getInt("idUsuario"));
	 				usuario.setEmail(rs.getString("email"));
	 				usuario.setNome(rs.getString("nome"));
	 				pergunta.setTitulo(rs.getString("titulo"));
	 				usuario.setPergunta(pergunta);
	 				
	 			}
	 
	 		} catch (SQLException e) {
	 			e.printStackTrace();
	 		} finally {
	 			rs.close();
	 			stmt.close();
	 			this.con.close();
	 		}
	 
		return usuario;
}
	/**
	 * Metodo responsavel por alterar uma resposta
	 * @param resposta - Resposta a ser alterada
	 * @throws SQLException - Excecao caso ocorra na alteracao da resposta
	 */
	@Override
	public String alterarResposta(Resposta resposta) throws SQLException {
		String retorno = "alteracaoSucesso";
		String sql = "update RESPOSTA set descricao=?, idUsuario=?, idPergunta=?, data=?, hora=? where idResposta = ?";
		PreparedStatement stmt = null;
		
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int index = 0;
			stmt.setString(++index, resposta.getDescricao());
			stmt.setInt(++index, resposta.getIdUsuario());
			stmt.setInt(++index, resposta.getIdPergunta());
			stmt.setDate(++index, resposta.getData());
			stmt.setTime(++index, resposta.getHora());
			stmt.setInt(++index, resposta.getIdResposta());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			this.con.close();
		}
		return retorno;
	}
}
