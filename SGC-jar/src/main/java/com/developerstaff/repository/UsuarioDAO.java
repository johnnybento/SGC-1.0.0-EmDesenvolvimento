package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.developerstaff.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	
/*	@Query("select * from usuarios u where u.tipo='TECNICO'")
	public List<Usuario> findByTipoTexto();*/
	
	@Query("select u from Usuario u where u.tipo='TECNICO'")
    public List<Usuario> getTecnicos();

}
