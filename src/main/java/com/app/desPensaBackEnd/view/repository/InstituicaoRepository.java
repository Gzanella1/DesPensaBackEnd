package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituicaoRepository extends JpaRepository<InstituicaoEntity,Long> {
    InstituicaoEntity findByIdInstituicao(Long id);

}
