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
import com.br.projeto.vitalusus.model.Video;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificacoesAdapter extends RecyclerView.Adapter<NotificacoesAdapter.ViewHolder> {

    private List<Video> listaVideos;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public NotificacoesAdapter(List<Video> listaVideos, Context context) {
        this.listaVideos = listaVideos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notificacao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = listaVideos.get(position);

        holder.txtTitulo.setText(video.getTitulo());
//        holder.txtCanal.setText(video.getCanal().getNome());

        // Mostrar thumbnail
        Bitmap thumbnail = BitmapFactory.decodeByteArray(video.getThumbnail(), 0, video.getThumbnail().length);
        holder.imgThumbnail.setImageBitmap(thumbnail);

        // Exibir a data de postagem
        String dataFormatada = dateFormat.format(video.getDataPubli());
        holder.txtData.setText(dataFormatada);
    }

    @Override
    public int getItemCount() {
        return listaVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtCanal, txtData;
        ImageView imgThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtNotificacaoTitulo);
            txtCanal = itemView.findViewById(R.id.txtNotificacaoCanal);
            txtData = itemView.findViewById(R.id.txtNotificacaoData);
            imgThumbnail = itemView.findViewById(R.id.imgNotificacaoThumbnail);
        }
    }
}
