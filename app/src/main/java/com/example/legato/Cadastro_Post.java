package com.example.legato;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.legato.objects.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro_Post extends AppCompatActivity {
    private static final String APP_LEGATO = "APP_LEGATO";
    FirebaseAuth mAuth;
    EditText txtTitulo;
    EditText txtAutor;
    EditText txtComunidade;
    EditText txtPostagem;
    Button btnSalvar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_post);

        databaseReference = FirebaseDatabase.getInstance().getReference("this is the path");

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        txtTitulo = findViewById(R.id.txtTituloPost);
        txtComunidade = findViewById(R.id.txtComunidadePost);
        txtAutor = findViewById(R.id.txtAutorPost);
        txtPostagem = findViewById(R.id.txtPostagemPost);
        btnSalvar = findViewById(R.id.btnSalvarPost);

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
        Post c = new Post();
        c.setTitulo(txtTitulo.getText().toString());
        c.setAutor(txtAutor.getText().toString());
        c.setPostagem(txtPostagem.getText().toString());
        c.setComunidade(txtComunidade.getText().toString());
        String postId = databaseReference.push().getKey();

        if (postId != null) {
            Intent intent = new Intent(Cadastro_Post.this, ListPost.class);
            startActivity(intent);
            c.setId(postId);
        } else {
            Log.e(APP_LEGATO, "Erro: postId é nulo");
            return;
        }

        DatabaseReference postRef = databaseReference.child(postId);

        Log.i(APP_LEGATO, "Salvando postagem: " + c.getTitulo()+ ", "+c.getAutor()+", "+c.getPostagem()+", "+c.getComunidade());

        postRef.setValue(c)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Postagem cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Falha ao cadastrar postagem", Toast.LENGTH_SHORT).show();
                    Log.e(APP_LEGATO, "Erro ao cadastrar postagem", e);
                });
    }
}
