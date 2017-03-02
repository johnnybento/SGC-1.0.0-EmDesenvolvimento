package com.developerstaff.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developerstaff.model.Chamado;
import com.developerstaff.model.Datas;
import com.developerstaff.model.Prioridade;
import com.developerstaff.model.ReAbrir;
import com.developerstaff.model.Solucao;
import com.developerstaff.model.Status;
import com.developerstaff.model.TipoSolucao;
import com.developerstaff.model.Usuario;
import com.developerstaff.repository.ChamadoDAO;
import com.developerstaff.repository.EquipamentoDAO;
import com.developerstaff.repository.LojaDAO;
import com.developerstaff.repository.UsuarioDAO;
import com.developerstaff.repository.filter.ChamadoFiltro;
import com.developerstaff.repository.filter.UsuarioFiltro;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {

	@Autowired
	private EquipamentoDAO daoEqui;
	@Autowired
	private LojaDAO daoLj;
	@Autowired
	private UsuarioDAO daoUser;
	@Autowired
	private ChamadoDAO dao;

	@GetMapping("/novo")
	public ModelAndView novo(Chamado chamado) {

		ModelAndView mv = new ModelAndView("chamados/criar-chamado");
		mv.addObject("prioridades", Prioridade.values());
		mv.addObject("usuarios", daoUser.findAll());
		mv.addObject("lojas", daoLj.findAll());
		mv.addObject("equipamentos", daoEqui.findAll());
		mv.addObject(chamado);

		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Chamado chamado, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/chamados/novo");

		if (result.hasErrors()) {
			return novo(chamado);

		}
		attributes.addFlashAttribute("mensagem", "Chamado criado com sucesso!");

		chamado.setDatas(new Datas());
		chamado.getDatas().setDataCriacao(Calendar.getInstance());
		chamado.setStatus(Status.valueOf("PENDENTE"));

		dao.save(chamado);
		return mv;
	}

	/*
	 * @GetMapping public ModelAndView listGlobal() {
	 * 
	 * ModelAndView mv = new ModelAndView("chamados/global-chamado");
	 * mv.addObject("chamados", dao.findAll());
	 * 
	 * return mv;
	 * 
	 * }
	 */

	@GetMapping(value = "/status/{status}")
	public ModelAndView filterByStatus(@PathVariable String status) {
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		mv.addObject("chamados", dao.findByStatus(Status.valueOf(status)));
		return mv;
	}

	/*
	 * @GetMapping(value="/{orderby}") public ModelAndView
	 * orderbyChamado(@PathVariable String orderby){ ModelAndView mv = new
	 * ModelAndView("chamados/global-chamado"); ChamadoBO bo = new ChamadoBO();
	 * 
	 * List<Chamado> lista = bo.obterListaOrdenada(orderby);
	 * mv.addObject("chamados", lista); return mv; }
	 */

	@GetMapping("/prioridade")
	public ModelAndView orderByPrioridade() {
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		mv.addObject("chamados", dao.selectPrioridadeOrderBy());
		return mv;
	}

	@GetMapping("/dataCricao")
	public ModelAndView orderByDataCricao() {
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		mv.addObject("chamados", dao.selectDataCriacaoOrderBy());
		return mv;
	}

	@GetMapping
	public ModelAndView listaGlobal(ChamadoFiltro chamadoFiltro) {
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		mv.addObject("tecnicos", daoUser.getTecnicos());
		mv.addObject("tecnico", new Usuario());
		mv.addObject("filtro",chamadoFiltro);

		if (chamadoFiltro.getId() != null) {
			List<Chamado> lista = new ArrayList<>();

			lista.add(dao.findOne(chamadoFiltro.getId()));
			mv.addObject("chamados", lista);
		} else if (chamadoFiltro.getAtributo() != null) {

			mv.addObject("chamados", dao.procuraByTudo("%"+chamadoFiltro.getAtributo()+"%"));

		} else {
			mv.addObject("chamados", dao.findAll());

		}
		System.out.println(chamadoFiltro.getIdFiltro());
		return mv;
	}
	
/*	@PostMapping
	public ModelAndView listaGlobalFiltro(ChamadoFiltro chamadoFiltro){
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		System.out.println(chamadoFiltro.getIdFiltro());
		return listaGlobalFiltro(chamadoFiltro);
	}*/

	@PostMapping("/tecnico/{id}")
	public ModelAndView definirTecnico(@PathVariable Long id, @Valid UsuarioFiltro tecnico, BindingResult result,
			RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/chamados");

		if (result.hasErrors()) {
			attributes.addFlashAttribute("erro", "ERRO! por favor ao definir o técnico, selecione algum técnico!");
			return mv;
		}
		Usuario tec = daoUser.getOne(tecnico.getId());
		Chamado chamado = dao.getOne(id);
		chamado.setTecnico(tec);
		dao.save(chamado);
		attributes.addFlashAttribute("mensagem", "Técnico definido com sucesso");
		return mv;
	}

	@GetMapping("/atender/{id}")
	public ModelAndView abrirAtender(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/chamados/alterar-chamado");
		Chamado chamado = dao.findOne(id);
		mv.addObject("chamado", chamado);
		return mv;

	}

	@PostMapping("/atender/{id}")
	public ModelAndView atender(@PathVariable Long id, RedirectAttributes attributes) {

		Chamado chamado = dao.findOne(id);

		chamado.getDatas().setDataAtendimento(Calendar.getInstance());
		chamado.setStatus(Status.valueOf("ATENDIMENTO"));

		Usuario tec = daoUser.findOne(new Long(3));

		chamado.setTecnico(tec);

		dao.save(chamado);
		attributes.addAttribute("mensagem", "Chamado em Atendimento");

		return new ModelAndView("redirect:/chamados/informacoes/" + id);

	}

	@GetMapping("/informacoes/{id}")
	public ModelAndView abrirInformacoes(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/chamados/informacoes-chamado");
		Chamado chamado = dao.findOne(id);
		Solucao solucao = chamado.getSolucao();
		mv.addObject("chamado", chamado);
		mv.addObject("solucao", solucao);

		return mv;
	}

	@GetMapping("/finalizar/{id}")
	public ModelAndView abrirFinalizar(@PathVariable Long id, Solucao solucao) {
		ModelAndView mv = new ModelAndView("/chamados/alterar-chamado");
		Chamado chamado = dao.findOne(id);
		mv.addObject("chamado", chamado);
		mv.addObject("TipoSolucoes", TipoSolucao.values());
		mv.addObject("solucao", solucao);
		return mv;
	}

	@PostMapping("/finalizar/{id}")
	public ModelAndView finalizar(@PathVariable Long id, @Valid Solucao solucao, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {

			return abrirFinalizar(id, solucao);

		}

		Chamado chamado = dao.getOne(id);
		chamado.setSolucao(solucao);
		chamado.getDatas().setDataFinalizacao(Calendar.getInstance());
		chamado.setStatus(Status.FINALIZADO);

		dao.save(chamado);

		attributes.addFlashAttribute("mensagem", "Chamado finalizado com sucesso");

		return new ModelAndView("redirect:/chamados/informacoes/" + id);
	}

	@GetMapping("/reabrir/{id}")
	public ModelAndView abrirReAbrir(@PathVariable Long id, ReAbrir reAbrir) {
		ModelAndView mv = new ModelAndView("/chamados/alterar-chamado");
		Chamado chamado = dao.findOne(id);
		mv.addObject("chamado", chamado);

		return mv;
	}

	@PostMapping("/reabrir/{id}")
	public ModelAndView reabrir(@PathVariable Long id, @Valid ReAbrir reAbrir, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return abrirReAbrir(id, reAbrir);
		}
		attributes.addAttribute("mensagem", "Chamado reaberto com sucesso!");

		Chamado chamado = dao.findOne(id);
		chamado.setReAbrir(reAbrir);
		chamado.getReAbrir().setReAberto(true);
		chamado.getDatas().setDataReAberto(Calendar.getInstance());
		chamado.getDatas().setDataFinalizacao(null);
		chamado.getDatas().setDataAtendimento(null);
		chamado.setSolucao(null);
		chamado.setStatus(Status.PENDENTE);

		dao.save(chamado);

		return new ModelAndView("redirect:/chamados/informacoes/" + id);
	}

}
