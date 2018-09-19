package com.app.rebec.cadevoce.model.VO;

public class ParentescoVO {

    protected int codigo;
    protected String descricao;

    public ParentescoVO() {}

    public ParentescoVO(int codigo, String descricao) {
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
}
