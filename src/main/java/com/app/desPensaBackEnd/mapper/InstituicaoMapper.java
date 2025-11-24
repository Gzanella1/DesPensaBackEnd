package com.app.desPensaBackEnd.mapper;

import com.app.desPensaBackEnd.model.dto.*;
import com.app.desPensaBackEnd.model.dto.instituicao.CrecheDTO;
import com.app.desPensaBackEnd.model.dto.instituicao.EscolaDTO;
import com.app.desPensaBackEnd.model.dto.instituicao.HotelDTO;
import com.app.desPensaBackEnd.model.dto.instituicao.InstituicaoBaseDTO;
import com.app.desPensaBackEnd.model.entity.*;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.*;
import org.springframework.stereotype.Component;

@Component
public class InstituicaoMapper {

    // --- CONVERTER ENTIDADE PARA DTO (LEITURA) ---

    public InstituicaoBaseDTO toDTO(InstituicaoEntity entity) {
        if (entity instanceof CrecheEntity) {
            return toCrecheDTO((CrecheEntity) entity);
        } else if (entity instanceof EscolaEntity) {
            return toEscolaDTO((EscolaEntity) entity);
        } else if (entity instanceof HotelEntity) {
            return toHotelDTO((HotelEntity) entity);
        }
        return null;
    }

    private CrecheDTO toCrecheDTO(CrecheEntity entity) {
        CrecheDTO dto = new CrecheDTO();
        preencherDadosBaseDTO(dto, entity);
        dto.setIdadeMaximaAtendidaMeses(entity.getIdadeMaximaAtendidaMeses());
        dto.setCapacidade(entity.getCapacidade());
        return dto;
    }

    private EscolaDTO toEscolaDTO(EscolaEntity entity) {
        EscolaDTO dto = new EscolaDTO();
        preencherDadosBaseDTO(dto, entity);
        dto.setNumeroMatriculas(entity.getNumeroMatriculas());
        dto.setTurno(entity.getTurno());
        return dto;
    }

    private HotelDTO toHotelDTO(HotelEntity entity) {
        HotelDTO dto = new HotelDTO();
        preencherDadosBaseDTO(dto, entity);
        dto.setNumeroQuartos(entity.getNumeroQuartos());
        dto.setClassificacaoEstrelas(entity.getClassificacaoEstrelas());
        return dto;
    }

    private void preencherDadosBaseDTO(InstituicaoBaseDTO dto, InstituicaoEntity entity) {
        dto.setIdInstituicao(entity.getIdInstituicao());
        dto.setCodigo(entity.getCodigo());
        dto.setNome(entity.getNome());
        dto.setTelefone(entity.getTelefone());

        if (entity.getEndereco() != null) {
            EnderecoDTO endDTO = new EnderecoDTO();
            endDTO.setRua(entity.getEndereco().getRua());
            endDTO.setNumero(entity.getEndereco().getNumero());
            endDTO.setBairro(entity.getEndereco().getBairro());
            endDTO.setCidade(entity.getEndereco().getCidade());
            endDTO.setCep(entity.getEndereco().getCep());
            endDTO.setEstado(entity.getEndereco().getEstado());
            // Se tiver estado ou complemento no DTO/Entity, adicione aqui também
            dto.setEndereco(endDTO);
        }
    }

    // --- CONVERTER DTO PARA ENTIDADE (CRIAÇÃO/ATUALIZAÇÃO) ---

    public CrecheEntity toEntity(CrecheDTO dto) {
        CrecheEntity entity = new CrecheEntity();
        preencherDadosBaseEntity(entity, dto);
        entity.setIdadeMaximaAtendidaMeses(dto.getIdadeMaximaAtendidaMeses());
        entity.setCapacidade(dto.getCapacidade());
        return entity;
    }

    public EscolaEntity toEntity(EscolaDTO dto) {
        EscolaEntity entity = new EscolaEntity();
        preencherDadosBaseEntity(entity, dto);
        entity.setNumeroMatriculas(dto.getNumeroMatriculas());
        entity.setTurno(dto.getTurno());
        return entity;
    }

    public HotelEntity toEntity(HotelDTO dto) {
        HotelEntity entity = new HotelEntity();
        preencherDadosBaseEntity(entity, dto);
        entity.setNumeroQuartos(dto.getNumeroQuartos());
        entity.setClassificacaoEstrelas(dto.getClassificacaoEstrelas());
        return entity;
    }

    private void preencherDadosBaseEntity(InstituicaoEntity entity, InstituicaoBaseDTO dto) {
        entity.setCodigo(dto.getCodigo());
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());

        // AQUI ESTAVA FALTANDO O MAPEAMENTO COMPLETO
        if (dto.getEndereco() != null) {
            EnderecoEntity endEntity = new EnderecoEntity();
            EnderecoDTO endDto = dto.getEndereco();

            endEntity.setRua(endDto.getRua());
            endEntity.setNumero(endDto.getNumero());
            endEntity.setBairro(endDto.getBairro()); // O erro acontecia aqui (estava null)
            endEntity.setCidade(endDto.getCidade());
            endEntity.setCep(endDto.getCep());
            endEntity.setEstado(endDto.getEstado());
            // Se o seu EnderecoDTO ou Entity tiverem 'estado' ou 'complemento', mapeie aqui:
            // endEntity.setEstado(endDto.getEstado());

            entity.setEndereco(endEntity);
        }
    }
}
