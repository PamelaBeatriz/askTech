package asktechforum.negocio;

import java.sql.SQLException;

import asktechforum.repositorio.RepositorioVoto;

/**
 * Classe que lida com as regras de negocio para cadastro de Voto
 */
public class VotoBC {
	private RepositorioVoto votoDAO;
	
	/**
	 * Construtor
	 */
	public VotoBC() {
		this.votoDAO = new RepositorioVoto();
	}
	
	/**
	 * Metodo responsavel por inserir um voto
	 * @param idUsuario- Id do usuario autor do voto
	 * @param idResposta- Id da resposta que recebera o voto
	 */
	public void inserirVoto(int idUsuario, int idResposta) {
		try {
			this.votoDAO.inserirVoto(idUsuario, idResposta);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo responsavel por deletar um voto
	 * @param idUsuario- Id do usuario autor do voto
	 * @param idResposta- Id da resposta que recebeu o voto
	 */
	public void deletarVoto(int idUsuario, int idResposta) {
		try {
			this.votoDAO.deletarVoto(idUsuario, idResposta);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo responsavel por consultar voto
	 * @param idUsuario- Id do usuario autor do voto
	 * @param idResposta- Id da resposta que recebeu o voto
	 */
	public Boolean consultarVoto(int idUsuario, int idResposta) {
		Boolean flag = true;
		try {
			flag = this.votoDAO.consultarVoto(idUsuario, idResposta);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
}