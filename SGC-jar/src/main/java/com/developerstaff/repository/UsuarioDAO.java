package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developerstaff.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	
/*	@Query("select * from usuarios u where u.tipo='TECNICO'")
	public List<Usuario> findByTipoTexto();*/
	
	@Query("select u from Usuario u where u.tipo='TECNICO'")
    public List<Usuario> getTecnicos();
	
	@Query("select u from Usuario u where "
			+ "UPPER(u.login) like UPPER(:texto) or "
			+ "UPPER(u.nome)  like UPPER(:texto) or "
			+ "UPPER(u.email)  like UPPER(:texto) or "
			+ "UPPER(u.telefone)  like UPPER(:texto) or "
			+ "UPPER(u.celular)  like UPPER(:texto) or "
			+ "UPPER(u.loja.nome)  like UPPER(:texto) or "
			+ "UPPER(u.tipo)  like UPPER(:texto)")
	public List<Usuario> procuraByTudo(@Param("texto") String texto);
		
	
	public Usuario findByNomeAndPassword(String nome, String password); 

	public Usuario findByNome(String nome);
	
	public Usuario findByLogin(String nome);
}
