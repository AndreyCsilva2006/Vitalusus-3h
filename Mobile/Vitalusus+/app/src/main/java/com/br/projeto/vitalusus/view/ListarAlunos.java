//package com.br.projeto.vitalusus.view;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.br.projeto.vitalusus.FormLogin;
//import com.br.projeto.vitalusus.R;
////import com.br.projeto.vitalusus.adapter.ListaAlunoAdapter;
////import com.br.projeto.vitalusus.dao.AlunoDAO;
//import com.br.projeto.vitalusus.model.Aluno;
//
//import java.util.List;
//
//public class ListarAlunos extends AppCompatActivity {
//
//    EditText editPesquisa;
//    Button btnPesquisar, btnCadastro;
//    ListView listView;
////    ListaAlunoAdapter adapter;
//    List<Aluno> lista;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_listar_alunos);
//
//        editPesquisa = findViewById(R.id.editConsultaAlunoPesquisa);
//        btnPesquisar = findViewById(R.id.btnConsultaAlunoPesquisar);
//        btnCadastro = findViewById(R.id.btnConsultaAlunoCadastro);
//        listView = findViewById(R.id.listViewConsultaAluno);
//
//        preenche("");
//
//        btnPesquisar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                preenche(editPesquisa.getText().toString());
//            }
//        });
//
//        btnCadastro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(ListarAlunos.this, ActivityAluno.class);
////                startActivity(intent);
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
////                Intent intent = new Intent(ListarAlunos.this, ActivityAluno.class);
//                // retorna o Aluno
//                Aluno a = (Aluno) adapterView.getItemAtPosition(i);
////                intent.putExtra("aluno", a.getId());
////                startActivity(intent);
//            }
//        });
//    }
//
//    private void preenche(String busca) {
////        AlunoDAO dao = new AlunoDAO();
//        // busca pesquisar
//        if (busca.isEmpty()) {
////            lista = dao.getAll();
//        } else {
////            lista = dao.getAll(busca);
//        }
//
//        adapter = new ListaAlunoAdapter(lista, this);
//        listView.setAdapter(adapter);
//    }
//}