package com.app.rebec.cadevoce.model.VO;

import android.content.Context;

public class UsuarioVO {

    protected int codigo;
    protected String nome;
    protected String email;
    protected String senha;
    protected String confSenha;
    protected String idFotoPerfil;
    protected String idFacebook;
    protected String idGoogle;


    public UsuarioVO() {
    }

    public UsuarioVO(int codigo, String nome, String email, String senha, String confSenha, String idFotoPerfil, String idFacebook, String idGoogle) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confSenha = confSenha;
        this.idFotoPerfil = idFotoPerfil;
        this.idFacebook = idFacebook;
        this.idGoogle = idGoogle;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIdFotoPerfil() {
        return idFotoPerfil;
    }

    public void setIdFotoPerfil(String idFotoPerfil) {
        this.idFotoPerfil = idFotoPerfil;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }


    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }


}


