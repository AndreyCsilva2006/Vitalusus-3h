package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Equipamento;
import br.itb.projeto.vitalususPlus.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Equipamento> evolucoes = this.equipamentoService.findAll();
        return new ResponseEntity<List<Equipamento>>(evolucoes, HttpStatus.OK);
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
    @PostMapping("post")
    public ResponseEntity<Equipamento> salvarEquipamento(@RequestBody @Valid Equipamento equipamento){
        Equipamento equipamentoSalvo = this.equipamentoService.save(equipamento);
        return new ResponseEntity<Equipamento>(equipamentoSalvo, HttpStatus.OK);
    }
}
