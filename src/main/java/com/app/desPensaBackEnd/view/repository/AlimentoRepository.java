package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentoRepository extends JpaRepository<AlimentoEntity,Long> {
    AlimentoEntity findByCodigo(Long codigo);

}
