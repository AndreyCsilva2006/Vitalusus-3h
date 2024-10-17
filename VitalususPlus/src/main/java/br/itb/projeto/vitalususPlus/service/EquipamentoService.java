package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.model.entity.Patrocinador;
import br.itb.projeto.vitalususPlus.model.repository.EquipamentoRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {
    
    private EquipamentoRepository equipamentoRepository;
    private PatrocinadorService patrocinadorService;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, PatrocinadorService patrocinadorService) {
        this.equipamentoRepository = equipamentoRepository;
        this.patrocinadorService = patrocinadorService;
    }

    public List<Equipamento> findAll(){
        List<Equipamento> listaEquipamento = equipamentoRepository.findAll();
        return listaEquipamento;
    }
    public Equipamento findById(long id) {
        Optional<Equipamento> equipamento = this.equipamentoRepository.findById(id);
        return equipamento.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }
    public Equipamento save(Equipamento equipamento, long id){
        equipamento.setId(null);
        Patrocinador patrocinador = patrocinadorService.findById(id);
        equipamento.setPatrocinador(patrocinador);
        equipamento.setStatusEquipamento("ATIVO");
        try {
			return equipamentoRepository.save(equipamento);
			}
			catch(DataIntegrityViolationException e) {
	            throw new RuntimeException("O link fornecido já está em uso. Por favor, escolha um link diferente.");
			}
    }
    public List<Equipamento> findAllByPatrocinador(long patrocinadorId){
        Patrocinador patrocinador = patrocinadorService.findById(patrocinadorId);
        return equipamentoRepository.findAllByPatrocinador(patrocinador);
    }
    public Equipamento deletar(long id){
        Optional<Equipamento> equipamento= equipamentoRepository.findById(id);
        if(equipamento.isPresent()){
            Equipamento _equipamento = equipamento.get();
            _equipamento.setStatusEquipamento("DELETADO");
            return equipamentoRepository.save(_equipamento);
        }
        throw new RuntimeException("Não foi possível encontrar este equipamento");
    }
    public Equipamento update(long id, long patrocinadorId, Equipamento equipamento){
        Optional<Equipamento> _equipamento= equipamentoRepository.findById(id);
        if(_equipamento.isPresent()){
            Equipamento equipamentoUpdatado = _equipamento.get();
            equipamentoUpdatado.setNome(equipamento.getNome());
            equipamentoUpdatado.setLink(equipamento.getLink());
            Patrocinador patrocinador = patrocinadorService.findById(patrocinadorId);
            equipamentoUpdatado.setPatrocinador(patrocinador);
            equipamentoUpdatado.setStatusEquipamento("ATIVO");
            return equipamentoRepository.save(equipamentoUpdatado);
        }
        throw new RuntimeException("Não foi possível encontrar este equipamento");
    }
}
