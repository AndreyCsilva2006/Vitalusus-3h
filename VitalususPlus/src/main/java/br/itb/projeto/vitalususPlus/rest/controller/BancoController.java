package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Banco;
import br.itb.projeto.vitalususPlus.model.entity.Comentario;
import br.itb.projeto.vitalususPlus.service.BancoService;
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
@RequestMapping("/vitalusus/banco")
public class BancoController {
    private BancoService bancoService;

    public BancoController(BancoService bancoService) {
        super();
        this.bancoService = bancoService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Banco>> findAll(){
        List<Banco> bancos = this.bancoService.findAll();
        return new ResponseEntity<List<Banco>>(bancos, HttpStatus.OK);
    }
    @PostMapping("findById")
    public ResponseEntity<Banco> findById(@RequestParam long id){
        Banco banco = this.bancoService.findById(id);
        return new ResponseEntity<Banco>(banco, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Banco> findId(@PathVariable long id) {
        Banco banco = this.bancoService.findById(id);
        return new ResponseEntity<Banco>(banco, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Banco> salvarBanco(@RequestBody @Valid Banco banco){
        Banco bancoSalvo = this.bancoService.save(banco);
        return new ResponseEntity<Banco>(bancoSalvo, HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public void deletarBanco(@RequestBody Banco banco){
        this.bancoService.delete(banco);
    }
    @PutMapping("update")
    public ResponseEntity<Banco> updateBanco(@RequestBody @Valid Banco banco){
        Banco bancoUpdatado = this.bancoService.update(banco);
        return new ResponseEntity<Banco>(bancoUpdatado, HttpStatus.OK);
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
