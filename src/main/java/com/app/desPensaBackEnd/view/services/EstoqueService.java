package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.view.repository.AlertaRepository;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EstoqueService {

    private final AlimentoRepository alimentoRepository;
    private final AlertaRepository alertaRepository;

    public EstoqueService(AlimentoRepository alimentoRepository, AlertaRepository alertaRepository) {
        this.alimentoRepository = alimentoRepository;
        this.alertaRepository = alertaRepository;
    }

    /**
     * Método responsável por dar baixa no item e verificar se precisa de alerta.
     */
    @Transactional
    public void consumirAlimento(AlimentoEntity alimento, int quantidadeParaRemover) {

        if (alimento.getQuantidade() >= quantidadeParaRemover) {
            int novaQuantidade = alimento.getQuantidade() - quantidadeParaRemover;
            alimento.setQuantidade(novaQuantidade);

            // 1. Atualiza o Alimento
            alimentoRepository.save(alimento);

            // 2. Verifica Regra de Alerta (A lógica fica encapsulada aqui)
            verificarNivelEstoque(alimento);

        } else {
            // Opcional: Lançar erro ou logar se tentar consumir mais do que tem
            System.out.println("Tentativa de consumir mais do que o estoque possui: " + alimento.getNome());
        }
    }

    private void verificarNivelEstoque(AlimentoEntity alimento) {
        // Regra de Negócio: Se tiver 5 ou menos, gera alerta
        if (alimento.getQuantidade() <= 5) {
            AlertaEntity alerta = new AlertaEntity();
            alerta.setTipoAlerta(TipoAlerta.ESTOQUE_BAIXO);

            if (alimento.getQuantidade() == 0) {
                alerta.setNivel(NivelAlerta.CRITICO);
                alerta.setMensagem("O estoque de '" + alimento.getNome() + "' ACABOU!");
            } else {
                alerta.setNivel(NivelAlerta.AVISO);
                alerta.setMensagem("Estoque baixo para '" + alimento.getNome() + "'. Restam apenas: " + alimento.getQuantidade());
            }

            alerta.setData(LocalDateTime.now());
            alerta.setVisualizado(false);
            alerta.setEstoque(alimento.getEstoque()); // Vincula ao Estoque Pai

            alertaRepository.save(alerta);
        }
    }
}