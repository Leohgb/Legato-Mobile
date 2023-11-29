package com.example.legato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCadastro = findViewById(R.id.btnEntrar);
        Button btnEntrar = findViewById(R.id.idEntrar);
        btnCadastro.setOnClickListener(e->{
            Intent intent = new Intent(MainActivity.this, Cadastro.class);
            startActivity(intent);
        });

        btnEntrar.setOnClickListener(e->{
            Intent intent = new Intent(MainActivity.this, Entrar.class);
            startActivity(intent);
        });

    }
}