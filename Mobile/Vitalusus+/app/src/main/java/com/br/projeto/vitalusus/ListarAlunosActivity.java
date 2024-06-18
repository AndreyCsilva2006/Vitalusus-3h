package com.br.projeto.vitalusus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {

    private ListView listView;
    private AlunoDAO dao;
    // listar
    private List<Aluno> alunos;
    // só alunos que forem consultados
    private List<Aluno> alunosFiltrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        listView = findViewById(R.id.lista_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
//        alunosFiltrados.addAll(alunos);
        // É necessario criar um adapatador para ter uma lista especifica, nesse caso usaremos o do Android que tem como padrão.
        // Array adapter q pega os dados e joga pra ser mostrado na tela.
//        AlunoAdapter adaptador = new AlunoAdapter(this, alunos); ADPTADOR EXTERNO
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listView.setAdapter(adaptador);

        // Registra que o menu contexto tem que aparecer nos itens da listView (lista)
        registerForContextMenu(listView);
    }

    // menu contexto
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraAluno(String nome) {
        alunos.clear();
        for(Aluno a : alunos) {
            if (a.getNome().toLowerCase().contains(nome.toLowerCase())) {
                alunos.add(a);
            }
        }
        listView.invalidateViews();
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listView.setAdapter(adaptador);
    }

    // Excluir item com menu contexto
    public void excluir(MenuItem item){
        // informando qual item o usuario clicou para poder excluir o escolhido
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Aluno alunoExcluir = alunos.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Realmente deseja excluir o aluno?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alunos.remove(alunoExcluir);
                dao.excluir(alunoExcluir);
                listView.invalidateViews();
            }
        }).create();
        dialog.show();
    }

    public void cadastrar(MenuItem item) {
        // Intent = intenção
        // this = tela atual
        // ou seja (this, FormCadastro) this (essa tela) vai ir para FormCadastro
        Intent cad = new Intent(this, FormCadastro.class);
        startActivity(cad);
    }

    // método atualizar item da lista
    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Aluno alunoAtualizar = alunos.get(menuInfo.position);
        Intent it = new Intent (this, FormCadastro.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);
    }

    // Override é usado para modificar um método já existente
    // Modificando um método para atualizar a lista dos Alunos e dos consultados(alunosFiltrados)
    @Override
    public void onResume() {
        super.onResume();
        alunos = dao.obterTodos();
//        alunosFiltrados.clear();
//        alunosFiltrados.addAll(alunos);
        listView.invalidateViews();
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listView.setAdapter(adaptador);
    }
}

