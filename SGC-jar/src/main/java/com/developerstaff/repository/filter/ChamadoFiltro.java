package com.developerstaff.repository.filter;

public class ChamadoFiltro {

	private String atributo;
	private Long id;
	private boolean filtroUm;
	private boolean filtroDois;
	private Long idFiltro;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public boolean isFiltroUm() {
		return filtroUm;
	}

	public void setFiltroUm(boolean filtroUm) {
		this.filtroUm = filtroUm;
	}

	public boolean isFiltroDois() {
		return filtroDois;
	}

	public void setFiltroDois(boolean filtroDois) {
		this.filtroDois = filtroDois;
	}

	public Long getIdFiltro() {
		return idFiltro;
	}

	public void setIdFiltro(Long idFiltro) {
		this.idFiltro = idFiltro;
	}
	
}
