package asktechforum.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Pergunta;
import asktechforum.dominio.ResultConsultarPergunta;

public interface RepositorioPergunta {
	
	public String inserirPergunta(Pergunta pergunta) throws SQLException;
	public void deletarPergunta(int id) throws SQLException;
	public Pergunta consultarPerguntaPorId(int id) throws SQLException;
	public ArrayList<Pergunta> consultarPerguntaIdUsuario(int id)
			throws SQLException;
	public ArrayList<Pergunta> consultarTodasPerguntas() throws SQLException;
	public ArrayList<ResultConsultarPergunta> consultarPerguntaPorTag(String tag)
			throws SQLException ;
	public ArrayList<ResultConsultarPergunta> consultarPerguntaPorTodasTags()
			throws SQLException ;
	public String alterarPergunta(Pergunta pergunta)
			throws SQLException ;
	
	
}