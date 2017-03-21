package com.developerstaff.model;

public enum Tipo {

	USUARIO("ROLE_USUARIO",0), TECNICO("ROLE_TECNICO",1), ADMINISTRADOR("ROLE_ADMINISTRADOR",2);
	
	
	public String nome;
	public int idTipo;
	

	Tipo(String nome,int idTipo) {
		this.idTipo = idTipo;
		this.nome = nome;
	}

	public int getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	public String getNome(){
		return this.nome;
	}

}
