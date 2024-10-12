package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.model.repository.DeslikesRepository;
import br.itb.projeto.vitalususPlus.model.repository.LikesRepository;
import br.itb.projeto.vitalususPlus.model.repository.VideoaulaRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class VideoaulaService {
    private final VideoaulaRepository videoaulaRepository;
    private final LikesRepository likesRepository;
    private final DeslikesRepository deslikesRepository;
    private final AlunoService alunoService;
    private final ComentarioService comentarioService;

    public VideoaulaService(VideoaulaRepository videoaulaRepository, LikesRepository likesRepository, DeslikesRepository deslikesRepository, AlunoService alunoService, ComentarioService comentarioService) {
        this.videoaulaRepository = videoaulaRepository;
        this.likesRepository = likesRepository;
        this.deslikesRepository = deslikesRepository;
        this.alunoService = alunoService;
        this.comentarioService = comentarioService;
    }

    @Transactional
    public List<Videoaula> findAll(){
        return videoaulaRepository.findAll();
    }
    @Transactional
    public List<Videoaula> findAllbyCanal(Canal canal){
        if (canal.getTreinador().getUsuario().getStatusUsuario().equals("ATIVO")) {
            return videoaulaRepository.findAllByCanal(canal);
        }
        else throw new RuntimeException("O canal que postou as videoaulas que vodê está procurando não está ativo ou sua conta foi banida ou deletada");
    }
    @Transactional
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
        videoaula.setDataPubli(LocalDateTime.now());
		videoaula.getDataPubli().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        videoaula.setLikes(videoaula.getAlunosLikes().size());
        videoaula.setDeslikes(videoaula.getAlunosDeslikes().size());
        videoaula.setPrivacidadeVideo("PÚBLICO");
        videoaula.setStatusVideo("ATIVO");
        if (videoaula.getEquipamento() == null) {
        	videoaula.setEquipamento("Nenhum");
        }
        return videoaulaRepository.save(videoaula);
    }
    public Videoaula postId(long id) {
    	Optional<Videoaula> videoaula = videoaulaRepository.findById(id);
    	if (videoaula !=null){
    		Videoaula _videoaula = videoaula.get();
    		return _videoaula;
    	}
    	return null;
    }
    public Videoaula banir(long id){
        Optional<Videoaula> _videoaula = videoaulaRepository.findById(id);
        if (_videoaula.isPresent()) {
            Videoaula videoaula = _videoaula.get();
            videoaula.setStatusVideo("BANIDO");
            return videoaulaRepository.save(videoaula);
        }
        else throw new RuntimeException("Essa videoaula não existe no banco de dados ou ocorreu um erro no servidor");
    }
    public Videoaula desbanir(long id){
        Optional<Videoaula> _videoaula = videoaulaRepository.findById(id);
        if (_videoaula.isPresent()) {
            Videoaula videoaula = _videoaula.get();
            videoaula.setStatusVideo("ATIVO");
            return videoaulaRepository.save(videoaula);
        }
        else throw new RuntimeException("Essa videoaula não existe no banco de dados ou ocorreu um erro no servidor");
    }
    public Videoaula tornarPublico(long id){
        Optional<Videoaula> _videoaula = videoaulaRepository.findById(id);
        if (_videoaula.isPresent()) {
            Videoaula videoaula = _videoaula.get();
            videoaula.setPrivacidadeVideo("PÚBLICO");
            return videoaulaRepository.save(videoaula);
        }
        else throw new RuntimeException("Essa videoaula não existe no banco de dados ou ocorreu um erro no servidor");
    }
    public Videoaula tornarPrivado(long id){
        Optional<Videoaula> _videoaula = videoaulaRepository.findById(id);
        if (_videoaula.isPresent()) {
            Videoaula videoaula = _videoaula.get();
            videoaula.setPrivacidadeVideo("PRIVADO");
            return videoaulaRepository.save(videoaula);
        }
        else throw new RuntimeException("Essa videoaula não existe no banco de dados ou ocorreu um erro no servidor");
    }
    public void delete(long id) {
        Optional<Videoaula> videoaula = videoaulaRepository.findById(id);
        if (videoaula.isPresent()) {
            Videoaula videoaulaDeletada = videoaula.get();
            List<Comentario> comentarios = videoaulaDeletada.getComentarios();
            for (Comentario comentario : comentarios) {
                comentarioService.delete(comentario);
            }
            this.videoaulaRepository.delete(videoaulaDeletada);
        }
    }
    public Videoaula updateFix(long id){
        Optional<Videoaula> videoaula = videoaulaRepository.findById(id);
        if(videoaula.isPresent()) {
            Videoaula _videoaula = videoaula.get();
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
    public Videoaula updateGeral(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setTitulo(videoaula.getTitulo());
            _videoaula.setDescricao(videoaula.getDescricao());
            _videoaula.setThumbnail(videoaula.getThumbnail());
            _videoaula.setCategoria(videoaula.getCategoria());
            _videoaula.setTags(videoaula.getTags());
            _videoaula.setEquipamento(videoaula.getEquipamento());
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateTitulo(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setTitulo(videoaula.getTitulo());
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateDescricao(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setDescricao(videoaula.getDescricao());
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateThumbnail(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setThumbnail(videoaula.getThumbnail());
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateCategoria(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setCategoria(videoaula.getCategoria());
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula updateTags(long id, Videoaula videoaula){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.setTags(videoaula.getTags());
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula addAlunos(long id, long alunoId){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Aluno aluno = alunoService.findById(alunoId);
            Videoaula _videoaula = videoaulaOptional.get();
            _videoaula.getAlunos().add(aluno);
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);

        }
        return null;
    }
    public Videoaula addLikes(long id, long alunoId){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            Aluno aluno = alunoService.findById(alunoId);
            if (!_videoaula.getAlunosLikes().contains(aluno)) {
                _videoaula.getAlunosLikes().add(aluno);
                _videoaula = updateFix(_videoaula.getId());
                return videoaulaRepository.save(_videoaula);
            }
            else throw new RuntimeException("o aluno " + aluno.getUsuario().getNome() + " já deu like nesta videoaula");
        }
        return null;
    }
    public Videoaula removeLikes(long id, long alunoId){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            Aluno aluno = alunoService.findById(alunoId);
            List<Likes> like = likesRepository.findAllByAlunoAndVideoaula(aluno, _videoaula);
            for (Likes likes : like) {
                _videoaula.getAlunosLikes().remove(likes.getAluno());
            }
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula addDeslikes(long id, long alunoId){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if(videoaulaOptional.isPresent()) {
            Videoaula _videoaula = videoaulaOptional.get();
            Aluno aluno = alunoService.findById(alunoId);
            if (!_videoaula.getAlunosDeslikes().contains(aluno)) {
                _videoaula.getAlunosDeslikes().add(aluno);
                _videoaula = updateFix(_videoaula.getId());
                return videoaulaRepository.save(_videoaula);
            }
            else throw new RuntimeException("o aluno " + aluno.getUsuario().getNome() + " já deu deslike nesta videoaula");
        }
        return null;
    }
    public Videoaula removeDeslikes(long id, long alunoId){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            Aluno aluno = alunoService.findById(alunoId);
            List<Deslikes> deslike = deslikesRepository.findAllByAlunoAndVideoaula(aluno, _videoaula);
            for (Deslikes deslikes : deslike) {
                _videoaula.getAlunosDeslikes().remove(deslikes.getAluno());
            }
            _videoaula = updateFix(_videoaula.getId());
            return videoaulaRepository.save(_videoaula);
        }
        return null;
    }
    public Videoaula addComentario(long id, long alunoId, Comentario comentario){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            Aluno aluno = alunoService.findById(alunoId);
            comentario.setAluno(aluno);
            Comentario _comentario = comentarioService.save(comentario);
            _videoaula.getComentarios().add(_comentario);
            return _videoaula;
        }
        return null;
    }
    @Transactional
    public Videoaula removeComentario(long id, long comentarioId){
        Optional<Videoaula> videoaulaOptional = videoaulaRepository.findById(id);
        if (videoaulaOptional.isPresent()){
            Videoaula _videoaula = videoaulaOptional.get();
            Comentario comentario = comentarioService.findById(comentarioId);
            comentarioService.delete(comentario);
            _videoaula = updateFix(_videoaula.getId());
            return _videoaula;
        }
        return null;
    }
}
