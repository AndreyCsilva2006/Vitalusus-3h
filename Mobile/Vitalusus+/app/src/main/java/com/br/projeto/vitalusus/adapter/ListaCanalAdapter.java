package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;

import java.io.Serializable;
import java.util.List;

public class ListaCanalAdapter extends BaseAdapter implements Serializable {

    private static final long serialVersionUID = 546546546546L;
    private List<Treinador> listaTreinador; // Agora é uma lista de Treinador
    private Context context;
    private LayoutInflater layout;

    public ListaCanalAdapter(List<Treinador> listaTreinador, Context context) {
        this.listaTreinador = listaTreinador;
        this.context = context;
        layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaTreinador.size(); // Número de treinadores
    }

    @Override
    public Object getItem(int i) {
        return listaTreinador.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Canal treinador = listaTreinador.get(i).getCanal();  // Pega o treinador
        Canal canal = treinador.getCanal();           // Pega o canal associado ao treinador

        View v = layout.inflate(R.layout.item_canal, null); // Usa o layout já existente

        TextView txtNomeTreinador = v.findViewById(R.id.tv_nome);
        TextView txtDescricaoTreinador = v.findViewById(R.id.tv_descricao);
        TextView txtSeguidores = v.findViewById(R.id.tv_seguidores);
        ImageView imgFoto = v.findViewById(R.id.iv_imagem);

        // Definindo os valores
        txtNomeTreinador.setText(treinador.getNome());
        txtDescricaoTreinador.setText(treinador.getDescricao());
        txtSeguidores.setText("Seguidores: " + canal.getSeguidores().toString());

        // Decodifica a foto para bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(canal.getFoto(), 0, canal.getFoto().length);

        // Exibe o bitmap na ImageView
        if (bitmap != null) {
            imgFoto.setImageBitmap(bitmap);
        } else {
            imgFoto.setImageResource(R.drawable.ic_launcher_background); // Imagem padrão
        }

        return v;
    }
}
