package com.example.legato.objects;

public class Artist {
    private String id;
    private String nome;
    private int idade;
    private String genero;
    private String biografia;
    public Artist() {

    }


    public Artist(String id, String nome, int idade, String genero, String biografia) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.biografia = biografia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
