package br.itb.projeto.vitalususPlus.model.repository;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Administrado;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministradoRepository extends JpaRepository<Administrado, Integer> {
    List<Administrado> findAllByUsuarioAndAdmin(Usuario usuario, Admin admin);
}
