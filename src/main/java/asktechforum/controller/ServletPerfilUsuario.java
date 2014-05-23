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
import asktechforum.repositorio.UsuarioDAO;

/**
 * Implementa��o do Servlet de Perfil de Usuario.
 */
@WebServlet("/ServletPerfilUsuario")
public class ServletPerfilUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String PERFIL = "perfilUsuario.jsp";
       
	private UsuarioDAO usuarioDAO;
    
    /**
     * Construtor do Servlet de Perfil de Usu�rio.
     */
    public ServletPerfilUsuario() {
        super();
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
	 * Implementacao do metodo doGet() Servlet de Perfil de Usuario.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doPost(request, response);
	}

	/**
	 * Implementacao do metodo doPost() Servlet de Perfil de Usuario.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = new Usuario();
		HttpSession session = request.getSession();
		String usuarioRadio = request.getParameter("usuarioRadio");
		
		if(usuarioRadio != null) {
			usuario = this.usuarioDAO.consultarUsuarioPorEmail(usuarioRadio);
			RequestDispatcher view = request.getRequestDispatcher(PERFIL);
			request.setAttribute("usuarioPerfil", usuario);
			session.setAttribute("usuarioPerfil", usuario);

	        view.forward(request, response);
		}
	}

}
