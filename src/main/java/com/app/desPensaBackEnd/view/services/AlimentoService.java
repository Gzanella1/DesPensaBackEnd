package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.app.desPensaBackEnd.model.dto.AlimentoInputDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.EstoqueRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final EstoqueRepository estoqueRepository; // Precisamos buscar o estoque pai

    public AlimentoService(AlimentoRepository alimentoRepository, EstoqueRepository estoqueRepository) {
        this.alimentoRepository = alimentoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    // 1. ADICIONAR
    @Transactional
    public AlimentoEntity adicionar(AlimentoInputDTO dto) {
        // Busca o Estoque pelo ID
        EstoqueEntity estoque = estoqueRepository.findById(dto.getEstoqueId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com ID: " + dto.getEstoqueId()));

        // Converte DTO para Entity
        AlimentoEntity alimento = new AlimentoEntity();
        alimento.setNome(dto.getNome());
        alimento.setCodigo(dto.getCodigo());
        alimento.setCategoria(dto.getCategoria());
        alimento.setUnidadeMedida(dto.getUnidadeMedida());
        alimento.setValorCalorico(dto.getValorCalorico());
        alimento.setDataValidade(dto.getDataValidade());
        alimento.setQuantidade(dto.getQuantidade());

        // Vincula o relacionamento
        alimento.setEstoque(estoque);

        return alimentoRepository.save(alimento);
    }

    // 2. LISTAR TUDO
    public List<AlimentoEntity> listarTodos() {
        return alimentoRepository.findAll();
    }

    // 2.1 LISTAR POR ESTOQUE (Mais útil para seu app)
    public List<AlimentoEntity> listarPorEstoque(Long estoqueId) {
        return alimentoRepository.findByEstoqueIdEstoque(estoqueId);
    }

    // 3. REMOVER
    @Transactional
    public void remover(Long idAlimento) {
        if (!alimentoRepository.existsById(idAlimento)) {
            throw new RuntimeException("Alimento não encontrado para remover.");
        }
        alimentoRepository.deleteById(idAlimento);
    }




    // put atualiza alimento
}
