package asktechforum.repositorio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Pergunta;
import asktechforum.dominio.ResultConsultarPergunta;
import asktechforum.interfaces.RepositorioPergunta;
import asktechforum.util.ConnectionUtil;

/**
 * Repositorio de dados para o objeto Pergunta
 */
public class RepositorioPerguntaBDR implements RepositorioPergunta {

	private Connection con = null;
	
	/**
	 * Construtor vazio
	 */
	public RepositorioPerguntaBDR() {
	}
	
	/**
	 * Metodo responsavel por inserir uma Pergunta no banco de dados
	 * @param pergunta- Pergunta a ser inserida
	 * @return status da acao
	 * @throws SQLException - Excecao caso ocorra na insercao
	 */
	public String inserirPergunta(Pergunta pergunta) throws SQLException {
		String retorno = "cadastroSucesso";

		String sql = "insert into PERGUNTA(titulo, data, hora, descricao, idUsuario, tag)values(?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		this.con = ConnectionUtil.getConnection();
		try {
			stmt = this.con.prepareStatement(sql);
			int index = 0;
			stmt.setString(++index, pergunta.getTitulo());
			stmt.setDate(++index, pergunta.getData());
			stmt.setTime(++index, pergunta.getHora());
			stmt.setString(++index, pergunta.getDescricao());
			stmt.setInt(++index, pergunta.getIdUsuario());
			stmt.setString(++index, pergunta.getTag());

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
	 * Metodo responsavel por detetar uma Pergunta
	 * @param id - Id da pergunta
	 * @throws SQLException - Excecao caso ocorra erro na delecao
	 */
	public void deletarPergunta(int id) throws SQLException {

		String sql = "delete from PERGUNTA where idPergunta = " + id;
		PreparedStatement stmt = null;
		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			this.con.close();
		}

	}
	
	/**
	 * Metodo responsavel por consultar Pergunta fazendo uso de um ID
	 * @param Id - Id da pergunta
	 * @return Pergunta encontrada
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	public Pergunta consultarPerguntaPorId(int id) throws SQLException {

		Pergunta pergunta = new Pergunta();

		String sql = "select * from PERGUNTA where idPergunta = " + id;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				pergunta.setDescricao(rs.getString("descricao"));
				pergunta.setIdPergunta(rs.getInt("idPergunta"));
				pergunta.setTitulo(rs.getString("titulo"));
				pergunta.setIdUsuario(rs.getInt("idUsuario"));
				pergunta.setData(rs.getDate("data"));
				pergunta.setHora(rs.getTime("hora"));
				pergunta.setTag(rs.getString("tag"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return pergunta;
	}

	/**
	 * Metodo responsavel por consultar pergunta atraves do Id de um usuario
	 * @param id- Id do usuario
	 * @return Lista de perguntas encontradas	 
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	public ArrayList<Pergunta> consultarPerguntaIdUsuario(int id)
			throws SQLException {
		ArrayList<Pergunta> pergunta = new ArrayList<Pergunta>();

		String sql = "select * from PERGUNTA where idUsuario = " + id
				+ " order by data, hora";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		this.con = ConnectionUtil.getConnection();
		try {
			stmt = this.con.prepareStatement(sql);

			rs = stmt.executeQuery();

			pergunta = carregarListaPerguntas(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return pergunta;
	}

	/**
	 * Metodo responsavel por consultar todas as perguntas
	 * @return Lista de perguntas encontradas
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	public ArrayList<Pergunta> consultarTodasPerguntas() throws SQLException {
		ArrayList<Pergunta> pergunta = new ArrayList<Pergunta>();

		String sql = "select * from PERGUNTA order by data, hora";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);

			rs = stmt.executeQuery();

			pergunta = carregarListaPerguntas(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return pergunta;
	}
	
	/**
	 * Metodo responsavel por consultar todas as Tags de perguntas
	 * @return Lista com todas as tags encontradas
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	public ArrayList<String> consultaTodasAsTags() throws SQLException {
		ArrayList<String> tags = new ArrayList<String>();
		
		String sql = "SELECT DISTINCT tag FROM pergunta order by tag";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);

			rs = stmt.executeQuery();
			tags = this.separaTags(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return tags;
		
	}
	
	/**
	 * Metodo responsavel por fazer tratamento de espaco entre as Tags
	 * @param rs - Retorno da consulta contedo as tags que serao tratadas
	 * @return Lista de Tags tratadas
	 */
	private ArrayList<String> separaTags(ResultSet rs){
		ArrayList<String> listTags = new ArrayList<String>();
		String [] resultTag ;
		
		try {
			while (rs.next()) {
				resultTag = rs.getString("tag").split(" ");
				
				for(int i=0; i<resultTag.length; i++ ){
					listTags.add(resultTag[i].toUpperCase());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return listTags;
	}
	
	/**
	 * Metodo responsavel por consultar pergunta atraves de uma data
	 * @param data- Data da pergunta
	 * @return Lista de perguntas encontradas	 
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	public ArrayList<Pergunta> consultarPerguntaPorData(Date data)
			throws SQLException {
		ArrayList<Pergunta> pergunta = new ArrayList<Pergunta>();

		String sql = "select * from PERGUNTA where idUsuario = " + data
				+ " order by hora";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);

			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Pergunta p = new Pergunta();
				p.setDescricao(rs.getString("descricao"));
				p.setIdPergunta(rs.getInt("idPergunta"));
				p.setTitulo(rs.getString("titulo"));
				p.setIdUsuario(rs.getInt("idUsuario"));
				p.setData(rs.getDate("data"));
				p.setHora(rs.getTime("hora"));
				p.setTag(rs.getString("tag"));
				pergunta.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return pergunta;
	}
	/**
	 * Metodo responsavel por consultar pergunta atraves de uma Tag
	 * @param tag -  Tag da pergunta
	 * @return Lista de perguntas encontradas	 
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	@Override
	public ArrayList<ResultConsultarPergunta> consultarPerguntaPorTag(String tag)
			throws SQLException {
		ArrayList<ResultConsultarPergunta> pergunta = new ArrayList<ResultConsultarPergunta>();

		String sql = " select p.descricao, count(r.idResposta) total, u.nome, p.idPergunta, p.titulo, p.data, p.hora , p.idUsuario" +
				"  from usuario u left join pergunta p on u.idUsuario = p.idUsuario " +
				"		left join resposta r on p.idPergunta = r.idPergunta " +
				"		where p.tag like '%"+ tag +"%'  group by u.nome, p.idPergunta ; ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);

			rs = stmt.executeQuery();

			ResultConsultarPergunta p;
			
			while(rs.next()){
				p = new ResultConsultarPergunta();
				p.setIdUsuario(rs.getInt("idUsuario"));
				p.setAutor(rs.getString("nome"));
				p.setDescricao(rs.getString("descricao"));
				p.setQtdResposta(rs.getInt("total"));
				p.setIdPergunta(rs.getInt("idPergunta"));
				p.setTitulo(rs.getString("titulo"));
				p.setData(rs.getDate("data"));
				p.setHora(rs.getTime("hora"));
				pergunta.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return pergunta;
	}

	
	/**
	 * Metodo responsavel por consultar pergunta atraves de todas as Tags existentes
	 * @param tag -  Tag da pergunta
	 * @return Lista de perguntas encontradas	 
	 * @throws SQLException - Excecao caso ocorra erro na consulta
	 */
	@Override
	public ArrayList<ResultConsultarPergunta> consultarPerguntaPorTodasTags()
			throws SQLException {
		ArrayList<ResultConsultarPergunta> pergunta = new ArrayList<ResultConsultarPergunta>();
		
		String sql = "SELECT p.descricao, COUNT( r.idResposta ) total, u.nome, p.idPergunta, p.titulo, p.tag, p.data, p.hora, p.idUsuario" +
		" FROM usuario u LEFT JOIN pergunta p ON u.idUsuario = p.idUsuario 	LEFT JOIN resposta r ON p.idPergunta = r.idPergunta " +
		" GROUP BY u.nome, p.idPergunta	ORDER BY p.data DESC , p.hora DESC LIMIT 0 , 15 ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.con = ConnectionUtil.getConnection();
			stmt = this.con.prepareStatement(sql);

			rs = stmt.executeQuery();

			ResultConsultarPergunta p;
			
			while(rs.next()){
				p = new ResultConsultarPergunta();
				p.setIdUsuario(rs.getInt("idUsuario"));
				p.setAutor(rs.getString("nome"));
				p.setDescricao(rs.getString("descricao"));
				p.setQtdResposta(rs.getInt("total"));
				p.setIdPergunta(rs.getInt("idPergunta"));
				p.setTitulo(rs.getString("titulo"));
				p.setTag(rs.getString("tag"));
				p.setData(rs.getDate("data"));
				p.setHora(rs.getTime("hora"));
				pergunta.add(p);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			this.con.close();
		}

		return pergunta;
	}
	/**
	 * Metodo responsavel por carregar uma lista de perguntas
	 * @param rs -  Resultado de uma consulta
	 * @return Lista de perguntas	 
	 * @throws SQLException - Excecao caso ocorra no carregamento dos dados
	 */
	private ArrayList<Pergunta> carregarListaPerguntas(ResultSet rs)
			throws SQLException {
		ArrayList<Pergunta> pergunta = new ArrayList<Pergunta>();

		try {
			this.con = ConnectionUtil.getConnection();
			while (rs.next()) {
				Pergunta p = new Pergunta();
				p.setDescricao(rs.getString("descricao"));
				p.setIdPergunta(rs.getInt("idPergunta"));
				p.setTitulo(rs.getString("titulo"));
				p.setIdUsuario(rs.getInt("idUsuario"));
				p.setData(rs.getDate("data"));
				p.setHora(rs.getTime("hora"));
				p.setTag(rs.getString("tag"));
				pergunta.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			rs.close();
			this.con.close();
		}

		return pergunta;
	}
	/**
	 * Metodo responsavel por alterar uma pergunta
	 * @return Status da acao
	 * @throws SQLException - Excecao caso erro na alteracao da pergunta
	 */
	@Override
	public String alterarPergunta(Pergunta pergunta) throws SQLException {
		String retorno = "alteracaoSucesso";

		String sql = "update PERGUNTA set titulo=?, data=?, hora=?, descricao=?, idUsuario=?, tag=?  where idPergunta = ?";
		PreparedStatement stmt = null;
		this.con = ConnectionUtil.getConnection();
		try {
			stmt = this.con.prepareStatement(sql);
			int index = 0;
			stmt.setString(++index, pergunta.getTitulo());
			stmt.setDate(++index, pergunta.getData());
			stmt.setTime(++index, pergunta.getHora());
			stmt.setString(++index, pergunta.getDescricao());
			stmt.setInt(++index, pergunta.getIdUsuario());
			stmt.setString(++index, pergunta.getTag());
			stmt.setInt(++index, pergunta.getIdPergunta());

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
