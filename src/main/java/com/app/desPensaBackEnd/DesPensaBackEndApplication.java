package com.app.desPensaBackEnd;

import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.model.entity.UsuarioEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesPensaBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesPensaBackEndApplication.class, args);

	}
}
