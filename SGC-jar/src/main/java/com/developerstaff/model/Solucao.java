package com.developerstaff.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="solucoes")
public class Solucao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String descricaoSolucao;
	@Transient
	private BigDecimal valorTotal;
	@NotNull
	private TipoSolucao tipoSolucao;
	
	//informações do chamado
	private Datas datas;
	private ReAbrir reAbrir;
	@OneToOne
	@JoinColumn(name="id_tecnico")
	private Usuario tecnico;
	@OneToOne
	@JoinColumn(name="id_usuario")
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
		Solucao other = (Solucao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	

}
