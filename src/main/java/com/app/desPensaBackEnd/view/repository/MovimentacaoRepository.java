package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoqueEntity, Long> {

    // Buscar histórico por Estoque
    List<MovimentacaoEstoqueEntity> findByEstoqueIdEstoqueOrderByDataDesc(Long idEstoque);

    // Buscar histórico por Alimento específico
    List<MovimentacaoEstoqueEntity> findByAlimentoIdAlimentoOrderByDataDesc(Long idAlimento);
}
