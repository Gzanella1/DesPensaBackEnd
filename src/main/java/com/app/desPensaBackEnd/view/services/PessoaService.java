package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.model.dto.PessoaDTO;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.model.entity.PessoaEntity;
import com.app.desPensaBackEnd.view.repository.InstituicaoRepository;
import com.app.desPensaBackEnd.view.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // 1. LISTAR
    public List<PessoaEntity> listarTodas() {
        return pessoaRepository.findAll();
    }

    // 2. BUSCAR POR ID
    public PessoaEntity buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }

    // 3. SALVAR
    public PessoaEntity salvar(PessoaDTO dto) {
        PessoaEntity pessoa = new PessoaEntity();
        copiarDtoParaEntity(dto, pessoa);
        return pessoaRepository.save(pessoa);
    }

    // 4. ATUALIZAR
    public PessoaEntity atualizar(Long id, PessoaDTO dto) {
        PessoaEntity pessoa = buscarPorId(id); // Verifica se existe
        copiarDtoParaEntity(dto, pessoa);
        return pessoaRepository.save(pessoa);
    }

    // 5. DELETAR
    public void deletar(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Pessoa não encontrada para deletar");
        }
        pessoaRepository.deleteById(id);
    }

    // --- MÉTODO AUXILIAR PARA CONVERSÃO ---
    private void copiarDtoParaEntity(PessoaDTO dto, PessoaEntity entity) {
        entity.setNome(dto.getNome());
        entity.setIdade(dto.getIdade());
        entity.setRestricaoAlimentar(dto.getRestricaoAlimentar());

        // Busca a instituição no banco para fazer o vínculo
        if (dto.getIdInstituicao() != null) {
            InstituicaoEntity instituicao = instituicaoRepository.findById(dto.getIdInstituicao())
                    .orElseThrow(() -> new RuntimeException("Instituição não encontrada com ID: " + dto.getIdInstituicao()));
            entity.setInstituicao(instituicao);
        }
    }
}
