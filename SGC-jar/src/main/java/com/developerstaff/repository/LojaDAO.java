package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developerstaff.model.Loja;

public interface LojaDAO extends JpaRepository<Loja, Long>{
	
	@Query("select l from Loja l where "
			+ "UPPER(l.nome) like UPPER(:texto) or "
			+ "UPPER(l.descricao)  like UPPER(:texto) or "
			+ "UPPER(l.numeroLoja)  like UPPER(:texto) or "
			+ "UPPER(l.endereco.logradouro)  like UPPER(:texto) or "
			+ "UPPER(l.endereco.numero)  like UPPER(:texto) or "
			+ "UPPER(l.endereco.cep)  like UPPER(:texto) or "
			+ "UPPER(l.endereco.bairro)  like UPPER(:texto) or "
			+ "UPPER(l.endereco.municipio)  like UPPER(:texto) or "
			+ "UPPER(l.endereco.estado)  like UPPER(:texto)")
	public List<Loja> procuraByTudo(@Param("texto") String texto);

	
	
}
