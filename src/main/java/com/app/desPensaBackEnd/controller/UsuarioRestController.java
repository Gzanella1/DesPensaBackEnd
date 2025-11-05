package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.view.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Set<UsuarioDTO> retornarContribuicoes() {
        return usuarioService.buscarUsuario();
    }








    /**
     * Chama um método que cadastra uma nova pessoa no banco de dados
     * @param cadastro
     *
     * Na anotação @PostMapping, o atributo consumes diz qual tipo de conteúdo (MIME type) o endpoint aceita receber no corpo da requisição (@RequestBody).
     * no caso ele so aceita json
     */
    @PostMapping(value = "/cadastro", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void cadastrar(@RequestBody UsuarioDTO cadastro) {
        usuarioService.cadastrar(cadastro);
    }
}
