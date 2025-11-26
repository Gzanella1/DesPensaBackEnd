package com.app.desPensaBackEnd.view.services;



import com.app.desPensaBackEnd.enums.TipoMovimentacao;
import com.app.desPensaBackEnd.model.dto.AlimentoInputDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.EstoqueRepository;
import com.app.desPensaBackEnd.view.repository.MovimentacaoEstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    @Autowired
    public AlimentoService(AlimentoRepository alimentoRepository,
                           EstoqueRepository estoqueRepository,
                           MovimentacaoEstoqueRepository movimentacaoRepository) {
        this.alimentoRepository = alimentoRepository;
        this.estoqueRepository = estoqueRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    // ===============================
    //   1) ADICIONAR ALIMENTO
    // ===============================
    @Transactional
    public AlimentoEntity adicionar(AlimentoInputDTO dto) {

        EstoqueEntity estoque = estoqueRepository.findById(dto.getEstoqueId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        AlimentoEntity alimento = new AlimentoEntity();
        alimento.setNome(dto.getNome());
        alimento.setCodigo(dto.getCodigo());
        alimento.setCategoria(dto.getCategoria());
        alimento.setUnidadeMedida(dto.getUnidadeMedida());
        alimento.setValorCalorico(dto.getValorCalorico());
        alimento.setDataValidade(dto.getDataValidade());
        alimento.setQuantidade(dto.getQuantidade());
        alimento.setEstoque(estoque);

        // Salva o alimento
        AlimentoEntity alimentoSalvo = alimentoRepository.save(alimento);

        // ============ REGISTRAR MOVIMENTAÇÃO DE ENTRADA ============
        MovimentacaoEstoqueEntity mov = new MovimentacaoEstoqueEntity();
        mov.setTipo(TipoMovimentacao.ENTRADA);
        mov.setData(LocalDateTime.now());
        mov.setQuantidade(dto.getQuantidade());
        mov.setOrigem("Cadastro");
        mov.setObservacao("Cadastro de novo alimento");
        mov.setAlimento(alimentoSalvo);
        mov.setEstoque(estoque);

        movimentacaoRepository.save(mov);
        // ============================================================

        return alimentoSalvo;
    }

    // ===============================
    //   2) LISTAR
    // ===============================
    public List<AlimentoEntity> listarTodos() {
        return alimentoRepository.findAll();
    }

    public List<AlimentoEntity> listarPorEstoque(Long estoqueId) {
        return alimentoRepository.findByEstoqueIdEstoque(estoqueId);
    }



    // No AlimentoService.java

    public Optional<AlimentoEntity> buscarPorId(Long id) {
        return alimentoRepository.findById(id);
    }

    public void remover(Long id) {
        AlimentoEntity alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado"));

        // Se a intenção é apagar o registro do banco, NÃO crie movimentação de estoque aqui.
        // Apenas apague.

        // Nota: Se já existirem movimentações antigas ligadas a este alimento,
        // o delete vai falhar por violação de chave estrangeira, a menos que você
        // tenha configurado CascadeType.REMOVE na entidade Alimento.

        alimentoRepository.delete(alimento);
    }


    // No AlimentoService.java

    // AlimentoService.java

    public AlimentoEntity atualizar(Long id, AlimentoEntity novosDados) {
        return alimentoRepository.findById(id).map(item -> {
            // Atualiza os campos
            item.setNome(novosDados.getNome());
            item.setCodigo(novosDados.getCodigo());
            item.setCategoria(novosDados.getCategoria());
            item.setQuantidade(novosDados.getQuantidade());
            item.setUnidadeMedida(novosDados.getUnidadeMedida());
            item.setDataValidade(novosDados.getDataValidade());
            item.setValorCalorico(novosDados.getValorCalorico());

            // Salva a atualização
            return alimentoRepository.save(item);
        }).orElse(null); // Retorna null se o ID não existir
    }
}