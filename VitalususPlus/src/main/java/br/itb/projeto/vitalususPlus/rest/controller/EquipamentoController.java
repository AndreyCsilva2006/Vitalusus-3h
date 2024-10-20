package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.service.EquipamentoService;
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
@RequestMapping("/vitalusus/equipamento")
public class EquipamentoController {
    private EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        super();
        this.equipamentoService = equipamentoService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Equipamento>> findAll(){
        List<Equipamento> equipamentos = this.equipamentoService.findAll();
        return new ResponseEntity<List<Equipamento>>(equipamentos, HttpStatus.OK);
    }
    @GetMapping("findAllByPatrocinador/{patrocinadorId}")
    public ResponseEntity<List<Equipamento>> findAllByPatrocinador(@PathVariable long patrocinadorId){
        List<Equipamento> equipamentos= this.equipamentoService.findAllByPatrocinador(patrocinadorId);
        return new ResponseEntity<List<Equipamento>>(equipamentos, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Equipamento> findById(@RequestParam long id){
        Equipamento equipamento = this.equipamentoService.findById(id);
        return new ResponseEntity<Equipamento>(equipamento, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Equipamento> findId(@PathVariable long id) {
        Equipamento equipamento = this.equipamentoService.findById(id);
        return new ResponseEntity<Equipamento>(equipamento, HttpStatus.OK);
    }
    @PostMapping("post/{patrocinadorId}")
    public ResponseEntity<Equipamento> salvarEquipamento(@RequestBody @Valid Equipamento equipamento, @PathVariable long patrocinadorId){
        Equipamento equipamentoSalvo = this.equipamentoService.save(equipamento, patrocinadorId);
        return new ResponseEntity<Equipamento>(equipamentoSalvo, HttpStatus.OK);
    }
    @PutMapping("deletar/{id}")
    public ResponseEntity<Equipamento> deletarEquipamento(@PathVariable long id){
        Equipamento equipamentoSalvo = this.equipamentoService.deletar(id);
        return new ResponseEntity<Equipamento>(equipamentoSalvo, HttpStatus.OK);
    }
    @PutMapping("update/{id}/{patrocinadorId}")
    public ResponseEntity<Equipamento>updateEquipamento(@PathVariable long id,@PathVariable long patrocinadorId, @RequestBody Equipamento equipamento){
        Equipamento equipamentoSalvo = this.equipamentoService.update(id, patrocinadorId, equipamento);
        return new ResponseEntity<Equipamento>(equipamentoSalvo, HttpStatus.OK);
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
