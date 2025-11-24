package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.TipoInstituicao;
import com.app.desPensaBackEnd.model.dto.InstituicaoDTO;
import com.app.desPensaBackEnd.model.entity.EnderecoEntity;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.CrecheEntity;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.EscolaEntity;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.HotelEntity;
import com.app.desPensaBackEnd.view.repository.InstituicaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // Retorna a lista de Entidades (o Controller converterá para DTO)
    public List<InstituicaoEntity> listarTodas() {
        return instituicaoRepository.findAll();
    }

    public InstituicaoEntity buscarPorId(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com id: " + id));
    }

    @Transactional
    public CrecheEntity salvarCreche(CrecheEntity creche) {
        creche.setTipoInstituição(TipoInstituicao.CRECHE);
        return instituicaoRepository.save(creche);
    }

    @Transactional
    public EscolaEntity salvarEscola(EscolaEntity escola) {
        escola.setTipoInstituição(TipoInstituicao.ESCOLA);
        return instituicaoRepository.save(escola);
    }

    @Transactional
    public HotelEntity salvarHotel(HotelEntity hotel) {
        hotel.setTipoInstituição(TipoInstituicao.HOTEL);
        return instituicaoRepository.save(hotel);
    }

    @Transactional
    public void remover(Long id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Instituição não encontrada para remoção.");
        }
        instituicaoRepository.deleteById(id);
    }


    // Adicione isso ao InstituicaoService.java
    public InstituicaoEntity atualizar(Long id, InstituicaoDTO dto) {
        // 1. Busca a instituição existente no banco
        InstituicaoEntity entity = instituicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada para o ID: " + id));

        // 2. Atualiza os dados COMUNS (Nome, Telefone, Código)
        entity.setNome(dto.getNome());
        entity.setCodigo(dto.getCodigo());
        entity.setTelefone(dto.getTelefone());

        // 3. Atualiza o ENDEREÇO
        if (dto.getEnderecoDTO() != null) {
            // Se o endereço for null no banco, cria um novo, senão atualiza o existente
            if (entity.getEndereco() == null) {
                entity.setEndereco(new EnderecoEntity());
            }
            entity.getEndereco().setCep(dto.getEnderecoDTO().getCep());
            entity.getEndereco().setRua(dto.getEnderecoDTO().getRua());
            entity.getEndereco().setNumero(dto.getEnderecoDTO().getNumero());
            entity.getEndereco().setBairro(dto.getEnderecoDTO().getBairro());
            entity.getEndereco().setCidade(dto.getEnderecoDTO().getCidade());
            entity.getEndereco().setEstado(dto.getEnderecoDTO().getEstado());
        }

        // 4. Atualiza dados ESPECÍFICOS (Verifica qual é o tipo da classe)
        if (entity instanceof com.app.desPensaBackEnd.model.entity.tipoInstituicao.CrecheEntity) {
            var creche = (com.app.desPensaBackEnd.model.entity.tipoInstituicao.CrecheEntity) entity;
            creche.setIdadeMaximaAtendidaMeses(dto.getIdadeMaximaAtendidaMeses());
            creche.setCapacidade(dto.getCapacidade());
        }
        else if (entity instanceof com.app.desPensaBackEnd.model.entity.tipoInstituicao.EscolaEntity) {
            var escola = (com.app.desPensaBackEnd.model.entity.tipoInstituicao.EscolaEntity) entity;
            escola.setNumeroMatriculas(dto.getNumeroMatriculas());
            escola.setTurno(dto.getTurno());
        }
        else if (entity instanceof com.app.desPensaBackEnd.model.entity.tipoInstituicao.HotelEntity) {
            var hotel = (com.app.desPensaBackEnd.model.entity.tipoInstituicao.HotelEntity) entity;
            hotel.setNumeroQuartos(dto.getNumeroQuartos());
            hotel.setClassificacaoEstrelas(dto.getClassificacaoEstrelas());
        }

        // 5. Salva as alterações
        return instituicaoRepository.save(entity);
    }

}