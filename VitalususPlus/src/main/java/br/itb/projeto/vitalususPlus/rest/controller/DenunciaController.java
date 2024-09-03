package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Denuncia;
import br.itb.projeto.vitalususPlus.model.entity.Denuncia;
import br.itb.projeto.vitalususPlus.service.DenunciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vitalusus/denuncia")
public class DenunciaController {
    private DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        super();
        this.denunciaService = denunciaService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Denuncia>> findAll(){
        List<Denuncia> denuncias = this.denunciaService.findAll();
        return new ResponseEntity<List<Denuncia>>(denuncias, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Denuncia> findById(@RequestParam long id){
        Denuncia denuncia = this.denunciaService.findById(id);
        return new ResponseEntity<Denuncia>(denuncia, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Denuncia> findId(@PathVariable long id) {
        Denuncia denuncia = this.denunciaService.findById(id);
        return new ResponseEntity<Denuncia>(denuncia, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Denuncia> salvarDenuncia(@RequestBody @Valid Denuncia denuncia){
        Denuncia denunciaSalvo = this.denunciaService.save(denuncia);
        return new ResponseEntity<Denuncia>(denunciaSalvo, HttpStatus.OK);
    }
}
