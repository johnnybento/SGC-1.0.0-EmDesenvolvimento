package com.developerstaff.model;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class ReAbrir {
	
	private Boolean reAberto;
	@NotBlank
	private String motivo;
	
	
	public Boolean getReAberto() {
		return reAberto;
	}
	public void setReAberto(Boolean reAberto) {
		this.reAberto = reAberto;
	}
	public String getMotivo() {
		if(motivo ==null){
			motivo="";
		}
		
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	

}
