package br.itb.projeto.vitalususPlus.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Likes;

	@Repository
	public interface LikesRepository extends JpaRepository<Likes, Long> {
		Likes findByAluno(Aluno aluno);
	}


