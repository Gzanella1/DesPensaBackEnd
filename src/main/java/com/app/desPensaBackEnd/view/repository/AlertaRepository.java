package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaEntity, Long> {
}