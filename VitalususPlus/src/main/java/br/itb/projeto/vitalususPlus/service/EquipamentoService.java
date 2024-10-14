package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.model.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {
    
    private EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
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
    public Equipamento save(Equipamento equipamento){
        equipamento.setId(null);
        return equipamentoRepository.save(equipamento);
    }
}
