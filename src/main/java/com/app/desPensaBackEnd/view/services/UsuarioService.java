package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.model.dto.LoginDTO;
import com.app.desPensaBackEnd.model.dto.LoginSemIdDTO;
import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.model.endity.PessoaEntity;
import com.app.desPensaBackEnd.model.endity.UsuarioEntity;
import com.app.desPensaBackEnd.view.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    /**
     * Retorna os usuarios
     * usuarioRepository.findAll() → busca todos os usuários do banco, retornando uma List<UsuarioEntity>.
     * Um stream/fluxo é uma forma moderna de percorrer coleções no Java — tipo uma versão poderosa de for.
     * .map(uE -> { ... }) → para cada usuário encontrado (uEntity), o código cria um novo UsuarioDTO e faz alguma transformação.
     * .map(uE -> { ... }), você está pegando um tipo de objeto (UsuarioEntity, que vem do banco de dados) e transformando em outro tipo (UsuarioDTO, que é o que você devolve na resposta da API).
     */
    public Set<UsuarioDTO> buscarUsuario(){
        return usuarioRepository.findAll().stream().map(uE -> {
            UsuarioDTO userDTO = new UsuarioDTO();
            BeanUtils.copyProperties(uE, userDTO);
            return userDTO;
        }).collect(Collectors.toSet());
    }


    public void cadastrar(UsuarioDTO userDTO){
        UsuarioEntity ue=new UsuarioEntity();
        BeanUtils.copyProperties(userDTO, ue);
        usuarioRepository.save(ue);
    }


    public LoginDTO login(LoginSemIdDTO loginSemIdDTO) {
       // PessoaEntity pE = pessoaRepository.findByLogin(loginSemIdDTO.getUsername());
        UsuarioEntity ue= usuarioRepository.findByLogin(loginSemIdDTO.getEmail());
        if(ue.getSenha().equals(loginSemIdDTO.getSenha())){
            LoginDTO loginDTO = new LoginDTO();

            BeanUtils.copyProperties(ue, loginDTO);
            return loginDTO;
        }
        return login(loginSemIdDTO);
    }

    /**
     * Retorna os detalhes de um usuário de acordo com o login dele
     *
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
