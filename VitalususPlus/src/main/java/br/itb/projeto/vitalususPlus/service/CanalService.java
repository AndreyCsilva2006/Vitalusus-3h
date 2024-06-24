package br.itb.projeto.vitalususPlus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.model.repository.CanalRepository;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;

@Service
public class CanalService {
	private CanalRepository canalRepository;

	public CanalService(CanalRepository canalRepository) {
		super();
		this.canalRepository = canalRepository;
	}

	public List<Canal> findAll() {
		List<Canal> canais = canalRepository.findAll();
		return canais;
	}

	public Canal findById(long id) {
		Optional<Canal> canal = this.canalRepository.findById(id);
		return canal.orElseThrow(() -> new RuntimeException("treinador não encontrado"));
	}

	public Canal save(Canal canal) {
		canal.setId(null);
		if (canal.getAlunos() == null) {
			canal.setAlunos(new ArrayList<>());
		}
		canal.setSeguidores(canal.getAlunos().size());
		canal.setVisualizacoes(0);
		return canalRepository.save(canal);
	}

	public void delete(Canal canal) {
		this.canalRepository.delete(canal);
	}

	public Canal updateFixSeguidores(Long id) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	public Canal addAlunos(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.getAlunos().addAll(canal.getAlunos());
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	public Canal removeAlunos(Long id, Aluno aluno) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();
			canalUpdatado.getAlunos().remove(aluno);
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			canalUpdatado.setSeguidores(canalUpdatado.getAlunos().size());
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
	public Canal updateNome(Long id, Canal canal) {
		Optional<Canal> _canal = canalRepository.findById(id);
		if (_canal.isPresent()) {
			Canal canalUpdatado = _canal.get();;
			canalUpdatado.setNome(canal.getNome());
			if (canalUpdatado.getAlunos() == null) {
				canalUpdatado.setAlunos(new ArrayList<>());
			}
			return canalRepository.save(canalUpdatado);
		}
		return null;
	}
}
