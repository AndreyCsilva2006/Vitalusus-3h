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
    public Videoaula update(Videoaula videoaula){
        if (videoaula.getAlunos()==null){
            videoaula.setAlunos(new ArrayList<>());
        }
        videoaula.setVisualizacoes(videoaula.getAlunos().size());
        return videoaulaRepository.save(videoaula);
    }
}
