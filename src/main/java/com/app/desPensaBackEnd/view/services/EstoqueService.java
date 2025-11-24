package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.view.repository.AlertaRepository;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EstoqueService {

    private final AlimentoRepository alimentoRepository;
    private final AlertaRepository alertaRepository;

    // Removido EstoqueRepository se não for usado diretamente aqui,
    // mas se precisar, adicione ao construtor também.

    public EstoqueService(AlimentoRepository alimentoRepository, AlertaRepository alertaRepository) {
        this.alimentoRepository = alimentoRepository;
        this.alertaRepository = alertaRepository;
    }

    /**
     * Método responsável por dar baixa no item e verificar se precisa de alerta.
     * Recebe o ID para garantir que estamos mexendo no dado atualizado do banco.
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

            // 5. Verifica Regra de Alerta
            verificarNivelEstoque(alimentoSalvo);

            return alimentoSalvo;

        } else {
            // Lança erro para o Controller tratar (retornar status 400 Bad Request)
            throw new IllegalArgumentException("Estoque insuficiente para '" + alimento.getNome() +
                    "'. Disponível: " + alimento.getQuantidade());
        }
    }

    private void verificarNivelEstoque(AlimentoEntity alimento) {
        // DICA: O ideal é ter um campo 'quantidadeMinima' na entidade Alimento.
        // Se não tiver, usamos um padrão (ex: 5).
        int limiteMinimo = 5;
        // Exemplo se você tiver o campo: int limiteMinimo = alimento.getQuantidadeMinima();

        if (alimento.getQuantidade() <= limiteMinimo) {

            // Evitar spam: (Opcional) Verificar se já existe um alerta pendente desse tipo hoje
            // para não criar 10 alertas se o usuário consumir 10 vezes seguidas estando baixo.

            AlertaEntity alerta = new AlertaEntity();

            // Definindo a mensagem e o nível base
            if (alimento.getQuantidade() == 0) {
                alerta.setTipoAlerta(TipoAlerta.ESTOQUE_BAIXO); // Ou criar um TipoAlerta.ESGOTADO
                alerta.setNivel(NivelAlerta.CRITICA);
                alerta.setMensagem("URGENTE: O estoque de '" + alimento.getNome() + "' ACABOU!");
            } else {
                alerta.setTipoAlerta(TipoAlerta.ESTOQUE_BAIXO);
                alerta.setNivel(NivelAlerta.AVISO);
                alerta.setMensagem("Atenção: Estoque baixo para '" + alimento.getNome() +
                        "'. Restam apenas: " + alimento.getQuantidade());
            }

            alerta.setData(LocalDateTime.now());
            alerta.setVisualizado(false);

            // Associação com o Estoque (Pai)
            if (alimento.getEstoque() != null) {
                alerta.setEstoque(alimento.getEstoque());
            }

            alertaRepository.save(alerta);
        }
    }
}