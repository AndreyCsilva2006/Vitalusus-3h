package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EstatisticasFragment extends Fragment {

    private EditText etPeso, etAltura;
    private TextView tvResultado;
    private Button btnCalcular;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas, container, false);

        etPeso = view.findViewById(R.id.et_peso);
        etAltura = view.findViewById(R.id.et_altura);
        tvResultado = view.findViewById(R.id.tv_resultado);
        btnCalcular = view.findViewById(R.id.btn_calcular);

        btnCalcular.setOnClickListener(v -> calcularIMC());

        return view;
    }

    private void calcularIMC() {
        String pesoStr = etPeso.getText().toString();
        String alturaStr = etAltura.getText().toString();

        if (!pesoStr.isEmpty() && !alturaStr.isEmpty()) {
            try {
                float peso = Float.parseFloat(pesoStr);
                float altura = Float.parseFloat(alturaStr) / 100; // Convertendo de cm para metros
                float imc = peso / (altura * altura);

                String resultado = "Seu IMC é: " + String.format("%.2f", imc) + "\n";

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
