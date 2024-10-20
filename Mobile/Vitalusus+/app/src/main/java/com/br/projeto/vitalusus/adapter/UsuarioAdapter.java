//package com.br.projeto.vitalusus.adapter;
//
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.br.projeto.vitalusus.R;
//import com.br.projeto.vitalusus.model.Usuario;
//
//import java.util.List;
//
//public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {
//    private List<Usuario> usuarios;
//
//    public UsuarioAdapter(List<Usuario> usuarios) {
//        this.usuarios = usuarios;
//    }
//
//    @NonNull
//    @Override
//    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
//        return new UsuarioViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
//        Usuario usuario = usuarios.get(position);
//        holder.nomeTextView.setText(String.valueOf(usuario.getNome()));
//        holder.emailTextView.setText(String.valueOf(usuario.getEmail()));
//        holder.senhaTextView.setText(String.valueOf(usuario.getSenha()));
//        holder.nivelAcessoTextView.setText(String.valueOf(usuario.getNivelAcesso()));
////        holder.fotoImageView.setImageBitmap(Bitmap.createBitmap(usuario.getFoto()));
//        holder.dataCadastroTextView.setText(usuario.getDataCadastro().toString());
//        holder.statusTextView.setText(String.valueOf(usuario.getStatusUsuario()));
//        holder.tipoUsuarioTextView.setText(String.valueOf(usuario.getTipoUsuario()));
//        holder.chaveSegurancaIdTextView.setText(String.valueOf(usuario.getChaveSegurancaId()));
//        holder.nivelPrivacidadeTextView.setText(String.valueOf(usuario.getNivelPrivacidade()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return usuarios.size();
//    }
//
//    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
//        TextView nomeTextView;
//        TextView emailTextView;
//        TextView senhaTextView;
//        TextView nivelAcessoTextView;
//        ImageView fotoImageView;
//        TextView dataCadastroTextView;
//        TextView statusTextView;
//        TextView tipoUsuarioTextView;
//        TextView chaveSegurancaIdTextView;
//        TextView nivelPrivacidadeTextView;
//
//        public UsuarioViewHolder(View itemView) {
//            super(itemView);
//            nomeTextView = itemView.findViewById(R.id.nomeUsuario);
//            emailTextView = itemView.findViewById(R.id.emailUsuario);
//            senhaTextView = itemView.findViewById(R.id.senhaUsuario);
//            nivelAcessoTextView = itemView.findViewById(R.id.nivelAcessoUsuario);
//            fotoImageView = itemView.findViewById(R.id.fotoUsuario);
//            dataCadastroTextView = itemView.findViewById(R.id.dataCadastroUsuario);
//            statusTextView = itemView.findViewById(R.id.statusUsuario);
//            tipoUsuarioTextView = itemView.findViewById(R.id.tipoUsuario);
//            chaveSegurancaIdTextView = itemView.findViewById(R.id.chaveSegurancaId);
//            nivelPrivacidadeTextView = itemView.findViewById(R.id.nivelPrivacidade);
//        }
//    }
//}
