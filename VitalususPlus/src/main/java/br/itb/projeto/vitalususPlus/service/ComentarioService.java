package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Comentario;
import br.itb.projeto.vitalususPlus.model.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {
    private ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        super();
        this.comentarioRepository = comentarioRepository;
    }
    public List<Comentario> findAll(){
        List<Comentario> listaComentarios = comentarioRepository.findAll();
        return listaComentarios;
    }
    public Comentario findById(long id) {
        Optional<Comentario> comentario = this.comentarioRepository.findById(id);
        return comentario.orElseThrow(() -> new RuntimeException(
                "Admin não encontrado"
        ));
    }
    @Transactional
    public Comentario save(Comentario comentario){
        comentario.setId(null);
        return comentarioRepository.save(comentario);
    }
    public void delete(Comentario comentario) {
        this.comentarioRepository.delete(comentario);
    }
    public Comentario update(Comentario comentario){
        return comentarioRepository.save(comentario);
    }
}
