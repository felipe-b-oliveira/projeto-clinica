package controle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modelo.dao.UsuarioDAO;
import modelo.dominio.Usuario;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*
	 * Dados de controle da autenticação
	 */
	private Usuario usuario;
	private boolean autenticado = false;

	/*
	 * Dados do formulário de login
	 */
	private String login;
	private String senha;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	/*
	 * Métodos
	 */
	public String exibirLogin() {
		return "index.jsf?faces-redirect=true";
	}

	public String acaoAutenticar() {
		
		UsuarioDAO dao = new UsuarioDAO();

		// ler o usuário do banco
		Usuario usuDoBanco = dao.obter(this.login);
		
		FacesContext context = FacesContext.getCurrentInstance();
		String msgErro = context.getApplication().evaluateExpressionGet(context, "#{msgs.msgSenhaInvalida}",
				String.class);
		
		if (usuDoBanco == null)
		{
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErro, null);
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			
			// usuário não existe
			return "index.jsf";
		}
		else
		{
			if (usuDoBanco.isSenhaCorreta(this.senha)) 			{
				this.usuario = usuDoBanco;
				this.autenticado = true;
				
				return "home.jsf?faces-redirect=true";
			}
			else {
				// mensagem de erro
				FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErro, null);
				FacesContext.getCurrentInstance().addMessage(null, mensagem);
				
				return "index.jsf";
			}
		}
	}
	
	public String acaoSair() {
		this.usuario = null;
		this.autenticado = false;

		return "index.jsf?faces-redirect=true";
	}
	
	public String home() {
		return "home.jsf?faces-redirect=true";
	}

}
