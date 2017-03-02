package com.developerstaff.model;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

@Embeddable
public class Solucao {

	@NotBlank
	private String descricaoSolucao;
	@Transient
	private BigDecimal valorTotal;
	@NotNull
	private TipoSolucao tipoSolucao;

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

			valorTransporte = new BigDecimal("0");
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

}
