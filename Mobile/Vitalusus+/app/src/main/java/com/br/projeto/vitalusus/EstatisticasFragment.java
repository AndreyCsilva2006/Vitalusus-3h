package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.br.projeto.vitalusus.model.Usuario;

public class EstatisticasFragment extends Fragment {

    private EditText etPeso, etAltura, etIdade;
    private RadioGroup rgGenero, rgAtividadeFisica;
    private RadioButton rbMasculino, rbFeminino, rbSedentario, rbModerado, rbIntenso;
    private TextView tvResultado;
    private Button btnCalcular;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas, container, false);

        getActivity().setTitle("Estatísticas");

        etPeso = view.findViewById(R.id.et_peso);
        etAltura = view.findViewById(R.id.et_altura);
        etIdade = view.findViewById(R.id.et_idade);
        rgGenero = view.findViewById(R.id.rg_genero);
        rgAtividadeFisica = view.findViewById(R.id.rg_atividade_fisica);
        rbMasculino = view.findViewById(R.id.rb_masculino);
        rbFeminino = view.findViewById(R.id.rb_feminino);
        rbSedentario = view.findViewById(R.id.rb_sedentario);
        rbModerado = view.findViewById(R.id.rb_moderado);
        rbIntenso = view.findViewById(R.id.rb_intenso);
        tvResultado = view.findViewById(R.id.tv_resultado);
        btnCalcular = view.findViewById(R.id.btn_calcular);

        btnCalcular.setOnClickListener(v -> calcularIMCTMBNDC());

        return view;
    }

    private void calcularIMCTMBNDC() {
        String pesoStr = etPeso.getText().toString();
        String alturaStr = etAltura.getText().toString();
        String idadeStr = etIdade.getText().toString(); // Capturando idade diretamente do EditText
        int generoSelecionadoId = rgGenero.getCheckedRadioButtonId();
        int atividadeFisicaSelecionadaId = rgAtividadeFisica.getCheckedRadioButtonId();

        if (!pesoStr.isEmpty() && !alturaStr.isEmpty() && !idadeStr.isEmpty() && generoSelecionadoId != -1 && atividadeFisicaSelecionadaId != -1) {

            try {
                float peso = Float.parseFloat(pesoStr);
                float altura = Float.parseFloat(alturaStr) / 100; // Convertendo de cm para metros
                int idade = Integer.parseInt(idadeStr); // Obtendo a idade do EditText

                // Calcula o IMC
                float imc = peso / (altura * altura);

                // Calcula a TMB
                double tmb;
                if (generoSelecionadoId == R.id.rb_masculino) {
                    tmb = 66.5 + (13.75 * peso) + (5.003 * (altura * 100)) - (6.75 * idade);
                } else {
                    tmb = 655.1 + (9.563 * peso) + (1.850 * (altura * 100)) - (4.676 * idade);
                }

                // Calcula a NDC com base no nível de atividade física
                double ndc = 0;
                if (atividadeFisicaSelecionadaId == R.id.rb_sedentario) {
                    ndc = tmb * 1.2; // Nenhuma atividade física
                } else if (atividadeFisicaSelecionadaId == R.id.rb_moderado) {
                    ndc = tmb * 1.55; // Atividade física moderada
                } else if (atividadeFisicaSelecionadaId == R.id.rb_intenso) {
                    ndc = tmb * 1.725; // Atividade física intensa
                }

                // Recomendações para perda de peso e manutenção
                double caloriasPerdaPeso = ndc - 500;
                double caloriasManutencaoPeso = ndc;

                // Monta o resultado para exibição
                String resultado = "Seu IMC é: " + String.format("%.2f", imc) + "\n";
                resultado += "Sua TMB é: " + String.format("%.2f", tmb) + " kcal/dia\n";
                resultado += "Sua NDC é: " + String.format("%.2f", ndc) + " kcal/dia\n";
                resultado += "Calorias para perder peso (redução de 500 kcal): " + String.format("%.2f", caloriasPerdaPeso) + " kcal/dia\n";
                resultado += "Calorias para manter o peso: " + String.format("%.2f", caloriasManutencaoPeso) + " kcal/dia\n";
                resultado += "Idade: " + idade + "\n";

                // Classificação do IMC
                if (imc < 18.5) {
                    resultado += "Classificação: Abaixo do peso";
                } else if (imc >= 18.5 && imc < 24.9) {
                    resultado += "Classificação: Peso normal";
                } else if (imc >= 25 && imc < 29.9) {
                    resultado += "Classificação: Sobrepeso";
                } else {
                    resultado += "Classificação: Obesidade";
                }

                tvResultado.setText(resultado);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
