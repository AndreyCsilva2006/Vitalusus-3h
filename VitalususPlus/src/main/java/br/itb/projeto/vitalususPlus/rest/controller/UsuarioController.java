package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import br.itb.projeto.vitalususPlus.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vitalusus/usuario/")
public class UsuarioController {
	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@GetMapping("findAll")
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> usuarios = this.usuarioService.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PostMapping("findById")
	public ResponseEntity<Usuario> findById(@RequestParam long id) {
		Usuario usuario = this.usuarioService.findById(id);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	@GetMapping("findById/{id}")
	public ResponseEntity<Usuario> findId(@PathVariable long id) {
		Usuario usuario = this.usuarioService.findById(id);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	@GetMapping("findByLogin/{email}/{senha}")
	public ResponseEntity<Usuario> findByLogin(@PathVariable String email, @PathVariable String senha) {
		Usuario usuario = this.usuarioService.findByLogin(email, senha);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PostMapping("post")
	public ResponseEntity<Usuario> salvarUsuario(@RequestBody @Valid Usuario usuario) {
		Usuario usuarioSalvo = this.usuarioService.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	@PutMapping("corrigirBugSenha/{id}")
	public ResponseEntity<Usuario> corrigirBugSenha(@PathVariable long id) {
		Usuario usuarioSalvo = this.usuarioService.corrigirBugSenha(id);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

	@DeleteMapping("delete")
	public void deletarUsuario(@RequestBody Usuario usuario) {
		this.usuarioService.delete(usuario);
	}

	@PutMapping("update")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody @Valid Usuario usuario) {
		Usuario usuarioUpdatado = this.usuarioService.update(usuario);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
		}
	@PutMapping("updateSenha/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
		Usuario usuarioUpdatado = this.usuarioService.updateSenha(id, usuario);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}

	@PostMapping("login")
	public ResponseEntity<?> sigin(@RequestParam String email, @RequestParam String senha) {
		Usuario usuario = usuarioService.sigin(email, senha);
		if (usuario != null) {
			return ResponseEntity.ok().body(usuario);
		}
		return ResponseEntity.badRequest().body("Dados incorretos!");
	}
	
	@PutMapping("inativar/{id}")
	public ResponseEntity<Usuario> inativar(@PathVariable long id){
		Usuario usuarioUpdatado = usuarioService.inativar(id);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}
	
	@PutMapping("reativar/{id}")
	public ResponseEntity<Usuario> reativar(@PathVariable long id){
		Usuario usuarioUpdatado = usuarioService.reativar(id);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}
	
	@PutMapping("alterarSenha/{id}")
	public ResponseEntity<Usuario> update(@PathVariable long id, @RequestBody Usuario usuario){
		Usuario usuarioUpdatado = usuarioService.alterarSenha(id, usuario);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> erro = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String erroMessage = error.getDefaultMessage();
			erro.put(fieldName, erroMessage);
		});
		return erro;
	}
}
