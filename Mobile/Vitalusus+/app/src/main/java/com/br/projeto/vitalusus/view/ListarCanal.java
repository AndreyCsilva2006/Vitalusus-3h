package com.br.projeto.vitalusus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.adapter.ListaCanalAdapter;
import com.br.projeto.vitalusus.dao.CanalDAO;
import com.br.projeto.vitalusus.model.Canal;

import java.util.List;

public class ListarCanal extends AppCompatActivity {

    EditText editPesquisa;
    Button btnPesquisar;
    ListView listView;
    ListaCanalAdapter adapter;
    List<Canal> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_canal);

        editPesquisa = findViewById(R.id.editConsultaCanalPesquisa);
        btnPesquisar = findViewById(R.id.btnConsultaCanalPesquisar);
        listView = findViewById(R.id.listViewConsultaCanal);

        preenche("");

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preenche(editPesquisa.getText().toString());
            }
        });

        // Quando clicar em algum item da Lista de Canais.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
//                Intent intent = new Intent(ListarCanal.this, ActivityAluno.class);
                // retorna o Canal
                Canal c = (Canal) adapterView.getItemAtPosition(i);
//                intent.putExtra("canal", c.getId());
//                startActivity(intent);
            }
        });
    }

    private void preenche(String busca) {
        CanalDAO dao = new CanalDAO();
        // busca pesquisar
        if (busca.isEmpty()) {
            lista = dao.getAll();
        } else {
            lista = dao.getAll(busca);
        }

        adapter = new ListaCanalAdapter(lista, this);
        listView.setAdapter(adapter);
    }

}