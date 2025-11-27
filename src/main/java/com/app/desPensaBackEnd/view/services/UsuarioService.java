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

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // --- READ (Todos) ---
    public Set<UsuarioDTO> buscarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::converterParaDTO).collect(Collectors.toSet());
    }

    // --- READ (Por ID) ---
    public UsuarioDTO buscarPorId(Long id) {
        UsuarioEntity ue = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return converterParaDTO(ue);
    }

    // --- CREATE ---
    public void cadastrar(UsuarioDTO userDTO) {
        if (userDTO.getIdInstituicao() == null) {
            throw new IllegalArgumentException("O ID da instituição é obrigatório.");
        }
        UsuarioEntity ue = new UsuarioEntity();
        BeanUtils.copyProperties(userDTO, ue);

        // Vincula a instituição
        InstituicaoEntity instituicao = instituicaoRepository.findById(userDTO.getIdInstituicao())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        ue.setInstituicao(instituicao);

        usuarioRepository.save(ue);
    }

    // --- UPDATE ---
    public UsuarioDTO atualizar(Long id, UsuarioDTO userDTO) {
        UsuarioEntity ue = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para atualização via ID: " + id));

        // Atualiza os dados básicos (Nome, Email, Senha, etc)
        // O terceiro parâmetro ignora o ID para não sobrescrever o ID do banco com nulo ou errado
        BeanUtils.copyProperties(userDTO, ue, "idUsuario");

        // Se o DTO tiver um ID de instituição novo, atualiza o vínculo
        if (userDTO.getIdInstituicao() != null) {
            InstituicaoEntity instituicao = instituicaoRepository.findById(userDTO.getIdInstituicao())
                    .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
            ue.setInstituicao(instituicao);
        }

        UsuarioEntity atualizado = usuarioRepository.save(ue);
        return converterParaDTO(atualizado);
    }

    // --- DELETE ---
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

    // --- LOGIN ---
    public LoginDTO login(LoginSemIdDTO loginSemIdDTO) {
        UsuarioEntity ue = usuarioRepository.findByLogin(loginSemIdDTO.getEmail());

        // Verifica se usuário existe e se a senha bate
        if (ue != null && ue.getSenha().equals(loginSemIdDTO.getSenha())) {
            LoginDTO loginDTO = new LoginDTO();
            BeanUtils.copyProperties(ue, loginDTO);
            return loginDTO;
        }

        // CORREÇÃO: Lancei uma exceção em vez de chamar o método recursivamente.
        // Chamar 'return login(...)' de novo criaria um loop infinito se a senha estivesse errada.
        throw new RuntimeException("Usuário ou senha inválidos");
    }

    // Método auxiliar para evitar repetição de código na conversão Entity -> DTO
    private UsuarioDTO converterParaDTO(UsuarioEntity uE) {
        UsuarioDTO dto = new UsuarioDTO();
        BeanUtils.copyProperties(uE, dto);
        if (uE.getInstituicao() != null) {
            dto.setIdInstituicao(uE.getInstituicao().getIdInstituicao());
        }
        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implementar se for usar Spring Security real
        return null;
    }
}