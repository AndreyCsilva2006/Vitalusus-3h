package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Treinador;

import java.util.List;

public class ListaCanalAdapter extends RecyclerView.Adapter<ListaCanalAdapter.ViewHolder> {

    private List<Treinador> listaTreinador;
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
        Treinador treinador = listaTreinador.get(position);
//        Canal canal = treinador.getCanal();

//        holder.txtNomeTreinador.setText(canal.getNome());
//        holder.txtDescricaoTreinador.setText(treinador.getDescricao());
//        holder.txtSeguidores.setText("Seguidores: " + canal.getSeguidores().toString());

//        if (canal.getFoto() != null) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(canal.getFoto(), 0, canal.getFoto().length);
//            holder.imgFoto.setImageBitmap(bitmap);
//        } else {
//            holder.imgFoto.setImageResource(R.drawable.ic_launcher_background);
//        }
    }

    @Override
    public int getItemCount() {
        return listaTreinador.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeTreinador;
        TextView txtDescricaoTreinador;
        TextView txtSeguidores;
        ImageView imgFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNomeTreinador = itemView.findViewById(R.id.txtNomeCanal);
            txtDescricaoTreinador = itemView.findViewById(R.id.tv_descricao);
            txtSeguidores = itemView.findViewById(R.id.txtSeguidoresCanal);
            imgFoto = itemView.findViewById(R.id.imgFotoCanal);
        }
    }
}


