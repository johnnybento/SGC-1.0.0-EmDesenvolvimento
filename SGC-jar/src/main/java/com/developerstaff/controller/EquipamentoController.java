package com.developerstaff.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developerstaff.model.Componente;
import com.developerstaff.model.Equipamento;
import com.developerstaff.repository.ComponenteDAO;
import com.developerstaff.repository.EquipamentoDAO;
import com.developerstaff.repository.LojaDAO;
import com.developerstaff.repository.filter.EquipamentoFiltro;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

	@Autowired
	private EquipamentoDAO dao;
	@Autowired
	private LojaDAO daoLj;
	@Autowired
	private ComponenteDAO daoComp;

	@GetMapping("/novo")
	public ModelAndView novo(Equipamento equipamento) {
		ModelAndView mv = new ModelAndView("equipamentos/cadastro-equipamento");
		mv.addObject("componentes", daoComp.findAll());
		mv.addObject("componente", new Componente());
		if (equipamento.getLoja() != null) {

			equipamento.setCampo(true);
			mv.addObject("setores", equipamento.getLoja().getSetores());

		} else {

		}

		mv.addObject("lojas", daoLj.findAll());

		mv.addObject(equipamento);
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/equipamentos/novo");

		if (result.hasErrors()) {
			return novo(equipamento);
		}
		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso");
		dao.save(equipamento);

		if (equipamento.getComponentes().isEmpty()) {

			return new ModelAndView("redirect:/equipamentos/" + equipamento.getId());
		}

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisa(EquipamentoFiltro equipamentoFiltro) {

		ModelAndView mv = new ModelAndView("equipamentos/pesquisa-equipamento");

		if (equipamentoFiltro.getId() != null) {
			List<Equipamento> lista = new ArrayList<>();

			lista.add(dao.findOne(equipamentoFiltro.getId()));
			mv.addObject("equipamentos", lista);
		} else if (equipamentoFiltro.getNome() != null) {

			mv.addObject("equipamentos", dao.procuraByTudo("%" + equipamentoFiltro.getNome() + "%"));

		} else {
			mv.addObject("equipamentos", dao.findAll());

		}

		return mv;

	}

	@GetMapping("{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		Equipamento equipamento = dao.findOne(id);

		return novo(equipamento);

	}

	@DeleteMapping("{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes) {

		
		try {
			dao.delete(id);
			attributes.addFlashAttribute("mensagem", "Equipamento excluido com sucesso!!");
			
		} catch (DataIntegrityViolationException e) {
			System.out.println("Depencia em alguma tabela ERROR: " + e.getMessage());
			attributes.addFlashAttribute("erro", "ERROR! possui informações deste Equipamento em outras tabelas do sistema.");

			return "redirect:/equipamentos";
		}
		return "redirect:/equipamentos";
	}

	@PostMapping("/componentes")
	public ModelAndView addSetor(Componente componente, Long idEquipamento) {

		if (componente.getNome() == null || componente.getNome().isEmpty()) {
			return new ModelAndView("redirect:/equipamentos/" + idEquipamento);
		}
		daoComp.save(componente);

		return new ModelAndView("redirect:/equipamentos/" + idEquipamento);
	}

	@DeleteMapping("/componentes")
	public ModelAndView removeSetor(Componente componente, Long idEquipamento) {

		if (componente.getId() == null) {
			return new ModelAndView("redirect:/equipamentos/" + idEquipamento);
		}

		List<Equipamento> equipamentos = dao.findAll();
		componente = daoComp.findOne(componente.getId());

		for (Equipamento equi : equipamentos) {

			if (equi.getComponentes().remove(componente)) {
				dao.save(equi);
			}

		}

		daoComp.delete(componente);

		return new ModelAndView("redirect:/equipamentos/" + idEquipamento);
	}

	@GetMapping("/detalhes/{id}")
	public ModelAndView detalhes(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("equipamentos/detalhes-equipamento");
		Equipamento equipamento = dao.findOne(id);

		mv.addObject("equipamento", equipamento);

		return mv;

	}

}
