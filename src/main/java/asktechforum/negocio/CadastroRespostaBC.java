package asktechforum.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Email;
import asktechforum.dominio.Resposta;
import asktechforum.dominio.Usuario;
import asktechforum.interfaces.RepositorioResposta;
import asktechforum.repositorio.RepositorioRespostaBDR;

/**
 * Classe que lida com as regras de negocio para cadastro de Resposta
 */
public class CadastroRespostaBC implements RepositorioResposta {

	private RepositorioRespostaBDR cadastro;
	private ArrayList<Resposta> lstResposta;

	/**
	 * Construtor
	 */
	public CadastroRespostaBC() {
		cadastro = new RepositorioRespostaBDR();
	}
	
	/**
	 * Metodo responsavel por inserir uma resposta
	 * @param Resposta a ser inserida
	 * @return String com status da acao
	 */
	@Override
	public String inserirResposta(Resposta resposta) {
		String msg = "";
		try {
			String msgErro = this.verificaCampos(resposta);
			 if(msgErro.equals("")){
				msg = cadastro.inserirResposta(resposta);
			}else{
				msg =  msgErro;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/** Metodo responsavel por enviar email para todos os usuarios que responderam 
	 * uma pergunta e para o autor dessa pergunta.
	 * Isso acontece quando uma nova resposta e cadastrada.
	 * Obs.: O ultimo usuario que respondeu a pergunta nao recebera esse email. 
	 * 
	 * @param idPergunta - Id da pergunta que recebeu a resposta
	 * @param idUsuario - Id do ultimo usuario que respondeu
	 */
	public void notificarContribuintesPerg(int idPergunta, int idUsuario){

		try {
			Usuario autorPergunta = cadastro.consultarAutorPergunta(idPergunta);
			ArrayList<Usuario> usuarios = cadastro.consultarContribuintesPergunta(idPergunta);

			Email email = new Email();

			if(idUsuario != autorPergunta.getIdUsuario()){
				email.sendEmailAutor(autorPergunta.getNome(), autorPergunta.getEmail(), autorPergunta.getPergunta().getTitulo());
			}

			for(int i = 0; i < usuarios.size(); i++){
				if(idUsuario != usuarios.get(i).getIdUsuario()){
					email.sendEmailAutor(usuarios.get(i).getNome(), usuarios.get(i).getEmail(), usuarios.get(i).getPergunta().getTitulo());
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizado para deletar uma resposta
	 * @param id - Id da resposta a ser deletada
	 */
	@Override
	public void deletarResposta(int id) {
		try {
			if (id == 0) {

			} else {
				cadastro.deletarResposta(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por consultar resposta por id
	 * @param id - Id da resposta
	 * @return Resposta encontrada
	 */
	@Override
	public Resposta consultarRespostaPorId(int id)  {
		Resposta resposta = new Resposta();
		try {
			if (id == 0) {

			} else {
				resposta = cadastro.consultarRespostaPorId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resposta;
	}
	
	/**
	 * Metodo responsavel por consultar respostas atraves de um id de usuario
	 * @return Lista de respostas encontradas
	 */
	@Override
	public ArrayList<Resposta> consultarRespostaPorIdUsuario(int id)
			throws SQLException {
		lstResposta = new ArrayList<Resposta>();
		try {
			if (id == 0) {

			} else {
				lstResposta = cadastro.consultarRespostaPorIdUsuario(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstResposta;
	}
	
	/**
	 * Metodo responsavel por consultar todas as respostas
	 * @return Lista com todas as respostas encontradas
	 */
	@Override
	public ArrayList<Resposta> consultarTodasRespostas() throws SQLException {
		lstResposta = new ArrayList<Resposta>();
		try {
			lstResposta = cadastro.consultarTodasRespostas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstResposta;
	}
	
	/**
	 * Metodo responsavel por consultar respostas atraves do id de uma pergunta
	 * @param id- IdPergunta
	 * @return Lista de respostas encontradas
	 */
	@Override
	public ArrayList<Resposta> consultarRespostaPorPergunta(int id) {
		lstResposta = new ArrayList<Resposta>();
		try {
			if (id == 0) {

			} else {
				lstResposta = cadastro.consultarRespostaPorPergunta(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstResposta;
	}
	
	/**
	 * Metodo responsavel por adicionar um voto em uma resposta
	 * @param id - Id da resposta
	 */
	public void adicionarVotoResposta(int id){
		try {
			cadastro.adcionarVotoResposta(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo responsavel por remover um voto de uma resposta
	 * @param id - Id da resposta
	 */
	public void removerVotoResposta(int id){
		try {
			cadastro.removerVotoResposta(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por alterar uma resposta
	 * @param resposta - Resposta a ser alterada
	 */
	@Override
	public String alterarResposta(Resposta resposta){
		String msg = "";
		try {
			String msgErro = this.verificaCampos(resposta);
			 if(msgErro.equals("")){
				msg = cadastro.alterarResposta(resposta);
			}else{
				msg =  msgErro;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * Metodo responsavel por validar os dados de uma resposta
	 * @param resposta -  Resposta a ser validada
	 * @return status da validacao - Se retornar ""(vazio) não houve erro.
	 * 							   - Se houver erro uma msg erro sera retornada. 
	 */
	private String verificaCampos(Resposta resposta){
		String retorno = "";
		if (resposta.getData() == null) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		} else if (resposta.getDescricao() == null || resposta.getDescricao().trim().equals("")) {
			retorno = "Preencha o campo 'Descrição' com dados válidos";
		} else if (resposta.getHora() == null) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		} else if (resposta.getIdPergunta() == 0) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		} else if (resposta.getIdUsuario() == 0) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		}
		return retorno;
	}

}
