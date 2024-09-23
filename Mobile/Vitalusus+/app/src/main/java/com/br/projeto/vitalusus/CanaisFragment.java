//package com.br.projeto.vitalusus;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.br.projeto.vitalusus.R;
//import com.br.projeto.vitalusus.adapter.ListaCanalAdapter;
//import com.br.projeto.vitalusus.model.Canal;
//import com.br.projeto.vitalusus.model.Treinador;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CanaisFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private ListaCanalAdapter adapter;
//    private List<Treinador> listaTreinador;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_canais, container, false);
//
//        recyclerView = view.findViewById(R.id.recyclerViewCanaisTreinadores);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        listaTreinador = new ArrayList<>();
//        inicializarTreinadores();
//
//        adapter = new ListaCanalAdapter(listaTreinador, getContext());
//        recyclerView.setAdapter(adapter);
//
//        adicionarNovoTreinador();
//
//        return view;
//    }
//
//    private void inicializarTreinadores() {
//        listaTreinador.add(createTreinador("João", "Treinador de musculação", 1, "Canal do João", BigInteger.valueOf(1500)));
//        listaTreinador.add(createTreinador("Maria", "Treinadora de yoga", 2, "Canal da Maria", BigInteger.valueOf(20000)));
//    }
//
//    private Treinador createTreinador(String nome, String descricao, int canalId, String canalNome, BigInteger seguidores) {
//        Canal canal = new Canal();
//        canal.setId(canalId);
//        canal.setNome(canalNome);
//        canal.setSeguidores(seguidores);
//        canal.setFoto(null);
//
//        return new Treinador(nome, descricao, canal);
//    }
//
//    private void adicionarNovoTreinador() {
//        Treinador novoTreinador = createTreinador("Pedro", "Treinador de crossfit", 3, "Canal do Pedro", BigInteger.valueOf(10000));
//        listaTreinador.add(novoTreinador);
//        adapter.notifyDataSetChanged();
//    }
//}
