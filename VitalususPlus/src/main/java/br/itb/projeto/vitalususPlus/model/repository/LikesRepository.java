package br.itb.projeto.vitalususPlus.model.repository;

import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Likes;

import java.util.List;

@Repository
	public interface LikesRepository extends JpaRepository<Likes, Long> {
		List<Likes> findAllByAlunoAndVideoaula(Aluno aluno, Videoaula videoaula);
	}


