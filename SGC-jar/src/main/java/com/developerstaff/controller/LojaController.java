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

import com.developerstaff.model.Loja;
import com.developerstaff.repository.LojaDAO;
import com.developerstaff.repository.filter.LojaFiltro;

@Controller
@RequestMapping("/lojas")
public class LojaController {

	@Autowired
	private LojaDAO dao;

	@GetMapping("/novo")
	public ModelAndView novo(Loja loja) {
		ModelAndView mv = new ModelAndView("lojas/cadastro-loja");
		mv.addObject(loja);
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Loja loja,  BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/lojas/novo");

		if (result.hasErrors()) {
			return novo(loja);
		}
		attributes.addFlashAttribute("mensagem", "Loja salva com sucesso");
		dao.save(loja);

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisa(LojaFiltro lojaFiltro) {
		
		ModelAndView mv = new ModelAndView("lojas/pesquisa-loja");
		mv.addObject("lojas",
				dao.findAll().stream()
						.filter(p -> lojaFiltro.getId() == null || lojaFiltro.getId().equals(p.getId()))
						.filter(p -> StringUtils.isEmpty(lojaFiltro.getNome())
								|| p.getNome().startsWith(lojaFiltro.getNome()))
						.collect(Collectors.toList()));
		mv.addObject("classe","Excluir Loja");
		
		return mv;

	}

	@GetMapping("{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		Loja loja = dao.findOne(id);

		return novo(loja);

	}

	@DeleteMapping("{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes) {

		attributes.addFlashAttribute("mensagem", "Loja excluida com sucesso!!");

		dao.delete(id);

		return "redirect:/lojas";
	}

}
