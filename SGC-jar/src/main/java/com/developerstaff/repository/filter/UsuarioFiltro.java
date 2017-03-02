package com.developerstaff.repository.filter;

import javax.validation.constraints.NotNull;

import com.developerstaff.model.Loja;
import com.developerstaff.model.Tipo;

public class UsuarioFiltro {
	
	@NotNull
	private Long id;
	private String Nome;
	private Loja loja;
	private Tipo tipo;
	
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	

}
