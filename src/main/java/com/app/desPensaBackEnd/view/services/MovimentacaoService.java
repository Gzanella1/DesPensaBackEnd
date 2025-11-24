package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.TipoMovimentacao;
import com.app.desPensaBackEnd.model.dto.MovimentacaoDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.EstoqueRepository;
import com.app.desPensaBackEnd.view.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Transactional
    public MovimentacaoEstoqueEntity registrarMovimentacao(MovimentacaoDTO dto) {
        // 1. Buscar Estoque e Alimento
        EstoqueEntity estoque = estoqueRepository.findById(dto.getIdEstoque())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        AlimentoEntity alimento = alimentoRepository.findById(dto.getIdAlimento())
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado"));

        // 2. Regra de Negócio: Atualizar Saldo
        if (dto.getTipo() == TipoMovimentacao.SAIDA) {
            if (alimento.getQuantidade() < dto.getQuantidade()) {
                throw new RuntimeException("Saldo insuficiente para realizar a saída. Disponível: " + alimento.getQuantidade());
            }
            alimento.setQuantidade(alimento.getQuantidade() - dto.getQuantidade());
        } else if (dto.getTipo() == TipoMovimentacao.ENTRADA) {
            alimento.setQuantidade(alimento.getQuantidade() + dto.getQuantidade());
        }

        // Salva a atualização do saldo do alimento
        alimentoRepository.save(alimento);

        // 3. Criar o registro de Movimentação (Histórico)
        MovimentacaoEstoqueEntity mov = new MovimentacaoEstoqueEntity();
        mov.setEstoque(estoque);
        mov.setAlimento(alimento); // Importante ter adicionado o campo na Entity
        mov.setData(LocalDateTime.now());
        mov.setTipo(dto.getTipo());
        mov.setQuantidade(dto.getQuantidade());
        mov.setOrigem(dto.getOrigem());
        mov.setObservacao(dto.getObservacao());

        return movimentacaoRepository.save(mov);
    }

    public List<MovimentacaoEstoqueEntity> listarPorEstoque(Long idEstoque) {
        return movimentacaoRepository.findByEstoqueIdEstoqueOrderByDataDesc(idEstoque);
    }

}