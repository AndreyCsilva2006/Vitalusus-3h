package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Denuncia;
import br.itb.projeto.vitalususPlus.model.entity.Evolucao;
import br.itb.projeto.vitalususPlus.model.repository.DenunciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenunciaService {
    private DenunciaRepository denunciaRepository;

    public DenunciaService(DenunciaRepository denunciaRepository) {
        this.denunciaRepository = denunciaRepository;
    }

    public List<Denuncia> findAll(){
        List<Denuncia> listaDenuncia = denunciaRepository.findAll();
        return listaDenuncia;
    }
    public Denuncia findById(long id) {
        Optional<Denuncia> denuncia = this.denunciaRepository.findById(id);
        return denuncia.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }
    public Denuncia save(Denuncia denuncia){
        denuncia.setId(null);
        return denunciaRepository.save(denuncia);
    }
}
