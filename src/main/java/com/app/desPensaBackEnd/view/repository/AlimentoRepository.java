package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlimentoRepository extends JpaRepository<AlimentoEntity, Long> {
    // Método customizado usado no Service
    List<AlimentoEntity> findByEstoqueIdEstoqueAndQuantidadeGreaterThan(Long idEstoque, int quantidadeMinima);
    List<AlimentoEntity> findByEstoqueIdEstoque(Long idEstoque);
}
