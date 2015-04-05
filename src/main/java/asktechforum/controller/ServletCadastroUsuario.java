package asktechforum.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asktechforum.dominio.Usuario;
import asktechforum.negocio.UsuarioBC;

/**
 * Implementacao do Servlet de Cadastro de Usuario.
 */
@WebServlet("/ServletCadastroUsuario")
public class ServletCadastroUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String SUCESSOCADASTRO = "cadastroUsuarioSucesso.jsp";
    private static String ERROCADASTRO = "cadastroUsuario.jsp";
	
    private UsuarioBC usuarioBC;
       
    /**
     * Construtor do Servlet de Cadastro de Usuario.
     */
    public ServletCadastroUsuario() {
        super();
        this.usuarioBC = new UsuarioBC();
    }

	/**
	 * Implementacao do metodo doGet() Servlet de Cadastro de Usuario.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * Implementacao do metodo doPost() Servlet de Cadastro de Usuario.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = new Usuario();
		int quantAdmin = this.usuarioBC.consultarQuantidadeAdmin(usuario);
		boolean flag = true;

		if(!this.usuarioBC.verificarEmail(request.getParameter("email"), usuario)) {
			usuario.setNome(request.getParameter("nome"));
			usuario.setDataString(request.getParameter("dataNascimento"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setLocalizacao(request.getParameter("localizacao"));
			usuario.setSenha(request.getParameter("senha"));
			usuario.setConfSenha(request.getParameter("confsenha"));
			
			if(quantAdmin == 0) {
				usuario.setAdmin(true);
			}else {
				usuario.setAdmin(false);
			}
			
			flag = this.usuarioBC.inserirUsuario(usuario);
			
			if(flag) {
				RequestDispatcher view = request.getRequestDispatcher(SUCESSOCADASTRO);
			    view.forward(request, response);
			}else {
				RequestDispatcher view = request.getRequestDispatcher(ERROCADASTRO);
				usuario.setSenha("");
				usuario.setConfSenha("");
				request.setAttribute("usuario", usuario);
			    view.forward(request, response);
			}
		    
		}else {
			usuario.setNome(request.getParameter("nome"));
			usuario.setDataString(request.getParameter("dataNascimento"));
			usuario.setLocalizacao(request.getParameter("localizacao"));
			
			RequestDispatcher view = request.getRequestDispatcher(ERROCADASTRO);
			request.setAttribute("emailExistente", true);
			request.setAttribute("usuario", usuario);
			view.forward(request, response);
		}
	}
}
