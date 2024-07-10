package br.itb.projeto.vitalususPlus.model.repository;

import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Deslikes;

import java.util.List;

@Repository
public interface DeslikesRepository extends JpaRepository<Deslikes, Long> {
		List<Deslikes> findAllByAlunoAndVideoaula(Aluno aluno, Videoaula videoaula);
}
