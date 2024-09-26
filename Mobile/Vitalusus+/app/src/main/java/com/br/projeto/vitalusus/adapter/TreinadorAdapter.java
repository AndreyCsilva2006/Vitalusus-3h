package com.br.projeto.vitalusus.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
//import com.bumptech.glide.Glide;

import java.util.List;

public class TreinadorAdapter extends RecyclerView.Adapter<TreinadorAdapter.TreinadorViewHolder> {
    private List<Usuario> usuarios;
    private List<Treinador> treinadores;
    private List<Canal> canais;

    // Construtor para passar as listas de Treinadores e Usuários
    public TreinadorAdapter(List<Usuario> usuarios, List<Treinador> treinadores, List<Canal> canais) {
        this.usuarios = usuarios;
        this.treinadores = treinadores;
        this.canais = canais;
    }

    @NonNull
    @Override
    public TreinadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canal, parent, false);
        return new TreinadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinadorViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        Treinador treinador = treinadores.get(position);
        Canal canal = canais.get(position);

        // Atribui os valores aos componentes de interface
        holder.nomeCanalTextView.setText(usuario.getNome());
        holder.seguidoresTextView.setText((CharSequence) canal.getSeguidores());

        // Carrega a imagem (se houver) com Glide, ou usa um placeholder
//        Glide.with(holder.itemView.getContext())
//                .load(usuario.getFoto()) // Substitua pelo campo real de URL de foto do treinador
//                .placeholder(R.drawable.perfil) // Placeholder enquanto carrega a imagem
//                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return treinadores.size();
    }

    public static class TreinadorViewHolder extends RecyclerView.ViewHolder {
        TextView nomeCanalTextView;
        TextView seguidoresTextView;
        ImageView fotoImageView;

        public TreinadorViewHolder(View itemView) {
            super(itemView);
            nomeCanalTextView = itemView.findViewById(R.id.txtNomeCanal); // Referência ao TextView para o nome do treinador
            seguidoresTextView = itemView.findViewById(R.id.txtSeguidoresCanal);//Referência ao TextView para os seguidores do treinador
            fotoImageView = itemView.findViewById(R.id.imgFotoCanal); // Referência ao ImageView para a foto do treinador
        }
    }
}
