package br.itb.projeto.vitalususPlus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.itb.projeto.vitalususPlus.model.entity.ChaveSeguranca;
import br.itb.projeto.vitalususPlus.model.entity.ChaveSeguranca;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.model.repository.ChaveSegurancaRepository;

@Service
public class ChaveSegurancaService {
	private ChaveSegurancaRepository chaveSegurancaRepository;
	
	public ChaveSegurancaService(ChaveSegurancaRepository chaveSegurancaRepository) {
		super();
		this.chaveSegurancaRepository = chaveSegurancaRepository;
	}
	
	public List<ChaveSeguranca> findAll() {
		List<ChaveSeguranca> chavesSeguranca = chaveSegurancaRepository.findAll();
		return chavesSeguranca;
	}

	public ChaveSeguranca findById(long id) {
		Optional<ChaveSeguranca> chaveSeguranca = this.chaveSegurancaRepository.findById(id);
		return chaveSeguranca.orElseThrow(() -> new RuntimeException("treinador não encontrado"));
	}
	
	public ChaveSeguranca save(ChaveSeguranca canal) {
		canal.setId(null);
		return chaveSegurancaRepository.save(canal);
	}
}
