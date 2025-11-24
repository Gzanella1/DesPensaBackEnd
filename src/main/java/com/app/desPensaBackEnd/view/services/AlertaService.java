package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import com.app.desPensaBackEnd.view.repository.AlertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }


    /**
     * Método principal para criar alertas.
     * Centraliza a definição de Nível e Mensagem.
     *
     * @param tipo O tipo do alerta (ex: ESTOQUE_BAIXO)
     * @param estoque A entidade de estoque relacionada (pode ser null se for erro de sistema)
     * @param detalheExtra Uma string opcional para complementar a mensagem automática
     */
    @Transactional
    public AlertaEntity criarAlerta(TipoAlerta tipo, EstoqueEntity estoque, String nomeAlimento, String detalheExtra) { // <--- Adicionei nomeAlimento
        AlertaEntity alerta = new AlertaEntity();

        alerta.setTipoAlerta(tipo);
        alerta.setEstoque(estoque);
        alerta.setData(LocalDateTime.now());
        alerta.setVisualizado(false);

        // Passamos o nome específico para a configuração
        configurarRegrasDoAlerta(alerta, tipo, nomeAlimento, detalheExtra);

        return alertaRepository.save(alerta);
    }

    /**
     * Método auxiliar que contém a lógica "switch" para definir
     * a gravidade e o texto do alerta.
     */
    private void configurarRegrasDoAlerta(AlertaEntity alerta, TipoAlerta tipo, String nomeAlimento, String detalheExtra) {

        // Se não passar nome (null), assume que é um alerta sobre a Despensa inteira
        String produto = (nomeAlimento != null && !nomeAlimento.isEmpty()) ? nomeAlimento : "Estoque Geral";

        String sufixo = (detalheExtra != null && !detalheExtra.isEmpty()) ? " - " + detalheExtra : "";

        switch (tipo) {
            case ESTOQUE_BAIXO:
                alerta.setNivel(NivelAlerta.ALTA);
                // Ex: "O estoque do produto 'Arroz' atingiu o nível mínimo."
                alerta.setMensagem(String.format("O estoque do produto '%s' atingiu o nível mínimo.%s", produto, sufixo));
                break;

            case ESTOQUE_VENCIDO:
                alerta.setNivel(NivelAlerta.CRITICA);
                // Ex: "ATENÇÃO: O produto 'Leite' venceu!"
                alerta.setMensagem(String.format("ATENÇÃO: O produto '%s' venceu! Descarte necessário.%s", produto, sufixo));
                break;

            case VALIDADE_PROXIMA:
                alerta.setNivel(NivelAlerta.MEDIA);
                alerta.setMensagem(String.format("O produto '%s' está próximo da data de validade.%s", produto, sufixo));
                break;

            case NOVO_FORNECIMENTO:
                alerta.setNivel(NivelAlerta.BAIXA);
                alerta.setMensagem(String.format("Novo fornecimento registrado para '%s'.%s", produto, sufixo));
                break;

            case ERRO_SISTEMA:
                alerta.setNivel(NivelAlerta.ALTA);
                alerta.setMensagem("Erro no sistema relacionado a: " + produto + sufixo);
                break;

            default:
                alerta.setNivel(NivelAlerta.BAIXA);
                alerta.setMensagem("Alerta: " + produto + sufixo);
                break;
        }
    }

    // Método para marcar como visualizado (útil para o front-end)
    @Transactional
    public void marcarComoVisualizado(Long id) {
        alertaRepository.findById(id).ifPresent(alerta -> {
            alerta.setVisualizado(true);
            alertaRepository.save(alerta);
        });
    }



    // --- NOVO MÉTODO PARA "LIMPAR" O ALERTA ---
    @Transactional
    public void resolverAlertaEstoqueBaixo(EstoqueEntity estoque, String nomeAlimento) {
        if (estoque == null) return;

        // Busca todos os alertas de ESTOQUE_BAIXO não lidos desta despensa
        List<AlertaEntity> alertasAtivos = alertaRepository
                .findByEstoqueIdEstoqueAndTipoAlertaAndVisualizadoFalse(
                        estoque.getIdEstoque(),
                        TipoAlerta.ESTOQUE_BAIXO
                );

        // Como não temos uma coluna "idAlimento" no alerta, verificamos pelo texto da mensagem
        for (AlertaEntity alerta : alertasAtivos) {
            if (alerta.getMensagem().contains(nomeAlimento)) {
                alerta.setVisualizado(true); // Marca como resolvido/lido
                // Opcional: alertaRepository.delete(alerta); // Se preferir apagar do banco
                alertaRepository.save(alerta);
            }
        }
    }
}