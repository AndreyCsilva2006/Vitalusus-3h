package com.br.projeto.vitalusus.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.bumptech.glide.Glide;

import java.util.List;

public class TreinadorAdapter extends RecyclerView.Adapter<TreinadorAdapter.TreinadorViewHolder> {
    private List<Usuario> usuarios;
    private List<Treinador> treinadores;

    // Construtor para passar as listas de Treinadores e Usuários
    public TreinadorAdapter(List<Usuario> usuarios, List<Treinador> treinadores) {
        this.usuarios = usuarios;
        this.treinadores = treinadores;
    }

    @NonNull
    @Override
    public TreinadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treinador, parent, false);
        return new TreinadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinadorViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        Treinador treinador = treinadores.get(position);

        // Atribui os valores aos componentes de interface
        holder.nomeTextView.setText(usuario.getNome());

        // Carrega a imagem (se houver) com Glide, ou usa um placeholder
        Glide.with(holder.itemView.getContext())
                .load(usuario.getFoto()) // Substitua pelo campo real de URL de foto do treinador
                .placeholder(R.drawable.perfil) // Placeholder enquanto carrega a imagem
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return treinadores.size();
    }

    public static class TreinadorViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        ImageView fotoImageView;

        public TreinadorViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.tvNomeTreinador); // Referência ao TextView para o nome do treinador
            fotoImageView = itemView.findViewById(R.id.imgTreinador); // Referência ao ImageView para a foto do treinador
        }
    }
}
