package com.developerstaff.repository.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChamadoFiltro {

	private String atributo;
	private Long id;
	private int filtroUm;
	private boolean filtroDois;
	private Long idFiltro;
	private int idDatas = 0;
	/* @DateTimeFormat(pattern="dd/MM/yyyy") */
	private String dataInicio;
	/* @DateTimeFormat(pattern="dd/MM/yyyy") */
	private String dataFim;
	private int idTeste;

	public int getIdTeste() {
		return idTeste;
	}

	public void setIdTeste(int idTeste) {
		this.idTeste = idTeste;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public int getFiltroUm() {
		return filtroUm;
	}

	public void setFiltroUm(int filtroUm) {
		this.filtroUm = filtroUm;
	}

	public boolean isFiltroDois() {
		return filtroDois;
	}

	public void setFiltroDois(boolean filtroDois) {
		this.filtroDois = filtroDois;
	}

	public Long getIdFiltro() {
		return idFiltro;
	}

	public void setIdFiltro(Long idFiltro) {
		this.idFiltro = idFiltro;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public int getIdDatas() {
		return idDatas;
	}

	public void setIdDatas(int idDatas) {
		this.idDatas = idDatas;
	}

	public Calendar convertTxtToC(String dataTxt) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date data = sdf.parse(dataTxt);
			
			c.setTime(data);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//é preciso tratar 
			System.out.println("Erro na conversão do filtro ERROR:"+e.getMessage());
			
		}

		return c;
	}

}
