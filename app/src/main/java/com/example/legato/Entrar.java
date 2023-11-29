package com.example.legato;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Entrar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrar);
        Button btnCadastro = findViewById(R.id.btnCadastramentoPostagem);
        Button btnEntrar = findViewById(R.id.btnCadastramentoArtista);

        btnCadastro.setOnClickListener(e->{
            Intent intent = new Intent(Entrar.this, Cadastro_Post.class);
            startActivity(intent);
        });

        btnEntrar.setOnClickListener(e->{
            Intent intent = new Intent(Entrar.this, Cadastro_Artista.class);
            startActivity(intent);
        });

    }
}