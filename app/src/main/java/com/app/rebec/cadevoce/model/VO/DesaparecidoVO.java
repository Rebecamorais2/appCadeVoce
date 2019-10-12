package com.app.rebec.cadevoce.model.VO;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.rebec.cadevoce.model.VO.CidadeVO;

import java.util.List;

public class DesaparecidoVO implements Parcelable {

    private int codDesap;
    private String nome;
    private int idade;
    private double altura;
    private String dataDesaparecimento;
    private String dataCadastro;
    private String telefoneContato;
    private String descricao;
    private int idFaces;
    private String parentesco;

    //    CidadeVO cidade = new CidadeVO();
    CidadeVO cidade = new CidadeVO();
    CorCabeloVO corcabelo = new CorCabeloVO();
    CorOlhosVO corolhos = new CorOlhosVO();
    RacaDesapVO raca = new RacaDesapVO();
    UsuarioVO usuario = new UsuarioVO();
    State estado = new State();
    SexoVO sexo = new SexoVO();
    FacesVO faces = new FacesVO();

    //    private int idCidade = cidade.codigo;
    private String URLface_01 = faces.face_01;
    private String URLface_03 = faces.face_03;
    private int idCidade = cidade.codigo;
    private String cidadeDesc = cidade.descricao;
    private int idCorCabelo = corcabelo.codigo;
    private int idCorOlhos = corolhos.codigo;
    private int idRaca = raca.codigo;
    private int idUsuario = usuario.codigo;
    private int idSexo = sexo.codigo;
    private List<String> cidDescricao = estado.cities;
    private String estDescricao = estado.stateName;
    private String racaDescricao = raca.descricao;
    private String olhosDescricao = corolhos.descricao;
    private String cabeloDescricao = corcabelo.descricao;
    private String sexoDescricao = sexo.descricao;


    public DesaparecidoVO() {
    }

    public DesaparecidoVO(int codDesap, String nome, int idade, double altura, String sexo, String dataDesaparecimento, String dataCadastro, String telefoneContato, String descricao, int idFaces, int idCidade, int idCorCabelo, int idCorOlhos, int idRaca, int idUsuario, int idSexo, List<String> cidDescricao, String estDescricao, /*String racaDescricao,*/ String olhosDescricao, String cabeloDescricao, String parentesco, String sexoDescricao, String cidadeDesc, String URLface_01, String URLface_03) {
        super();
        this.codDesap = codDesap;
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.dataDesaparecimento = dataDesaparecimento;
        this.dataCadastro = dataCadastro;
        this.telefoneContato = telefoneContato;
        this.descricao = descricao;
        this.idFaces = idFaces;
//        this.idCidade = idCidade;
        this.idCorCabelo = idCorCabelo;
        this.idCorOlhos = idCorOlhos;
        this.parentesco = parentesco;
        this.idRaca = idRaca;
        this.idUsuario = idUsuario;
        this.idSexo = idSexo;
        this.estDescricao = estDescricao;
        this.cidDescricao = cidDescricao;
        this.racaDescricao = racaDescricao;
        this.olhosDescricao = olhosDescricao;
        this.cabeloDescricao = cabeloDescricao;
        this.sexoDescricao = sexoDescricao;
        this.URLface_01 = URLface_01;
        this.URLface_03 = URLface_03;
    }


    protected DesaparecidoVO(Parcel in) {
        codDesap = in.readInt();
        nome = in.readString();
        idade = in.readInt();
        altura = in.readDouble();
        dataDesaparecimento = in.readString();
        dataCadastro = in.readString();
        telefoneContato = in.readString();
        descricao = in.readString();
        idFaces = in.readInt();
        parentesco = in.readString();
        URLface_01 = in.readString();
        URLface_03 = in.readString();
        idCidade = in.readInt();
        cidadeDesc = in.readString();
        idCorCabelo = in.readInt();
        idCorOlhos = in.readInt();
        idRaca = in.readInt();
        idUsuario = in.readInt();
        idSexo = in.readInt();
        cidDescricao = in.createStringArrayList();
        estDescricao = in.readString();
        racaDescricao = in.readString();
        olhosDescricao = in.readString();
        cabeloDescricao = in.readString();
        sexoDescricao = in.readString();
    }

