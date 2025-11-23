package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.model.dto.EnderecoDTO;
import com.app.desPensaBackEnd.model.entity.EnderecoEntity;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.view.repository.EnderecoRepository;
import com.app.desPensaBackEnd.view.repository.InstituicaoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    /**
     * Buscar todos os endereços
     */
    public Set<EnderecoDTO> buscarEnderecos() {
        return enderecoRepository.findAll().stream().map(e -> {
            EnderecoDTO dto = new EnderecoDTO();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toSet());
    }

    /**
     * Buscar endereço por ID
     */
    public EnderecoDTO buscarPorId(Long id) {
        return enderecoRepository.findById(id).map(e -> {
            EnderecoDTO dto = new EnderecoDTO();
            BeanUtils.copyProperties(e, dto);
            dto.setIdInstituicao(e.getInstituicao().getIdInstituicao());
            return dto;
        }).orElse(null);
    }

    /**
     * Cadastrar endereço
     */
    public EnderecoDTO cadastrar(EnderecoDTO dto) {

        if (dto.getIdInstituicao() == null) {
            throw new IllegalArgumentException("ID da instituição é obrigatório.");
        }

        // Buscar instituição
        InstituicaoEntity instituicao = instituicaoRepository
                .findById(dto.getIdInstituicao())
                .orElseThrow(() -> new RuntimeException(
                        "Instituição não encontrada com ID: " + dto.getIdInstituicao()
                ));

        // Criar e copiar dados
        EnderecoEntity entity = new EnderecoEntity();
        BeanUtils.copyProperties(dto, entity);

        // Vincular instituição
        entity.setInstituicao(instituicao);

        // Salvar
        EnderecoEntity salvo = enderecoRepository.save(entity);

        // Retornar DTO
        EnderecoDTO resposta = new EnderecoDTO();
        BeanUtils.copyProperties(salvo, resposta);
        resposta.setIdInstituicao(instituicao.getIdInstituicao());

        return resposta;
    }

    /**
     * Atualizar endereço
     */
    public EnderecoDTO atualizar(Long id, EnderecoDTO dto) {
        return enderecoRepository.findById(id).map(existente -> {

            // Copiar sem alterar ID
            BeanUtils.copyProperties(dto, existente, "idEndereco");

            EnderecoEntity atualizado = enderecoRepository.save(existente);

            EnderecoDTO retorno = new EnderecoDTO();
            BeanUtils.copyProperties(atualizado, retorno);
            retorno.setIdInstituicao(atualizado.getInstituicao().getIdInstituicao());

            return retorno;

        }).orElse(null);
    }

    /**
     * Deletar endereço
     */
    public boolean deletar(Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
