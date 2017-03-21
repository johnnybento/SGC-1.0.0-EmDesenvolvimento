package com.developerstaff.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author JohnnyBento
 *
 */

@Entity
@Table(name = "chamados")
public class Chamado {

	private Long id;
	@NotBlank
	private String descricao;
	@NotNull
	private Prioridade prioridade;
	@NotNull
	private Loja loja;
	@Valid
	private Datas datas;
	@NotNull
	private Equipamento equipamento;
	private Usuario tecnico;
	private Long tempodeAtendimento;
	private Usuario usuario;
	private Status status;
	private BigDecimal valor;
	private List<Solucao> solucoes = new ArrayList<>();
	private ReAbrir reAbrir;
	@NotBlank
	private String componente;

	/*
	 * gets e sets
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Enumerated(EnumType.STRING)
	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "dataCriacao", column = @Column(name = "data_criacao")),
			@AttributeOverride(name = "dataAtendimento", column = @Column(name = "data_atendimento")),
			@AttributeOverride(name = "dataFinalizacao", column = @Column(name = "data_finalizacao")) })
	public Datas getDatas() {
		return datas;
	}

	public void setDatas(Datas datas) {
		this.datas = datas;
	}

	@ManyToOne
	public Usuario getTecnico() {
		/*
		 * if(tecnico==null){ tecnico = new Usuario(); tecnico.setNome(""); }
		 */

		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

	@Transient
	public Long getTempodeAtendimento() {
		return tempodeAtendimento;
	}

	public void setTempodeAtendimento(Long tempodeAtendimento) {
		this.tempodeAtendimento = tempodeAtendimento;
	}

	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@JoinColumn(name = "id_loja")
	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	@ManyToOne
	@JoinColumn(name = "id_equipamento")
	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

    @OneToMany(cascade = CascadeType.MERGE)
   	public List<Solucao> getSolucoes() {
		return solucoes;
	}

	public void setSolucoes(List<Solucao> solucoes) {
		this.solucoes = solucoes;
	}

	public ReAbrir getReAbrir() {
		return reAbrir;
	}

	public void setReAbrir(ReAbrir reAbrir) {
		this.reAbrir = reAbrir;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	/*
	 * Fim gets sets Começo Equals e HashCode
	 */

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
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
