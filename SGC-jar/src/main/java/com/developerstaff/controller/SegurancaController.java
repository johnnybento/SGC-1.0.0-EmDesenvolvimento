package com.developerstaff.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.developerstaff.model.Usuario;
import com.developerstaff.repository.UsuarioDAO;

@Controller
public class SegurancaController {
 
	@Autowired
	private UsuarioDAO  daoUser;
	
	private Usuario usuario;
	
    @RequestMapping("/login")
	public String logon(@AuthenticationPrincipal User usuario){
    	
    	/*Usuario userlogon = daoUser.findByLogin(usuario.getUsername());*/
    	
    	
    	
    	String login = "";
    	String senha = "";

          if(usuario != null){
        	  login = usuario.getUsername();
        	  senha = usuario.getAuthorities().toString();
        	  System.out.println("1 login "+login+" senha"+senha);
        	  
        	  return "redirect:chamados";
        	  
          }
    	
         
/*    	usuario = new Usuario();
		SecurityContext context = SecurityContextHolder.getContext();
		if(context instanceof SecurityContext){
			
			Authentication authentication = context.getAuthentication();
			if(authentication instanceof Authentication){
				usuario.setNome(((User)authentication.getPrincipal()).getUsername());
			}
		}*/
    	
    	
    	
/*    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	if (usuarioLogado  instanceof Usuario ) {
    	   username= ( (Usuario)usuarioLogado).getUsername();
    	  
    	} else {
    	   username = usuarioLogado .toString();
    	  
    	}
    	
    	System.out.println("Usuario Logado "+username);*/
    	
		
    	
    	return "login";
    }
    
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    
    public String logout(){
    	return null;
    }
}
