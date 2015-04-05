package asktechforum.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Resposta;

public interface RepositorioResposta {
	
	public String inserirResposta(Resposta resposta) throws SQLException;
	public void deletarResposta(int id) throws SQLException;
	public Resposta consultarRespostaPorId(int id) throws SQLException;
	public ArrayList<Resposta> consultarRespostaPorIdUsuario(int id)
			throws SQLException ;
	public ArrayList<Resposta> consultarTodasRespostas() throws SQLException;
	public ArrayList<Resposta> consultarRespostaPorPergunta(int id)
			throws SQLException;
	public String alterarResposta(Resposta resposta) throws SQLException;
	
}
