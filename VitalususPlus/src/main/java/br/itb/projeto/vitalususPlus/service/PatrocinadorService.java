package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.model.entity.Patrocinador;
import br.itb.projeto.vitalususPlus.model.repository.EquipamentoRepository;
import br.itb.projeto.vitalususPlus.model.repository.PatrocinadorRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatrocinadorService {

    private PatrocinadorRepository patrocinadorRepository;
    private EquipamentoService equipamentoService;

    public PatrocinadorService(PatrocinadorRepository patrocinadorRepository, EquipamentoService equipamentoService) {
        this.patrocinadorRepository = patrocinadorRepository;
        this.equipamentoService = equipamentoService;
    }

    public List<Patrocinador> findAll(){
        List<Patrocinador> listaPatrocinador = patrocinadorRepository.findAll();
        return listaPatrocinador;
    }
    public Patrocinador findById(long id) {
        Optional<Patrocinador> patrocinador = this.patrocinadorRepository.findById(id);
        return patrocinador.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }
    public Patrocinador save(Patrocinador patrocinador){
        patrocinador.setId(null);
        patrocinador.setStatusPatrocinador("ATIVO");
        try {
			return patrocinadorRepository.save(patrocinador);
			}
			catch(DataIntegrityViolationException e) {
	            throw new RuntimeException("O e-mail fornecido já está em uso. Por favor, escolha um e-mail diferente.");
			}
    }
    public void deletar(long id){
        Optional<Patrocinador> patrocinador= patrocinadorRepository.findById(id);
        if(patrocinador.isPresent()){
            Patrocinador _patrocinador = patrocinador.get();
            List<Equipamento> equipamentos = equipamentoService.findAllByPatrocinador(_patrocinador);
            for (Equipamento equipamento:equipamentos){
                equipamentoService.deletar(equipamento.getId());
            }
            patrocinadorRepository.delete(_patrocinador);
        }
        else throw new RuntimeException("Não foi possível encontrar o patrocinador");
    }
    public Patrocinador update(long id, Patrocinador patrocinador){
        Optional<Patrocinador> patrocinadorOptional = patrocinadorRepository.findById(id);
        if(patrocinadorOptional.isPresent()) {
            Patrocinador _patrocinador = patrocinadorOptional.get();
            _patrocinador.setNome(patrocinador.getNome());
            _patrocinador.setLink(patrocinador.getLink());
            _patrocinador.setFoto(patrocinador.getFoto());
            return patrocinadorRepository.save(_patrocinador);
        }
        return null;
    }
}
