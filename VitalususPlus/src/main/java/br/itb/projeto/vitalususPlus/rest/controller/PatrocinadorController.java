package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Patrocinador;
import br.itb.projeto.vitalususPlus.service.PatrocinadorService;
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
@RequestMapping("/vitalusus/patrocinador")
public class PatrocinadorController {

    private PatrocinadorService patrocinadorService;

    public PatrocinadorController(PatrocinadorService patrocinadorService) {
        this.patrocinadorService = patrocinadorService;
    }

    @GetMapping("findAll")
    public ResponseEntity<List<Patrocinador>> findAll(){
        List<Patrocinador> evolucoes = this.patrocinadorService.findAll();
        return new ResponseEntity<List<Patrocinador>>(evolucoes, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Patrocinador> findById(@RequestParam long id){
        Patrocinador patrocinador = this.patrocinadorService.findById(id);
        return new ResponseEntity<Patrocinador>(patrocinador, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Patrocinador> findId(@PathVariable long id) {
        Patrocinador patrocinador = this.patrocinadorService.findById(id);
        return new ResponseEntity<Patrocinador>(patrocinador, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Patrocinador> salvarPatrocinador(@RequestBody @Valid Patrocinador patrocinador){
        Patrocinador patrocinadorSalvo = this.patrocinadorService.save(patrocinador);
        return new ResponseEntity<Patrocinador>(patrocinadorSalvo, HttpStatus.OK);
    }
    @PutMapping("deletar/{id}")
    public ResponseEntity<Patrocinador> salvarPatrocinador(@PathVariable long id){
        Patrocinador patrocinadorDeletado = this.patrocinadorService.deletar(id);
        return new ResponseEntity<Patrocinador>(patrocinadorDeletado, HttpStatus.OK);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Patrocinador> updateFoto(@PathVariable long id, @RequestBody Patrocinador patrocinador) {
        Patrocinador patrocinadorUpdatado = this.patrocinadorService.update(id, patrocinador);
        return new ResponseEntity<Patrocinador>(patrocinadorUpdatado, HttpStatus.OK);
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
