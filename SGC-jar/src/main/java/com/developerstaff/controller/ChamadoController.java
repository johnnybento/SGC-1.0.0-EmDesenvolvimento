package com.developerstaff.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developerstaff.business.ChamadoBO;
import com.developerstaff.model.Chamado;
import com.developerstaff.model.Componente;
import com.developerstaff.model.Datas;
import com.developerstaff.model.Equipamento;
import com.developerstaff.model.Messenger;
import com.developerstaff.model.Prioridade;
import com.developerstaff.model.ReAbrir;
import com.developerstaff.model.Solucao;
import com.developerstaff.model.Status;
import com.developerstaff.model.TipoSolucao;
import com.developerstaff.model.Usuario;
import com.developerstaff.repository.ChamadoDAO;
import com.developerstaff.repository.EquipamentoDAO;
import com.developerstaff.repository.LojaDAO;
import com.developerstaff.repository.MessengerDAO;
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
	@Autowired
	private MessengerDAO daoMsg;
	/*
	 * @Autowired private ComponenteDAO daoComp;
	 */

	private ChamadoBO bo;

	@GetMapping("/novo")
	public ModelAndView novo(Chamado chamado, @AuthenticationPrincipal User user) {

		Usuario userlogon = daoUser.findByLogin(user.getUsername());

		ModelAndView mv = new ModelAndView("chamados/criar-chamado");
		List<Equipamento> equipamentos;
		List<Componente> comps;
		if (userlogon.getTipo().idTipo == 0) {
			equipamentos = daoEqui.findByLojaId(userlogon.getLoja().getId());
			chamado.setLoja(userlogon.getLoja());
			chamado.setUsuario(userlogon);
		} else {
			if (chamado.getLoja() != null) {
				equipamentos = daoEqui.findByLojaId(chamado.getLoja().getId());
				chamado.setUsuario(userlogon);
			} else {
				userlogon.setLoja(null);

				equipamentos = daoEqui.findAll();
			}
		}

		if (chamado.getEquipamento() != null) {

			comps = chamado.getEquipamento().getComponentes();
			Componente c = new Componente();
			c.setNome("Outros");
			comps.add(c);
		} else {
			comps = new ArrayList<>();
		}

		mv.addObject("prioridades", Prioridade.values());
		mv.addObject("componentes", comps);
		mv.addObject("usuarios", daoUser.findAll());
		mv.addObject("lojas", daoLj.findAll());
		mv.addObject("equipamentos", equipamentos);
		mv.addObject("logon", userlogon);
		mv.addObject(chamado);

		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Chamado chamado, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal User user) {
		ModelAndView mv = new ModelAndView("redirect:/chamados/novo");

		if (result.hasErrors()) {
			return novo(chamado, user);

		}
		attributes.addFlashAttribute("mensagem", "Chamado criado com sucesso!");

		chamado.setDatas(new Datas());

		chamado.getDatas().setDataCriacao(Calendar.getInstance());
		chamado.setStatus(Status.valueOf("PENDENTE"));

		dao.save(chamado);
		return mv;
	}

	@GetMapping(value = "/status/{status}")
	public ModelAndView filterByStatus(@PathVariable String status) {
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		mv.addObject("chamados", dao.findByStatus(Status.valueOf(status)));
		return mv;
	}

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
	public ModelAndView listaGlobal(@Valid ChamadoFiltro chamadoFiltro, @AuthenticationPrincipal User user) {

		Usuario userlogon = daoUser.findByLogin(user.getUsername());

		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		mv.addObject("tecnicos", daoUser.getTecnicos());
		mv.addObject("tecnico", new Usuario());
		mv.addObject("lojas", daoLj.findAll());
		mv.addObject("usuarios", daoUser.findAll());
		mv.addObject("filtro", chamadoFiltro);
		mv.addObject("prioridades", Prioridade.values());
		mv.addObject("statuss", Status.values());
		mv.addObject("logado", userlogon);

		bo = new ChamadoBO();

		mv.addObject("chamados", bo.fazFiltroChamados(chamadoFiltro, userlogon, dao));

		return mv;
	}
	
	@GetMapping("/meuschamados")
	public ModelAndView abrirMeusChamados(@Valid ChamadoFiltro chamadoFiltro,@AuthenticationPrincipal User user){
		ModelAndView mv = new ModelAndView("chamados/global-chamado");
		
		
		Usuario userlogon = daoUser.findByLogin(user.getUsername());
		
		
		mv.addObject("tecnicos", daoUser.getTecnicos());
		mv.addObject("tecnico", new Usuario());
		mv.addObject("lojas", daoLj.findAll());
		mv.addObject("usuarios", daoUser.findAll());
		mv.addObject("filtro", chamadoFiltro);
		mv.addObject("prioridades", Prioridade.values());
		mv.addObject("statuss", Status.values());
		mv.addObject("logado", userlogon);
		
		mv.addObject("chamados",dao.findByUsuarioId(userlogon.getId()));
		
	     
		
		return mv;
	}

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
		ModelAndView mv;
		
		Chamado chamado = dao.findOne(id);
		
		if(chamado.getStatus().getIdStatus() == 0){
			
			mv = new ModelAndView("/chamados/alterar-chamado");
		}else{
			 mv = new ModelAndView("redirect:/chamados/detalhes/"+id);
		}
		
		
		
		mv.addObject("chamado", chamado);
		
		return mv;

	}

	@PostMapping("/atender/{id}")
	public ModelAndView atender(@PathVariable Long id, RedirectAttributes attributes,@AuthenticationPrincipal User user) {

		Usuario userlogon = daoUser.findByLogin(user.getUsername());
		Chamado chamado = dao.findOne(id);
		

		chamado.getDatas().setDataAtendimento(Calendar.getInstance());
		chamado.setStatus(Status.valueOf("ATENDIMENTO"));
       	chamado.setTecnico(userlogon);

		dao.save(chamado);
		attributes.addFlashAttribute("mensagem", "Chamado em Atendimento");

		return new ModelAndView("redirect:/chamados/detalhes/" + id);

	}

	@GetMapping("/detalhes/{id}")
	public ModelAndView abrirInformacoes(@PathVariable Long id,@AuthenticationPrincipal User user,Messenger messenger) {
		Usuario userlogon = daoUser.findByLogin(user.getUsername());
		ModelAndView mv = new ModelAndView("/chamados/detalhes-chamado");
		Chamado chamado = dao.findOne(id);
		

		if (!chamado.getSolucoes().isEmpty()) {
			Solucao solucao = chamado.getSolucoes().get(0);
			mv.addObject("solucao", solucao);
		} 

		if(!chamado.getSolucoes().isEmpty()){
			chamado.setUsuario(chamado.getSolucoes().get(0).getUsuario());	
		}
		
		mv.addObject("chamado", chamado);
		mv.addObject("logon", userlogon);
		mv.addObject("messenger",messenger);
		mv.addObject("mensagens", daoMsg.findByChamadoId(chamado.getId()));
		mv.addObject("size", daoMsg.findByChamadoId(chamado.getId()).size());

/*		for(Messenger m :  chamado.getMensagens()){
			
			System.out.println(m.getMensagem()+" User"+m.getUsuario().getNome());
		}*/
		
		return mv;
	}

	@GetMapping("/finalizar/{id}")
	public ModelAndView abrirFinalizar(@PathVariable Long id, Solucao solucao,@AuthenticationPrincipal User user) {
		
		Usuario userlogon = daoUser.findByLogin(user.getUsername());
		
		
		
		Chamado chamado = dao.findOne(id);
		
		
		if(chamado.getStatus().getIdStatus() == 1 && chamado.getTecnico().getId() == userlogon.getId()){
			ModelAndView mv = new ModelAndView("/chamados/alterar-chamado");
			mv.addObject("chamado", chamado);
			mv.addObject("TipoSolucoes", TipoSolucao.values());
			mv.addObject("solucao", solucao);
			return mv;
			
		}else{
			
			return new ModelAndView("redirect:/chamados/detalhes/"+id);
		}

	}

	@PostMapping("/finalizar/{id}")
	public ModelAndView finalizar(@PathVariable Long id, @Valid Solucao solucao, BindingResult result,
			RedirectAttributes attributes,@AuthenticationPrincipal User user) {
		
		//está vindo com id não entendo?
		solucao.setId(null);
		
		if (result.hasErrors()) {

			return abrirFinalizar(id, solucao,user);

		}
		Chamado chamado = dao.getOne(id);
		chamado.setReAbrir(null);
		//finaliza o chamado
		chamado.getDatas().setDataFinalizacao(Calendar.getInstance());
		chamado.setStatus(Status.FINALIZADO);
		
		//pega informações do chamado para a solução
		solucao.setDatas(chamado.getDatas());
		solucao.setTecnico(chamado.getTecnico());
		solucao.setUsuario(chamado.getUsuario());
		
		
		 
       /* solucao = daoSolu.saveAndFlush(s);*/
		
        System.out.println("IDDD"+solucao.getId());
		chamado.getSolucoes().add(solucao);	
		dao.save(chamado);

		attributes.addFlashAttribute("mensagem", "Chamado finalizado com sucesso");

		return new ModelAndView("redirect:/chamados/detalhes/" + id);
	}

	@GetMapping("/reabrir/{id}")
	public ModelAndView abrirReAbrir(@PathVariable Long id, ReAbrir reAbrir) {
		
		Chamado chamado = dao.findOne(id);
		
		if(chamado.getStatus().idStatus == 2 && (!chamado.getReabrirChamado())){
			chamado.setUsuario(chamado.getSolucoes().get(0).getUsuario());
			
		ModelAndView mv = new ModelAndView("/chamados/alterar-chamado");
		
		mv.addObject("chamado", chamado);

		return mv;
		}else{
			
			return new ModelAndView("redirect:/chamados/detalhes/" + id);
		}
	}

	@PostMapping("/reabrir/{id}")
	public ModelAndView reabrir(@PathVariable Long id, @Valid ReAbrir reAbrir, BindingResult result,
			RedirectAttributes attributes,@AuthenticationPrincipal User user) {
		Usuario userlogon = daoUser.findByLogin(user.getUsername());
		Chamado chamado = dao.findOne(id);
		if (result.hasErrors()) {
			
			return abrirReAbrir(id, reAbrir);
		}

		attributes.addFlashAttribute("mensagem", "Chamado reaberto com sucesso!");

		
		chamado.setReAbrir(reAbrir);
		
		List<Solucao> listaS = chamado.getSolucoes();
		for(Solucao s : listaS){
			
			if(s.getReAbrir()==null){
				s.setReAbrir(reAbrir);
				/*daoSolu.save(s);*/
			}
		}
		
		chamado.getReAbrir().setReAberto(true);
        chamado.setDatas(new Datas());
		chamado.getDatas().setDataCriacao(Calendar.getInstance());

		chamado.setTecnico(null);
		chamado.setUsuario(userlogon);
		chamado.setStatus(Status.PENDENTE);

		dao.save(chamado);

		return new ModelAndView("redirect:/chamados/detalhes/" + id);
	}
	
	@PostMapping("/mensagem/{id}")
	public ModelAndView enviarMsg(@PathVariable Long id,@Valid Messenger msg,BindingResult result
			,@AuthenticationPrincipal User user,RedirectAttributes attributes){
		
		msg.setId(null);
		Usuario userlogon = daoUser.findByLogin(user.getUsername());
		Chamado chamado = dao.findOne(id);
		
		
		if(result.hasErrors()){
			attributes.addFlashAttribute("erro","Campo mensagem é obrigatório!");
			ModelAndView mv = new ModelAndView("redirect:/chamados/detalhes/" + id);
					
			return mv;
		}
		msg.setUsuario(userlogon);
		msg.setChamado(chamado);
		msg.setDataMsg(Calendar.getInstance());
		chamado.getMensagens().add(msg);
		dao.save(chamado);
		attributes.addFlashAttribute("mensagem","Mensagem enviada com sucesso!");
		
		return new ModelAndView("redirect:/chamados/detalhes/" + id);
	}
	


	@GetMapping("/cad")
	public ModelAndView cadastrar() {

		Equipamento equi = daoEqui.findOne(new Long(1));

		List<Componente> lista = equi.getComponentes();
		List<Componente> listaC = new ArrayList<>();

		for (Componente c : lista) {
			listaC.add(c);
		}

		for (int i = 6; i < 27; i++) {

			Equipamento equipamento = new Equipamento();

			equipamento.setComponentes(listaC);
			equipamento.setDescricao(equi.getDescricao());
			equipamento.setLoja(equi.getLoja());
			equipamento.setPatrimonio(equi.getPatrimonio());
			equipamento.setSetor(equi.getSetor());

			Long patri = equipamento.getPatrimonio() + i;
			equipamento.setPatrimonio(patri);

			if (i < 10) {

				equipamento.setNome("pdma0" + i);
				equipamento.setLocal("Caixa 0" + i);

			} else {

				equipamento.setNome("pdma" + i);
				equipamento.setLocal("Caixa " + i);
			}

			daoEqui.save(equipamento);

		}

		return new ModelAndView("redirect:/equipamentos");
	}

}
