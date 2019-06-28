package controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import modelo.dao.ConsultaDAO;
import modelo.dao.PacienteDAO;
import modelo.dominio.Consulta;
import modelo.dominio.Paciente;
import modelo.dominio.Usuario;

@ManagedBean(name = "pacienteMB")
@RequestScoped
public class PacienteMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * ATRIBUTOS
	 */
	
	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;
	
	private List<Paciente> pacientes = null;
	private List<Consulta> consultas = null;
	private Paciente paciente = new Paciente();
	private PacienteDAO dao = new PacienteDAO();
	
	public String getContador() {
		
		if (this.paciente.getNome() == null)
			return "";
		
		return String.valueOf(this.paciente.getNome().length());
	}
	
	public void caixaAlta()
	{
		if (this.paciente.getNome() != null)
		{
			String maiusculas = this.paciente.getNome().toUpperCase();
			this.paciente.setNome(maiusculas);
		}
	}
	
	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}
	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public List<Consulta> getConsultas() {
		
		if (this.consultas == null) {
			ConsultaDAO consultaDao = new ConsultaDAO();
			this.consultas = consultaDao.lerTodos();
		}		
		
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public List<Paciente> getPacientes() {
	
		// verifica se a lista já foi lida do banco
		if (this.pacientes == null)
			this.pacientes = this.dao.lerTodos();
		
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	/*
	 * MÉTODOS
	 */
	
	public String acaoListar() {
		return "pacienteListar.jsf?faces-redirect=true";
	}

	public String acaoAbrirInclusao() {
		return "pacienteEditar.jsf";
	}

	public String acaoAbrirAlteracao(Integer idPaciente) {
		this.paciente = this.dao.lerPorId(idPaciente);
		
		return "pacienteEditar.jsf";
	}
	
	public String acaoSalvar() 	{
		
		Usuario usuario = loginMB.getUsuario();
		
		this.dao.salvar(this.paciente);
		
		return acaoListar();
	}

	public String acaoExcluir(Integer idPaciente) 	{
		this.paciente = this.dao.lerPorId(idPaciente);
		this.dao.excluir(this.paciente);
		
		this.pacientes = null;
		
		return acaoListar();
	}
	
}
