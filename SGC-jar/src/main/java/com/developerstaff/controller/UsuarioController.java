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

import com.developerstaff.model.Role;
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

		mv.addObject("lojas", daoLJ.findAll());
		mv.addObject("tipos", Tipo.values());
		mv.addObject(usuario);
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("redirect:/usuarios/novo");

		Usuario user = dao.findByLogin(usuario.getLogin());

		if (user != null) {
			result.rejectValue("login", "error.usuario", "Este login não está disponivel ");

			return novo(usuario);

		}

		if (result.hasErrors()) {
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso");

		Role role = new Role();
		role.setNome(usuario.getTipo());
		role.setUsuario(usuario);
		usuario.getRoles().add(role);
		dao.save(usuario);

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisa(UsuarioFiltro usuarioFiltro) {

		ModelAndView mv = new ModelAndView("usuarios/pesquisa-usuario");
		if (usuarioFiltro.getId() != null) {
			List<Usuario> lista = new ArrayList<>();

			lista.add(dao.findOne(usuarioFiltro.getId()));
			mv.addObject("usuarios", lista);
		} else if (usuarioFiltro.getNome() != null) {

			mv.addObject("usuarios", dao.procuraByTudo("%" + usuarioFiltro.getNome() + "%"));

		} else {
			mv.addObject("usuarios", dao.findAll());

		}

		return mv;

	}

	@GetMapping("{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		Usuario usuario = dao.findOne(id);

		if (usuario.getTipo().getIdTipo() == 2) {
			usuario.setPassword("");
		}
		return novo(usuario);

	}

	@DeleteMapping("{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes) {

		try {
			dao.delete(id);
			attributes.addFlashAttribute("mensagem", "Usuario excluido com sucesso!!");
		} catch (DataIntegrityViolationException e) {
			System.out.println("Depencia em alguma tabela ERROR: " + e.getMessage());
			attributes.addFlashAttribute("erro",
					"ERROR! possui informações deste Usuário em outras tabelas do sistema.");

			return "redirect:/usuarios";
		}

		return "redirect:/usuarios";
	}

	@GetMapping("/detalhes/{id}")
	public ModelAndView detalhes(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("usuarios/detalhes-usuario");
		Usuario usuario = dao.findOne(id);

		mv.addObject("usuario", usuario);

		return mv;

	}

}
