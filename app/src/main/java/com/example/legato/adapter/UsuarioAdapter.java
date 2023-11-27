package com.example.legato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legato.R;
import com.example.legato.objects.User;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {
    private Context context;
    private List<User> list;

    public UsuarioAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item do usuário aqui (item_usuario.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        User user = list.get(position);

        // Certifique-se de que os IDs correspondem aos IDs no seu layout XML
        holder.Nome.setText(user.getNome());
        holder.Email.setText(user.getEmail());
        holder.Password.setText(user.getSenha());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView Nome, Email, Password;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            Nome = itemView.findViewById(R.id.txtNome);
            Email = itemView.findViewById(R.id.txtEmail);
            Password = itemView.findViewById(R.id.txtPassword);
        }
    }
}
