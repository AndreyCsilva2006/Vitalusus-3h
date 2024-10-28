package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.model.entity.Patrocinador;
import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import br.itb.projeto.vitalususPlus.model.repository.EquipamentoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {
    
    private EquipamentoRepository equipamentoRepository;
    private VideoaulaService videoaulaService;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, VideoaulaService videoaulaService) {
        this.equipamentoRepository = equipamentoRepository;
        this.videoaulaService = videoaulaService;
    }

    public List<Equipamento> findAll(){
        List<Equipamento> listaEquipamento = equipamentoRepository.findAll();
        return listaEquipamento;
    }
    public List<Equipamento> findAllByPatrocinador(Patrocinador patrocinador){
        List<Equipamento> listaEquipamento = equipamentoRepository.findAllByPatrocinador(patrocinador);
        return listaEquipamento;
    }
    public Equipamento findById(long id) {
        Optional<Equipamento> equipamento = this.equipamentoRepository.findById(id);
        return equipamento.orElseThrow(() -> new RuntimeException(
                "Equipamento não encontrado"
        ));
    }
    public Equipamento findByNome(String nome) {
        Optional<Equipamento> equipamento = this.equipamentoRepository.findByNome(nome);
        return equipamento.orElseThrow(() -> new RuntimeException(
                "Equipamento não encontrado"
        ));
    }
    public Equipamento save(Equipamento equipamento){
        equipamento.setId(null);
        equipamento.setStatusEquipamento("ATIVO");
			return equipamentoRepository.save(equipamento);
    }

    public void deletar(long id){
        Optional<Equipamento> equipamento= equipamentoRepository.findById(id);
        if(equipamento.isPresent()){
            Equipamento _equipamento = equipamento.get();
            List<Videoaula> videoaulas = videoaulaService.findAllbyEquipamento(_equipamento);
            for (Videoaula videoaula : videoaulas) {
                videoaula.setEquipamento(findByNome("Nenhuma das opções"));
            }
            equipamentoRepository.delete(_equipamento);
        }
        else throw new RuntimeException("Não foi possível encontrar este equipamento");
    }
    public Equipamento update(long id, Equipamento equipamento){
        Optional<Equipamento> _equipamento= equipamentoRepository.findById(id);
        if(_equipamento.isPresent()){
            Equipamento equipamentoUpdatado = _equipamento.get();
            equipamentoUpdatado.setNome(equipamento.getNome());
            equipamentoUpdatado.setLink(equipamento.getLink());
            equipamentoUpdatado.setStatusEquipamento("ATIVO");
            return equipamentoRepository.save(equipamentoUpdatado);
        }
        throw new RuntimeException("Não foi possível encontrar este equipamento");
    }
}
