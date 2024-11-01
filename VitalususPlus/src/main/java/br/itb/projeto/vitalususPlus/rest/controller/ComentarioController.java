package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Comentario;
import br.itb.projeto.vitalususPlus.model.entity.Videoaula;
import br.itb.projeto.vitalususPlus.service.AdminService;
import br.itb.projeto.vitalususPlus.service.ComentarioService;
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
@RequestMapping("/vitalusus/comentario")
public class ComentarioController {
    private ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        super();
        this.comentarioService = comentarioService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Comentario>> findAll(){
        List<Comentario> comentarios = this.comentarioService.findAll();
        return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
    }
    @PostMapping("findAllByVideoaula")
    public ResponseEntity<List<Comentario>> findAllByVideoaula(@RequestBody Videoaula videoaula){
        List<Comentario> comentarios = this.comentarioService.findAllByVideoaula(videoaula);
        return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Comentario> findById(@RequestParam long id){
        Comentario comentario = this.comentarioService.findById(id);
        return new ResponseEntity<Comentario>(comentario, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Comentario> findId(@PathVariable long id) {
        Comentario comentario = this.comentarioService.findById(id);
        return new ResponseEntity<Comentario>(comentario, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Comentario> salvarComentario(@RequestBody @Valid Comentario comentario){
        Comentario comentarioSalvo = this.comentarioService.save(comentario);
        return new ResponseEntity<Comentario>(comentarioSalvo, HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public void deletarComentario(@RequestBody Comentario comentario){
        this.comentarioService.delete(comentario);
    }

    @PutMapping("updateTexto/{id}")
    public ResponseEntity<Comentario> updateComentario(@PathVariable long id, @RequestBody Comentario comentario){
        Comentario comentarioUpdatado = this.comentarioService.update(id, comentario);
        return new ResponseEntity<Comentario>(comentarioUpdatado, HttpStatus.OK);
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
