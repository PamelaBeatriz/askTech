package asktechforum.negocio;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asktechforum.dominio.Usuario;
import asktechforum.repositorio.RepositorioUsuario;
import asktechforum.util.UsuarioUtil;
import asktechforum.dominio.Pergunta;
import asktechforum.dominio.Resposta;
import asktechforum.negocio.CadastroPerguntaBC;
import asktechforum.negocio.CadastroRespostaBC;

/**
 * Classe que lida com as regras de negocio para cadastro de Usuario
 */
public class UsuarioBC {

	private RepositorioUsuario usuarioDAO;
	private CadastroPerguntaBC perguntaBC;
	private CadastroRespostaBC respostaBC;
	
	/**
	 * Construtor
	 */
	public UsuarioBC() {
        super();
        this.usuarioDAO = new RepositorioUsuario();
        this.perguntaBC = new CadastroPerguntaBC();
        this.respostaBC = new CadastroRespostaBC();
	}
	
	/**
	 * Metodo responsavel por alterar um usuario
	 * @param Usuario a ser alterado
	 */
	public boolean alterarUsuario(Usuario usuario){
		boolean flag = validarUsuario(usuario);
		
		if(flag) {
			try {
				this.usuarioDAO.alterarUsuario(usuario);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	/**
	 * Metodo responsavel por atribuir ou remover a funcao de admin de um usuario
	 * @param Usuario a ser alterado
	 */
	public boolean alterarUsuarioAdmin(Usuario usuario){
		boolean flag = validarUsuario(usuario);
		
		if(flag) {
			try {
				this.usuarioDAO.alterarUsuarioAdmin(usuario);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	/**
	 * Metodo responsavel por inserir um usuario na base de dados
	 * @param Usuario a ser inserido
	 */
	public boolean inserirUsuario(Usuario usuario){
		boolean flag = validarUsuario(usuario);
		
		if(flag) {
			try {
				this.usuarioDAO.inserirUsuario(usuario);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	/**
	 * Metodo responsavel por deletar um usuario na base de dados atraves de um id
	 * @param idUsuario - Id do usuario a ser deletado
	 */
	public void deletarUsuarioPorId(int idUsuario) {
		try {
			this.usuarioDAO.deletarUsuarioPorId(idUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por deletar um usuario na base de dados atraves do email
	 * @param email- Email do usuario a ser deletado
	 */
	public void deletarUsuarioPorEmail(String email) {
		Usuario usuario = new Usuario();
		try {
			usuario = this.usuarioDAO.consultarUsuarioPorEmail(email);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Usuario usuarioExcluido = new Usuario();
		ArrayList<Pergunta> perguntas = null;
		ArrayList<Resposta> respostas = null;
        Random geradorRandomico = new Random();
        int numeroRandomico = geradorRandomico.nextInt();
        int idUsuarioExcluido;
        
        try {
			while(numeroRandomico < 1 || numeroRandomico > 99999999 || this.usuarioDAO.consultarUsuarioPorId(numeroRandomico).getIdUsuario() == numeroRandomico) {
				numeroRandomico = geradorRandomico.nextInt();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			perguntas = this.perguntaBC.consultarPerguntaIdUsuario(usuario.getIdUsuario());
			respostas = this.respostaBC.consultarRespostaPorIdUsuario(usuario.getIdUsuario());
			
			if(perguntas != null || respostas != null) {
				if(perguntas.isEmpty() != true || respostas.isEmpty() != true) {
					usuarioExcluido.setNome("Usuário Excluído");
					usuarioExcluido.setDataString("");
					usuarioExcluido.setAdmin(false);
					usuarioExcluido.setEmail("usuarioExcluido@" + numeroRandomico + ".com");
					usuarioExcluido.setLocalizacao("");
					usuarioExcluido.setSenha(numeroRandomico+"");
					usuarioExcluido.setConfSenha(numeroRandomico+"");
					this.inserirUsuario(usuarioExcluido);
					idUsuarioExcluido = this.usuarioDAO.consultarUsuarioPorEmail("usuarioExcluido@" + numeroRandomico + ".com").getIdUsuario();
					usuarioExcluido.setIdUsuario(usuario.getIdUsuario());
					this.alterarUsuario(usuarioExcluido);
					this.deletarUsuarioPorId(idUsuarioExcluido);
				}else {
					this.usuarioDAO.deletarUsuarioPorEmail(email);
				}
			}else {
				this.usuarioDAO.deletarUsuarioPorEmail(email);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por consultar um usuario atraves do email e senha
	 * @param email - Email do usuario a ser consultado
	 * @param senha - Senha do usuario a ser consultado
	 * @return Usuario - usuario encontrado
	 */
	public Usuario consultarUsuarioPorEmail_Senha(String email, String senha) {
		Usuario usuario = null;
		try {
			if(this.usuarioDAO.consultarUsuarioPorEmail_Senha(email, senha).getIdUsuario() != 0) {
				usuario = this.usuarioDAO.consultarUsuarioPorEmail_Senha(email, senha);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}

	/**
	 * Metodo responsavel por consultar um usuario atraves do id
	 * @param Id - Id do usuario a ser consultado
	 * @return Usuario - usuario encontrado
	 */
	public Usuario consultarUsuarioPorId(int idUsuario) {
		Usuario usuario = new Usuario();
		try {
			usuario = this.usuarioDAO.consultarUsuarioPorId(idUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	/**
	 * Metodo responsavel por consultar um usuario atraves do email
	 * @param email - Email do usuario a ser consultado
	 * @return Usuario - usuario encontrado
	 */
	public Usuario consultarUsuarioPorEmail(String email) {
		Usuario usuario = new Usuario();
		try {
			usuario = this.usuarioDAO.consultarUsuarioPorEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	/**
	 * Metodo responsavel por consultar um usuario atraves do nome
	 * @param nome - Email do usuario a ser consultado
	 * @return Lista de usuario encontrados
	 */
	public List<Usuario> consultarUsuarioPorNome(String nome) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			usuarios = this.usuarioDAO.consultarUsuarioPorNome(nome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	/**
	 * Metodo responsavel por consultar todos os usuarios
	 * @return Lista de usuario encontrados
	 */
	public List<Usuario> consultarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
			usuarios = this.usuarioDAO.consultarTodosUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return usuarios;
	}
	
	/**
	 * Metodo responsavel por validadar os dados de um usuario
	 * @param usuario - Usuario a ser validado
	 * @return status de validacao - verdadeiro se validado
	 * 							   - falso se não validado
	 */
	private boolean validarUsuario(Usuario usuario) {
		boolean flag = true;
		Date data = null;
		
		if(usuario != null) {
			
			if(usuario.getNome() != null) {
				if(usuario.getNome().trim().equals("")) {
					flag = false;
				}
			}else {
				flag = false;
			}
			
			if(usuario.getEmail() != null) {
				if(usuario.getEmail().trim().equals("")) {
					flag = false;
				}else if(!usuario.getEmail()
						.matches("^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$")) {
					flag = false;
				}
			}else {
				flag = false;
			}
			
			if(usuario.getSenha() != null) {
				if(usuario.getSenha().trim().equals("")) {
					flag = false;
				}
			}else {
				flag = false;
			}
			
			if(usuario.getDataString() != null) {
				if(!usuario.getDataString().trim().equals("")) {
					if(usuario.getDataString().length() == 10) {
						data = UsuarioUtil.converterStringData(usuario.getDataString());
					}
					if(usuario.getDataString().length() != 10 || data == null) {
						flag = false;
					}else {
						usuario.setDataNascimento(data);
					}
				}else {
					usuario.setDataNascimento(null);
				}
			}else {
				usuario.setDataNascimento(null);
			}
			
			if(usuario.getLocalizacao() == null) {
				usuario.setLocalizacao("");
			}else if(usuario.getLocalizacao().trim().equals("")) {
				usuario.setLocalizacao("");
			}
			
			if(usuario.getSenha() != null) {
				if(!usuario.getSenha().trim().equals("")) {
					if(usuario.getSenha().length() > 8) {
						flag = false;
					}
				}else {
					flag = false;
				}
			}else {
				flag = false;
			}
			
			if(usuario.getSenha() != null && usuario.getConfSenha() != null) {
				if(usuario.getSenha().trim().equals("") && usuario.getConfSenha().trim().equals("")) {
					flag = false;
				}
				if(!usuario.getSenha().equals(usuario.getConfSenha())) {
					flag = false;
				}
			}
			
		}else {
			flag = false;
		}
		
		return flag;
	}
		
	/**
	 * Metodo responsavel por consultar quantos usuarios admin estao cadastrados
	 * @param usuario -  Usuario logado 
	 * @return - quantidade de usuarios admin
	 */
	public int consultarQuantidadeAdmin(Usuario usuario) {
		int quantAdmin = 0;
		Boolean flag = false;
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		try {
			listaUsuarios = this.usuarioDAO.consultarTodosUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(listaUsuarios != null) {
			for(Usuario u : listaUsuarios) {
				if(u.isAdmin()) {
					quantAdmin++;
					if(u.getEmail().equals(usuario.getEmail())){
						flag = true;
					}
				}
			}
			if(quantAdmin == 1 && flag == false) {
				quantAdmin++;
			} 
		}
		
		return quantAdmin;
	}
	
	/**
	 * Metodo responsavel por verificar email de um usuario
	 * @param email - email a ser verificado
	 * @param usuario - usuario a ser verificado
	 * @return verdadeiro - se o email pertencer ao usuario
	 *         falso - se o email nao pertencer ao usuario
	 */
	public boolean verificarEmail(String email, Usuario usuario) {
		boolean flag = false;
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		try {
			listaUsuarios = this.usuarioDAO.consultarTodosUsuarios();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(listaUsuarios != null) {
			for(Usuario u : listaUsuarios) {
				if(u.getEmail().equals(email)) {
					if(u.getEmail().equals(usuario.getEmail())) {
						flag = false;
					}else {
						flag = true;
					}
				}
			}
		}
		
		return flag;
	}
	
	/**
	 * Metodo responsavel por formatar uma data
	 * @param dataString - data a ser formatada
	 * @return data formatada
	 */
	public String formatarDataSQL(String dataString) {
		String arrayDataString[] = dataString.split("-");
		dataString = "";
		
		for(int i = arrayDataString.length-1; i > -1; i--) {
			dataString = dataString + arrayDataString[i];
			if(i != 0) {
				dataString = dataString + "/";
			}
		}
		
		return dataString;
	}
		
}
