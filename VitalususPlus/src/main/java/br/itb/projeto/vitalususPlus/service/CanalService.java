package br.itb.projeto.vitalususPlus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.model.repository.SeguidorRepository;
import br.itb.projeto.vitalususPlus.model.repository.VideoaulaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.repository.CanalRepository;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;

@Service
public class CanalService {
	private final CanalRepository canalRepository;
	private final SeguidorRepository seguidorRepository;

	public CanalService(CanalRepository canalRepository, SeguidorRepository seguidorRepository) {
		super();
		this.canalRepository = canalRepository;
		this.seguidorRepository = seguidorRepository;
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
		canal.setVisualizacoes(0);
		canal.setSeguidores(canal.getAlunos().size());
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
			canalUpdatado.setVisualizacoes(0);
			for (int i = 0; i < videoaula.size(); i++) {
				canalUpdatado.setVisualizacoes(canalUpdatado.getVisualizacoes() + videoaula.get(i).getVisualizacoes());
			}
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal addAlunos(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			List<Videoaula> videoaula = canalUpdatado.getVideoaulas();
			canalUpdatado.getAlunos().addAll(canal.getAlunos());
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			canalUpdatado.setVisualizacoes(0);
			for (int i = 0; i < videoaula.size(); i++) {
				canalUpdatado.setVisualizacoes(canalUpdatado.getVisualizacoes() + videoaula.get(i).getVisualizacoes());
			}
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal removeAlunos(Long id, Aluno aluno) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			List<Videoaula> videoaula = canalUpdatado.getVideoaulas();
			List<Seguidor> seguidor = seguidorRepository.findAllByAlunoAndCanal(aluno, canalUpdatado);
			seguidorRepository.deleteAll(seguidor);
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			canalUpdatado.setVisualizacoes(0);
			for (int i = 0; i < videoaula.size(); i++) {
				canalUpdatado.setVisualizacoes(canalUpdatado.getVisualizacoes() + videoaula.get(i).getVisualizacoes());
			}
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	@Transactional
	public Canal updateNome(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			List<Videoaula> videoaula = canalUpdatado.getVideoaulas();
			canalUpdatado.setNome(canal.getNome());
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			canalUpdatado.setVisualizacoes(0);
			for (int i = 0; i < videoaula.size(); i++) {
				canalUpdatado.setVisualizacoes(canalUpdatado.getVisualizacoes() + videoaula.get(i).getVisualizacoes());
			}
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
}
