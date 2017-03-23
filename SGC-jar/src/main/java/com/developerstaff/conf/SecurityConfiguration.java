package com.developerstaff.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
    @Autowired
    private UserDetailsService userDetailsService;
	
	
/*	@Autowired
	private AuthProviderService authProvider;*/
	
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
	
	
/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth
        .authenticationProvider(authProvider);
	}*/
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web
		.ignoring().antMatchers("/layout/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.
		authorizeRequests()
		.antMatchers("/equipamentos").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/equipamentos/detalhes/**").hasAnyRole("TECNICO","ADMINISTRADOR","USUARIO")
		.antMatchers("/equipamentos/**").hasRole("ADMINISTRADOR")
		.antMatchers("/usuarios").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/usuarios/detalhes/**").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/usuarios/**").hasRole("ADMINISTRADOR")
		.antMatchers("/lojas").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/lojas/detalhes/**").hasAnyRole("TECNICO","ADMINISTRADOR","USUARIO")
		.antMatchers("/lojas/**").hasRole("ADMINISTRADOR")
		.antMatchers("/chamados/atender/**").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/chamados/finalizar/**").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/chamados/reabrir/**").hasAnyRole("TECNICO","ADMINISTRADOR")
		.antMatchers("/chamados").hasAnyRole("TECNICO","ADMINISTRADOR","USUARIO")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		;
	}

}
