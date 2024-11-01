package com.br.projeto.vitalusus;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private TextView nomeTextView, emailTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Perfil");
        // Infla o layout da Fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Recupera os dados do Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String usuarioNome = bundle.getString("usuarioNome");
            String usuarioEmail = bundle.getString("usuarioEmail");

            // Exibe os dados ou usa conforme necessário
            nomeTextView = view.findViewById(R.id.txtPerfilNomeAluno);
            emailTextView = view.findViewById(R.id.txtPerfilEmailAluno);
            nomeTextView.setText(usuarioNome);
            emailTextView.setText(usuarioEmail);

            // Aqui você pode usar `usuarioId`, `usuarioNome`, `usuarioEmail` conforme necessário
        }

        return view;
    }
}