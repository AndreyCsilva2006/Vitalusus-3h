package com.br.projeto.vitalusus.model;

import java.math.BigInteger;

public class Canal {

    // te amo pq vc tem bastante força e coragem para enfrentar oq vier pela frente, e vc tbm me trata bem com carinho e eu gosto disso, me sinto acolhido por vc minha totosa, eu me importo com vc mesmo que vc negue isso, eu posso ter uma maneira diferente de me importar, por exemplo eu me importo com minha mãe mas eu não pergunto se ela está bem toda hora.
    private Integer id;
    private String nome;
    private Integer visualizacoes;
    private BigInteger seguidores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public BigInteger getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(BigInteger seguidores) {
        this.seguidores = seguidores;
    }
}
