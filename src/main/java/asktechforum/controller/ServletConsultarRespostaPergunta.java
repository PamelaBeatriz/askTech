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

import asktechforum.dominio.Pergunta;
import asktechforum.dominio.Resposta;
import asktechforum.negocio.CadastroPerguntaBC;
import asktechforum.negocio.CadastroRespostaBC;

/**
 * Implementacao do Servlet de Consultar Respostas de Pergunta.
 */
@WebServlet("/ServletConsultarRespostaPergunta")
public class ServletConsultarRespostaPergunta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RESULTADO_CONSULTA = "consultarRespostaPorPergunta.jsp";
	private static final String ALTERACAO_RESPOSTA = "usuarioAutenticado/alterarResposta.jsp";
	private static final String ALTERACAO_PERGUNTA = "usuarioAutenticado/alterarPergunta.jsp";
	private static final String EXCLUSAO_PERGUNTA_SUCESSO = "usuarioAutenticado/exclusaoPerguntaSucesso.jsp";
	private static final String EXCLUSAO_RESPOSTA_SUCESSO = "usuarioAutenticado/exclusaoRespostaSucesso.jsp";
	private CadastroRespostaBC cadastroRespostaBC = new CadastroRespostaBC();
	private CadastroPerguntaBC cadastroPerguntaBC = new CadastroPerguntaBC();
       
	/**
     * Construtor do Servlet de Consultar Respostas de Pergunta.
     */
    public ServletConsultarRespostaPergunta() {
        super();
    }

	/**
	 * Implementacao do metodo service() Servlet de Consultar Respostas de Pergunta.
	 */
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		super.service(arg0, arg1);
	}

	/**
	 * Implementacao do metodo doGet() Servlet de Consultar Respostas de Pergunta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher view = null;
		
		if(request.getParameter("alterarResposta")!= null){
			Resposta resposta = this.cadastroRespostaBC.consultarRespostaPorId(
					Integer.parseInt(request.getParameter("idRespostaSelecionada")));
			session.setAttribute("resposta", resposta);
			view = request.getRequestDispatcher(ALTERACAO_RESPOSTA);
			view.forward(request, response);
		}else if(request.getParameter("excluirResposta")!= null){
			this.cadastroRespostaBC.deletarResposta(
					Integer.parseInt(request.getParameter("idRespostaSelecionada")));
			view = request	.getRequestDispatcher(EXCLUSAO_RESPOSTA_SUCESSO);
			view.forward(request, response);
		}else{
			
			session.setAttribute("idPergunta", request.getParameter("idPergunta"));
			String idPergunta = (String)session.getAttribute("idPergunta");
			String descricao = request.getParameter("descricao");
			String autor = request.getParameter("autor");
			String titulo = request.getParameter("titulo");
			
			this.cadastroRespostaBC = new CadastroRespostaBC();

			ArrayList<Resposta> resp = this.cadastroRespostaBC.consultarRespostaPorPergunta(Integer.parseInt(idPergunta));

		    view = request.getRequestDispatcher(RESULTADO_CONSULTA);
			request.setAttribute("resposta", resp);
			request.setAttribute("descricao", descricao);
			request.setAttribute("autor", autor);
			request.setAttribute("titulo", titulo);
			view.forward(request, response);
		}
	}

	/**
	 * Implementacao do metodo doPost() Servlet de Consultar Respostas de Pergunta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher view = null;
		
		if(request.getParameter("alterarResposta")!= null){
			Resposta resposta = this.cadastroRespostaBC.consultarRespostaPorId(
					Integer.parseInt(request.getParameter("idRespostaSelecionada")));
		    session.setAttribute("resposta", resposta);
			view = request.getRequestDispatcher(ALTERACAO_RESPOSTA);
			view.forward(request, response);
		}else if(request.getParameter("excluirResposta")!= null){
			this.cadastroRespostaBC.deletarResposta(
					Integer.parseInt(request.getParameter("idRespostaSelecionada")));
			view = request	.getRequestDispatcher(EXCLUSAO_RESPOSTA_SUCESSO);
			view.forward(request, response);
		}else if(request.getParameter("alterarPergunta")!=null){
			String idPergunta = (String)session.getAttribute("idPergunta");
			Pergunta pergunta = this.cadastroPerguntaBC.consultarPerguntaPorId(
					Integer.parseInt(idPergunta));
			session.setAttribute("pergunta", pergunta);
			request.setAttribute("pergunta", pergunta);
			view = request.getRequestDispatcher(ALTERACAO_PERGUNTA);
			view.forward(request, response);
		}else if(request.getParameter("excluirPergunta")!=null){
			String idPergunta = (String)session.getAttribute("idPergunta");
			this.cadastroPerguntaBC.deletarPergunta(
					Integer.parseInt(idPergunta));
			view = request	.getRequestDispatcher(EXCLUSAO_PERGUNTA_SUCESSO);
			view.forward(request, response);
			
		}else{
			
			session.setAttribute("idPergunta", request.getParameter("idPergunta"));
			String idPergunta = (String)session.getAttribute("idPergunta");
			String descricao = request.getParameter("descricao");
			String autor = request.getParameter("autor");
			String titulo = request.getParameter("titulo");
			
			this.cadastroRespostaBC = new CadastroRespostaBC();

			ArrayList<Resposta> resp = this.cadastroRespostaBC.consultarRespostaPorPergunta(Integer.parseInt(idPergunta));

		    view = request.getRequestDispatcher(RESULTADO_CONSULTA);
			request.setAttribute("resposta", resp);
			request.setAttribute("descricao", descricao);
			request.setAttribute("autor", autor);
			request.setAttribute("titulo", titulo);
			view.forward(request, response);
		}
	}

}
