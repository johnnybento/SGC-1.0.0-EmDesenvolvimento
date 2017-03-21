package com.developerstaff.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name ="role")
public class Role implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4791899777150878036L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Enumerated(EnumType.STRING)
	private Tipo nome;
	
    @ManyToOne
	private Usuario usuario;
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nome.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tipo getNome() {
		return nome;
	}

	public void setNome(Tipo nome) {
		this.nome = nome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
