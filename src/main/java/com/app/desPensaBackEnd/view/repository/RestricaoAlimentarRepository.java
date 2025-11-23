package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.RestricaoAlimentarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestricaoAlimentarRepository extends JpaRepository<RestricaoAlimentarEntity, Long> {
    Optional<RestricaoAlimentarEntity> findByNomeIgnoreCase(String nome);
}