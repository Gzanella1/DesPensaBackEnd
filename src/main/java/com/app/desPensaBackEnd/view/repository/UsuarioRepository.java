package com.app.desPensaBackEnd.view.repository;

import com.app.desPensaBackEnd.model.endity.PessoaEntity;
import com.app.desPensaBackEnd.model.endity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByLogin(String login);
}
