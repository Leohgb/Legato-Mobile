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
import com.example.legato.objects.Artist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ArtistaViewHolder> {
    private Context context;
    private List<Artist> list;
    private DatabaseReference databaseReference;

    public ArtistaAdapter(Context context, List<Artist> list, DatabaseReference databaseReference) {
        this.context = context;
        this.list = list;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public ArtistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artistas, parent, false);
        return new ArtistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistaAdapter.ArtistaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Artist artist = list.get(position);

        holder.Nome.setText(artist.getNome());
        holder.Idade.setText(String.valueOf(artist.getIdade()));
        holder.Biografia.setText(artist.getBiografia());
        holder.Genero.setText(artist.getGenero());
        holder.btnEditArtist.setOnClickListener(new View.OnClickListener() {
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
                            .setContentHolder(new ViewHolder(R.layout.artist_update_popup))
                            .setExpanded(true, 700)
                            .create();

                    View view = dialogPlus.getHolderView();

                    EditText nome = view.findViewById(R.id.txtArtistaNomeUpdate);
                    EditText idade = view.findViewById(R.id.txtArtistaIdadeUpdate);
                    EditText genero = view.findViewById(R.id.txtArtistaGenero);
                    EditText biografia = view.findViewById(R.id.txtArtistaBiografia);

                    Button btnUpdate = view.findViewById(R.id.btnAtualizarArtista);

                    nome.setText(artist.getNome());
                    idade.setText(String.valueOf(artist.getIdade()));
                    biografia.setText(artist.getBiografia());
                    genero.setText(artist.getGenero());

                    dialogPlus.show();

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("nome", nome.getText().toString());
                            map.put("idade", Integer.parseInt(idade.getText().toString()));
                            map.put("biografia", biografia.getText().toString());
                            map.put("genero", genero.getText().toString());

                            String artistKey = list.get(position).getId();

                            FirebaseDatabase.getInstance().getReference("artists")
                                    .child(artistKey).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.Nome.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.Nome.getContext(), "Error While Updating.", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });

                        }
                    });

                } else {
                    Log.e("ArtistaAdapter", "Error: Unable to get the activity context");
                }
            }
        });

        holder.btnDeleteArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(artist.getId()); 
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    private void deleteArtist(String artistId) {
        if (artistId != null && !artistId.isEmpty()) {
            DatabaseReference artistRef = databaseReference.child(artistId);

            artistRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Artista excluído com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Falha ao excluir artista", Toast.LENGTH_SHORT).show();
                            Log.e("ArtistaAdapter", "Error deleting Artist", e);
                        }
                    });
        } else {
            Log.e("ArtistaAdapter", "Error: artistId is null or empty");
        }
    }
    public static class ArtistaViewHolder extends RecyclerView.ViewHolder {
        TextView Id, Nome, Biografia, Idade, Genero;
        Button btnEditArtist, btnDeleteArtist;

        public ArtistaViewHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.txtIdArtist);
            Nome = itemView.findViewById(R.id.txtNomeArtist);
            Biografia = itemView.findViewById(R.id.txtBiografiaArtist);
            Idade = itemView.findViewById(R.id.txtIdadeArtist);
            Genero = itemView.findViewById(R.id.txtGeneroArtist);
            btnDeleteArtist = itemView.findViewById(R.id.btnDeleteArtist);
            btnEditArtist = itemView.findViewById(R.id.btnEditArtist);
        }
    }
}
