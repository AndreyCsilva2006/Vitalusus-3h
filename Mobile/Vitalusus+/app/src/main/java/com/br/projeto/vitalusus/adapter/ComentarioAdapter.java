package com.br.projeto.vitalusus.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Comentario;
import com.br.projeto.vitalusus.model.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> {
    private List<Comentario> comentarios;
    private List<Usuario> usuarios;
    private List<Aluno> alunos;

    public ComentarioAdapter(List<Comentario> comentarios, List<Usuario> usuarios, List<Aluno> alunos) {
        this.comentarios = comentarios;
        this.usuarios = usuarios;
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, parent, false);
        return new ComentarioViewHolder(view);
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
    public void onBindViewHolder(@NonNull ComentarioAdapter.ComentarioViewHolder holder, int position) {
        Comentario comentario = comentarios.get(position);
        Aluno aluno = (position < alunos.size()) ? alunos.get(position) : null;

        Usuario usuario = usuarios.get(position);

        if ("PRIVADO".equals(usuario.getNivelPrivacidade()) || "DELETADO".equals(usuario.getStatusUsuario()) || "INATIVO".equals(usuario.getStatusUsuario())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        if (aluno != null) {
            int usuarioId = aluno.getUsuario_id();
            // Procurar o usuário correto pelo ID
            for (Usuario u : usuarios) {
                if (u.getId() == usuarioId) {
                    usuario = u;
                    break;
                }
            }
        }

        if (comentario != null) {
            holder.dataPubliComentTextView.setText(comentario.getDataPubli());
            holder.textoComentTextView.setText(comentario.getTexto());
        }

        // Decodificar e exibir a foto do usuário correto
        if (usuario != null && usuario.getFoto() != null && !usuario.getFoto().isEmpty()) {
            Bitmap bitmap = decodeBase64(usuario.getFoto());
            if (bitmap != null) {
                holder.fotoAlunoImageView.setImageBitmap(bitmap);
            } else {
                holder.fotoAlunoImageView.setImageResource(R.drawable.ic_defaultuser);
            }
        } else {
            holder.fotoAlunoImageView.setImageResource(R.drawable.ic_defaultuser);
        }

        Log.d("UsuarioFoto", "ID: " + usuario.getId() + ", Nome: " + usuario.getNome() + ", Foto: " + usuario.getFoto());
    }

    @Override
    public int getItemCount() {
        return comentarios != null ? comentarios.size() : 0;
    }

    public static class ComentarioViewHolder extends RecyclerView.ViewHolder{
        TextView dataPubliComentTextView;
        TextView nomeAlunoComentTextView;
        TextView textoComentTextView;
        CircleImageView fotoAlunoImageView;

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            dataPubliComentTextView = itemView.findViewById(R.id.txtDataPubliComentario); // Referência ao TextView para o nome do treinador
            nomeAlunoComentTextView = itemView.findViewById(R.id.txtNomeAlunoComentario); // Referência ao TextView para o nome do treinador
            textoComentTextView = itemView.findViewById(R.id.txtTextoComentario); // Referência ao TextView para o nome do treinador
            fotoAlunoImageView = itemView.findViewById(R.id.imgFotoAlunoComentario); // Referência ao TextView para o nome do treinador
        }
    }
}
