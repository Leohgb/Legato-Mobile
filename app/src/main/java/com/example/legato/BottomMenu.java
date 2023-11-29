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

import com.google.android.material.bottomnavigation.BottomNavigationView;


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
}

