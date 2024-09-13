package br.itb.projeto.vitalususPlus.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import br.itb.projeto.vitalususPlus.model.repository.AlunoRepository;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.entity.ChaveSeguranca;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final ChaveSegurancaService chavesegurancaService;

	public UsuarioService(UsuarioRepository usuarioRepository, ChaveSegurancaService chaveSegurancaService) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.chavesegurancaService = chaveSegurancaService;
	}
	@Transactional
	public List<Usuario> findAll() {
        return usuarioRepository.findAll();
	}
	@Transactional
	public Usuario findById(long id) {
		Optional<Usuario> usuario = this.usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
	@Transactional
	public Usuario findByLogin(String email, String senha) {
		Optional<Usuario> usuario = this.usuarioRepository.findByEmailAndSenha(email, senha);
		return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
	@Transactional
	public Usuario findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
	}
	@Transactional
	public Usuario save(Usuario usuario){
		usuario.setId(null);
		String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
		usuario.setStatusUsuario("ATIVO");
		usuario.setSenha(senha);
		usuario.setDataCadastro(LocalDateTime.now());
		usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		ChaveSeguranca chaveSeguranca = new ChaveSeguranca();
		usuario.setChaveSeguranca(chaveSeguranca);
		usuario.setNivelAcesso("PUBLICO");
		chavesegurancaService.save(usuario.getChaveSeguranca());
		return usuarioRepository.save(usuario);
	}
	@Transactional
	public Usuario corrigirBugSenha(long id) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if(_usuario.isPresent()) {
		Usuario usuario = _usuario.get();
		String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
		usuario.setSenha(senha);
		return usuarioRepository.save(usuario);
		}
		return null;
	}
	@Transactional
	public Usuario tornarPrivado (long id) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			usuarioUpdatado.setNivelPrivacidade("PRIVADO");
			return usuarioRepository.save(usuarioUpdatado);
		}
		return null;
	}
	
	@Transactional
	public Usuario tornarPublico (long id) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			usuarioUpdatado.setNivelPrivacidade("PUBLICO");
			return usuarioRepository.save(usuarioUpdatado);
		}
		return null;
	}
	
	@Transactional
	public Usuario inativar(long id) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			usuarioUpdatado.setStatusUsuario("INATIVO");
			return usuarioRepository.save(usuarioUpdatado);
		}
		return null;
	}
	@Transactional
	public void delete(Usuario usuario) {
		this.usuarioRepository.delete(usuario);
	}
	@Transactional
	public Usuario update(Usuario usuario) {
		usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return usuarioRepository.save(usuario);
	}
	@Transactional
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
	public Usuario updateFoto(Long id, Usuario usuario) {
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuarioUpdatado = _usuario.get();
			usuarioUpdatado.setFoto(usuario.getFoto());
			return usuarioRepository.save(usuarioUpdatado);
		}
		return null;
	}


	@Transactional
	public Usuario reativar(Long id) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);
        Usuario usuarioUpdatado = null;
        if (_usuario.isPresent()) {
            usuarioUpdatado = _usuario.get();
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
				else throw new RuntimeException("A senha está incorreta");
			}
			else throw new RuntimeException("Este usuário está inativo, não pode fazer login");
		}
		return null;
	} 
	
	@Transactional
	public Usuario findByChaveSeguranca(Long chaveSeguranca) {
		ChaveSeguranca _chaveSeguranca = chavesegurancaService.findById(chaveSeguranca);
        return usuarioRepository.findByChaveSeguranca(_chaveSeguranca);
	}
}
