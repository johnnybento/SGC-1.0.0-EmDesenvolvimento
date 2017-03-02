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

import com.developerstaff.model.Tipo;
import com.developerstaff.model.Usuario;
import com.developerstaff.repository.LojaDAO;
import com.developerstaff.repository.UsuarioDAO;
import com.developerstaff.repository.filter.UsuarioFiltro;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO dao;
	@Autowired
	private LojaDAO daoLJ;

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuarios/cadastro-usuario");
		
		mv.addObject("lojas",daoLJ.findAll());
		mv.addObject("tipos", Tipo.values());
		mv.addObject(usuario);
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario,  BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/usuarios/novo");

		if (result.hasErrors()) {
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso");
		dao.save(usuario);

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisa(UsuarioFiltro usuarioFiltro) {
		
		ModelAndView mv = new ModelAndView("usuarios/pesquisa-usuario");
		mv.addObject("usuarios",
				dao.findAll().stream()
						.filter(p -> usuarioFiltro.getId() == null || usuarioFiltro.getId().equals(p.getId()))
						.filter(p -> StringUtils.isEmpty(usuarioFiltro.getNome())|| p.getNome().startsWith(usuarioFiltro.getNome()))
						.collect(Collectors.toList()));
		mv.addObject("classe","Excluir Usuario");
		
		return mv;

	}

	@GetMapping("{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		Usuario usuario = dao.findOne(id);

		return novo(usuario);

	}

	@DeleteMapping("{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes) {

		attributes.addFlashAttribute("mensagem", "Usuario excluido com sucesso!!");

		dao.delete(id);

		return "redirect:/usuarios";
	}

}
