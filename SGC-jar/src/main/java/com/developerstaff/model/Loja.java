package com.developerstaff.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author JohnnyBento
 *
 */
@Entity
@Table(name = "lojas")
public class Loja implements Serializable {

	private static final long serialVersionUID = 8692905457510786734L;


	private Long id;
	@NotNull
	private Long numeroLoja;
	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;
    @Valid
	private Endereco endereco;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "numero_loja", nullable = true)
	public Long getNumeroLoja() {
		return numeroLoja;
	}

	public void setNumeroLoja(Long numeroLoja) {
		this.numeroLoja = numeroLoja;
	}

	@Embedded
	@Column(nullable = true)
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	

	
	@Column(nullable = true)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(nullable = true)
	public String getDescricao() {
		return descricao;
	}

	@Column(nullable = true)
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loja other = (Loja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
