package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Evolucao;
import br.itb.projeto.vitalususPlus.model.repository.AlunoRepository;
import br.itb.projeto.vitalususPlus.model.repository.EvolucaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvolucaoService {
    private EvolucaoRepository evolucaoRepository;

    public EvolucaoService(EvolucaoRepository evolucaoRepository) {
        super();
        this.evolucaoRepository = evolucaoRepository;
    }
    public List<Evolucao> findAll(){
        List<Evolucao> listaEvolucao = evolucaoRepository.findAll();
        return listaEvolucao;
    }
    public Evolucao findById(long id) {
        Optional<Evolucao> evolucao = this.evolucaoRepository.findById(id);
        return evolucao.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }
    public Evolucao save(Evolucao evolucao){
        evolucao.setId(null);
        return evolucaoRepository.save(evolucao);
    }
    public void delete(Evolucao evolucao) {
        this.evolucaoRepository.delete(evolucao);
    }
    public Evolucao updateGeral(Long id, Evolucao evolucao){
        Optional<Evolucao> _evolucao = evolucaoRepository.findById(id);
        if (_evolucao.isPresent()) {
            Evolucao evolucaoUpdatado = _evolucao.get();
            evolucaoUpdatado.setAlturaAtual(evolucao.getAlturaAtual());
            evolucaoUpdatado.setImc(evolucao.getImc());
            evolucaoUpdatado.setMetBasal(evolucao.getMetBasal());
            evolucaoUpdatado.setAlturaAtual(evolucao.getAlturaAtual());
            return evolucaoRepository.save(evolucaoUpdatado);
        }
        return null;
    }
    public Evolucao updateAlturaAtual(Long id, Evolucao evolucao){
        Optional<Evolucao> _evolucao = evolucaoRepository.findById(id);
        if (_evolucao.isPresent()) {
        	Evolucao evolucaoUpdatado = _evolucao.get();
        	evolucaoUpdatado.setAlturaAtual(evolucao.getAlturaAtual());
        	return evolucaoRepository.save(evolucaoUpdatado);
        }
        return null;
    }
    public Evolucao updateImc(Long id, Evolucao evolucao){
        Optional<Evolucao> _evolucao = evolucaoRepository.findById(id);
        if (_evolucao.isPresent()) {
        	Evolucao evolucaoUpdatado = _evolucao.get();
        	evolucaoUpdatado.setImc(evolucao.getImc());
        	return evolucaoRepository.save(evolucaoUpdatado);
        }
        return null;
    }
    public Evolucao updateMetBasal(Long id, Evolucao evolucao){
        Optional<Evolucao> _evolucao = evolucaoRepository.findById(id);
        if (_evolucao.isPresent()) {
        	Evolucao evolucaoUpdatado = _evolucao.get();
        	evolucaoUpdatado.setMetBasal(evolucao.getMetBasal());
        	return evolucaoRepository.save(evolucaoUpdatado);
        }
        return null;
    }
    public Evolucao updatePesoAtual(Long id, Evolucao evolucao){
        Optional<Evolucao> _evolucao = evolucaoRepository.findById(id);
        if (_evolucao.isPresent()) {
        	Evolucao evolucaoUpdatado = _evolucao.get();
        	evolucaoUpdatado.setPesoAtual(evolucao.getPesoAtual());
        	return evolucaoRepository.save(evolucaoUpdatado);
        }
        return null;
    }
}
