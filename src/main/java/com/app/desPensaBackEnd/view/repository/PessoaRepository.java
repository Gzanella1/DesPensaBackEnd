package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
    // Método extra útil: Buscar todas as pessoas de uma instituição específica
    List<PessoaEntity> findByInstituicaoIdInstituicao(Long idInstituicao);
}