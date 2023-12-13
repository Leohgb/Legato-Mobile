package com.example.legato.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legato.R;
import com.example.legato.objects.Music;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicaViewHolder> {
    private Context context;
    private List<Music> list;
    private DatabaseReference databaseReference;

    public MusicAdapter(Context context, List<Music> list, DatabaseReference databaseReference) {
        this.context = context;
        this.list = list;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musicas, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Music music = list.get(position);

        holder.musicName.setText(music.getMusicName());
        holder.genre.setText(music.getGenre());
        holder.album.setText(music.getAlbum());
        holder.composer.setText(music.getComposer());

        holder.btnEditMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context activityContext = null;

                if (v.getContext() instanceof Activity) {
                    activityContext = (Activity) v.getContext();
                } else if (v.getContext() instanceof ContextThemeWrapper) {
                    activityContext = ((ContextThemeWrapper) v.getContext()).getBaseContext();
                }

                if (activityContext != null) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(activityContext)
                            .setContentHolder(new ViewHolder(R.layout.music_update_popup))
                            .setExpanded(true, 700)
                            .create();

                    View view = dialogPlus.getHolderView();

                    EditText musicName = view.findViewById(R.id.txtMusicNameUpdate);
                    EditText genre = view.findViewById(R.id.txtMusicGenreUpdate);
                    EditText album = view.findViewById(R.id.txtMusicAlbumUpdate);
                    EditText composer = view.findViewById(R.id.txtMusicComposerUpdate);

                    Button btnUpdate = view.findViewById(R.id.btnUpdateMusic);

                    musicName.setText(music.getMusicName());
                    genre.setText(music.getGenre());
                    album.setText(music.getAlbum());
                    composer.setText(music.getComposer());

                    dialogPlus.show();

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("musicName", musicName.getText().toString());
                            map.put("genre", genre.getText().toString());
                            map.put("album", album.getText().toString());
                            map.put("composer", composer.getText().toString());

                            String musicKey = list.get(position).getId_music();

                            FirebaseDatabase.getInstance().getReference("musics")
                                    .child(musicKey).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.musicName.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.musicName.getContext(), "Error While Updating.", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });

                        }
                    });

                } else {
                    Log.e("MusicAdapter", "Error: Unable to get the activity context");
                }
            }
        });

        holder.btnDeleteMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMusic(music.getId_music());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void deleteMusic(String musicId) {
        if (musicId != null && !musicId.isEmpty()) {
            DatabaseReference musicRef = databaseReference.child(musicId);

            musicRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Música excluída com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Falha ao excluir música", Toast.LENGTH_SHORT).show();
                            Log.e("MusicAdapter", "Error deleting Music", e);
                        }
                    });
        } else {
            Log.e("MusicAdapter", "Error: musicId is null or empty");
        }
    }

    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView musicName, genre, album, composer;
        Button btnEditMusic, btnDeleteMusic;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txtMusicName);
            genre = itemView.findViewById(R.id.txtMusicGenre);
            album = itemView.findViewById(R.id.txtMusicAlbum);
            composer = itemView.findViewById(R.id.txtMusicComposer);
            btnDeleteMusic = itemView.findViewById(R.id.btnDeleteMusic);
            btnEditMusic = itemView.findViewById(R.id.btnEditMusic);
        }
    }
}
