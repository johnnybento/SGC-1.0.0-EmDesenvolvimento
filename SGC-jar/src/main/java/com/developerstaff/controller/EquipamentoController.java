package com.developerstaff.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developerstaff.model.Equipamento;
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

	@GetMapping("/novo")
	public ModelAndView novo(Equipamento equipamento) {
		ModelAndView mv = new ModelAndView("equipamentos/cadastro-equipamento");
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

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisa(EquipamentoFiltro equipamentoFiltro) {
		
		ModelAndView mv = new ModelAndView("equipamentos/pesquisa-equipamento");
		mv.addObject("equipamentos",
				dao.findAll().stream()
						.filter(p -> equipamentoFiltro.getId() == null || equipamentoFiltro.getId().equals(p.getId()))
						.filter(p -> StringUtils.isEmpty(equipamentoFiltro.getNome())
								|| p.getNome().startsWith(equipamentoFiltro.getNome()))
						.collect(Collectors.toList()));
		mv.addObject("classe","Excluir equipamento");
		
		return mv;

	}

	@GetMapping("{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		Equipamento equipamento = dao.findOne(id);

		return novo(equipamento);

	}

	@DeleteMapping("{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes) {

		attributes.addFlashAttribute("mensagem", "Equipamento excluido com sucesso!!");

		dao.delete(id);

		return "redirect:/equipamentos";
	}

}
