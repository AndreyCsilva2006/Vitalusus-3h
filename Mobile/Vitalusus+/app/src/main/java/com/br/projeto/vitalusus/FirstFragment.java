package com.br.projeto.vitalusus;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.br.projeto.vitalusus.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ImageView imagem, btnProximoTutorial;
    private TextView textoTutorial;
    private int num_passoTut = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textoTutorial = view.findViewById(R.id.tutorialDescricao);
        imagem = view.findViewById(R.id.logo);
        btnProximoTutorial = view.findViewById(R.id.btn_proximoTutorial);

        textoTutorial.setText("Bem Vindo(a) ao Vitalusus+ o aplicativo que irá te deixar estruturado(a) saudávelmente de verdade.");
        binding.btnAnteriorTutorial.setColorFilter(ContextCompat.getColor(getContext(), R.color.iconGrey), PorterDuff.Mode.SRC_ATOP);
        imagem.setImageResource(R.drawable.logo);
        btnProximoTutorial.setImageResource(R.drawable.ic_seta0);

        binding.cadastro.setVisibility(View.GONE);
        binding.cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_formCadastro);
            }
        });

        binding.btnProximoTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_passoTut = num_passoTut + 1;
                if (num_passoTut == 1) {
                    textoTutorial.setText("O nosso objetivo é fornecer aos usuários informações precisas e confiáveis sobre nutrição e alimentação saudável.");
                    binding.btnAnteriorTutorial.setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
                if (num_passoTut == 2) {
                    textoTutorial.setText("Seguidas por Treinadores que possuem CREF(Conselho Regional de Educação Física) para desenvolvimento de Videoaulas.");
                }
                if (num_passoTut == 3) {
                    textoTutorial.setText("Uma forma fácil e eficiente de treinar. Cadastre-se agora no botão abaixo.");
                    binding.btnProximoTutorial.setColorFilter(ContextCompat.getColor(getContext(), R.color.iconGrey), PorterDuff.Mode.SRC_ATOP);
                    binding.cadastro.setVisibility(View.VISIBLE);
                }
                // caso dê pra fugir pro 4, retrocede -1 para voltar ao 3.
                if (num_passoTut == 4) {
                    num_passoTut = num_passoTut - 1;
                }
            }
        });

        binding.btnAnteriorTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_passoTut = num_passoTut - 1;
                if (num_passoTut == -1) {
                    num_passoTut = num_passoTut + 1;
                }
                if (num_passoTut == 0) {
                    textoTutorial.setText("Bem Vindo(a) ao Vitalusus+ o aplicativo que irá te deixar estruturado(a) saudávelmente de verdade.");
                    imagem.setImageResource(R.drawable.logo);
                    binding.btnAnteriorTutorial.setColorFilter(ContextCompat.getColor(getContext(), R.color.iconGrey), PorterDuff.Mode.SRC_ATOP);
                }
                if (num_passoTut == 1) {
                    textoTutorial.setText("O nosso objetivo é fornecer aos usuários informações precisas e confiáveis sobre nutrição e alimentação saudável.");
//                    imagem.setImageResource(R.drawable.img_tut1);
                }
                if (num_passoTut == 2) {
                    textoTutorial.setText("Seguidas por Treinadores que possuem CREF(Conselho Regional de Educação Física) para desenvolvimento de Videoaulas.");
                    imagem.setImageResource(R.drawable.logo);
                    binding.btnProximoTutorial.setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}