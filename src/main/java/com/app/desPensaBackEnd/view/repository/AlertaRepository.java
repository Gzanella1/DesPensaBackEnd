package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaEntity, Long> {
    // Exemplo: Buscar alertas não visualizados para mostrar no "sininho" do front
    List<AlertaEntity> findByVisualizadoFalseOrderByDataDesc();
}