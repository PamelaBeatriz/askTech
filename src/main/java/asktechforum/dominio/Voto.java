package asktechforum.dominio;

/**
 * Classe que representa um Voto
 */
public class Voto {
	private int idVoto;
	private int idUsuario;
	private int idResposta;
	
	public Voto() {
	}

	//GETTERS AND SETTERS
	public int getIdVoto() {
		return idVoto;
	}

	public void setIdVoto(int idVoto) {
		this.idVoto = idVoto;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(int idResposta) {
		this.idResposta = idResposta;
	}
}
