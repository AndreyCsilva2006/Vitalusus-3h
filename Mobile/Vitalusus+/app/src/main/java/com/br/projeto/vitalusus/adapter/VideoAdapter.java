package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Equipamento;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Canal> canalList;
    private final List<Video> videoList;
    private final List<Usuario> usuarioList;
    private final List<Treinador> treinadorList;
//    private final List<Equipamento> equipamentoList;
    private final OnItemClickListener listener;

    public VideoAdapter(List<Usuario> usuarioList, List<Canal> canalList, List<Treinador> treinadorList, List<Video> videoList, OnItemClickListener listener) {
        this.videoList = videoList;
        this.canalList = canalList;
        this.usuarioList = usuarioList;
//        this.equipamentoList = equipamentoList;
        this.treinadorList = treinadorList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Video video, Canal canal, Usuario usuario, Treinador treinador);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
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
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        Canal canal = canalList.get(position);
//        Equipamento equipamento = (position < equipamentoList.size()) ? equipamentoList.get(position) : null;

        Treinador treinador = (position < treinadorList.size()) ? treinadorList.get(position) : null;
        Usuario usuario = usuarioList.get(position);

        if (treinador != null) {
            int usuarioId = treinador.getUsuarioId();
            // Procurar o usuário correto pelo ID
            for (Usuario u : usuarioList) {
                if (u.getId() == usuarioId) {
                    usuario = u;
                    break;
                }
            }
        }

        if (canal != null) {
            holder.canalNome.setText(canal.getNome());
//            holder.canalSeguidores.setText(String.valueOf(canal.getSeguidores()));
        }

        if (video != null){
            holder.videoTitulo.setText(video.getTitulo());
            holder.videoVisualizacoes.setText(video.getVisualizacoes().toString());

        }

        // Decodificar imagem de thumbnail do vídeo
        if (video.getThumbnail() != null && !video.getThumbnail().isEmpty()) {
            Bitmap bitmap = decodeBase64(video.getThumbnail());
            if (bitmap != null) {
                holder.videoThumbnail.setImageBitmap(bitmap);
            } else {
                holder.videoThumbnail.setImageResource(R.drawable.ic_defaultuser);
            }
        } else {
            holder.videoThumbnail.setImageResource(R.drawable.ic_defaultuser);
        }

        // Decodificar imagem de perfil do canal/usuário
        if (usuario.getFoto() != null && !usuario.getFoto().isEmpty()) {
            Bitmap bitmap = decodeBase64(usuario.getFoto());
            if (bitmap != null) {
                holder.canalFoto.setImageBitmap(bitmap);
            } else {
                holder.canalFoto.setImageResource(R.drawable.ic_defaultuser);
            }
        } else {
            holder.canalFoto.setImageResource(R.drawable.ic_defaultuser);
        }
        if (usuario != null && canal != null) {
            Usuario finalUsuario = usuario;
            holder.itemView.setOnClickListener(v -> listener.onItemClick(video, canal, finalUsuario, treinador));
        } else {
            holder.itemView.setOnClickListener(null);
        }
        if ("PRIVADO".equals(usuario.getNivelPrivacidade()) || "DELETADO".equals(usuario.getStatusUsuario()) || "INATIVO".equals(usuario.getStatusUsuario())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        Log.d("UsuarioFoto", "ID: " + usuario.getId() + ", Nome: " + usuario.getNome() + ", Foto: " + usuario.getFoto());
        Log.d("video", "ID: " + video.getId() + ", título: " + video.getTitulo() + ", thumbnail: " + video.getThumbnail());
//        Log.d("equipamento", "ID: " + equipamento.getId());
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView videoThumbnail;
        TextView videoTitulo;
        TextView videoVisualizacoes;
        TextView canalNome;
        CircleImageView canalFoto;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitulo = itemView.findViewById(R.id.tituloVideo);
            videoVisualizacoes = itemView.findViewById(R.id.visualizacoesVideo);
            canalNome = itemView.findViewById(R.id.nomeCanal);
            canalFoto = itemView.findViewById(R.id.fotoCanal);
        }
    }
}
