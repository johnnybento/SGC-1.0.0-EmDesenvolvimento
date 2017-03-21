package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.developerstaff.model.Equipamento;


@Repository
public interface EquipamentoDAO extends JpaRepository<Equipamento, Long> {
	
	
	@Query("select e from Equipamento e where "
			+ "UPPER(e.nome) like UPPER(:texto) or "
			+ "UPPER(e.descricao)  like UPPER(:texto) or "
			+ "UPPER(e.setor.nomeSetor)  like UPPER(:texto) or "
			+ "UPPER(e.local)  like UPPER(:texto) or "
			+ "UPPER(e.loja.nome)  like UPPER(:texto) or "
			+ "UPPER(e.patrimonio)  like UPPER(:texto)")
	public List<Equipamento> procuraByTudo(@Param("texto") String texto);
	

	public List<Equipamento> findByIdIsNotNullAndNomeContainingIgnoreCase(String nome);
	
	public List<Equipamento> findByNomeContainingIgnoreCase(String nome);

	public List<Equipamento> findByLojaId(Long id);
	
	
	
	
}
