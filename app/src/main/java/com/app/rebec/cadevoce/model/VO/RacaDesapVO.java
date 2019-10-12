package com.app.rebec.cadevoce.model.VO;

public class RacaDesapVO {
    protected int codigo;
    protected String descricao;


    public RacaDesapVO() {
    }

    public RacaDesapVO(int codigo, String descricao) {
        super();
        this.codigo = codigo;
        this.descricao = descricao;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