    public static final Creator<DesaparecidoVO> CREATOR = new Creator<DesaparecidoVO>() {
        @Override
        public DesaparecidoVO createFromParcel(Parcel in) {
            return new DesaparecidoVO(in);
        }

        @Override
        public DesaparecidoVO[] newArray(int size) {
            return new DesaparecidoVO[size];
        }
    };

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

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
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

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefone) {
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

    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public String getEstDescricao() {
        return estDescricao;
    }

    public void setEstDescricao(String estDescricao) {
        this.estDescricao = estDescricao;
    }

    public List<String> getCidDescricao() {
        return cidDescricao;
    }

    public void setCidDescricao(List<String> cidDescricao) {
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

    public String getSexoDescricao() {
        return sexoDescricao;
    }

    public void setSexoDescricao(String sexoDescricao) {
        this.sexoDescricao = sexoDescricao;
    }

    public String getCidadeDescricao() {
        return cidadeDesc;
    }

    public void setCidadeDescricao(String cidadeDesc) {
        this.cidadeDesc = cidadeDesc;
    }

    public String getURLface_01() {
        return URLface_01;
    }

    public void setURLface_01(String URLface_01) {
        this.URLface_01 = URLface_01;
    }

    public String getURLface_03() {
        return URLface_03;
    }

    public void setURLface_03(String URLface_03) {
        this.URLface_03 = URLface_03;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codDesap);
        dest.writeString(nome);
        dest.writeInt(idade);
        dest.writeDouble(altura);
        dest.writeString(dataDesaparecimento);
        dest.writeString(dataCadastro);
        dest.writeString(telefoneContato);
        dest.writeString(descricao);
        dest.writeInt(idFaces);
        dest.writeString(parentesco);
        dest.writeString(URLface_01);
        dest.writeString(URLface_03);
        dest.writeInt(idCidade);
        dest.writeString(cidadeDesc);
        dest.writeInt(idCorCabelo);
        dest.writeInt(idCorOlhos);
        dest.writeInt(idRaca);
        dest.writeInt(idUsuario);
        dest.writeInt(idSexo);
        dest.writeStringList(cidDescricao);
        dest.writeString(estDescricao);
        dest.writeString(racaDescricao);
        dest.writeString(olhosDescricao);
        dest.writeString(cabeloDescricao);
        dest.writeString(sexoDescricao);
    }

    public static final Parcelable.Creator<DesaparecidoVO> CRIAR = new Parcelable.Creator<DesaparecidoVO>() {
        @Override
        public DesaparecidoVO createFromParcel(Parcel p) {
            DesaparecidoVO d = new DesaparecidoVO();
            d.readFromParcel(p);
            return d;
        }

        @Override
        public DesaparecidoVO[] newArray(int size) {
            return new DesaparecidoVO[size];
        }
    };

    private void readFromParcel(Parcel p) {
        this.codDesap = p.readInt();
        this.nome = p.readString();
        this.idade = p.readInt();
        this.altura = p.readDouble();
        this.dataDesaparecimento = p.readString();
        this.dataCadastro = p.readString();
        this.telefoneContato = p.readString();
        this.descricao = p.readString();
        this.idFaces = p.readInt();
        this.parentesco = p.readString();
        this.URLface_01 = p.readString();
        this.URLface_03 = p.readString();
        this.idCidade = p.readInt();
        this.cidadeDesc = p.readString();
        this.idCorCabelo = p.readInt();
        this.idCorOlhos = p.readInt();
        this.idRaca = p.readInt();
        this.idUsuario = p.readInt();
        this.idSexo = p.readInt();
        this.cidadeDesc = p.readString();
        this.estDescricao = p.readString();
        this.racaDescricao = p.readString();
        this.racaDescricao = p.readString();
        this.cabeloDescricao = p.readString();
        this.sexoDescricao = p.readString();
    }
}
