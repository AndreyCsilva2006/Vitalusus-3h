package br.itb.projeto.vitalususPlus.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Deslikes;

@Repository
public interface DeslikesRepository extends JpaRepository<Deslikes, Long> {
		Deslikes findByAluno(Aluno aluno);
}
