package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoqueEntity, Long> {

    // MUDOU DE: findByEstoqueId
    // PARA: findByEstoqueIdEstoque
    // (Lê-se: Encontre pelo atributo 'Estoque', dentro dele o atributo 'IdEstoque')
    List<MovimentacaoEstoqueEntity> findByEstoqueIdEstoque(Long idEstoque);
}
