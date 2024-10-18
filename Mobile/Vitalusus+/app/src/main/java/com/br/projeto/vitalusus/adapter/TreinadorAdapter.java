package com.br.projeto.vitalusus.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
//import com.bumptech.glide.Glide;


import java.util.Arrays;
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

    public Bitmap decodeBase64(String encodedImage) {
        try {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (IllegalArgumentException e) {
            Log.e("DecodeBase64", "Erro ao decodificar imagem Base64", e);
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TreinadorViewHolder holder, int position) {
//        if (position < usuarios.size() && position < treinadores.size() && position < canais.size()) {
//            Usuario usuario = usuarios.get(position);
//            Treinador treinador = treinadores.get(position);
        Canal canal = canais.get(position);

        // Obter o treinador correspondente ao canal (caso exista)
        Treinador treinador = (position < treinadores.size()) ? treinadores.get(position) : null;
        Usuario usuario = usuarios.get(position);

        // Se o treinador for válido, buscar o usuário pelo ID do treinador
        if (treinador != null) {
            int usuarioId = treinador.getUsuarioId();
            // Procurar o usuário correto pelo ID
            for (Usuario u : usuarios) {
                if (u.getId() == usuarioId) {
                    usuario = u;
                    break;
                }
            }
        }

        // Lógica de exibição do canal
        if (canal != null) {
            holder.nomeCanalTextView.setText(canal.getNome());
            holder.seguidoresTextView.setText(formatarNumeroAbreviado((int) canal.getSeguidores()));
        }

        // Decodificar e exibir a foto do usuário correto
        if (usuario != null && usuario.getFoto() != null && !usuario.getFoto().isEmpty()) {
            Bitmap bitmap = decodeBase64(usuario.getFoto());
            if (bitmap != null) {
                holder.fotoImageView.setImageBitmap(bitmap);
            } else {
                holder.fotoImageView.setImageResource(R.drawable.ic_defaultuser);
            }
        } else {
            holder.fotoImageView.setImageResource(R.drawable.ic_defaultuser);
        }

        // Verificar se usuario e treinador não são nulos antes de definir o clique
        if (usuario != null && treinador != null && canal != null) {
            Usuario finalUsuario = usuario;
            holder.itemView.setOnClickListener(v -> listener.onItemClick(finalUsuario, treinador, canal));
        } else {
            // Remover o listener para itens nulos
            holder.itemView.setOnClickListener(null);
        }

        if ("PRIVADO".equals(usuario.getNivelPrivacidade()) || "DELETADO".equals(usuario.getStatusUsuario()) || "INATIVO".equals(usuario.getStatusUsuario())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        // Log.d("UsuarioFoto", "ID: " + usuario.getId() + ", Nome: " + usuario.getNome() + ", Foto: " + usuario.getFoto());
    }

//    @Override
//    public int getItemCount() {
//        // Retorna o tamanho da menor lista para evitar IndexOutOfBoundsException
//        return Math.min(usuarios.size(), Math.min(treinadores.size(), canais.size()));
//    }

    @Override
    public int getItemCount() {
        // Retorna o tamanho da lista de canais, já que é o dado principal
        return canais != null ? canais.size() : 0;
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
