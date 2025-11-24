package com.app.desPensaBackEnd.view.services;


import com.app.desPensaBackEnd.mapper.MovimentacaoEstoqueMapper;
import com.app.desPensaBackEnd.model.dto.MovimentacaoEstoqueDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.EstoqueRepository;
import com.app.desPensaBackEnd.view.repository.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoqueService {

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public MovimentacaoEstoqueDTO criar(MovimentacaoEstoqueDTO dto) {

        AlimentoEntity alimento = alimentoRepository.findById(dto.getAlimentoId())
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado"));

        EstoqueEntity estoque = estoqueRepository.findById(dto.getEstoqueId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        MovimentacaoEstoqueEntity entity =
                MovimentacaoEstoqueMapper.toEntity(dto, alimento, estoque);

        entity = movimentacaoRepository.save(entity);

        return MovimentacaoEstoqueMapper.toDTO(entity);
    }

    public MovimentacaoEstoqueDTO atualizar(Long id, MovimentacaoEstoqueDTO dto) {

        MovimentacaoEstoqueEntity existente =
                movimentacaoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        existente.setQuantidade(dto.getQuantidade());
        existente.setOrigem(dto.getOrigem());
        existente.setData(dto.getDataMovimentacao());
        existente.setObservacao(dto.getObservacao());
        existente.setTipo(dto.getTipo());

        existente = movimentacaoRepository.save(existente);

        return MovimentacaoEstoqueMapper.toDTO(existente);
    }


    public void deletar(Long id) {
        movimentacaoRepository.deleteById(id);
    }

    public MovimentacaoEstoqueDTO buscarPorId(Long id) {
        MovimentacaoEstoqueEntity entity =
                movimentacaoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        return MovimentacaoEstoqueMapper.toDTO(entity);
    }

    public List<MovimentacaoEstoqueDTO> listarTodos() {
        return movimentacaoRepository.findAll()
                .stream()
                .map(MovimentacaoEstoqueMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovimentacaoEstoqueDTO> listarPorEstoque(Long estoqueId) {
        List<MovimentacaoEstoqueEntity> lista =
                movimentacaoRepository.findByEstoqueIdEstoque(estoqueId);

        return lista.stream()
                .map(MovimentacaoEstoqueDTO::new)
                .collect(Collectors.toList());
    }
}

