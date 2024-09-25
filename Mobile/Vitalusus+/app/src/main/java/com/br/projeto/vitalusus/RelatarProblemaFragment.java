package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RelatarProblemaFragment extends Fragment {

    private EditText editFormSuporte;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout para o fragmento
        View view = inflater.inflate(R.layout.fragment_relatarproblema, container, false);

        // Obtém referência ao EditText
        editFormSuporte = view.findViewById(R.id.editFormSuporte);

        getActivity().setTitle("Relatar um problema");

        // Configura o botão para chamar o método enviar quando clicado
        View btnEnviar = view.findViewById(R.id.btnFormSuporteEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });

        return view;
    }

    // Método chamado quando o botão é clicado
    public void enviar() {
        // Obtém o texto do EditText
        String texto = editFormSuporte.getText().toString();

        // Verifica se o campo não está vazio
        if (!texto.isEmpty()) {
            // Aqui você pode adicionar a lógica para processar o texto, como enviar para um servidor
            // Exemplo: Toast para mostrar uma mensagem simples
            Toast.makeText(getActivity(), "Mensagem enviada: " + texto, Toast.LENGTH_SHORT).show();





        } else {
            // Informa ao usuário que o campo está vazio
            Toast.makeText(getActivity(), "Por favor, preencha o campo de mensagem.", Toast.LENGTH_SHORT).show();
        }
    }
}