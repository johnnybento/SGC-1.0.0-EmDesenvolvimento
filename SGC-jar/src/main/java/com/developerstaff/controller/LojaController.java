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

import com.developerstaff.model.Loja;
import com.developerstaff.model.Setor;
import com.developerstaff.repository.LojaDAO;
import com.developerstaff.repository.SetorDAO;
import com.developerstaff.repository.filter.LojaFiltro;

@Controller
@RequestMapping("/lojas")
public class LojaController {

	@Autowired
	private LojaDAO dao;
	@Autowired
	private SetorDAO setorDao;

	@GetMapping("/novo")
	public ModelAndView novo(Loja loja) {

		ModelAndView mv = new ModelAndView("lojas/cadastro-loja");
		mv.addObject(loja);
		mv.addObject("setores", setorDao.findAll());
		mv.addObject("setor", new Setor());
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/lojas/novo");

		if (result.hasErrors()) {
			return novo(loja);
		}
		attributes.addFlashAttribute("mensagem", "Loja salva com sucesso");
		dao.save(loja);

		if (loja.getSetores().isEmpty()) {

			return new ModelAndView("redirect:/lojas/" + loja.getId());
		}

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisa(LojaFiltro lojaFiltro) {

		ModelAndView mv = new ModelAndView("lojas/pesquisa-loja");
		if (lojaFiltro.getId() != null) {
			List<Loja> lista = new ArrayList<>();

			lista.add(dao.findOne(lojaFiltro.getId()));
			mv.addObject("lojas", lista);
		} else if (lojaFiltro.getNome() != null) {

			mv.addObject("lojas", dao.procuraByTudo("%" + lojaFiltro.getNome() + "%"));

		} else {
			mv.addObject("lojas", dao.findAll());

		}
		
		
		return mv;

	}

	@GetMapping("{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		Loja loja = dao.findOne(id);

		return novo(loja);

	}

	@DeleteMapping("{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes) {

		

		try{
		
			dao.delete(id);
			attributes.addFlashAttribute("mensagem", "Loja excluida com sucesso!!");

		}catch (DataIntegrityViolationException e) {
			System.out.println("Depencia em alguma tabela ERROR: "+e.getMessage());
			attributes.addFlashAttribute("erro","ERROR! possui informações desta Loja em outras tabelas do sistema.");
			
			return "redirect:/lojas";
		}
		return "redirect:/lojas";
	}

	@PostMapping("/setor")
	public ModelAndView addSetor(Setor setor, Long idLoja) {

		if (setor.getNomeSetor() == null || setor.getNomeSetor().isEmpty()) {
			return new ModelAndView("redirect:/lojas/" + idLoja);
		}
		setorDao.save(setor);

		return new ModelAndView("redirect:/lojas/" + idLoja);
	}

	@DeleteMapping("/setor")
	public ModelAndView removeSetor(Setor setor, Long idLoja) {

		if (setor.getId() == null) {
			return new ModelAndView("redirect:/lojas/" + idLoja);
		}
         
		List<Loja> lojas = dao.findAll();
		setor = setorDao.findOne(setor.getId());
		
		for(Loja lj : lojas){
			
			if(lj.getSetores().remove(setor)){
				dao.save(lj);	
			}
			
		}
		
		
		setorDao.delete(setor);

		return new ModelAndView("redirect:/lojas/" + idLoja);
	}
	
	@GetMapping("/detalhes/{id}")
	public ModelAndView detalhes(@PathVariable Long id){
		ModelAndView mv = new ModelAndView("lojas/detalhes-loja");
        Loja loja = dao.findOne(id);
        
        mv.addObject("loja",loja);
		
		
		return mv;
		
	}

}
