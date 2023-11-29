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
import com.example.legato.objects.Post;
import com.example.legato.objects.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;
    private List<Post> list;
    private DatabaseReference databaseReference;

    public PostAdapter(Context context, List<Post> list, DatabaseReference databaseReference) {
        this.context = context;
        this.list = list;
        this.databaseReference = databaseReference; // Initialize the DatabaseReference
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Post post = list.get(position);

        holder.Titulo.setText(post.getTitulo());
        holder.Autor.setText(post.getAutor());
        holder.Comunidade.setText(post.getComunidade());
        holder.Postagem.setText(post.getPostagem());
        holder.btnEditPost.setOnClickListener(new View.OnClickListener() {
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
                            .setContentHolder(new ViewHolder(R.layout.post_update_popup))
                            .setExpanded(true, 700)
                            .create();

                    View view = dialogPlus.getHolderView();

                    EditText titulo = view.findViewById(R.id.txtPostagemTituloUpdate);
                    EditText autor = view.findViewById(R.id.txtPostagemAutorUpdate);
                    EditText comunidade = view.findViewById(R.id.txtPostagemComunidadeUpdate);
                    EditText postagem = view.findViewById(R.id.txtPostagemPostagemUpdate);

                    Button btnUpdate = view.findViewById(R.id.btnAtualizarPostagem);

                    titulo.setText(post.getTitulo());
                    autor.setText(post.getAutor());
                    comunidade.setText(post.getComunidade());
                    postagem.setText(post.getPostagem());
                    dialogPlus.show();

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("titulo", titulo.getText().toString());
                            map.put("autor", autor.getText().toString());
                            map.put("comunidade", comunidade.getText().toString());
                            String postKey = list.get(position).getId();

                            FirebaseDatabase.getInstance().getReference("posts")
                                    .child(postKey).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.Titulo.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.Titulo.getContext(), "Error While Updating.", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });

                        }
                    });

                } else {
                    Log.e("UsuarioAdapter", "Error: Unable to get the activity context");
                }
            }
        });

        holder.btnDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost(post.getId());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    private void deletePost(String postId) {
        if (postId != null && !postId.isEmpty()) {
            DatabaseReference postRef = databaseReference.child(postId);

            postRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Falha ao excluir postagem", Toast.LENGTH_SHORT).show();
                            Log.e("PostagemAdapter", "Error deleting post", e);
                        }
                    });
        } else {
            Log.e("PostagemAdapter", "Error: postId is null or empty");
        }
    }
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView Id, Titulo, Autor, Comunidade, Postagem;
        Button btnEditPost, btnDeletePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.txtId);
            Titulo = itemView.findViewById(R.id.txtTituloPostagem);
            Autor = itemView.findViewById(R.id.txtAutorPostagem);
            Comunidade = itemView.findViewById(R.id.txtComunidadePostagem);
            Postagem = itemView.findViewById(R.id.txtPostagemPostagem);
            btnDeletePost = itemView.findViewById(R.id.btnDeletePostagem);
            btnEditPost = itemView.findViewById(R.id.btnEditPostagem);
        }
    }
}