package com.br.projeto.vitalusus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Canal> canalList;
    private final List<Video> videoList;
    private final List<Usuario> usuarioList;
    private final Context context;

    public VideoAdapter(List<Video> videoList, List<Canal> canalList, List<Usuario> usuarioList, Context context) {
        this.videoList = videoList;
        this.canalList = canalList;
        this.usuarioList = usuarioList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        Canal canal = canalList.get(position);
        Usuario usuario = usuarioList.get(position);

        // Converte byte[] para Bitmap e exibe a thumbnail
//        Bitmap bitmap = BitmapFactory.decodeByteArray(video.getThumbnail(), 0, video.getThumbnail().length);
//        holder.videoThumbnail.setImageBitmap(bitmap);

        // Define os outros detalhes do vídeo
        holder.videoTitulo.setText(video.getTitulo());
        holder.videoVisualizacoes.setText((CharSequence) video.getVisualizacoes());
        holder.videoDataPubli.setText(video.getDataPubli());
//        holder.videoThumbnail.setImageBitmap(video.getThumbnail());
        holder.canalNome.setText(canal.getNome());
        holder.canalSeguidores.setText((int) canal.getSeguidores());
//        holder.canalFoto.setImageBitmap(usuario.getFoto());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
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
