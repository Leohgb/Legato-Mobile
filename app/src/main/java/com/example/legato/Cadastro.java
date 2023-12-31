package com.example.legato;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.legato.objects.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Cadastro extends AppCompatActivity {
    private static final String APP_LEGATO = "APP_LEGATO";
    FirebaseAuth mAuth;
    List<User> lista = new ArrayList<>();

    EditText txtNome;
    EditText txtEmail;
    EditText txtPassword;

    Button btnSalvar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_l);

        Button btnCadastro = findViewById(R.id.id_cadastrar_b);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        txtNome = findViewById(R.id.id_nome_pt);
        txtEmail = findViewById(R.id.id_email_pt);
        txtPassword = findViewById(R.id.id_senha_pt);

        btnSalvar = findViewById(R.id.id_cadastrar_b);

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
        User c = new User();
        c.setNome(txtNome.getText().toString());
        c.setEmail(txtEmail.getText().toString());
        c.setSenha(txtPassword.getText().toString());

        // Adiciona o usuário ao nó "users"
        String userId = databaseReference.push().getKey(); // Cria uma chave única para o usuário

        // Verifica se userId não é nulo antes de atribuir ao id do usuário
        if (userId != null) {
            Intent intent = new Intent(Cadastro.this, ListUsers.class);
            startActivity(intent);
            c.setId(userId);
        } else {
            // Lidar com a situação em que userId é nulo (pode ocorrer em condições excepcionais)
            Log.e(APP_LEGATO, "Erro: userId é nulo");
            return; // Não continue com o processo de salvamento
        }

        DatabaseReference userRef = databaseReference.child(userId);

        // Log antes de salvar
        Log.i(APP_LEGATO, "Salvando usuário: " + c.getNome() + ", " + c.getEmail());

        userRef.setValue(c)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Falha ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                    Log.e(APP_LEGATO, "Erro ao cadastrar usuário", e);
                });
    }

}

