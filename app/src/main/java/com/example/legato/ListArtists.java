package com.example.legato;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legato.adapter.ArtistaAdapter;
import com.example.legato.objects.Artist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListArtists extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ArtistaAdapter artistaAdapter;
    ArrayList<Artist> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artistas);

        recyclerView = findViewById(R.id.artistsList);
        database = FirebaseDatabase.getInstance().getReference("artists");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        artistaAdapter = new ArtistaAdapter(this, list,database);
        recyclerView.setAdapter(artistaAdapter);

        // Adicionando um divisor entre os itens da lista (opcional)
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();  // Limpa a lista antes de adicionar novos dados
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Artist artist = dataSnapshot.getValue(Artist.class);
                    list.add(artist);
                }
                artistaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}