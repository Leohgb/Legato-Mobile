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

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {
    private Context context;
    private List<User> list;
    private DatabaseReference databaseReference;

    public UsuarioAdapter(Context context, List<User> list, DatabaseReference databaseReference) {
        this.context = context;
        this.list = list;
        this.databaseReference = databaseReference; // Initialize the DatabaseReference
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = list.get(position);

        holder.Nome.setText(user.getNome());
        holder.Email.setText(user.getEmail());
        holder.Password.setText(user.getSenha());
        holder.btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the activity context, not the context from the View
                Context activityContext = null;

                if (v.getContext() instanceof Activity) {
                    activityContext = (Activity) v.getContext();
                } else if (v.getContext() instanceof ContextThemeWrapper) {
                    activityContext = ((ContextThemeWrapper) v.getContext()).getBaseContext();
                }

                if (activityContext != null) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(activityContext)
                            .setContentHolder(new ViewHolder(R.layout.user_update_popup))
                            .setExpanded(true, 700)
                            .create();

                    View view = dialogPlus.getHolderView();

                    EditText nome = view.findViewById(R.id.txtNomeUpdate);
                    EditText email = view.findViewById(R.id.txtEmailUpdate);
                    EditText password = view.findViewById(R.id.txtUpdatePassword);

                    Button btnUpdate = view.findViewById(R.id.btnAtualizar);

                   nome.setText(user.getNome());
                   email.setText(user.getEmail());
                   password.setText(user.getSenha());

                    dialogPlus.show();

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("nome", nome.getText().toString());
                            map.put("email", email.getText().toString());
                            map.put("senha", password.getText().toString());
                            String userKey = list.get(position).getId();

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(userKey).updateChildren(map)
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
                    Log.e("UsuarioAdapter", "Error: Unable to get the activity context");
                }
            }
        });

        holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to delete the user
                deleteUser(user.getId()); // Pass the user ID to the deleteUser method
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    private void deleteUser(String userId) {
        // Make sure userId is not null or empty
        if (userId != null && !userId.isEmpty()) {
            DatabaseReference userRef = databaseReference.child(userId);

            userRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Falha ao excluir usuário", Toast.LENGTH_SHORT).show();
                            Log.e("UsuarioAdapter", "Error deleting user", e);
                        }
                    });
        } else {
            // Handle the case where userId is null or empty
            Log.e("UsuarioAdapter", "Error: userId is null or empty");
        }
    }
    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView Id, Nome, Email, Password;
        Button btnEditUser, btnDeleteUser;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.txtId);
            Nome = itemView.findViewById(R.id.txtNome);
            Email = itemView.findViewById(R.id.txtEmail);
            Password = itemView.findViewById(R.id.txtPassword);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
            btnEditUser = itemView.findViewById(R.id.btnEditUser);
        }
    }
}
