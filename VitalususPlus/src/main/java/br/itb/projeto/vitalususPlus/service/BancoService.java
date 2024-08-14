package br.itb.projeto.vitalususPlus.service;


import br.itb.projeto.vitalususPlus.model.entity.Banco;
import br.itb.projeto.vitalususPlus.model.repository.BancoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoService {
    private BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        super();
        this.bancoRepository = bancoRepository;
    }
    public List<Banco> findAll(){
        List<Banco> listaBancos = bancoRepository.findAll();
        return listaBancos;
    }
    public Banco findById(long id) {
        Optional<Banco> banco = this.bancoRepository.findById(id);
        return banco.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }
    public Banco save(Banco banco){
        banco.setId(null);
        return bancoRepository.save(banco);
    }
    public void delete(Banco banco) {
        this.bancoRepository.delete(banco);
    }
    public Banco update(Banco banco){
        return bancoRepository.save(banco);
    }
}
