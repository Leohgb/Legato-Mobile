package com.example.legato;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.legato.objects.Music;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro_Music extends AppCompatActivity {
    private static final String APP_LEGATO = "APP_LEGATO";
    FirebaseAuth mAuth;
    EditText txtNome;
    EditText txtAnoLancamento;
    EditText txtCompositor;
    EditText txtGenero;

    Button btnSalvar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_music);

        databaseReference = FirebaseDatabase.getInstance().getReference("this_is_the_path_for_music");

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("musics");

        txtNome = findViewById(R.id.txtNomeMusica);
        txtAnoLancamento = findViewById(R.id.txtAnoLancamentoMusica);
        txtCompositor = findViewById(R.id.txtCompositorMusica);
        txtGenero = findViewById(R.id.txtGeneroMusica);

        btnSalvar = findViewById(R.id.btnSalvarMusica);

        btnSalvar.setOnClickListener(e -> salvar());

        mAuth.addAuthStateListener(auth -> {
            if (auth.getCurrentUser() != null) {
                String token = auth.getCurrentUser().getIdToken(true).getResult().getToken();
                Log.i(APP_LEGATO, "TOKEN : " + token);
            } else {
                Log.i(APP_LEGATO, "TOKEN : null");
            }
        });
    }

    private void salvar() {
        Music music = new Music();

        String nome = txtNome.getText().toString().trim();
        String anoLancamentoStr = txtAnoLancamento.getText().toString().trim();
        String compositor = txtCompositor.getText().toString().trim();
        String genero = txtGenero.getText().toString().trim();

        if (nome.isEmpty() || anoLancamentoStr.isEmpty() || compositor.isEmpty() || genero.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!anoLancamentoStr.matches("\\d+")) {
            Toast.makeText(getApplicationContext(), "Ano de lançamento inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        int anoLancamento = Integer.parseInt(anoLancamentoStr);

        music.setMusicName(nome);
        music.setAnoLancamento(anoLancamento);
        music.setComposer(compositor);
        music.setGenre(genero);

        String musicId = databaseReference.push().getKey();

        if (musicId != null) {
            Intent intent = new Intent(Cadastro_Music.this, ListMusics.class);
            startActivity(intent);
            music.setId_music(musicId);
        } else {
            Log.e(APP_LEGATO, "Erro: musicId é nulo");
            return;
        }

        DatabaseReference musicRef = databaseReference.child(musicId);

        Log.i(APP_LEGATO, "Salvando música: " + music.getMusicName() + ", " + music.getGenre() +
                ", " + music.getComposer() + ", " + music.getAnoLancamento());

        musicRef.setValue(music)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Música cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Falha ao cadastrar música", Toast.LENGTH_SHORT).show();
                    Log.e(APP_LEGATO, "Erro ao cadastrar música", e);
                });
    }
}
