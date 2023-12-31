package com.example.legato.objects;

import java.text.DateFormat;

public class Music {
    private String id_music;
    private String nome_music;
    private DateFormat ano_lancam_music;
    private String genero_music;
    private String album_music;
    private String compositor_music;

    public Music() {

    }

    public Music(String id_music, String nome_music, DateFormat ano_lancam_music, String genero_music, String album_music, String compositor_music) {
        this.id_music = id_music;
        this.nome_music = nome_music;
        this.ano_lancam_music = ano_lancam_music;
        this.genero_music = genero_music;
        this.album_music = album_music;
        this.compositor_music = compositor_music;
    }

    public String getId_music() {
        return id_music;
    }

    public void setId_music(String id_music) {
        this.id_music = id_music;
    }

    public String getNome_music() {
        return nome_music;
    }

    public void setNome_music(String nome_music) {
        this.nome_music = nome_music;
    }

    public DateFormat getAno_lancam_music() {
        return ano_lancam_music;
    }

    public void setAno_lancam_music(DateFormat ano_lancam_music) {
        this.ano_lancam_music = ano_lancam_music;
    }

    public String getGenero_music() {
        return genero_music;
    }

    public void setGenero_music(String genero_music) {
        this.genero_music = genero_music;
    }

    public String getAlbum_music() {
        return album_music;
    }

    public void setAlbum_music(String album_music) {
        this.album_music = album_music;
    }

    public String getCompositor_music() {
        return compositor_music;
    }

    public void setCompositor_music(String compositor_music) {
        this.compositor_music = compositor_music;
    }
}