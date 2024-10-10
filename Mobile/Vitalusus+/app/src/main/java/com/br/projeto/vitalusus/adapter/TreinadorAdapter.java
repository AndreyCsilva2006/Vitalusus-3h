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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TreinadorAdapter extends RecyclerView.Adapter<TreinadorAdapter.TreinadorViewHolder> {
    private List<Usuario> usuarios;
    private List<Treinador> treinadores;
    private List<Canal> canais;
    private OnItemClickListener listener;

    // Construtor
    public TreinadorAdapter(List<Usuario> usuarios, List<Treinador> treinadores, List<Canal> canais, OnItemClickListener listener) {
        this.usuarios = usuarios;
        this.treinadores = treinadores;
        this.canais = canais;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Usuario usuario, Treinador treinador, Canal canal);
    }

    private String formatarNumeroAbreviado(int numero) {
        if (numero < 1_000) {
            return String.valueOf(numero); // Menos de mil, retorna o número normal
        } else if (numero < 1_000_000) {
            // Para milhares
            if (numero % 1_000 == 0) {
                return String.format(Locale.US, "%.0f mil", numero / 1_000.0).replace('.', ',');
            } else {
                return String.format(Locale.US, "%.1f mil", numero / 1_000.0).replace('.', ',');
            }
        } else {
            // Para milhões
            if (numero % 1_000_000 == 0) {
                return String.format(Locale.US, "%.0f mi", numero / 1_000_000.0).replace('.', ',');
            } else {
                return String.format(Locale.US, "%.1f mi", numero / 1_000_000.0).replace('.', ',');
            }
        }
    }

    @NonNull
    @Override
    public TreinadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canal, parent, false);
        return new TreinadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinadorViewHolder holder, int position) {
        // Verifica se a posição é válida para todas as listas
        if (position < usuarios.size() && position < treinadores.size() && position < canais.size()) {
            Usuario usuario = usuarios.get(position);
            Treinador treinador = treinadores.get(position);
            Canal canal = canais.get(position);

            // Verifica se o nível de privacidade é "PRIVADO"
            if ("PRIVADO".equals(usuario.getNivelPrivacidade())) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0)); // Remove o espaço do item
            } else {
                // Se o nível de privacidade for público, exibe o canal normalmente
                holder.itemView.setVisibility(View.VISIBLE);
                holder.nomeCanalTextView.setText(canal.getNome());

                holder.seguidoresTextView.setText(formatarNumeroAbreviado((int) canal.getSeguidores()));

                // Carregar a imagem (caso use Glide)
                // Glide.with(holder.itemView.getContext())
                //     .load(usuario.getFoto()) // Substitua pelo campo real de URL de foto do treinador
                //     .placeholder(R.drawable.perfil) // Placeholder enquanto carrega a imagem
                //     .into(holder.fotoImageView);

                holder.itemView.setOnClickListener(v -> listener.onItemClick(usuario, treinador, canal));
            }
        }
    }

    @Override
    public int getItemCount() {
        // Retorna o tamanho da menor lista para evitar IndexOutOfBoundsException
        return Math.min(usuarios.size(), Math.min(treinadores.size(), canais.size()));
    }



    public static class TreinadorViewHolder extends RecyclerView.ViewHolder {
        TextView nomeCanalTextView;
        TextView seguidoresTextView;
        CircleImageView fotoImageView;

        public TreinadorViewHolder(View itemView) {
            super(itemView);
            nomeCanalTextView = itemView.findViewById(R.id.txtNomeCanal); // Referência ao TextView para o nome do treinador
            seguidoresTextView = itemView.findViewById(R.id.tv_SeguidoresCanal);//Referência ao TextView para os seguidores do treinador
            fotoImageView = itemView.findViewById(R.id.imgFotoCanal); // Referência ao ImageView para a foto do treinador
        }
    }
}
