package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developerstaff.model.Messenger;

public interface MessengerDAO extends JpaRepository<Messenger, Long>{
	
	List<Messenger> findByChamadoId(Long id);



}
