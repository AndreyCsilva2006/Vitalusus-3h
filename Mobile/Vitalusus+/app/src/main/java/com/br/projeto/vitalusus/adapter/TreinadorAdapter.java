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
            holder.emailTextView.setText(usuario.getEmail());
            holder.senhaTextView.setText(usuario.getSenha());
            holder.nivelAcessoTextView.setText(String.valueOf(usuario.getNivelAcesso()));
            holder.dataCadastroTextView.setText(usuario.getDataCadastro().toString());
            holder.statusTextView.setText(String.valueOf(usuario.getStatusUsuario()));
            holder.tipoUsuarioTextView.setText(String.valueOf(usuario.getTipoUsuario()));
            holder.chaveSegurancaIdTextView.setText(String.valueOf(usuario.getChaveSegurancaId()));
            holder.nivelPrivacidadeTextView.setText(String.valueOf(usuario.getNivelPrivacidade()));

            // Dados do treinador
            holder.idTreinadorTextView.setText(String.valueOf(treinador.getId()));
            holder.crefTextView.setText(String.valueOf(treinador.getCref()));
            holder.dataNascTextView.setText(String.valueOf(treinador.getDataNasc()));
            holder.usuario_idTextView.setText(String.valueOf(treinador.getUsuario_id()));
        }

    @Override
    public int getItemCount() {
        return treinadores.size();
    }

    public static class TreinadorViewHolder extends RecyclerView.ViewHolder{
        TextView nomeTextView;
        TextView emailTextView;
        TextView senhaTextView;
        TextView nivelAcessoTextView;
        ImageView fotoImageView;
        TextView dataCadastroTextView;
        TextView statusTextView;
        TextView tipoUsuarioTextView;
        TextView chaveSegurancaIdTextView;
        TextView nivelPrivacidadeTextView;
        TextView idTreinadorTextView;
        TextView crefTextView;
        TextView dataNascTextView;
        TextView usuario_idTextView;

        public TreinadorViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeUsuario);
            emailTextView = itemView.findViewById(R.id.emailUsuario);
            senhaTextView = itemView.findViewById(R.id.senhaUsuario);
            nivelAcessoTextView = itemView.findViewById(R.id.nivelAcessoUsuario);
            fotoImageView = itemView.findViewById(R.id.fotoUsuario);
            dataCadastroTextView = itemView.findViewById(R.id.dataCadastroUsuario);
            statusTextView = itemView.findViewById(R.id.statusUsuario);
            tipoUsuarioTextView = itemView.findViewById(R.id.tipoUsuario);
            chaveSegurancaIdTextView = itemView.findViewById(R.id.chaveSegurancaId);
            nivelPrivacidadeTextView = itemView.findViewById(R.id.nivelPrivacidade);
            idTreinadorTextView = itemView.findViewById(R.id.idTreinador);
            crefTextView = itemView.findViewById(R.id.crefTreinador);
            dataNascTextView = itemView.findViewById(R.id.dataNascTreinador);
            usuario_idTextView = itemView.findViewById(R.id.usuarioId);
        }
    }
}
