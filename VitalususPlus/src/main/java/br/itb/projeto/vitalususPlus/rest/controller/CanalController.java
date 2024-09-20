package br.itb.projeto.vitalususPlus.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.itb.projeto.vitalususPlus.model.entity.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import br.itb.projeto.vitalususPlus.service.CanalService;
import br.itb.projeto.vitalususPlus.service.TreinadorService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vitalusus/canal")
public class CanalController {
	   private CanalService canalService;

	    public CanalController(CanalService canalService) {
	        super();
	        this.canalService = canalService;
	    }
	    @GetMapping("findAll")
	    public ResponseEntity<List<Canal>> findAll(){
	        List<Canal> canais = this.canalService.findAll();
	        return new ResponseEntity<List<Canal>>(canais, HttpStatus.OK);
	    }
	    @PostMapping("findById/")
	    public ResponseEntity<Canal> findById(@RequestParam long id){
	        Canal canal = this.canalService.findById(id);
	        return  new ResponseEntity<Canal>(canal, HttpStatus.OK);
	    }
		@GetMapping("findById/{id}")
		public ResponseEntity<Canal> findId(@PathVariable long id) {
		Canal canal = this.canalService.findById(id);
		return new ResponseEntity<Canal>(canal, HttpStatus.OK);
	}
	    @PostMapping("post")
	    public ResponseEntity<Canal> salvarCanal(@RequestBody Canal canal){
	        Canal canalSalvo = this.canalService.save(canal);
	        return new ResponseEntity<Canal>(canalSalvo, HttpStatus.OK);
	    }
	    @PutMapping("updateFix/{id}")
	    public ResponseEntity<Canal> updateFix(@PathVariable long id){
	        Canal canalUpdatado = this.canalService.updateFix(id);
	        return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
	    }
		@PutMapping("addAlunos/{id}/{alunoId}")
		public ResponseEntity<Canal> updateAlunos(@PathVariable long id, @PathVariable long alunoId){
		Canal canalUpdatado = this.canalService.addAlunos(id, alunoId);
		return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
	}
		@PutMapping("removeAlunos/{id}/{alunoId}")
		public ResponseEntity<Canal> removeAlunos(@PathVariable long id, @PathVariable long alunoId){
		Canal canalUpdatado = this.canalService.removeAlunos(id, alunoId);
		return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
	}
	    @PutMapping("updateNome/{id}")
	    public ResponseEntity<Canal> updateNome(@PathVariable long id, @RequestBody Canal canal){
	        Canal canalUpdatado = this.canalService.updateNome(id, canal);
	        return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
	    }
		@PutMapping("updateBio/{id}")
		public ResponseEntity<Canal> updateBio(@PathVariable long id, @RequestBody Canal canal){
		Canal canalUpdatado = this.canalService.updateBio(id, canal);
		return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
	}
		@PutMapping("addVideoaula/{id}")
		public ResponseEntity<Canal> addVideoaula(@PathVariable long id, @RequestBody Videoaula videoaula){
			Canal canalUpdatado = this.canalService.addVideoaula(id, videoaula);
			return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
		}
		@PutMapping("removeVideoaula/{id}/{videoaulaId}")
		public ResponseEntity<Canal> addVideoaula(@PathVariable long id, @PathVariable long videoaulaId){
		Canal canalUpdatado = this.canalService.removeVideoaula(id, videoaulaId);
		return new ResponseEntity<Canal>(canalUpdatado, HttpStatus.OK);
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
