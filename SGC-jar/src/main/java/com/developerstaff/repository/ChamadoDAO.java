package com.developerstaff.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.developerstaff.model.Chamado;
import com.developerstaff.model.Status;

@Repository
public interface ChamadoDAO extends JpaRepository<Chamado, Long>{

	
	public List<Chamado> findByLojaNumeroLoja(Long numeroLoja);
	
	public Chamado findByIdAndLojaNumeroLoja(Long id,Long numeroLoja);

	
	public List<Chamado>findByStatus(Status status);

	@Query("select c from Chamado c order by CASE"
            +" when c.prioridade = 'ALTA' THEN CHAR(1)"
            +" when c.prioridade = 'MEDIA' THEN CHAR(2)"
            +" when c.prioridade = 'BAIXA' THEN CHAR(3) end")
	public List<Chamado> selectPrioridadeOrderBy();
	
	@Query("select c from Chamado c order by c.datas.dataCriacao desc")
	public List<Chamado> selectDataCriacaoOrderBy();
	
	@Query("select c from Chamado c where c.loja.nome  like ?1%")
	public List<Chamado> procuraByLoja(String lojaNome,Sort sort);
	
	@Query("select c from Chamado c where UPPER(c.loja.nome)  = UPPER(?)")
	public List<Chamado> procuraByLoja(String lojaNome);
	
	@Query("select c from Chamado c where UPPER(c.status)  = UPPER(?)")
	public List<Chamado> procuraByStatus(String status);
	
	@Query("select c from Chamado c where UPPER(c.status)  like UPPER(?)")
	public List<Chamado> procuraByStatusContaing(String status);
	
	@Query("select c from Chamado c where "
			+ "UPPER(c.status) like UPPER(:texto) or "
			+ "UPPER(c.prioridade)  like UPPER(:texto) or "
			+ "UPPER(c.descricao)  like UPPER(:texto) or "
			+ "UPPER(c.equipamento.nome)  like UPPER(:texto) or "
			+ "UPPER(c.equipamento.local)  like UPPER(:texto) or "
			+ "UPPER(c.loja.nome)  like UPPER(:texto) or "
			+ "UPPER(c.usuario.nome)  like UPPER(:texto)")
	public List<Chamado> procuraByTudoSemTecnico(@Param("texto") String texto);
		
	@Query("select c from Chamado c where "
			+ "UPPER(c.tecnico.nome)  like UPPER(:texto)")
	public List<Chamado> procuraByTecnico(@Param("texto") String texto);
	
	@Query("select c from Chamado c where "
			+ "UPPER(c.tecnico.nome)  like UPPER(:texto)"
			+ "and c.loja.numeroLoja = :id")
	public List<Chamado> procuraByTecnicoComId(@Param("texto") String texto, @Param("id") Long id);
	
	@Query("select c from Chamado c where "
			+ "UPPER(c.status) like UPPER(:texto) and c.loja.numeroLoja = :id or "
			+ "UPPER(c.prioridade)  like UPPER(:texto) and c.loja.numeroLoja = :id or "
			+ "UPPER(c.descricao)  like UPPER(:texto) and c.loja.numeroLoja = :id or "
			+ "UPPER(c.equipamento.nome)  like UPPER(:texto) and c.loja.numeroLoja = :id or "
			+ "UPPER(c.equipamento.local)  like UPPER(:texto) and c.loja.numeroLoja = :id or "
			+ "UPPER(c.loja.nome)  like UPPER(:texto) and c.loja.numeroLoja = :id or "
			//+ "UPPER(c.solucao.descricaoSolucao)  like UPPER(:texto) or "
			+ "UPPER(c.usuario.nome)  like UPPER(:texto)"
			+ "and c.loja.numeroLoja = :id")
	public List<Chamado> procuraByTudoComId(@Param("texto") String texto, @Param("id") Long id);
	
	
	@Query("select c from Chamado c where c.datas.dataCriacao between :dataInicio and :dataFim")
	public List<Chamado> pegaDatasCriacao(@Param("dataInicio") Calendar dataInico,@Param("dataFim") Calendar dataFim);
	
	@Query("select c from Chamado c where c.datas.dataAtendimento between :dataInicio and :dataFim")
	public List<Chamado> pegaDatasAtendimento(@Param("dataInicio") Calendar dataInico,@Param("dataFim") Calendar dataFim);
	
	@Query("select c from Chamado c where c.datas.dataFinalizacao between :dataInicio and :dataFim")
	public List<Chamado> pegaDatasFinalizacao(@Param("dataInicio") Calendar dataInico,@Param("dataFim") Calendar dataFim);
	
	public List<Chamado> findByLojaNome(String nome);
	
	
	

	

	
	
}
