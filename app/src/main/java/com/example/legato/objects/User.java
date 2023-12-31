package com.example.legato.objects;

public class User {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String accountType;


    public User() {

    }

    public User( String id, String nome, String email, String senha, String accountType) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.accountType = accountType;
    }

    public User(String email, String senha) {
        this.email = email;
        this.senha = senha;
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

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
