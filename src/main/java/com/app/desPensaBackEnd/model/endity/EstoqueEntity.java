package com.app.desPensaBackEnd.model.endity;

import java.util.ArrayList;
import java.util.List;

public class EstoqueEntity {

    private Long idEstoque;
    private List<AlimentoEntity> alimentos=new ArrayList<>();

    private InstituicaoEntity instituicao;
    private String localizacao;

}
