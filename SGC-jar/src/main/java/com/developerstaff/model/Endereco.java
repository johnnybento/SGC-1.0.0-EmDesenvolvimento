package com.developerstaff.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Endereco {

	@NotBlank
	private String logradouro;
	@NotNull
	private Long numero;
	@NotBlank
	private String cep;
	@NotBlank
	private String bairro;
	@NotBlank
	private String municipio;
	@NotBlank
	private String estado;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
