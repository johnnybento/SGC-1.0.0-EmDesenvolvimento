package com.developerstaff.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.developerstaff.model.Chamado;
import com.developerstaff.model.Status;
import com.developerstaff.model.Usuario;
import com.developerstaff.repository.ChamadoDAO;
import com.developerstaff.repository.filter.ChamadoFiltro;

public class ChamadoBO {

	private ChamadoDAO dao;

	public void criarChamado(Chamado chamado) {

		chamado.getDatas().setDataCriacao(Calendar.getInstance());
		chamado.setStatus(Status.PENDENTE);

		// dao.save(chamado);

	}

	public List<Chamado> fazFiltroChamados(ChamadoFiltro chamadoFiltro, Usuario userlogon, ChamadoDAO dao) {

		this.dao = dao;
		List<Chamado> lista;
		if (userlogon.getTipo().getIdTipo() == 0) {
			lista = pegaListaUser(chamadoFiltro, userlogon);
		} else {

			lista = pegaListaAdm(chamadoFiltro, userlogon);
		}

		return lista;
	}

	private List<Chamado> pegaListaUser(ChamadoFiltro chamadoFiltro, Usuario userlogon) {

		List<Chamado> lista;

		if (chamadoFiltro.getId() != null) {

			lista = new ArrayList<>();

			Chamado ch = dao.findByIdAndLojaNumeroLoja(chamadoFiltro.getId(), userlogon.getLoja().getNumeroLoja());

			if (ch != null) {
				lista.add(ch);
			}

		} else if (chamadoFiltro.getAtributo() != null) {

			if(chamadoFiltro.getIdFiltro() != 4){ 
			lista = dao.procuraByTudoComId("%" + chamadoFiltro.getAtributo() + "%",
					(userlogon.getLoja().getNumeroLoja()));
			}else{
				
				lista = dao.procuraByTecnicoComId("%" + chamadoFiltro.getAtributo() + "%",userlogon.getLoja().getNumeroLoja());
			}
		} else {

			lista = dao.findByLojaNumeroLoja(userlogon.getLoja().getNumeroLoja());

		}

		if (chamadoFiltro.getIdFiltro() != null) {
			if (chamadoFiltro.getIdFiltro().longValue() == new Long(1).longValue()) {

				if (chamadoFiltro.getIdDatas() == 1) {

					lista = dao.pegaDatasCriacao(chamadoFiltro.convertTxtToC(chamadoFiltro.getDataInicio()),
							chamadoFiltro.convertTxtToC(chamadoFiltro.getDataFim()));
				} else if (chamadoFiltro.getIdDatas() == 2) {
					lista = dao.pegaDatasAtendimento(chamadoFiltro.convertTxtToC(chamadoFiltro.getDataInicio()),
							chamadoFiltro.convertTxtToC(chamadoFiltro.getDataFim()));
				} else if (chamadoFiltro.getIdDatas() == 3) {
					lista = dao.pegaDatasFinalizacao(chamadoFiltro.convertTxtToC(chamadoFiltro.getDataInicio()),
							chamadoFiltro.convertTxtToC(chamadoFiltro.getDataFim()));
				}

			}
		}

		return lista;

	}

	private List<Chamado> pegaListaAdm(ChamadoFiltro chamadoFiltro, Usuario userlogon) {
		List<Chamado> lista;

		if (chamadoFiltro.getId() != null) {

			lista = new ArrayList<>();

			Chamado ch = dao.findOne(chamadoFiltro.getId());

			if (ch != null) {
				lista.add(ch);
			}

		} else if (chamadoFiltro.getAtributo() != null) {

			if (chamadoFiltro.getIdFiltro() != 4) {
				lista = dao.procuraByTudoSemTecnico("%" + chamadoFiltro.getAtributo() + "%");
			} else {
				lista = dao.procuraByTecnico("%" + chamadoFiltro.getAtributo() + "%");
			}
		} else {

			lista = dao.findAll();

		}

		if (chamadoFiltro.getIdFiltro() != null) {
			if (chamadoFiltro.getIdFiltro().longValue() == new Long(1).longValue()) {

				if (chamadoFiltro.getIdDatas() == 1) {

					lista = dao.pegaDatasCriacao(chamadoFiltro.convertTxtToC(chamadoFiltro.getDataInicio()),
							chamadoFiltro.convertTxtToC(chamadoFiltro.getDataFim()));
				} else if (chamadoFiltro.getIdDatas() == 2) {
					lista = dao.pegaDatasAtendimento(chamadoFiltro.convertTxtToC(chamadoFiltro.getDataInicio()),
							chamadoFiltro.convertTxtToC(chamadoFiltro.getDataFim()));
				} else if (chamadoFiltro.getIdDatas() == 3) {
					lista = dao.pegaDatasFinalizacao(chamadoFiltro.convertTxtToC(chamadoFiltro.getDataInicio()),
							chamadoFiltro.convertTxtToC(chamadoFiltro.getDataFim()));
				}

			}
		}

		return lista;
	}

}
