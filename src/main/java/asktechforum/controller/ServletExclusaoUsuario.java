package asktechforum.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import asktechforum.dominio.Usuario;
import asktechforum.negocio.UsuarioBC;

/**
 * Implementacao do Servlet de Exclusao de Usuario.
 */
@WebServlet("/ServletExclusaoUsuario")
public class ServletExclusaoUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String SUCESSOEXCLUSAO = "./usuarioAutenticado/exclusaoUsuarioSucesso.jsp";
    private static String ERROEXCLUSAO = "./usuarioAutenticado/alteracaoExclusaoUsuarioErro.jsp";
    
	private UsuarioBC usuarioBC;

    /**
     * Construtor do Servlet de Exclusao de Usuario.
     */
    public ServletExclusaoUsuario() {
        super();
        this.usuarioBC = new UsuarioBC();
    }

	/**
	 * Implementacao do metodo doGet() Servlet de Exclusao de Usuario.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * Implementacao do metodo doPost() Servlet de Exclusao de Usuario.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pesquisaUsuarioEmail = request.getParameter("exclusaoUsuarioEmail");
		Usuario usuario = this.usuarioBC.consultarUsuarioPorEmail(pesquisaUsuarioEmail);
		RequestDispatcher view;
		HttpSession session = request.getSession();
		int quantAdmin = this.usuarioBC.consultarQuantidadeAdmin(usuario);

		Usuario usuarioExcluido = (Usuario) session.getAttribute("usuarioExcluido");
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); 
		
    	session.setAttribute("erroAlteracaoExclusao", true);
		
		if(pesquisaUsuarioEmail != null) {
			if(quantAdmin > 1) {
				this.usuarioBC.deletarUsuarioPorEmail(pesquisaUsuarioEmail);
				
				if(usuarioExcluido != null && usuarioLogado != null) {
					if(usuarioExcluido.getIdUsuario() == usuarioLogado.getIdUsuario()) {
					    session.invalidate();
					}
				}
				view = request.getRequestDispatcher(SUCESSOEXCLUSAO);
				view.forward(request, response);
			}else {
				view = request.getRequestDispatcher(ERROEXCLUSAO);
				view.forward(request, response);
			}
		}
	}

}
