package asktechforum.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Pergunta;
import asktechforum.dominio.ResultConsultarPergunta;
import asktechforum.repositorio.RepositorioPerguntaBDR;
import asktechforum.interfaces.RepositorioPergunta;

/**
 * Classe que lida com as regras de negocio para cadastro de Pergunta
 */
public class CadastroPerguntaBC implements RepositorioPergunta {

	private RepositorioPerguntaBDR cadastro;
	private ArrayList<Pergunta> lstPergunta;
	private ArrayList<ResultConsultarPergunta> lstQtdPergunta;
	
	/**
	 * Construtor
	 */
	public CadastroPerguntaBC() {
		cadastro = new RepositorioPerguntaBDR();
	}
	
	/**
	 * Metodo responsavel por inserir uma Pergunta no banco de dados
	 * @param pergunta- Pergunta a ser inserida
	 * @return status da acao
	 */
	@Override
	public String inserirPergunta(Pergunta pergunta)  {
		String msg = "";
		try {
			String msgErro = this.verificaCampos(pergunta);
			if (msgErro.length()==0) {
				msg= cadastro.inserirPergunta(pergunta);
			}else{
				msg= msgErro;
			}
			          
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return msg;

	}
	
	/**
	 * Metodo responsavel por detetar uma Pergunta
	 * @param id - Id da pergunta
	 */
	@Override
	public void deletarPergunta(int id) {
		try {
			if (id == 0) {

			} else {
				cadastro.deletarPergunta(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo responsavel por consultar Pergunta fazendo uso de um ID
	 * @param Id - Id da pergunta
	 * @return Pergunta encontrada
	 */
	@Override
	public Pergunta consultarPerguntaPorId(int id) {

		Pergunta pergunta = new Pergunta();
		try {
			if (id == 0) {

			} else {
				pergunta = cadastro.consultarPerguntaPorId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pergunta;
	}
	
	/**
	 * Metodo responsavel por consultar pergunta atraves do Id de um usuario
	 * @param id- Id do usuario
	 * @return Lista de perguntas encontradas	 
	 */
	@Override
	public ArrayList<Pergunta> consultarPerguntaIdUsuario(int id)
			throws SQLException {
		this.lstPergunta = new ArrayList<Pergunta>();
		try {

			if (id == 0) {

			} else {
				this.lstPergunta = cadastro.consultarPerguntaIdUsuario(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstPergunta;
	}
	
	/**
	 * Metodo responsavel por consultar todas as Tags de perguntas
	 * @return Lista com todas as tags encontradas
	 */
	public ArrayList<String> consultaTodasAsTags() {
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<String> tagFiltradas = new ArrayList<String>();
		try {

		   tags = cadastro.consultaTodasAsTags();
		   
		   tagFiltradas.add(tags.get(0));
		   boolean ehIgual = false;
		   for(int i=1; i < tags.size(); i++){
			   for(int j=0; j < tagFiltradas.size(); j++){
				   if(tagFiltradas.get(j).equals(tags.get(i))){
					   ehIgual = true;
				   }
			   }	
			   if(!ehIgual){
				   tagFiltradas.add(tags.get(i));
			   }
			   ehIgual = false;
		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagFiltradas;
		
	}
	
	/**
	 * Metodo responsavel por consultar todas as perguntas
	 * @return Lista de perguntas encontradas
	 */
	@Override
	public ArrayList<Pergunta> consultarTodasPerguntas() {
		lstPergunta = new ArrayList<Pergunta>();
		try {

			this.lstPergunta = cadastro.consultarTodasPerguntas();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstPergunta;
	}

	/**
	 * Metodo responsavel por consultar pergunta atraves de uma Tag
	 * @param tag -  Tag da pergunta
	 * @return Lista de perguntas encontradas	 
	 */
	@Override
	public ArrayList<ResultConsultarPergunta> consultarPerguntaPorTag(String tag){
		lstQtdPergunta = new ArrayList<ResultConsultarPergunta>();
		try {
			if(tag.equals("all")){
				this.lstQtdPergunta = cadastro.consultarPerguntaPorTodasTags();
			}else{
				this.lstQtdPergunta = cadastro.consultarPerguntaPorTag(tag);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstQtdPergunta;
	}
	
	/**
	 * Metodo responsavel por consultar pergunta atraves de todas as Tags existentes
	 * @param tag -  Tag da pergunta
	 * @return Lista de perguntas encontradas	 
	 */
	@Override
	public ArrayList<ResultConsultarPergunta> consultarPerguntaPorTodasTags()
			throws SQLException {
		return null;
	}

	/**
	 * Metodo responsavel por alterar uma pergunta
	 * @return Status da acao
	 */
	@Override
	public String alterarPergunta(Pergunta pergunta)
			 {
		String msg = "";
		try {
			String msgErro = this.verificaCampos(pergunta);
			if (msgErro.equals("")) {
				msg = cadastro.alterarPergunta(pergunta);
			}else{
				msg = msgErro;
			}
			          
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return msg;
		
	}
	/**
	 * Metodo responsavel por validar os dados da pergunta
	 * @param pergunta - Pergunta a ser validada
	 * @return status da validacao - Se retornar ""(vazio) não houve erro.
	 * 							   - Se houver erro uma msg erro sera retornada.
	 */
	private String verificaCampos(Pergunta pergunta){
		String retorno = "";
		if (pergunta.getData() == null) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		} else if (pergunta.getDescricao() == null ||  pergunta.getDescricao().trim().equals("")) {
			retorno = "Preencha o campo 'Descrição' com dados válidos";
		} else if (pergunta.getHora() == null) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		} else if (pergunta.getTitulo() == null ||  pergunta.getTitulo().trim().equals("")) {
			retorno = "Preencha o campo 'Pergunta' com dados válidos";
		} else if (pergunta.getIdUsuario() == 0) {
			retorno = "Erro no sistema. Tente novamente em instantes.";
		} else if (pergunta.getTag() == null || pergunta.getTag().trim().equals("")) {
			retorno = "Preencha o campo 'Assuntos relacionados' com dados válidos";
		}	
		return retorno;
		
	}
}
