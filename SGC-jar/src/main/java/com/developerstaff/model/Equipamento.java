package com.developerstaff.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

/**
 * @author JohnnyBento
 *
 */
@Entity
@Table(name = "equipamentos")
public class Equipamento implements Serializable {

	private static final long serialVersionUID = -3799155159721099894L;

	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;
	@NotNull
	private Setor setor;
	@NotBlank
	private String local;
	@NotNull
	private Loja loja;
	@NotNull
	private Long patrimonio;
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	@NotNull
	private List<Componente> componentes = new ArrayList<>();

	private boolean campo;

	/* Inicio gets e sets */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToOne
	@JoinColumn(name = "id_setor")
	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@ManyToOne
	@JoinColumn(name = "id_loja")
	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Long getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Long patrimonio) {
		this.patrimonio = patrimonio;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Transient
	public boolean isCampo() {
		return campo;
	}

	public void setCampo(boolean campo) {
		this.campo = campo;
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}

	@Transient
	public String getComponentesTexto() {
		String texto = "";

		List<Componente> lista = componentes;
        
		
		for(int i = 0; i<lista.size(); i++){
			
			Componente c = lista.get(i);
			if(i == lista.size() -1){
				texto = texto+c.getNome()+".";
			}else{
				texto = texto+c.getNome()+", ";
			}
		}

		

		return texto;
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
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * Fim gets e sets e Comeco Equals e HashCode
	 */

}
