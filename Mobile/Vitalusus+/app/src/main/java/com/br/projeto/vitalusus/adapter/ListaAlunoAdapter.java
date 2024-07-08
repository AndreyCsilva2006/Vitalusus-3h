package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Aluno;

import java.io.Serializable;
import java.util.List;

public class ListaAlunoAdapter extends BaseAdapter implements Serializable {

    private static final long serialVersionUID = 546546546546L;
    private List<Aluno> listaAluno;
    private Context context;
    private LayoutInflater layout;

    public ListaAlunoAdapter(List<Aluno> listaAluno, Context context) {
        this.listaAluno = listaAluno;
        this.context = context;
        layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaAluno.size();
    }

    @Override
    public Aluno getItem(int i) {
        return listaAluno.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Aluno alu = listaAluno.get(i);
        // Transformando um padrão para a listview
        View v = layout.inflate(R.layout.item_aluno, null);

        TextView txtId = v.findViewById(R.id.txtConsultaAlunoId);
        TextView txtNome = v.findViewById(R.id.txtConsultaAlunoNome);
        TextView txtEmail = v.findViewById(R.id.txtConsultaAlunoEmail);
        TextView txtSenha = v.findViewById(R.id.txtConsultaAlunoSenha);

        txtId.setText(alu.getId().toString());
        txtNome.setText(alu.getNome());
        txtEmail.setText(alu.getEmail().toString());
        txtSenha.setText(alu.getSenha().toString());

        return v;
    }
}
