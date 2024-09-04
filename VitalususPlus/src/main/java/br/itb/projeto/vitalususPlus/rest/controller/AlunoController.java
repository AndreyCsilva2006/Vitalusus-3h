package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.service.AdminService;
import br.itb.projeto.vitalususPlus.service.AlunoService;
import br.itb.projeto.vitalususPlus.service.UsuarioService;
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
@RequestMapping("/vitalusus/aluno")
public class AlunoController {
    private AlunoService alunoService;
    private UsuarioService usuarioService;

    public AlunoController(AlunoService alunoService, UsuarioService usuarioService) {
        super();
        this.alunoService = alunoService;
        this.usuarioService = usuarioService;
    }
    @GetMapping("findAll")
    public ResponseEntity<List<Aluno>> findAll(){
        List<Aluno> alunos = this.alunoService.findAll();
        return new ResponseEntity<List<Aluno>>(alunos, HttpStatus.OK);
    }
    @PostMapping("findById/")
    public ResponseEntity<Aluno> findById(@RequestParam long id){
        Aluno aluno = this.alunoService.findById(id);
        return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Aluno> findId(@PathVariable long id) {
        Aluno aluno = this.alunoService.findById(id);
        return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);
    }
    @PostMapping("post")
    public ResponseEntity<Aluno> salvarAluno(@RequestBody @Valid Aluno aluno){
        Aluno alunoSalvo = this.alunoService.save(aluno);
        return new ResponseEntity<Aluno>(alunoSalvo, HttpStatus.OK);
    }
    @PutMapping("updateGeral/{id}")
    public ResponseEntity<Aluno> updateGeral(@PathVariable long id, @RequestBody @Valid Aluno aluno){
        Aluno alunoUpdatado = this.alunoService.updateGeral(id, aluno);
        return new ResponseEntity<Aluno>(alunoUpdatado, HttpStatus.OK);
    }
    @PutMapping("updateAltura/{id}")
    public ResponseEntity<Aluno> updateAltura(@PathVariable long id, @RequestBody @Valid Aluno aluno){
        Aluno alunoUpdatado = this.alunoService.updateAltura(id, aluno);
        return new ResponseEntity<Aluno>(alunoUpdatado, HttpStatus.OK);
    }
    @PutMapping("updatePeso/{id}")
    public ResponseEntity<Aluno> updatePeso(@PathVariable long id, @RequestBody @Valid Aluno aluno){
        Aluno alunoUpdatado = this.alunoService.updatePeso(id, aluno);
        return new ResponseEntity<Aluno>(alunoUpdatado, HttpStatus.OK);
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
