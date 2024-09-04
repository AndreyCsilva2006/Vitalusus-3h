package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.service.CanalService;
import br.itb.projeto.vitalususPlus.service.TreinadorService;
import br.itb.projeto.vitalususPlus.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vitalusus/treinador")
public class TreinadorController {
    private TreinadorService treinadorService;
    private UsuarioService usuarioService;
    private CanalService canalService;

    public TreinadorController(TreinadorService treinadorService,
                               UsuarioService usuarioService,
                               CanalService canalService) {
        super();
        this.treinadorService = treinadorService;
        this.usuarioService = usuarioService;
        this.canalService = canalService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Treinador>> findAll(){
        List<Treinador> treinadores = this.treinadorService.findAll();
        return new ResponseEntity<List<Treinador>>(treinadores, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Treinador> findId(@PathVariable long id) {
        Treinador treinador = this.treinadorService.findById(id);
        return new ResponseEntity<Treinador>(treinador, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Treinador> findById(@RequestParam long id){
        Treinador treinador = this.treinadorService.findById(id);
        return  new ResponseEntity<Treinador>(treinador, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Treinador> salvarTreinador(@RequestBody @Valid Treinador treinador) throws IOException {
        Treinador treinadorSalvo = this.treinadorService.save(treinador);
        return new ResponseEntity<Treinador>(treinadorSalvo, HttpStatus.OK);
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
