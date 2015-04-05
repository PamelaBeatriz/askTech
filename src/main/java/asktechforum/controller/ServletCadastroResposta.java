package asktechforum.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import asktechforum.dominio.Resposta;
import asktechforum.dominio.Usuario;
import asktechforum.negocio.CadastroRespostaBC;
import asktechforum.negocio.VotoBC;
import asktechforum.util.Util;

/**
 * Implementacao do Servlet de Cadastro de Resposta.
 */
@WebServlet("/ServletCadastroResposta")
public class ServletCadastroResposta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUCESSOCADASTRO = "usuarioAutenticado/cadastroRespostaSucesso.jsp";
	private static final String SUCESSOALTERACAO = "usuarioAutenticado/alterarRespostaSucesso.jsp";
	private static final String PAGECONSULTARESPOSTAS = "consultarRespostaPorPergunta.jsp";
	
	private CadastroRespostaBC cadastro;
	private VotoBC votoBC;
	
	/**
     * Construtor do Servlet de Cadastro de Resposta.
     */
    public ServletCadastroResposta() {
        super();
        this.cadastro = new CadastroRespostaBC();
        this.votoBC = new VotoBC();
    }

    /**
	 * Implementacao do metodo doGet() Servlet de Cadastro de Resposta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String idPergunta = (String)session.getAttribute("idPergunta");
		request.setAttribute("descricao", session.getAttribute("descricao"));
		request.setAttribute("autor", session.getAttribute("autor"));
		request.setAttribute("titulo", session.getAttribute("titulo"));
		
		boolean isVotar = (boolean)session.getAttribute("isVotar");
		boolean liked = true;
		
		if(request.getParameter("liked").equals("true")) {
			liked = true;
		}else if(request.getParameter("liked").equals("false")) {
			liked = false;
		}
		String idResposta = request.getParameter("idR");
		String idUsuario = request.getParameter("idUser");
		
		if(isVotar){
			if(liked) {
				
				cadastro.adicionarVotoResposta(Integer.parseInt(idResposta));
				this.votoBC.inserirVoto(Integer.parseInt(idUsuario), Integer.parseInt(idResposta));
				
				this.cadastro = new CadastroRespostaBC();
	
				ArrayList<Resposta> resp = this.cadastro.consultarRespostaPorPergunta(Integer.parseInt(idPergunta));
	
				RequestDispatcher view = request.getRequestDispatcher(PAGECONSULTARESPOSTAS);
				request.setAttribute("resposta", resp);
				view.forward(request, response);
				
			}else {
				
				cadastro.removerVotoResposta(Integer.parseInt(idResposta));
				this.votoBC.deletarVoto(Integer.parseInt(idUsuario), Integer.parseInt(idResposta));
				
				this.cadastro = new CadastroRespostaBC();
	
				ArrayList<Resposta> resp = this.cadastro.consultarRespostaPorPergunta(Integer.parseInt(idPergunta));
	
				RequestDispatcher view = request.getRequestDispatcher(PAGECONSULTARESPOSTAS);
				request.setAttribute("resposta", resp);
				view.forward(request, response);
				
			}
		}
	}

	/**
	 * Implementacao do metodo doPost() Servlet de Cadastro de Resposta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Resposta resposta = new Resposta();
		HttpSession session = request.getSession();
		String flag = request.getParameter("acao");
		
		
		if (flag != null && flag.contentEquals("cadastrar")) {			
			
			Usuario usuario = (Usuario)session.getAttribute("usuarioLogado");
			
			resposta.setStrData(Util.getDataSistema());
			resposta.setDescricao(request.getParameter("descricao"));
			resposta.setStrHora(Util.getHoraSistema());
			resposta.setIdPergunta(Integer.parseInt((String)session.getAttribute("idPergunta")));
			resposta.setIdUsuario(usuario.getIdUsuario());			

			String retornoCadastroResposta = cadastro
					.inserirResposta(resposta);

			if (retornoCadastroResposta != null
					&& !retornoCadastroResposta.equals("cadastroSucesso")) {
				session.setAttribute("erroCadastroResposta",
						retornoCadastroResposta);
				request.setAttribute("resposta", resposta);
				request.getRequestDispatcher("usuarioAutenticado/responder.jsp")
						.forward(request, response);
			} else {
				RequestDispatcher view = request
						.getRequestDispatcher(SUCESSOCADASTRO);
				request.setAttribute("resposta", resposta);
				cadastro.notificarContribuintesPerg(resposta.getIdPergunta(), resposta.getIdUsuario());
				view.forward(request, response);
			}
		} else if (flag.contentEquals("alterar")) {
			
			resposta = (Resposta)session.getAttribute("resposta");
			resposta.setStrData(Util.getDataSistema());
			resposta.setDescricao(request.getParameter("descricao"));
			resposta.setStrHora(Util.getHoraSistema());
			
			String retornoAlterarResposta = cadastro
					.alterarResposta(resposta);
			if (retornoAlterarResposta != null
					&& !retornoAlterarResposta.equals("alteracaoSucesso")) {
				session.setAttribute("erroCadastroResposta",
						retornoAlterarResposta);
				request.setAttribute("resposta", resposta);
				request.getRequestDispatcher("usuarioAutenticado/alterarResposta.jsp")
						.forward(request, response);
			} else {
				RequestDispatcher view = request
						.getRequestDispatcher(SUCESSOALTERACAO);
				request.setAttribute("resposta", resposta);
				view.forward(request, response);
			}
		}

	}
}
