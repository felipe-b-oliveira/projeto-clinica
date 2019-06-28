package modelo.dao;

import modelo.dominio.Usuario;

public class UsuarioDAO {
	
	public Usuario obter(String login)
	{
		Usuario novo = new Usuario();
		novo.setLogin(login);
		novo.setSenha("123");
		
		return novo;
	}

}
