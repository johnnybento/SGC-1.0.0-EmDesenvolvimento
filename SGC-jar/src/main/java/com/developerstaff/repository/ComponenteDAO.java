package com.developerstaff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developerstaff.model.Componente;

public interface ComponenteDAO extends JpaRepository<Componente, Long>{

	
	/*String hql = "select a.firstName, a.lastName from Authors a, Books b, AuthorBook ab where ab.authorId=a.authorId and ab.bookId=b.bookId and ab.bookId=:id";*/
	
	@Query("select c from Componente c ,Equipamento e where e.id=:idEquipamento")
	public List<Componente> pegaPeloJoinEquipa(@Param("idEquipamento") Long id);

}
