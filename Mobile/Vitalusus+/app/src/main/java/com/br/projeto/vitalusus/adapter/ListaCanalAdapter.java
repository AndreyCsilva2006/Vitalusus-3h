package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;

import java.io.Serializable;
import java.util.List;

public class ListaCanalAdapter extends BaseAdapter implements Serializable {

    private static final long serialVersionUID = 546546546546L;
    private List<Canal> listaCanal;
    private Context context;
    private LayoutInflater layout;

    public ListaCanalAdapter(List<Canal> listaCanal, Context context) {
        this.listaCanal = listaCanal;
        this.context = context;
        layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaCanal.size();
    }

    @Override
    public Object getItem(int i) {
        return listaCanal.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Canal canal = listaCanal.get(i);
        View v = layout.inflate(R.layout.item_canal, null);

        TextView txtNome = v.findViewById(R.id.txtConsultaCanalNome);
        TextView txtVisualizacoes = v.findViewById(R.id.txtConsultaCanalVisualizacoes);
        TextView txtSeguidores = v.findViewById(R.id.txtConsultaCanalSeguidores);
        ImageView imgFoto = v.findViewById(R.id.imgConsultaCanalFoto);

        txtNome.setText(canal.getNome().toString());
        txtVisualizacoes.setText(canal.getVisualizacoes().toString());
        txtSeguidores.setText(canal.getSeguidores().toString());

        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.logo);
        imgFoto.setImageDrawable(drawable);

        return v;
    }
}
