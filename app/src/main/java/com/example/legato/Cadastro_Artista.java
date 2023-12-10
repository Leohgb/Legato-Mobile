package com.example.legato;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.legato.objects.Artist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro_Artista extends AppCompatActivity {
    private static final String APP_LEGATO = "APP_LEGATO";
    FirebaseAuth mAuth;
    EditText txtNome;
    EditText txtIdade;
    EditText txtBiografia;
    EditText txtGenero;

    Button btnSalvar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_artista);

        databaseReference = FirebaseDatabase.getInstance().getReference("this is the path");

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("artists");

        txtNome = findViewById(R.id.txtNomeArtista);
        txtBiografia = findViewById(R.id.txtBiografiaArtista);
        txtIdade = findViewById(R.id.txtIdadeArtista);
        txtGenero = findViewById(R.id.txtGeneroArtista);

        btnSalvar = findViewById(R.id.btnSalvarArtista);

        btnSalvar.setOnClickListener(e -> salvar());

        // Adicione um listener para verificar quando a autenticação é concluída
        mAuth.addAuthStateListener(auth -> {
            if (auth.getCurrentUser() != null) {
                // A autenticação foi concluída, agora você pode obter o token
                String token = auth.getCurrentUser().getIdToken(true).getResult().getToken();
                Log.i(APP_LEGATO, "TOKEN : " + token);
            } else {
                // O usuário não está autenticado, token é nulo
                Log.i(APP_LEGATO, "TOKEN : null");
            }
        });

    }
    private void salvar() {
        Artist c = new Artist();
        
        
    
    // Retrieve text from EditText fields
    String nome = txtNome.getText().toString().trim();
    String biografia = txtBiografia.getText().toString().trim();
    String idadeStr = txtIdade.getText().toString().trim();
    String genero = txtGenero.getText().toString().trim();

    // Check if any field is empty
    if (nome.isEmpty() || biografia.isEmpty() || idadeStr.isEmpty() || genero.isEmpty()) {
        Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        return; // Stop further processing
    }

    // Check if idade contains only digits
    if (!idadeStr.matches("\\d+")) {
        Toast.makeText(getApplicationContext(), "Idade inválida", Toast.LENGTH_SHORT).show();
        return; // Stop further processing
    }

    // Convert idade to integer
    int idade = Integer.parseInt(idadeStr);

    // Create Artist object
    Artist c = new Artist();
    c.setNome(nome);
    c.setBiografia(biografia);
    c.setIdade(idade);
    c.setGenero(genero);

    // Rest of your code remains unchanged...

        
        
        
        
        
        
        
        
        // Adiciona o artista ao nó "users"
        String artistId = databaseReference.push().getKey(); // Cria uma chave única para o artista

        // Verifica se userId não é nulo antes de atribuir ao id do artista
        if (artistId != null) {
            Intent intent = new Intent(Cadastro_Artista.this, ListArtists.class);
            startActivity(intent);
            c.setId(artistId);
        } else {
            // Lidar com a situação em que artistId é nulo (pode ocorrer em condições excepcionais)
            Log.e(APP_LEGATO, "Erro: artistId é nulo");
            return; // Não continue com o processo de salvamento
        }

        DatabaseReference artistRef = databaseReference.child(artistId);

        // Log antes de salvar
        Log.i(APP_LEGATO, "Salvando artista: " + c.getNome()+ ", "+c.getGenero()+", "+c.getBiografia()+", "+c.getIdade());

        artistRef.setValue(c)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Artista cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Falha ao cadastrar artista", Toast.LENGTH_SHORT).show();
                    Log.e(APP_LEGATO, "Erro ao cadastrar artista", e);
                });
    }
}
