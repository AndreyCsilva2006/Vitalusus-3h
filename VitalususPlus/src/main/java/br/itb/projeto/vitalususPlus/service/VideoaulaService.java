package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Likes;
import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import br.itb.projeto.vitalususPlus.model.repository.AlunoRepository;
import br.itb.projeto.vitalususPlus.model.repository.DeslikesRepository;
import br.itb.projeto.vitalususPlus.model.repository.LikesRepository;
import br.itb.projeto.vitalususPlus.model.repository.VideoaulaRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Double.isNaN;


@Service
public class VideoaulaService {
    private VideoaulaRepository videoaulaRepository;
    private CanalService canalService;
    private LikesRepository likesRepository;
    private DeslikesRepository deslikesRepository;

    public VideoaulaService(VideoaulaRepository videoaulaRepository, CanalService canalService, LikesRepository likesRepository, DeslikesRepository deslikesRepository) {
        super();
        this.videoaulaRepository = videoaulaRepository;
        this.canalService = canalService;
        this.likesRepository = likesRepository;
        this.deslikesRepository = deslikesRepository;
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
        if (videoaula.getAlunosLikes()==null){
            videoaula.setAlunosLikes(new ArrayList<>());
        }
        if (videoaula.getAlunosDeslikes()==null){
            videoaula.setAlunosDeslikes(new ArrayList<>());
        }
        videoaula.setVisualizacoes(videoaula.getAlunos().size());
        return videoaulaRepository.save(videoaula);
    }
    public void delete(Videoaula videoaula) {
        this.videoaulaRepository.delete(videoaula);
    }
    public Videoaula updateFixVisualicoes(long id){
        Optional<Videoaula> videoaula = videoaulaRepository.findById(id);
        if(videoaula.isPresent()) {
            Videoaula _videoaula = videoaula.get();
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateTitulo(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setTitulo(videoaula.getTitulo());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateDescricao(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setDescricao(videoaula.getDescricao());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateThumbnail(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setThumbnail(videoaula.getThumbnail());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateAlunos(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.getAlunos().addAll(videoaula.getAlunos());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula addLikes(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.getAlunosLikes().addAll(videoaula.getAlunosLikes());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            _videoaula.setLikes(_videoaula.getAlunosLikes().size());
            _videoaula.setDeslikes(_videoaula.getAlunosDeslikes().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula removeLikes(long id, Aluno aluno){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
        	Likes likes = likesRepository.findByAluno(aluno);
            Videoaula _videoaula = videoaulaOptional.get();
            likesRepository.delete(likes);
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            _videoaula.setLikes(_videoaula.getAlunosLikes().size());
            _videoaula.setDeslikes(_videoaula.getAlunosDeslikes().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula addDeslikes(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.getAlunosDeslikes().addAll(videoaula.getAlunosDeslikes());
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            _videoaula.setLikes(_videoaula.getAlunosLikes().size());
            _videoaula.setDeslikes(_videoaula.getAlunosDeslikes().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula removeDeslikes(long id, Aluno aluno){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.getAlunosDeslikes().remove(aluno);
            if (_videoaula.getAlunos() == null) {
                _videoaula.setAlunos(new ArrayList<>());
            }
            if (_videoaula.getAlunosLikes() == null) {
                _videoaula.setAlunosLikes(new ArrayList<>());
            }
            if (_videoaula.getAlunosDeslikes() == null) {
                _videoaula.setAlunosDeslikes(new ArrayList<>());
            }
            _videoaula.setVisualizacoes(_videoaula.getAlunos().size());
            _videoaula.setLikes(_videoaula.getAlunosLikes().size());
            _videoaula.setDeslikes(_videoaula.getAlunosDeslikes().size());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
}
