package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Comentario;
import br.itb.projeto.vitalususPlus.model.entity.Evolucao;
import br.itb.projeto.vitalususPlus.service.ComentarioService;
import br.itb.projeto.vitalususPlus.service.EvolucaoService;
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
@RequestMapping("/vitalusus/evolucao")
public class EvolucaoController {
    private EvolucaoService evolucaoService;

    public EvolucaoController(EvolucaoService evolucaoService) {
        super();
        this.evolucaoService = evolucaoService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Evolucao>> findAll(){
        List<Evolucao> evolucoes = this.evolucaoService.findAll();
        return new ResponseEntity<List<Evolucao>>(evolucoes, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Evolucao> findById(@PathVariable long id){
        Evolucao evolucao = this.evolucaoService.findById(id);
        return new ResponseEntity<Evolucao>(evolucao, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Evolucao> salvarEvolucao(@RequestBody @Valid Evolucao evolucao){
        Evolucao evolucaoSalvo = this.evolucaoService.save(evolucao);
        return new ResponseEntity<Evolucao>(evolucaoSalvo, HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public void deletarEvolucao(@RequestBody Evolucao evolucao){
        this.evolucaoService.delete(evolucao);
    }
    @PutMapping("updateAlturaAtual/{id}")
    public ResponseEntity<Evolucao> updateAlturaAtual(@PathVariable Long id, @RequestBody @Valid Evolucao evolucao){
        Evolucao evolucaoUpdatado = this.evolucaoService.updateAlturaAtual(id, evolucao);
        return new ResponseEntity<Evolucao>(evolucaoUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateImc/{id}")
    public ResponseEntity<Evolucao> updateImc(@PathVariable Long id, @RequestBody @Valid Evolucao evolucao){
        Evolucao evolucaoUpdatado = this.evolucaoService.updateImc(id, evolucao);
        return new ResponseEntity<Evolucao>(evolucaoUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateMetBasal/{id}")
    public ResponseEntity<Evolucao> updateMetBasal(@PathVariable Long id, @RequestBody @Valid Evolucao evolucao){
        Evolucao evolucaoUpdatado = this.evolucaoService.updateMetBasal(id, evolucao);
        return new ResponseEntity<Evolucao>(evolucaoUpdatado, HttpStatus.OK);
    }
    @PutMapping("updatePesoAtual/{id}")
    public ResponseEntity<Evolucao> updatePesoAtual(@PathVariable Long id, @RequestBody @Valid Evolucao evolucao){
        Evolucao evolucaoUpdatado = this.evolucaoService.updatePesoAtual(id, evolucao);
        return new ResponseEntity<Evolucao>(evolucaoUpdatado, HttpStatus.OK);
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
