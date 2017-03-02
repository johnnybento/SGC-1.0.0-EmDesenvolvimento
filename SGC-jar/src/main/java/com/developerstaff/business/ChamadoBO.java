package com.developerstaff.business;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;


import com.developerstaff.model.Chamado;
import com.developerstaff.model.Status;
import com.developerstaff.repository.ChamadoDAO;


public class ChamadoBO {

	@Autowired
	private ChamadoDAO dao;

	public void criarChamado(Chamado chamado) {

		chamado.getDatas().setDataCriacao(Calendar.getInstance());
		chamado.setStatus(Status.PENDENTE);

		dao.save(chamado);

	}

}
