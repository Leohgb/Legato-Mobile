package com.example.legato.objects;

public class Post {
    private String id;
    private String titulo;
    private String autor;
    private String comunidade;
    private String postagem;
    public Post() {

    }

    public Post(String id, String titulo, String autor, String comunidade, String postagem) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.comunidade = comunidade;
        this.postagem = postagem;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getComunidade() {
        return comunidade;
    }
    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getPostagem() {
        return postagem;
    }

    public void setPostagem(String postagem) {
        this.postagem = postagem;
    }
}
