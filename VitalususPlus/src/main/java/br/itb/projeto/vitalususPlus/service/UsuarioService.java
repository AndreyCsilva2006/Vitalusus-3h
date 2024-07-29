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
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	public List<Usuario> findAll() {
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		return listaUsuarios;
	}

	public Usuario findById(long id) {
		Optional<Usuario> usuario = this.usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}

	public Usuario findByLogin(String email, String senha) {
		Optional<Usuario> usuario = this.usuarioRepository.findByEmailAndSenha(email, senha);
		return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}

	public Usuario findByEmail(String email) {
		Usuario usuario = this.usuarioRepository.findByEmail(email);
		return usuario;
	}

	public Usuario save(Usuario usuario) {
		usuario.setId(null);
		String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
		usuario.setSenha(senha);
		usuario.setDataCadastro(LocalDateTime.now());
		usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return usuarioRepository.save(usuario);
	}

	public Usuario inativar(long id) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			usuarioUpdatado.setStatusUsuario("INATIVO");
			return usuarioRepository.save(usuarioUpdatado);
		}
		return null;
	}

	public void delete(Usuario usuario) {
		this.usuarioRepository.delete(usuario);
	}

	public Usuario update(Usuario usuario) {
		usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return usuarioRepository.save(usuario);
	}

	public Usuario updateSenha(Long id, Usuario usuario) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
			usuarioUpdatado.setSenha(senha);
			return usuarioRepository.save(usuarioUpdatado);
		}
		;
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public Usuario inativar(Long id, Usuario usuario) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			usuarioUpdatado.setStatusUsuario("INATIVO");
			return usuarioRepository.save(usuarioUpdatado);
		}
		;
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public Usuario reativar(Long id) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);
        Usuario usuarioUpdatado = null;
        if (_usuario.isPresent()) {
            usuarioUpdatado = _usuario.get();
            String senha = Base64.getEncoder().encodeToString("12345678".getBytes());
            usuarioUpdatado.setSenha(senha);
            usuarioUpdatado.setDataCadastro(LocalDateTime.now());
            usuarioUpdatado.setStatusUsuario("ATIVO");
            return usuarioRepository.save(usuarioUpdatado);
        }
        return null;
    }
	
	public Usuario alterarSenha(long id, Usuario usuario) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
			usuarioUpdatado.setSenha(senha);
			return usuarioRepository.save(usuarioUpdatado);
		}
		return null;
	}

	@Transactional
	public Usuario sigin(String email, String senha) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario != null) {
			if (!usuario.getStatusUsuario().equals("INATIVO")) {
				byte[] decodedPass = Base64.getDecoder().decode(usuario.getSenha());
				if (new String(decodedPass).equals(senha)) {
					return usuario;
				}
			}
		}
		return null;
	}
}
