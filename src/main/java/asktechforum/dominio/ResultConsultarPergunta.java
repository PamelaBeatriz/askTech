package asktechforum.dominio;

/**
 * Classe que representa um Result de consultar Pergunta
 */
public class ResultConsultarPergunta extends Pergunta {
	
	private String autor;
	private int qtdResposta;
	
	public ResultConsultarPergunta(){
		super();
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getQtdResposta() {
		return qtdResposta;
	}

	public void setQtdResposta(int qtdResposta) {
		this.qtdResposta = qtdResposta;
	}
	
}
