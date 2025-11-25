package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.model.dto.LoginDTO;
import com.app.desPensaBackEnd.model.dto.LoginSemIdDTO;
import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.model.entity.UsuarioEntity;
import com.app.desPensaBackEnd.view.repository.InstituicaoRepository;
import com.app.desPensaBackEnd.view.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    /**
     * Retorna os usuarios
     * usuarioRepository.findAll() → busca todos os usuários do banco, retornando uma List<UsuarioEntity>.
     * Um stream/fluxo é uma forma moderna de percorrer coleções no Java — tipo uma versão poderosa de for.
     * .map(uE -> { ... }) → para cada usuário encontrado (uEntity), o código cria um novo UsuarioDTO e faz alguma transformação.
     * .map(uE -> { ... }), você está pegando um tipo de objeto (UsuarioEntity, que vem do banco de dados) e transformando em outro tipo (UsuarioDTO, que é o que você devolve na resposta da API).
     */
    public Set<UsuarioDTO> buscarUsuarios() {
        return usuarioRepository.findAll().stream().map(uE -> {
            UsuarioDTO dto = new UsuarioDTO();
            BeanUtils.copyProperties(uE, dto);

            // Define o ID da instituição manualmente
            if (uE.getInstituicao() != null) {
                dto.setIdInstituicao(uE.getInstituicao().getIdInstituicao());
            }

            return dto;
        }).collect(Collectors.toSet());
    }


    public void cadastrar(UsuarioDTO userDTO){
        if (userDTO.getIdInstituicao() == null) {
            throw new IllegalArgumentException("O ID da instituição é obrigatório.");
        }

        UsuarioEntity ue = new UsuarioEntity();
        BeanUtils.copyProperties(userDTO, ue);

        InstituicaoEntity instituicao = instituicaoRepository
                .findById(userDTO.getIdInstituicao())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada: " + userDTO.getIdInstituicao()));

        ue.setInstituicao(instituicao);
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
    
    public UsuarioDTO buscarPorCodigo(Long codigo) {

        UsuarioEntity entity = usuarioRepository.findByCodigo(codigo);

        if (entity == null)
            throw new RuntimeException("Usuário não encontrado: " + codigo);

        UsuarioDTO dto = new UsuarioDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getInstituicao() != null) {
            dto.setIdInstituicao(entity.getInstituicao().getIdInstituicao());
        }

        return dto;
    }
    
    
    public void atualizar(UsuarioDTO dto) {

        UsuarioEntity entity = usuarioRepository.findByCodigo(dto.getCodigo());

        if (entity == null)
            throw new RuntimeException("Usuário não encontrado: " + dto.getCodigo());

        BeanUtils.copyProperties(dto, entity, "codigo", "dataCriacao");

        if (dto.getIdInstituicao() != null) {
            InstituicaoEntity inst = instituicaoRepository.findById(dto.getIdInstituicao())
                    .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
            entity.setInstituicao(inst);
        }

        usuarioRepository.save(entity);
    } 
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
