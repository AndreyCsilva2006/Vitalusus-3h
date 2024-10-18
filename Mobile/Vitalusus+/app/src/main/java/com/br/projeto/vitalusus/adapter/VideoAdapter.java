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
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Canal> canalList;
    private final List<Video> videoList;
    private final List<Usuario> usuarioList;
    private final List<Treinador> treinadores;
    private final OnItemClickListener listener;
    private final Context context;

    public VideoAdapter(List<Video> videoList, List<Canal> canalList, List<Usuario> usuarioList, List<Treinador> treinadores, OnItemClickListener listener, Context context) {
        this.videoList = videoList;
        this.canalList = canalList;
        this.usuarioList = usuarioList;
        this.treinadores = treinadores;
        this.listener = listener;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(Video video, Canal canal, Usuario usuario);
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
        Usuario usuario = usuarioList.get(position);

        // Define os outros detalhes do vídeo
        holder.videoTitulo.setText(video.getTitulo());
//        holder.videoVisualizacoes.setText((CharSequence) video.getVisualizacoes());
        holder.videoVisualizacoes.setText(video.getVisualizacoes().toString());
        holder.videoDataPubli.setText(video.getDataPubli());
//        holder.videoThumbnail.setImageBitmap(video.getThumbnail());
        holder.canalNome.setText(canal.getNome());
//        holder.canalSeguidores.setText((int) canal.getSeguidores());
        holder.canalSeguidores.setText(String.valueOf(canal.getSeguidores()));
//        holder.canalFoto.setImageBitmap(usuario.getFoto());

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

        holder.itemView.setOnClickListener(v -> listener.onItemClick(video, canal, usuario));
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView videoThumbnail;
        TextView videoTitulo;
        TextView videoVisualizacoes;
        TextView videoDataPubli;
        TextView canalNome;
        TextView canalSeguidores;
        CircleImageView canalFoto;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitulo = itemView.findViewById(R.id.tituloVideo);
            videoDataPubli = itemView.findViewById(R.id.DataPubliVideo);
            videoVisualizacoes = itemView.findViewById(R.id.visualizacoesVideo);
            canalNome = itemView.findViewById(R.id.nomeCanal);
            canalSeguidores = itemView.findViewById(R.id.seguidoresCanal);
            canalFoto = itemView.findViewById(R.id.fotoCanal);
        }
    }
}
