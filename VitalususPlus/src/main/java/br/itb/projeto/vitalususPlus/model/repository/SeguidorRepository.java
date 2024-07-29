package br.itb.projeto.vitalususPlus.model.repository;

import br.itb.projeto.vitalususPlus.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {
    List<Seguidor> findAllByAlunoAndCanal(Aluno aluno, Canal canal);
}


