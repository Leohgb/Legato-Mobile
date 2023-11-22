package com.example.legato;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_l);

        Button btnCadastro = findViewById(R.id.id_cadastrar_b);

        btnCadastro.setOnClickListener(e->{
            Intent intent = new Intent(Cadastro.this,Tabela.class);
            startActivity(intent);
        });
    }

}
