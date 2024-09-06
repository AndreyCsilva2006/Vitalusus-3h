package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.adapter.ListaCanalAdapter;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CanaisFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListaCanalAdapter adapter;
    private List<Treinador> listaTreinador; // Lista de treinadores

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_canais, container, false);

        // Inicializando o RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCanaisTreinadores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicialize a lista de treinadores
        listaTreinador = new ArrayList<>();

        // Adiciona alguns exemplos de treinadores e seus canais
        Canal canal1 = new Canal();
        canal1.setId(1);
        canal1.setNome("Canal do João");
        canal1.setSeguidores(BigInteger.valueOf(1500));
        canal1.setFoto(null); // Pode ser uma imagem real em byte[]

        Treinador treinador1 = new Treinador("João", "Treinador de musculação", canal1);
        listaTreinador.add(treinador1);

        Canal canal2 = new Canal();
        canal2.setId(2);
        canal2.setNome("Canal da Maria");
        canal2.setSeguidores(BigInteger.valueOf(20000));
        canal2.setFoto(null); // Pode ser uma imagem real em byte[]

        Treinador treinador2 = new Treinador("Maria", "Treinadora de yoga", canal2);
        listaTreinador.add(treinador2);

        // Inicializa o adapter com a lista de treinadores
        adapter = new ListaCanalAdapter(listaTreinador, getContext());
        recyclerView.setAdapter(adapter);

        // Adicionando um novo treinador e seu canal
        adicionarNovoTreinador();

        return view;
    }

    // Método para adicionar um novo treinador dinamicamente
    private void adicionarNovoTreinador() {
        Canal novoCanal = new Canal();
        novoCanal.setId(3);
        novoCanal.setNome("Canal do Pedro");
        novoCanal.setSeguidores(BigInteger.valueOf(10000));
        novoCanal.setFoto(null); // Pode ser uma imagem real em byte[]

        Treinador novoTreinador = new Treinador("Pedro", "Treinador de crossfit", novoCanal);
        listaTreinador.add(novoTreinador);

        // Notifica o adapter sobre a mudança nos dados
        adapter.notifyDataSetChanged();
    }
}
