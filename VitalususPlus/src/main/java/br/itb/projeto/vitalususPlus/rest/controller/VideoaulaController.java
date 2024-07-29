package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import br.itb.projeto.vitalususPlus.service.CanalService;
import br.itb.projeto.vitalususPlus.service.VideoaulaService;
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
    @GetMapping("findById/{id}")
    public ResponseEntity<Videoaula> findById(@PathVariable long id){
        Videoaula videoaula = this.videoaulaService.findById(id);
        return new ResponseEntity<Videoaula>(videoaula, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Videoaula> salvarVideoaula(@RequestBody @Valid Videoaula videoaula){
        Videoaula videoaulaSalvo = this.videoaulaService.save(videoaula);
        return new ResponseEntity<Videoaula>(videoaulaSalvo, HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public void deletarVideoaula(@RequestBody Videoaula videoaula){
        this.videoaulaService.delete(videoaula);
    }
    @PutMapping("updateFix/{id}")
    public ResponseEntity<Videoaula> update(@PathVariable long id){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateFix(id);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateGeral/{id}")
    public ResponseEntity<Videoaula> update(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateGeral(id, videoaula);
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
    @PutMapping("addLikes/{id}")
    public ResponseEntity<Videoaula> addLikes(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.addLikes(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("removeLikes/{id}")
    public ResponseEntity<Videoaula> removeLikes(@PathVariable long id, @RequestBody Aluno aluno){
        Videoaula videoaulaUpdatado = this.videoaulaService.removeLikes(id, aluno);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("addDeslikes/{id}")
    public ResponseEntity<Videoaula> addDeslikes(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.addDeslikes(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("removeDeslikes/{id}")
    public ResponseEntity<Videoaula> removeDeslikes(@PathVariable long id, @RequestBody Aluno aluno){
        Videoaula videoaulaUpdatado = this.videoaulaService.removeDeslikes(id, aluno);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateThumbnail/{id}")
    public ResponseEntity<Videoaula> updateThumbnail(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.updateThumbnail(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
    }
    @PutMapping("addAlunos/{id}")
    public ResponseEntity<Videoaula> addAlunos(@PathVariable long id, @RequestBody Videoaula videoaula){
        Videoaula videoaulaUpdatado = this.videoaulaService.addAlunos(id, videoaula);
        return new ResponseEntity<Videoaula>(videoaulaUpdatado, HttpStatus.OK);
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
