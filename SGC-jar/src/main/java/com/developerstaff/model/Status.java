package com.developerstaff.model;

public enum Status {

	PENDENTE(0), ATENDIMENTO(1), FINALIZADO(2);

	public int idStatus;

	Status(int idStatus) {
		this.idStatus = idStatus;
	}
	
	public int getIdStatus(){
		return this.idStatus;
	}

}
