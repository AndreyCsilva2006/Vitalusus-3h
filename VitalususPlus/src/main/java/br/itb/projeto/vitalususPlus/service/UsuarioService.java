package br.itb.projeto.vitalususPlus.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import br.itb.projeto.vitalususPlus.model.repository.AlunoRepository;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	public List<Usuario> findAll(){
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		return listaUsuarios;
	}
	public Usuario findById(long id) {
		Optional<Usuario> usuario = this.usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new RuntimeException(
				"Usuário não encontrado"
		));
	}
	public Usuario findByLogin(String email, String senha) {
		Optional<Usuario> usuario = this.usuarioRepository.findByEmailAndSenha(email,senha);
		return usuario.orElseThrow(() -> new RuntimeException(
				"Usuário não encontrado"
		));
	}
	public Usuario save(Usuario usuario){
		usuario.setId(null);
		Base64.getEncoder().encode(usuario.getSenha().getBytes());
		usuario.setDataCadastro(LocalDateTime.now());
		usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return usuarioRepository.save(usuario);
	}
	public void delete(Usuario usuario) {
		this.usuarioRepository.delete(usuario);
	}
	public Usuario update(Usuario usuario){
		usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return usuarioRepository.save(usuario);
	}
}
