package com.developerstaff.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.developerstaff.model.Usuario;
import com.developerstaff.repository.UsuarioDAO;

@Component
public class AuthProviderService implements AuthenticationProvider {

	@Autowired
	private UsuarioDAO dao;
	
/*	@Autowired
	private Usuario user;*/
	
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String login = authentication.getName();
		String senha = authentication.getCredentials().toString();

		Usuario user = dao.findByNomeAndPassword(login, senha);
		

		if (user != null) {
			user.setEstaLogado(true);
			if (usuarioAtivo(user)) {
				Collection<? extends GrantedAuthority> authorities = user.getRoles();
				
				return new UsernamePasswordAuthenticationToken(login, senha,authorities);
			}
			throw new BadCredentialsException("Este usuário está desativado.");

		} else {
			throw new UsernameNotFoundException("Login e/ou Senha inválidos.");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private boolean usuarioAtivo(Usuario usuario) {
		if (usuario != null) {
			if (usuario.isEnabled() == true) {
				return true;
			}
		}
		return false;
	}

/*	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}*/

}
