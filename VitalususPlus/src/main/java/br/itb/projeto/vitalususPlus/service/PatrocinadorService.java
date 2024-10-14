package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Patrocinador;
import br.itb.projeto.vitalususPlus.model.repository.PatrocinadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatrocinadorService {

    private PatrocinadorRepository patrocinadorRepository;

    public PatrocinadorService(PatrocinadorRepository patrocinadorRepository) {
        this.patrocinadorRepository = patrocinadorRepository;
    }

    public void setPatrocinadorRepository(PatrocinadorRepository patrocinadorRepository) {
        this.patrocinadorRepository = patrocinadorRepository;
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
        return patrocinadorRepository.save(patrocinador);
    }
    public Patrocinador deletar(long id){
        Optional<Patrocinador> patrocinador= patrocinadorRepository.findById(id);
        if(patrocinador.isPresent()){
            Patrocinador _patrocinador = patrocinador.get();
            _patrocinador.setStatusPatrocinador("DELETADO");
            return patrocinadorRepository.save(_patrocinador);
        }
        else throw new RuntimeException("Não foi possível encontrar o patrocinador");
    }
}
