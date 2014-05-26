package asktechforum.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import asktechforum.dominio.Resposta;
import asktechforum.interfaces.CadastroResposta;
import asktechforum.repositorio.CadastroRespostaDAO;


public class CadastroRespostaBC implements CadastroResposta {

	private CadastroRespostaDAO cadastro;
	private ArrayList<Resposta> lstResposta;
	

	public CadastroRespostaBC() {
		cadastro = new CadastroRespostaDAO();
	}

	@Override
	public String adcionarResposta(Resposta resposta) {
		String retorno = "";
		try {
			if (resposta.getData() == null) {
				retorno = "Erro no sistema. Tente novamente em instantes.";
			} else if (resposta.getDescricao() == null || resposta.getDescricao().trim().equals("")) {
				retorno = "Preencha o campo 'Descri��o' com dados v�lidos";
			} else if (resposta.getHora() == null) {
				retorno = "Erro no sistema. Tente novamente em instantes.";
			} else if (resposta.getIdPergunta() == 0) {
				retorno = "Erro no sistema. Tente novamente em instantes.";
			} else if (resposta.getIdUsuario() == 0) {
				retorno = "Erro no sistema. Tente novamente em instantes.";
			} else {
				retorno = cadastro.adcionarResposta(resposta);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public void deletarResposta(int id) throws SQLException {
		// TODO Auto-generated method stub
		try {
			if (id == 0) {

			} else {
				cadastro.deletarResposta(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Resposta consultarRespostaPorIdResposta(int id) throws SQLException {
		Resposta resposta = new Resposta();
		try {
			if (id == 0) {

			} else {
				resposta = cadastro.consultarRespostaPorIdResposta(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resposta;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstResposta;
	}

	@Override
	public ArrayList<Resposta> consultarTodasResposta() throws SQLException {
		// TODO Auto-generated method stub
		lstResposta = new ArrayList<Resposta>();
		try {
			lstResposta = cadastro.consultarTodasResposta();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstResposta;
	}

	@Override
	public ArrayList<Resposta> consultarRespostaPorPergunta(int id) {
		lstResposta = new ArrayList<Resposta>();
		try {
			if (id == 0) {

			} else {
				lstResposta = cadastro.consultarRespostaPorPergunta(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstResposta;
	}

}
