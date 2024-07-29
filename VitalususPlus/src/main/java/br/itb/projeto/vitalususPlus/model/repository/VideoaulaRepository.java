package br.itb.projeto.vitalususPlus.model.repository;

import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoaulaRepository extends JpaRepository<Videoaula, Long> {
    List<Videoaula> findAllByCanal(Canal canal);
}
