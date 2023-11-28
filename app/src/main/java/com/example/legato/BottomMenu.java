package com.example.legato;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.content.Intent;


public class BottomMenu extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.menu_bottom, container, false);


        Button btnHome = view.findViewById(R.id.btnHome);
        Button btnBusca = view.findViewById(R.id.btnBusca);
        Button btnComunidade = view.findViewById(R.id.btnComunidade);
        Button btnMais = view.findViewById(R.id.btnMais);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BottomMenu", "Botão Home");

                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);

            }
        });


        btnBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BottomMenu", "Botão Busca");

            }
        });

        btnComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exemplo: Iniciar uma nova atividade
                Log.d("BottomMenu", "Botão Comunidade");
                Intent intent = new Intent(getActivity(), Comunidade.class);
                startActivity(intent);
            }
        });

        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BottomMenu", "Botão Mais");
            }
        });

        return view;
    }
}
