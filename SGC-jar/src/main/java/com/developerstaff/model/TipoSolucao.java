package com.developerstaff.model;

public enum TipoSolucao {
	
	TROCA("Troca do Equipamento", 0),REPARO("Reparo do Equipamento", 1),OUTROS("Outros", 2);
	
	public String nomeTipos;
	public int idTipos;
	
	TipoSolucao(String nomeTipos,int idTipos){
		this.nomeTipos = nomeTipos;
		this.idTipos = idTipos;
	}

}
