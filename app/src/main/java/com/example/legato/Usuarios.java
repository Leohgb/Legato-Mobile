package com.example.legato;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Usuarios extends AppCompatActivity {
    private static final String BASE_URL = "https://backend-pdmi--antoniocarval11.repl.co";
    private static final String APP_LEGATO = "APP_LEGATO";
    List<Usuario> lista = new ArrayList<>();

    EditText txtNome; // variavel de instancia
    EditText txtEmail;
    EditText txtPassword;
    Button btnSalvar;
    Button btnPesquisar;
    Button btnCreditos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios);
    }
}

