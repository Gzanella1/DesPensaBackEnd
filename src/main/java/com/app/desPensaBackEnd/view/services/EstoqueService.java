package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import com.app.desPensaBackEnd.model.dto.EstoqueDetalhadoDTO;
import com.app.desPensaBackEnd.model.dto.MovimentacaoEstoqueDTO;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlertaRepository;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.EstoqueRepository;
import com.app.desPensaBackEnd.view.repository.MovimentacaoEstoqueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private final AlimentoRepository alimentoRepository;
    private final EstoqueRepository estoqueRepository;
    private final AlertaService alertaService;
    private final AlertaRepository alertaRepository;

    public EstoqueService(AlimentoRepository alimentoRepository, EstoqueRepository estoqueRepository, AlertaService alertaService, AlertaRepository alertaRepository) {
        this.alimentoRepository = alimentoRepository;
        this.estoqueRepository = estoqueRepository;
        this.alertaService = alertaService;
        this.alertaRepository = alertaRepository;
    }

    /**
     * Método responsável por dar baixa no item e verificar se precisa de alerta.
     * Recebe o ID para garantir que estamos mexendo no dado atualizado do banco.
     */

    /**
     * Método responsável por dar baixa no item e verificar se precisa de alerta.
     */
    @Transactional
    public AlimentoEntity consumirAlimento(Long alimentoId, int quantidadeParaRemover) {
        // 1. Busca a versão mais recente do banco
        AlimentoEntity alimento = alimentoRepository.findById(alimentoId)
                .orElseThrow(() -> new EntityNotFoundException("Alimento não encontrado com ID: " + alimentoId));

        // 2. Validação básica
        if (quantidadeParaRemover <= 0) {
            throw new IllegalArgumentException("A quantidade a remover deve ser maior que zero.");
        }

        // 3. Verifica se há estoque suficiente
        if (alimento.getQuantidade() >= quantidadeParaRemover) {
            int novaQuantidade = alimento.getQuantidade() - quantidadeParaRemover;
            alimento.setQuantidade(novaQuantidade);

            // 4. Salva a alteração
            AlimentoEntity alimentoSalvo = alimentoRepository.save(alimento);

            // 5. Verifica Regra de Alerta (Agora delegando para o método correto)
            verificarNivelEstoque(alimentoSalvo);

            return alimentoSalvo;

        } else {
            throw new IllegalArgumentException("Estoque insuficiente para '" + alimento.getNome() +
                    "'. Disponível: " + alimento.getQuantidade());
        }
    }

    private void verificarNivelEstoque(AlimentoEntity alimento) {
        // Defina seu limite (pode vir de configuração ou do próprio alimento)
        int limiteMinimo = 5;

        if (alimento.getQuantidade() <= limiteMinimo) {

            // Lógica para decidir o detalhe da mensagem
            String detalhe;
            if (alimento.getQuantidade() == 0) {
                detalhe = "Item ESGOTADO.";
            } else {
                detalhe = "Restam apenas: " + alimento.getQuantidade();
            }

            // CORREÇÃO 2: Chamada limpa ao AlertaService.
            // O EstoqueService não sabe qual cor ou texto usar, ele só avisa o fato.
            alertaService.criarAlerta(
                    TipoAlerta.ESTOQUE_BAIXO,
                    alimento.getEstoque(), // Entidade Estoque pai
                    alimento.getNome(),    // Nome específico do alimento
                    detalhe                // Detalhe extra
            );
        }
    }


    /**
     * 2. REABASTECER ALIMENTO (O código novo está aqui)
     * Aumenta a quantidade e remove o alerta se o nível ficar seguro.
     */
    @Transactional
    public AlimentoEntity reabastecerAlimento(Long alimentoId, int quantidadeParaAdicionar) {
        AlimentoEntity alimento = alimentoRepository.findById(alimentoId)
                .orElseThrow(() -> new EntityNotFoundException("Alimento não encontrado com ID: " + alimentoId));

        if (quantidadeParaAdicionar <= 0) {
            throw new IllegalArgumentException("A quantidade a adicionar deve ser maior que zero.");
        }

        // Aumenta o estoque
        int novaQuantidade = alimento.getQuantidade() + quantidadeParaAdicionar;
        alimento.setQuantidade(novaQuantidade);
        AlimentoEntity salvo = alimentoRepository.save(alimento);

        // Verifica se o estoque saiu do nível crítico (acima de 5)
        int limiteMinimo = 5;

        if (salvo.getQuantidade() > limiteMinimo) {
            // Se agora está seguro, chama o serviço para limpar alertas antigos deste alimento
            alertaService.resolverAlertaEstoqueBaixo(salvo.getEstoque(), salvo.getNome());
        }

        return salvo;
    }

    // NOVO MÉTODO: Busca o Estoque e monta o DTO com alertas
    @Transactional(readOnly = true)
    public EstoqueDetalhadoDTO buscarEstoquePorId(Long idEstoque) {
        // 1. Busca o Estoque
        EstoqueEntity estoque = estoqueRepository.findById(idEstoque)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado com ID: " + idEstoque));

        // 2. Busca os Alertas desse estoque (precisa ter criado esse método no repo, veja abaixo)
        List<AlertaEntity> alertas = alertaRepository.findByEstoqueIdEstoque(idEstoque);

        // 3. Retorna o DTO combinado
        return new EstoqueDetalhadoDTO(estoque, alertas);
    }




}