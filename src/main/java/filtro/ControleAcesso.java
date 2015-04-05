package filtro;

import java.io.IOException;

import javax.servlet.Filter;

import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import asktechforum.dominio.Usuario;

/**
 * Classe responsavel por fazer o controle de acesso das paginas do sistema
 */
public class ControleAcesso implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}
	
	/**
	 * Metodo responsavel por redirecionar o acesso as paginas do sistema
	 * de acordo com a existencia de um usuario em uma sessao.
	 * Caso haja usuario na sessao entao o acesso as paginas sera permitido. 
	 * Caso contrario o acesso sera negado.
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest)req).getSession();
		Usuario usuario = (Usuario)session.getAttribute("usuarioLogado");
		if(usuario==null){
			((HttpServletResponse)res).sendRedirect("../acessoNegado.jsp");
		}else{
			chain.doFilter(req, res);
		}
	} 
	public void destroy() {

	}

}