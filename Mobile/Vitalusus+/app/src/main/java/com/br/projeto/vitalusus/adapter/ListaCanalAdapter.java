package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;

import java.util.List;

public class ListaCanalAdapter extends RecyclerView.Adapter<ListaCanalAdapter.ViewHolder> {

    private List<Treinador> listaTreinador; // Agora é uma lista de Treinador
    private List<Canal> listaCanal; // Agora é uma lista de Treinador
    private Context context;
    private LayoutInflater layout;

    public ListaCanalAdapter(List<Treinador> listaTreinador, Context context) {
        this.listaTreinador = listaTreinador;
        this.context = context;
        layout = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Treinador treinador = listaTreinador.get(position);  // Pega o treinador
//        Treinador treinador = listaTreinador.get(position);
        Canal canal = treinador.getCanal();           // Pega o canal associado ao treinador
        // Configure os dados do treinador no ViewHolder
        holder.txtNomeTreinador.setText(treinador.getNome());
        holder.txtDescricaoTreinador.setText(treinador.getDescricao());
        holder.txtSeguidores.setText("Seguidores: " + canal.getSeguidores().toString());

    }

    public int getItemCount() {
        return listaTreinador.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Defina os elementos de UI do item_treinador.xml
        TextView txtNomeTreinador;
        TextView txtDescricaoTreinador;
        TextView txtSeguidores;
        ImageView imgFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicialize os elementos de UI
            TextView txtNomeTreinador = itemView.findViewById(R.id.tv_nome);
            TextView txtDescricaoTreinador = itemView.findViewById(R.id.tv_descricao);
            TextView txtSeguidores = itemView.findViewById(R.id.tv_seguidores);
            ImageView imgFoto = itemView.findViewById(R.id.iv_imagem);
        }
    }

    // acho que é pra lista
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        // Decodifica a foto para bitmap
////        Bitmap bitmap = BitmapFactory.decodeByteArray(canal.getFoto(), 0, canal.getFoto().length);
//
//        // Exibe o bitmap na ImageView
//        if (bitmap != null) {
//            imgFoto.setImageBitmap(bitmap);
//        } else {
//            imgFoto.setImageResource(R.drawable.ic_launcher_background); // Imagem padrão
//        }
//
//        return v;
//    }
}
