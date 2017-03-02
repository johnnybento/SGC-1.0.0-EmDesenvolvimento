package com.developerstaff.model;

public enum Prioridade {

	ALTA(0,"Alta"), MEDIA(1,"Media"), BAIXA(2,"Baixa");

	public int idPrioridade;
	public String nomeP;

	Prioridade(int idPrioridade,String nomeP) {
		this.idPrioridade = idPrioridade;
		this.nomeP = nomeP;
	}

}
