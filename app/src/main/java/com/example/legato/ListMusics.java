package com.example.legato;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legato.adapter.MusicAdapter;
import com.example.legato.objects.Music;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListMusics extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MusicAdapter musicAdapter;
    ArrayList<Music> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musics);

        recyclerView = findViewById(R.id.musicsList);
        database = FirebaseDatabase.getInstance().getReference("musics"); // Update the database reference
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        musicAdapter = new MusicAdapter(this, list, database); // Update the adapter
        recyclerView.setAdapter(musicAdapter);

        // Add a divider between list items (optional)
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();  // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Music music = dataSnapshot.getValue(Music.class);
                    list.add(music);
                }
                musicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
