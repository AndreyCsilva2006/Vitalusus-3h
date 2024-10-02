package com.br.projeto.vitalusus;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

public class ConfiguracoesFragment extends Fragment {

    private SwitchCompat switchModoClaro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracoes, container, false);

        // Encontre o Switch
        switchModoClaro = view.findViewById(R.id.SwitchModoClaro);

        // Carregue a preferência do tema
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);

        // Verifica o tema atual do sistema
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            switchModoClaro.setChecked(false);  // Modo escuro ativo
        } else {
            switchModoClaro.setChecked(true);   // Modo claro ativo
        }

        // Define o comportamento do Switch para alterar o tema
        switchModoClaro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    // Modo claro
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode", false);
                } else {
                    // Modo escuro
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode", true);
                }
                editor.apply();
                getActivity().recreate(); // Recria a atividade para aplicar o novo tema
            }
        });

        return view;
    }
}
