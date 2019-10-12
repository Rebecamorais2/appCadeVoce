package com.app.rebec.cadevoce.model.VO;

public class CidadeVO {

    protected int codigo;
    protected String descricao;
    protected int codEstado;


    public CidadeVO() {
    }

    public CidadeVO(int codigo, String descricao, int codEstado) {
        super();
        this.codigo = codigo;
        this.descricao = descricao;
        this.codEstado = codEstado;
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

    public int getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(int codEstado) {
        this.codEstado = codEstado;
    }


    @Override
    public String toString() {
        return descricao;
    }

}
