package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.service.CanalService;
import br.itb.projeto.vitalususPlus.service.VideoaulaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vitalusus/videoaula")
public class VideoaulaController {
    private VideoaulaService videoaulaService;
    private CanalService canalService;

    public VideoaulaController(VideoaulaService videoaulaService, CanalService canalService) {
        super();
        this.videoaulaService = videoaulaService;
        this.canalService = canalService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Videoaula>> findAll(){
        List<Videoaula> videoaulas = this.videoaulaService.findAll();
        return new ResponseEntity<List<Videoaula>>(videoaulas, HttpStatus.OK);
    }
    @PostMapping("findAllByCanal")
    public ResponseEntity<List<Videoaula>> findAllByCanal(@RequestBody Canal canal){
        List<Videoaula> videoaulas = this.videoaulaService.findAllbyCanal(canal);
        return new ResponseEntity<List<Videoaula>>(videoaulas, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Videoaula> findById(@RequestParam long id){
        Videoaula videoaula = this.videoaulaService.findById(id);
        return new ResponseEntity<Videoaula>(videoaula, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Videoaula> findId(@PathVariable long id) {
        Videoaula videoaula = this.videoaulaService.findById(id);
        return new ResponseEntity<Videoaula>(videoaula, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Videoaula> salvarVideoaula(@RequestBody Videoaula videoaula){
        Videoaula videoaulaSalvo = this.videoaulaService.save(videoaula);
        return new ResponseEntity<Videoaula>(videoaulaSalvo, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public void deletarVideoaula(@PathVariable Long id){
        this.videoaulaService.delete(id);
    }
    @PutMapping("updateFix/{id}")
    public ResponseEntity<Videoaula> update(@PathVariable long id){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateFix(id);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateGeral/{id}/{equipamentoId}")
    public ResponseEntity<Videoaula> update(@PathVariable long id, @RequestBody Videoaula videoaula, @PathVariable long equipamentoId){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateGeral(id, videoaula, equipamentoId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateTitulo/{id}")
    public ResponseEntity<Videoaula> updateTitulo(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateTitulo(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateDescricao/{id}")
    public ResponseEntity<Videoaula> updateDescricao(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateDescricao(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateCategoria/{id}")
    public ResponseEntity<Videoaula> updateCategoria(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateCategoria(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateTags/{id}")
    public ResponseEntity<Videoaula> updateTipoVideoaula(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateTags(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("addLikes/{id}/{alunoId}")
    public ResponseEntity<Videoaula> addLikes(@PathVariable long id, @PathVariable long alunoId){
        Videoaula videoaulaUpdatado = this.videoaulaService.addLikes(id, alunoId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("removeLikes/{id}/{alunoId}")
    public ResponseEntity<Videoaula> removeLikes(@PathVariable long id, @PathVariable long alunoId){
        Videoaula videoaulaUpdatado = this.videoaulaService.removeLikes(id, alunoId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("addDeslikes/{id}/{alunoId}")
    public ResponseEntity<Videoaula> addDeslikes(@PathVariable long id, @PathVariable long alunoId){
        Videoaula videoaulaUpdatado = this.videoaulaService.addDeslikes(id, alunoId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("removeDeslikes/{id}/{alunoId}")
    public ResponseEntity<Videoaula> removeDeslikes(@PathVariable long id, @PathVariable long alunoId){
        Videoaula videoaulaUpdatado = this.videoaulaService.removeDeslikes(id, alunoId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateThumbnail/{id}")
    public ResponseEntity<Videoaula> updateThumbnail(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateThumbnail(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("addAlunos/{id}/{alunoId}")
    public ResponseEntity<Videoaula> addAlunos(@PathVariable long id, @PathVariable long alunoId){
        Videoaula videoaulaUpdatado = this.videoaulaService.addAlunos(id, alunoId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("addComentario/{id}/{alunoId}")
    public ResponseEntity<Videoaula> add(@PathVariable long id, @PathVariable long alunoId, @RequestBody Comentario comentario){
        Videoaula videoaulaUpdatado = this.videoaulaService.addComentario(id, alunoId, comentario);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("removeComentario/{id}/{comentarioId}")
    public ResponseEntity<Videoaula> remove(@PathVariable long id, @PathVariable long comentarioId, @RequestBody Comentario comentario){
        Videoaula videoaulaUpdatado = this.videoaulaService.removeComentario(id, comentarioId);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("banir/{id}")
    public ResponseEntity<Videoaula> banir(@PathVariable long id) {
        Videoaula videoaulaBanido = this.videoaulaService.banir(id);
        return new ResponseEntity<Videoaula>(videoaulaBanido, HttpStatus.OK);
    }
    @PutMapping("desbanir/{id}")
    public ResponseEntity<Videoaula> desbanir(@PathVariable long id) {
        Videoaula videoaulaDesbanido = this.videoaulaService.desbanir(id);
        return new ResponseEntity<Videoaula>(videoaulaDesbanido, HttpStatus.OK);
    }
    @PutMapping("tornarPrivado/{id}")
    public ResponseEntity<Videoaula> tornarPrivado(@PathVariable long id) {
        Videoaula videoaulaPrivado = this.videoaulaService.banir(id);
        return new ResponseEntity<Videoaula>(videoaulaPrivado, HttpStatus.OK);
    }
    @PutMapping("tornarPublico/{id}")
    public ResponseEntity<Videoaula> tornarPublico(@PathVariable long id) {
        Videoaula videoaulaPublico = this.videoaulaService.banir(id);
        return new ResponseEntity<Videoaula>(videoaulaPublico, HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> erro = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError)error).getField();
            String erroMessage = error.getDefaultMessage();
            erro.put(fieldName, erroMessage);
        });
        return erro;
    }
}
