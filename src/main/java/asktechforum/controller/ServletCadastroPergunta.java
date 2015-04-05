package asktechforum.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import asktechforum.dominio.Pergunta;
import asktechforum.dominio.Usuario;
import asktechforum.negocio.CadastroPerguntaBC;
import asktechforum.util.Util;

/**
 * Implementacao do Servlet de Cadastro de Pergunta.
 */
@WebServlet("/ServletCadastroPergunta")
public class ServletCadastroPergunta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SUCESSOCADASTRO = "usuarioAutenticado/cadastroPerguntaSucesso.jsp";
	private final String SUCESSOALTERAR = "usuarioAutenticado/alterarPerguntaSucesso.jsp";
	private CadastroPerguntaBC cadastro;

	/**
     * Construtor do Servlet de Cadastro de Pergunta.
     */
	public ServletCadastroPergunta() {
		super();
	}

	/**
	 * Implementacao do metodo doGet() Servlet de Cadastro de Pergunta.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * Implementacao do metodo doPost() Servlet de Cadastro de Pergunta.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario)session.getAttribute("usuarioLogado");
		
		this.cadastro = new CadastroPerguntaBC();
		Pergunta pergunta = new Pergunta();

		pergunta.setStrData(Util.getDataSistema());
		pergunta.setDescricao(request.getParameter("descricao"));
		pergunta.setStrHora(Util.getHoraSistema());
		pergunta.setTitulo(request.getParameter("titulo"));
		pergunta.setIdUsuario(usuario.getIdUsuario());
		pergunta.setTag(request.getParameter("tag"));
		String acao = request.getParameter("acao");
		if (acao.contentEquals("cadastrar")) {
			String retornoCadastroPergunta = cadastro
					.inserirPergunta(pergunta);

			if (retornoCadastroPergunta != null
					&& !retornoCadastroPergunta.equals("cadastroSucesso")) {
				session.setAttribute("erroCadastroPergunta",
						retornoCadastroPergunta);
				request.setAttribute("pergunta", pergunta);
				request.getRequestDispatcher(
						"usuarioAutenticado/CadastroPergunta.jsp").forward(
						request, response);
			} else {
				RequestDispatcher view = request
						.getRequestDispatcher(SUCESSOCADASTRO);
				request.setAttribute("pergunta", pergunta);
				view.forward(request, response);
			}
			
		}else if(acao.contains("alterar")){
								
			Pergunta perguntaSelecionada = (Pergunta)session.getAttribute("pergunta");
			pergunta.setIdPergunta(perguntaSelecionada.getIdPergunta());
			String retornoAlteracaoPergunta = cadastro.alterarPergunta(pergunta);
			
			if (retornoAlteracaoPergunta != null
					&& !retornoAlteracaoPergunta.equals("alteracaoSucesso")) {
				session.setAttribute("erroCadastroPergunta",
						retornoAlteracaoPergunta);
				request.setAttribute("pergunta", pergunta);
				request.getRequestDispatcher(
						"usuarioAutenticado/alterarPergunta.jsp").forward(
						request, response);
			} else {
				RequestDispatcher view = request
						.getRequestDispatcher(SUCESSOALTERAR);
				request.setAttribute("pergunta", pergunta);
				view.forward(request, response);
			}
		}

	}

}
