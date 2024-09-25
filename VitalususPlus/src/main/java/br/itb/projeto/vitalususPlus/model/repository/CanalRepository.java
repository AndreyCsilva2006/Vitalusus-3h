package br.itb.projeto.vitalususPlus.model.repository;

import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.itb.projeto.vitalususPlus.model.entity.Canal;

import java.util.Optional;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {
    Canal findByTreinador(Treinador treinador);
}
