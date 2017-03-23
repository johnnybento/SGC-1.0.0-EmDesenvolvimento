package com.developerstaff.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;



@Embeddable
public class Datas {

	
	private Calendar dataCriacao;
	private Calendar dataAtendimento;
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
	
	@Transient
	public String getTempoSolucao(){

	      int dias = getTempoDias();
	      int hours = (getTempoHoras())- (24*dias);
		return ""+dias+" dias e "+hours+" horas";
	}
	@Transient
	public int getTempoDias(){
	
	     DateTime dataInicial = new DateTime(dataCriacao);
	     DateTime dataFinal = new DateTime(dataFinalizacao);
	      int dias = Days.daysBetween(dataInicial, dataFinal).getDays();
	      
		return dias;
	}
	@Transient
	public int getTempoHoras(){
	
	     DateTime dataInicial = new DateTime(dataCriacao);
	     DateTime dataFinal = new DateTime(dataFinalizacao);
	     
	      int hours = Hours.hoursBetween(dataInicial, dataFinal).getHours();
		return hours;
	} 
	

	public Calendar getDataDePrevisao() {
		return dataDePrevisao;
	}

	public void setDataDePrevisao(Calendar dataDePrevisao) {
		this.dataDePrevisao = dataDePrevisao;
	}

}
