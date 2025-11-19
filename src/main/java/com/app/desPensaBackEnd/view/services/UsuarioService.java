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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------- Buscar usuários ----------------
    public Set<UsuarioDTO> buscarUsuarios() {
        return usuarioRepository.findAll().stream().map(uE -> {
            UsuarioDTO dto = new UsuarioDTO();
            BeanUtils.copyProperties(uE, dto);

            if (uE.getInstituicao() != null) {
                dto.setIdInstituicao(uE.getInstituicao().getIdInstituicao());
            }

            return dto;
        }).collect(Collectors.toSet());
    }

    // ---------------- Cadastrar ----------------
    public void cadastrar(UsuarioDTO userDTO) {
        if (userDTO.getIdInstituicao() == null) {
            throw new IllegalArgumentException("O ID da instituição é obrigatório.");
        }

        UsuarioEntity ue = new UsuarioEntity();

        // copia tudo do DTO para a entidade EXCETO a senha
        BeanUtils.copyProperties(userDTO, ue, "senha");

        // agora seta a senha já criptografada
        ue.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        InstituicaoEntity instituicao = instituicaoRepository
                .findById(userDTO.getIdInstituicao())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada: " + userDTO.getIdInstituicao()));

        ue.setInstituicao(instituicao);
        usuarioRepository.save(ue);
    }

    // ---------------- Login (manutenção/endpoint de login, se usar) ----------------
    public LoginDTO login(LoginSemIdDTO loginSemIdDTO) {
        // padronizei para buscar por email
        UsuarioEntity ue = usuarioRepository.findByEmail(loginSemIdDTO.getEmail());

        if (ue == null) {
            throw new RuntimeException("Email não encontrado.");
        }

        // usa o PasswordEncoder para comparar raw vs encoded
        if (!passwordEncoder.matches(loginSemIdDTO.getSenha(), ue.getSenha())) {
            throw new RuntimeException("Senha inválida.");
        }

        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(ue, loginDTO);
        return loginDTO;
    }

    // ---------------- UserDetailsService ----------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return usuario;
    }
}
