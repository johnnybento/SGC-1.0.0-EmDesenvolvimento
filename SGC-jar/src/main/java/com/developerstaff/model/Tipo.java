package com.developerstaff.model;

public enum Tipo {

	USUARIO(0), TECNICO(1), ADMINISTRADOR(2);

	public int idTipo;

	Tipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public int getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

}
