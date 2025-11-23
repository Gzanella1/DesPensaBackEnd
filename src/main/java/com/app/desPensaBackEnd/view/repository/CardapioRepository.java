package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardapioRepository extends JpaRepository<CardapioEntity, Long> {

    // Exemplos de métodos úteis que você pode vir a usar:

    // Buscar cardápios de uma instituição específica
    List<CardapioEntity> findByInstituicaoIdInstituicao(Long idInstituicao);

    // Buscar cardápios gerados em uma data específica
    List<CardapioEntity> findByDataGeracaoCardapio(LocalDate data);
}