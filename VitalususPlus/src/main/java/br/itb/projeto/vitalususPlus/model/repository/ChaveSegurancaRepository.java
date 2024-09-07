package br.itb.projeto.vitalususPlus.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.itb.projeto.vitalususPlus.model.entity.ChaveSeguranca;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;

@Repository
public interface ChaveSegurancaRepository extends JpaRepository<ChaveSeguranca, Long> {
}
