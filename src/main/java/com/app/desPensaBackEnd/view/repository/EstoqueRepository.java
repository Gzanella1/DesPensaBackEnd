package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {

}
