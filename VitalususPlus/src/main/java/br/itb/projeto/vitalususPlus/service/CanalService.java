package br.itb.projeto.vitalususPlus.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.model.repository.SeguidorRepository;
import br.itb.projeto.vitalususPlus.model.repository.VideoaulaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.repository.CanalRepository;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CanalService {
	private final CanalRepository canalRepository;
	private final SeguidorRepository seguidorRepository;
	private final AlunoService alunoService;
	private final VideoaulaService videoaulaService;
	private final TreinadorService treinadorService;
	private final UsuarioService usuarioService;

	public CanalService(CanalRepository canalRepository, SeguidorRepository seguidorRepository,
			AlunoService alunoService, VideoaulaService videoaulaService, TreinadorService treinadorService,
			UsuarioService usuarioService) {
		super();
		this.canalRepository = canalRepository;
		this.seguidorRepository = seguidorRepository;
		this.alunoService = alunoService;
		this.videoaulaService = videoaulaService;
		this.treinadorService = treinadorService;
		this.usuarioService = usuarioService;
	}

	@Transactional
	public List<Canal> findAll() {
		List<Canal> canais = canalRepository.findAll();
		return canais;
	}

	@Transactional
	public Canal findById(long id) {
		Optional<Canal> canal = this.canalRepository.findById(id);
		return canal.orElseThrow(() -> new RuntimeException("treinador não encontrado"));
	}

	@Transactional
	public Canal save(Canal canal) {
		canal.setId(null);
		if (canal.getAlunos() == null) {
			canal.setAlunos(new ArrayList<>());
		}
		if (canal.getVideoaulas() == null){
			canal.setVideoaulas(new ArrayList<>());
		}
		Treinador treinador = canal.getTreinador();
		canal.setVisualizacoes(0);
		canal.setSeguidores(canal.getAlunos().size());
		canal.setNumeroVideos(canal.getVideoaulas().size());
		treinadorService.save(treinador);
		return canalRepository.save(canal);
	}

	public void delete(Canal canal) {
		this.canalRepository.delete(canal);
	}

	@Transactional
	public Canal updateFix(Long id) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			List<Videoaula> videoaula = canalUpdatado.getVideoaulas();
			if (videoaula == null){
				videoaula = new ArrayList<>();
			}
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			if (canalUpdatado.getVideoaulas()==null){
				canalUpdatado.setVideoaulas(new ArrayList<>());
			}
			canalUpdatado.setVisualizacoes(0);
			for (int i = 0; i < videoaula.size(); i++) {
				canalUpdatado.setVisualizacoes(canalUpdatado.getVisualizacoes() + videoaula.get(i).getVisualizacoes());
			}
			canalUpdatado.setNumeroVideos(canalUpdatado.getVideoaulas().size());
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal addAlunos(Long id, Long alunoId) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Aluno aluno = alunoService.findById(alunoId);
			Canal canalUpdatado = _canal.get();
			if (!canalUpdatado.getAlunos().contains(aluno)) {
				canalUpdatado.getAlunos().add(aluno);
				canalUpdatado = updateFix(canalUpdatado.getId());
				return canalRepository.save(canalUpdatado);
			}
			else throw new RuntimeException("O aluno " + aluno.getUsuario().getNome() + " já está inscrito neste canal");
		}
		return null;
	}
	@Transactional
	public Canal removeAlunos(Long id, Long alunoId) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Aluno aluno = alunoService.findById(alunoId);
			Canal canalUpdatado = _canal.get();
			List<Seguidor> seguidor = seguidorRepository.findAllByAlunoAndCanal(aluno, canalUpdatado);
			for (Seguidor value : seguidor) {
				canalUpdatado.getAlunos().remove(value.getAluno());
			}
			canalUpdatado = updateFix(canalUpdatado.getId());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	public Canal updateInformacoes(long id, Canal canal){
		Optional<Canal> canalOptional = canalRepository.findById(id);
		if(canalOptional.isPresent()) {
			Canal _canal = canalOptional.get();
			_canal.setNome(canal.getNome());
			_canal.setBio(canal.getBio());
			_canal = updateFix(_canal.getId());
			return canalRepository.save(_canal);
			
		}
		return null;
	}
	@Transactional
	public Canal tornarPrivado (long id) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.getTreinador().getUsuario().setNivelPrivacidade("PRIVADO");
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal tornarPublico (long id) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.getTreinador().getUsuario().setNivelPrivacidade("PUBLICO");
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal addVideoaula(long id, Videoaula videoaula){
		Optional<Canal> canalOptional = canalRepository.findById(id);
		if (canalOptional.isPresent()){
			Canal _canal = canalOptional.get();
			videoaula.setCanal(_canal);
			Videoaula _videoaula = videoaulaService.save(videoaula);
			_canal.getVideoaulas().add(_videoaula);
			_canal = updateFix(_canal.getId());
			return _canal;
		}
		return null;
	}
	@Transactional
	public Canal removeVideoaula(long id, long videoaulaId){
		Optional<Canal> canalOptional = canalRepository.findById(id);
		if (canalOptional.isPresent()){
			Canal _canal = canalOptional.get();
			Videoaula videoaula = videoaulaService.findById(videoaulaId);
			videoaulaService.delete(videoaula.getId());
			_canal = updateFix(_canal.getId());
			return _canal;
		}
		return null;
	}
	@Transactional
	public Canal updateNome(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.setNome(canal.getNome());
			canalUpdatado = updateFix(canalUpdatado.getId());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal updateSenha(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			String senha = Base64.getEncoder().encodeToString(canal.getTreinador().getUsuario().getSenha().getBytes());
			canalUpdatado.getTreinador().getUsuario().setSenha(senha);
			return canalRepository.save(canalUpdatado);
		}
		return canalRepository.save(canal);
	}
	@Transactional
	public Canal updateFoto(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.getTreinador().getUsuario().setFoto(canal.getTreinador().getUsuario().getFoto());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal reativar(Long id) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if(_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			Usuario usuario = canalUpdatado.getTreinador().getUsuario();
			usuario = usuarioService.reativar(usuario.getId());
			canalUpdatado.getTreinador().setUsuario(usuario);
			return canalUpdatado;
		}
		return null;
	}
	
	@Transactional
	public Canal updateBio(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.setBio(canal.getBio());
			canalUpdatado = updateFix(canalUpdatado.getId());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal findByTreinador(Treinador treinador) {
		return canalRepository.findByTreinador(treinador);
	}
}
