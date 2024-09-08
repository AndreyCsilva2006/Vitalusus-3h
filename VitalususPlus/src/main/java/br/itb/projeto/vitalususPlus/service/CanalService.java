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
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CanalService {
	private final CanalRepository canalRepository;
	private final SeguidorRepository seguidorRepository;
	private final AlunoService alunoService;

	public CanalService(CanalRepository canalRepository, SeguidorRepository seguidorRepository, AlunoService alunoService) {
		super();
		this.canalRepository = canalRepository;
		this.seguidorRepository = seguidorRepository;
		this.alunoService = alunoService;
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
}
