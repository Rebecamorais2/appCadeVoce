package com.app.rebec.cadevoce.model.VO;

import com.app.rebec.cadevoce.model.VO.CidadeVO;

public class DesaparecidoVO {

    private int codDesap;
    private String nome;
    private int idade;
    private double altura;
    private String sexo;
    private String dataDesaparecimento;
    private String dataCadastro;

    private int telefoneContato;
    private String descricao;
    private int idFaces;
    private boolean fotoPrincipal;

    CidadeVO cidade = new CidadeVO();
    CorCabeloVO corcabelo = new CorCabeloVO();
    CorOlhosVO corolhos = new CorOlhosVO();
    ParentescoVO parentesco = new ParentescoVO();
    RacaDesapVO raca = new RacaDesapVO();
    UsuarioVO usuario = new UsuarioVO();
    EstadoVO estado = new EstadoVO();

    private int idCidade = cidade.codigo;
    private int idCorCabelo = corcabelo.codigo;
    private int idCorOlhos = corolhos.codigo;
    private int idParentesco = parentesco.codigo;
    private int idRaca = raca.codigo;
    private int idUsuario = usuario.codigo;
    private String cidDescricao = cidade.descricao;
    private String estDescricao = estado.descricao;
    private String racaDescricao = raca.descricao;
    private String olhosDescricao = corolhos.descricao;
    private String cabeloDescricao = corcabelo.descricao;
    private String parentescoDescricao = parentesco.descricao;



    public DesaparecidoVO() {}

    public DesaparecidoVO(int codDesap, String nome, int idade, double altura, String sexo,
                          String dataDesaparecimento, String dataCadastro, int telefoneContato, String descricao, int idFaces,
                          boolean fotoPrincipal, int idCidade, int idCorCabelo, int idCorOlhos, int idParentesco,
                          int idRaca, int idUsuario, String cidDescricao, String estDescricao, String racaDescricao, String olhosDescricao,
                          String cabeloDescricao, String parentescoDescricao) {
        super();
        this.codDesap = codDesap;
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.sexo = sexo;
        this.dataDesaparecimento = dataDesaparecimento;
        this.dataCadastro = dataCadastro;
        this.telefoneContato = telefoneContato;
        this.descricao = descricao;
        this.idFaces = idFaces;
        this.fotoPrincipal = fotoPrincipal;
        this.idCidade = idCidade;
        this.idCorCabelo = idCorCabelo;
        this.idCorOlhos = idCorOlhos;
        this.idParentesco = idParentesco;
        this.idRaca = idRaca;
        this.idUsuario = idUsuario;
        this.estDescricao = estDescricao;
        this.cidDescricao = cidDescricao;
        this.racaDescricao = racaDescricao;
        this.olhosDescricao = olhosDescricao;
        this.cabeloDescricao = cabeloDescricao;
        this.parentescoDescricao = parentescoDescricao;
    }


    public int getCodDesap() {
        return codDesap;
    }
    public void setCodDesap(int codDesap) {
        this.codDesap = codDesap;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getDataDesaparecimento() {
        return dataDesaparecimento;
    }
    public void setDataDesaparecimento(String dataDesaparecimento) {
        this.dataDesaparecimento = dataDesaparecimento;
    }
    public String getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public int getTelefoneContato() {
        return telefoneContato;
    }
    public void setTelefoneContato(int telefone) {
        this.telefoneContato = telefone;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getIdFaces() {
        return idFaces;
    }
    public void setIdFaces(int idFaces) {
        this.idFaces = idFaces;
    }
    public boolean isFotoPrincipal() {
        return fotoPrincipal;
    }
    public void setFotoPrincipal(boolean fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }
    public int getIdCidade() {
        return idCidade;
    }
    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }
    public int getIdCorOlhos() {
        return idCorOlhos;
    }
    public void setIdCorOlhos(int idCorOlhos) {
        this.idCorOlhos = idCorOlhos;
    }
    public int getIdCorCabelo() {
        return idCorCabelo;
    }
    public void setIdCorCabelo(int idCorCabelo) {
        this.idCorCabelo = idCorCabelo;
    }
    public int getIdParentesco() {
        return idParentesco;
    }
    public void setIdParentesco(int idParentesco) {
        this.idParentesco = idParentesco;
    }
    public int getIdRaca() {
        return idRaca;
    }
    public void setIdRaca(int idRaca) {
        this.idRaca = idRaca;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getEstDescricao() {
        return estDescricao;
    }
    public void setEstDescricao(String estDescricao) {
        this.estDescricao = estDescricao;
    }
    public String getCidDescricao() {
        return cidDescricao;
    }
    public void setCidDescricao(String cidDescricao) {
        this.cidDescricao = cidDescricao;
    }
    public String getRacaDescricao() {
        return racaDescricao;
    }
    public void setRacaDescricao(String racaDescricao) {
        this.racaDescricao = racaDescricao;
    }
    public String getOlhosDescricao() {
        return olhosDescricao;
    }
    public void setOlhosDescricao(String olhosDescricao) {
        this.olhosDescricao = olhosDescricao;
    }
    public String getCabeloDescricao() {
        return cabeloDescricao;
    }
    public void setCabeloDescricao(String cabeloDescricao) {
        this.cabeloDescricao = cabeloDescricao;
    }
    public String getParentecoDescricao() {
        return parentescoDescricao;
    }
    public void setParentescoDescricao(String parentescoDescricao) {
        this.parentescoDescricao = parentescoDescricao;
    }
}
