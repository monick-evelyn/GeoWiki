package com.example.geowiki2.objetos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "rocha")
public class Rocha implements Serializable {
    @ColumnInfo(name = "id_rocha")
    @PrimaryKey(autoGenerate = true)
    private int idRocha;

    //img é string pois é convertida de bipmap para um array de bytes para uma string
    @ColumnInfo(name = "img_rocha")
    private String imgRocha;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "tipo")
    private String tipo;

    @ColumnInfo(name = "classificacao_minerologica")
    private String classificacaoMinerologica;

    @ColumnInfo(name = "cor")
    private String cor;

    @ColumnInfo(name = "textura")
    private String textura;

    @ColumnInfo(name = "dureza")
    private int dureza;

    @ColumnInfo(name = "densidade")
    private double densidade;

    public Rocha(String imgRocha, String nome, String tipo, String classificacaoMinerologica, String cor, String textura, int dureza, double densidade) {
        this.imgRocha = imgRocha;
        this.nome = nome;
        this.tipo = tipo;
        this.classificacaoMinerologica = classificacaoMinerologica;
        this.cor = cor;
        this.textura = textura;
        this.dureza = dureza;
        this.densidade = densidade;
    }

    public int getIdRocha() {
        return idRocha;
    }
    public void setIdRocha(int idRocha) {
        this.idRocha = idRocha;
    }

    public String getImgRocha() {
        return imgRocha;
    }
    public void setImgRocha(String imgRocha) {
        this.imgRocha = imgRocha;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClassificacaoMinerologica() {
        return classificacaoMinerologica;
    }
    public void setClassificacaoMinerologica(String classificacaoMinerologica) {
        this.classificacaoMinerologica = classificacaoMinerologica;
    }

    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTextura() {
        return textura;
    }
    public void setTextura(String textura) {
        this.textura = textura;
    }

    public int getDureza() {
        return dureza;
    }
    public void setDureza(int dureza) {
        this.dureza = dureza;
    }

    public double getDensidade() {
        return densidade;
    }
    public void setDensidade(double densidade) {
        this.densidade = densidade;
    }

    @Override
    public String toString() {
        return "Rocha {" +
                "Nome: " + nome + '\'' +
                "Tipo; " + tipo + '\'' +
                "Classificação Minerológica: " + classificacaoMinerologica + '\'' +
                "Cor: " + cor + '\'' +
                "Textura: " + textura + '\'' +
                "Dureza; " + dureza + " Mohs " + '\'' +
                "Densidade: " + densidade + " g/cm³" + '\'' +
                " }";
    }
}