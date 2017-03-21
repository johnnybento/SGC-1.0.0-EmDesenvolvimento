package com.developerstaff.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="solucoes")
public class Solucao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String descricaoSolucao;
	@Transient
	private BigDecimal valorTotal;
	@NotNull
	private TipoSolucao tipoSolucao;
	private Datas datas;
	private ReAbrir reAbrir;
	private Usuario tecnico;
	private Usuario usuario;
	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorTransporte;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal outrosValores;

	public String getDescricaoSolucao() {
		return descricaoSolucao;
	}

	public void setDescricaoSolucao(String descricaoSolucao) {
		this.descricaoSolucao = descricaoSolucao;
	}

	public Usuario getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getValorTotal() {
		if (valorTotal == null) {

			valorTotal = new BigDecimal("0");
		}

		outrosValores = getOutrosValores();
		
		valorTotal = outrosValores.add(valorTransporte);
		
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public TipoSolucao getTipoSolucao() {
		return tipoSolucao;
	}

	public void setTipoSolucao(TipoSolucao tipoSolucao) {
		this.tipoSolucao = tipoSolucao;
	}

	public BigDecimal getValorTransporte() {
		if (valorTransporte == null) {

			valorTransporte = new BigDecimal(0.0);
		}
		return valorTransporte;
	}

	public void setValorTransporte(BigDecimal valorTransporte) {
		this.valorTransporte = valorTransporte;
	}
	
	

	public BigDecimal getOutrosValores() {
		if (outrosValores == null) {
			outrosValores = new BigDecimal(0.0);
		}

		return outrosValores;
	}

	public void setOutrosValores(BigDecimal outrosValores) {
		this.outrosValores = outrosValores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Datas getDatas() {
		return datas;
	}

	public void setDatas(Datas datas) {
		this.datas = datas;
	}

	public ReAbrir getReAbrir() {
		return reAbrir;
	}

	public void setReAbrir(ReAbrir reAbrir) {
		this.reAbrir = reAbrir;
	}

}
