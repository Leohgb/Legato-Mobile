package com.example.legato;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legato.objects.Artist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BottomMenu extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.menu_bottom, container, false);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav_menu);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.btnHome) {
                startActivity(new Intent(getActivity(), Home.class));
                return true;
            } else if (itemId == R.id.btnBusca) {
                // Faça algo relacionado à busca
                return true;
            } else if (itemId == R.id.btnComunidades) {
                startActivity(new Intent(getActivity(), Comunidade.class));
                return true;
            } else if (itemId == R.id.btnMais) {
                // Faça algo relacionado a "Mais"
                return true;
            } else {
                return false;
            }
        });

        return view;
    }

    public static class Cadastro_Music extends AppCompatActivity {
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

            txtNome = findViewById(R.id.txtNomeMusic);
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
            c.setNome(txtNome.getText().toString());
            c.setBiografia(txtBiografia.getText().toString());
            c.setIdade(Integer.parseInt(txtIdade.getText().toString()));
            c.setGenero(txtGenero.getText().toString());
            // Adiciona o artista ao nó "users"
            String artistId = databaseReference.push().getKey(); // Cria uma chave única para o artista

            // Verifica se userId não é nulo antes de atribuir ao id do artista
            if (artistId != null) {
                Intent intent = new Intent(Cadastro_Music.this, ListArtists.class);
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
}

