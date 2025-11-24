package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.RestricaoAlimentarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RestricaoAlimentarRepository extends JpaRepository<RestricaoAlimentarEntity, Long> {
    Optional<RestricaoAlimentarEntity> findByNomeIgnoreCase(String nome);
}