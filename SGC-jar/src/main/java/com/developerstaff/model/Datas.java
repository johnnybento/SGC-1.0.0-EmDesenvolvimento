package com.developerstaff.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Datas {

	
	private Calendar dataCriacao;
	private Calendar dataAtendimento;
	private Calendar dataReAberto;
	private Calendar dataFinalizacao;
	@Transient
	private Calendar dataDePrevisao;

	public Calendar getDataCriacao() {

		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Calendar getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Calendar dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public Calendar getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Calendar dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	

	public Calendar getDataReAberto() {
		return dataReAberto;
	}

	public void setDataReAberto(Calendar dataReAberto) {
		this.dataReAberto = dataReAberto;
	}

	@Transient
	public String getDataCriacaoF() {
		String dataFormatada = "";
		if (dataCriacao != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			dataFormatada = sdf.format(dataCriacao.getTime());
		}

		return dataFormatada;
	}

	@Transient
	public String getDataAtendimentoF() {
		String dataFormatada = "Ainda não foi Atendido";
		if (dataAtendimento != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			dataFormatada = sdf.format(dataAtendimento.getTime());
		}

		return dataFormatada;
	}

	@Transient
	public String getDataFinalizacaoF() {
		String dataFormatada = "Ainda não foi finalizado";
		if (dataFinalizacao != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			dataFormatada = sdf.format(dataFinalizacao.getTime());
		}

		return dataFormatada;
	}

	public Calendar getDataDePrevisao() {
		return dataDePrevisao;
	}

	public void setDataDePrevisao(Calendar dataDePrevisao) {
		this.dataDePrevisao = dataDePrevisao;
	}

}
