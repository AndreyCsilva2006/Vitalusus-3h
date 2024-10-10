package br.itb.projeto.vitalususPlus.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
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
		usuario.setNivelPrivacidade("PUBLICO");
		LocalDate dataAtual = LocalDate.now();
		LocalDate dataNascimento = usuario.getDataNasc().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		usuario.setIdade(Period.between(dataNascimento, dataAtual).getYears());
		if(usuario.getIdade() >=13) {
			return usuarioRepository.save(usuario);
		}
		else throw new RuntimeException("O usuário possui menos de 13 anos");
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
            switch (usuarioUpdatado.getStatusUsuario()) {
                case "INATIVO" -> {
                    usuarioUpdatado.setDataCadastro(LocalDateTime.now());
                    usuarioUpdatado.setStatusUsuario("ATIVO");
                    return usuarioRepository.save(usuarioUpdatado);
                }
                case "ATIVO" -> throw new RuntimeException("Este usuário já está ativo") ;
                case "BANIDO" -> throw new RuntimeException("Este usuário foi banido");
				case "DELETADO" -> throw new RuntimeException("Este usuário foi deletado");
            }
        }
        return null;
    }
	@Transactional
	public Usuario banir(long id){
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuario = _usuario.get();
			usuario.setStatusUsuario("BANIDO");
			return usuarioRepository.save(usuario);
		}
		else throw new RuntimeException("Esse usuário não existe no banco de dados ou ocorreu um erro no servidor");
	}
	public Usuario desbanir(long id){
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuario = _usuario.get();
			if(usuario.getStatusUsuario().equals("BANIDO")) {
			usuario.setStatusUsuario("ATIVO");
			}
			return usuarioRepository.save(usuario);
		}
		else throw new RuntimeException("Esse usuário não existe no banco de dados ou ocorreu um erro no servidor");
	}
	public Usuario deletar(long id){
		Optional<Usuario> _usuario = usuarioRepository.findById(id);
		if (_usuario.isPresent()) {
			Usuario usuario = _usuario.get();
			usuario.setStatusUsuario("DELETADO");
			return usuarioRepository.save(usuario);
		}
		else throw new RuntimeException("Esse usuário não existe no banco de dados ou ocorreu um erro no servidor");
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
			if (!usuario.getStatusUsuario().equals("BANIDO") || !usuario.getStatusUsuario().equals("DELETADO")){
				byte[] decodedPass = Base64.getDecoder().decode(usuario.getSenha());
				if (new String(decodedPass).equals(senha)) {
					return usuario;
				}
				else throw new RuntimeException("A senha está incorreta");
			}
			else throw new RuntimeException("Este usuário está banido ou deletado, não pode fazer login");
		}
		return null;
	} 
	
	@Transactional
	public Usuario findByChaveSeguranca(UUID chaveSeguranca) {
		return usuarioRepository.findByChaveSeguranca(chaveSeguranca);
	}
}
