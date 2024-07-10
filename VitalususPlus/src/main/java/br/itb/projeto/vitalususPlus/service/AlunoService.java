package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.model.repository.AlunoRepository;
import br.itb.projeto.vitalususPlus.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
	private AlunoRepository alunoRepository;
	private UsuarioService usuarioService;
	private EvolucaoService evolucaoService;

	public AlunoService(AlunoRepository alunoRepository, UsuarioService usuarioService,
			EvolucaoService evolucaoService) {
		super();
		this.alunoRepository = alunoRepository;
		this.usuarioService = usuarioService;
		this.evolucaoService = evolucaoService;
	}

	public List<Aluno> findAll() {
		List<Aluno> listaAlunos = alunoRepository.findAll();
		return listaAlunos;
	}

	public Aluno findById(long id) {
		Optional<Aluno> aluno = this.alunoRepository.findById(id);
		return aluno.orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
	}

	public Aluno save(Aluno aluno) {
		aluno.setId(null);
		return alunoRepository.save(aluno);
	}

	public void delete(Aluno aluno) {
		alunoRepository.delete(aluno);
	}

	public Aluno updateAltura(Long id, Aluno aluno) {
		Optional<Aluno> _aluno = alunoRepository.findById(id);
		if (_aluno.isPresent()) {
			Aluno alunoUpdatado = _aluno.get();
			alunoUpdatado.setAltura(aluno.getAltura());
			return alunoRepository.save(alunoUpdatado);
		}
		return null;
	}
	
	public Aluno updatePeso(Long id, Aluno aluno) {
		Optional<Aluno> _aluno = alunoRepository.findById(id);
		if (_aluno.isPresent()) {
			Aluno alunoUpdatado = _aluno.get();
			alunoUpdatado.setPeso(aluno.getPeso());
			return alunoRepository.save(alunoUpdatado);
		}
		return null;
	}
}
