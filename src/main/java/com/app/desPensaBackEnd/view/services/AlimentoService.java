package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.app.desPensaBackEnd.model.dto.AlimentoDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.RestricaoAlimentarEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.RestricaoAlimentarRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepo;
    private final RestricaoAlimentarRepository restricaoRepo;

    public AlimentoService(AlimentoRepository alimentoRepo,
                           RestricaoAlimentarRepository restricaoRepo) {
        this.alimentoRepo = alimentoRepo;
        this.restricaoRepo = restricaoRepo;
    }

    /**
     * Converte lista de nomes de restrições para entidades persistidas (se existirem).
     * Ignora nomes desconhecidos.
     */
    public Set<RestricaoAlimentarEntity> buscarRestricoesPorNome(List<String> nomes) {
        if (nomes == null) return Collections.emptySet();

        return nomes.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toUpperCase)
                .map(nome -> restricaoRepo.findByNomeIgnoreCase(nome).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Busca todos alimentos que NÃO contenham NENHUMA das restrições passadas.
     */
    public List<AlimentoEntity> buscarAlimentosPermitidos(Set<RestricaoAlimentarEntity> restricoesProibidas) {

        List<AlimentoEntity> todos = alimentoRepo.findAll();

        if (restricoesProibidas == null || restricoesProibidas.isEmpty()) {
            return todos;
        }

        // filtrar: manter apenas alimentos cuja interseção com restricoesProibidas seja vazia
        return todos.stream()
                .filter(a -> {
                    if (a.getRestricoes() == null || a.getRestricoes().isEmpty()) return true;
                    // verifica se existe alguma restrição do alimento que esteja nas proibidas
                    for (RestricaoAlimentarEntity r : a.getRestricoes()) {
                        if (restricoesProibidas.stream().anyMatch(rp -> rp.getNome().equalsIgnoreCase(r.getNome()))) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

}