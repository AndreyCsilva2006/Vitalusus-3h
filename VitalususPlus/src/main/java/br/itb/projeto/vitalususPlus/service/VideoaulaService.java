package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import br.itb.projeto.vitalususPlus.model.repository.AlunoRepository;
import br.itb.projeto.vitalususPlus.model.repository.VideoaulaRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Double.isNaN;


@Service
public class VideoaulaService {
    private VideoaulaRepository videoaulaRepository;
    private CanalService canalService;

    public VideoaulaService(VideoaulaRepository videoaulaRepository, CanalService canalService) {
        super();
        this.videoaulaRepository = videoaulaRepository;
        this.canalService = canalService;
    }
    public List<Videoaula> findAll(){
        List<Videoaula> listaVideoaula = videoaulaRepository.findAll();
        return listaVideoaula;
    }
    public Videoaula findById(long id) {
        Optional<Videoaula> videoaula = this.videoaulaRepository.findById(id);
        return videoaula.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }
    @Transactional
    public Videoaula save(Videoaula videoaula){
        videoaula.setId(null);
        if (videoaula.getAlunos()==null){
            videoaula.setAlunos(new ArrayList<>());
        }
        videoaula.setVisualizacoes(videoaula.getAlunos().size());
        return videoaulaRepository.save(videoaula);
    }
    public void delete(Videoaula videoaula) {
        this.videoaulaRepository.delete(videoaula);
    }
    public Videoaula updateFixVisualicoes(long id){
        Optional<Videoaula> videoaula = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaula.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        return videoaulaRepository.save(_videoaula);
    }
    public Videoaula updateTitulo(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaulaOptional.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        _videoaula.setTitulo(videoaula.getTitulo());
        return videoaulaRepository.save(_videoaula);
    }
    public Videoaula updateDescricao(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaulaOptional.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        _videoaula.setDescricao(videoaula.getDescricao());
        return videoaulaRepository.save(_videoaula);
    }
    public Videoaula updateThumbnail(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaulaOptional.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.setThumbnail(videoaula.getThumbnail());
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        return videoaulaRepository.save(_videoaula);
    }
    public Videoaula updateAlunos(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaulaOptional.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.getAlunos().addAll(videoaula.getAlunos());
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        return videoaulaRepository.save(_videoaula);
    }
    public Videoaula updateLikes(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaulaOptional.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        _videoaula.setLikes(videoaula.getLikes());
        return videoaulaRepository.save(_videoaula);
    }
    public Videoaula updateDeslikes(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        Videoaula _videoaula = videoaulaOptional.get();
        if (_videoaula.getAlunos()==null){
            _videoaula.setAlunos(new ArrayList<>());
        }
        _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
        _videoaula.setDeslikes(videoaula.getDeslikes());
        return videoaulaRepository.save(_videoaula);
    }

}
