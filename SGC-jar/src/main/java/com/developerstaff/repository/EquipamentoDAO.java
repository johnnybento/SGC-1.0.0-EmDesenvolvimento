package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developerstaff.model.Equipamento;


@Repository
public interface EquipamentoDAO extends JpaRepository<Equipamento, Long> {
	
	
    
	public List<Equipamento> findByIdIsNotNullAndNomeContainingIgnoreCase(String nome);
	public List<Equipamento> findByNomeContainingIgnoreCase(String nome);

}
