package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Aluno;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.service.AdminService;
import br.itb.projeto.vitalususPlus.service.AlunoService;
import br.itb.projeto.vitalususPlus.service.ChaveSegurancaService;
import br.itb.projeto.vitalususPlus.service.TreinadorService;
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
	private AdminService adminService;
	private TreinadorService treinadorService;
	private AlunoService alunoService;
	private ChaveSegurancaService chaveSegurancaService;

	public UsuarioController(UsuarioService usuarioService, AdminService adminService, TreinadorService treinadorService, AlunoService alunoService, ChaveSegurancaService chaveSegurancaService) {
		super();
		this.usuarioService = usuarioService;
		this.adminService = adminService;
		this.treinadorService = treinadorService;
		this.alunoService = alunoService;
		this.chaveSegurancaService = chaveSegurancaService;
	}

	@GetMapping("findAll")
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> usuarios = this.usuarioService.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PostMapping("findById/")
	public ResponseEntity<Usuario> findById(@RequestParam long id) {
		Usuario usuario = this.usuarioService.findById(id);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	@PostMapping("findByChaveSeguranca/")
	public ResponseEntity<Usuario> findByChaveSeguranca(@RequestParam long chaveSeguranca) {
		Usuario usuario = this.usuarioService.findByChaveSeguranca(chaveSeguranca);
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
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioUpdatado = this.usuarioService.update(usuario);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
		}
	@PutMapping("updateFoto/{id}")
	public ResponseEntity<Usuario> updateFoto(@PathVariable long id, @RequestBody Usuario usuario) {
		Usuario usuarioUpdatado = this.usuarioService.updateFoto(id, usuario);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}
	@PutMapping("updateSenha/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
		Usuario usuarioUpdatado = this.usuarioService.updateSenha(id, usuario);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}

	@PostMapping("login/")
	public ResponseEntity<?> sigin(@RequestParam String email, @RequestParam String senha) {
		Usuario usuario = usuarioService.sigin(email, senha);
		if (usuario != null) {
            switch (usuario.getTipoUsuario()) {
                case "ADMINISTRADOR" -> {
                    Admin admin = adminService.findByUsuario(usuario);
                    return ResponseEntity.ok().body(admin);
                }
                case "ALUNO" -> {
                    Aluno aluno = alunoService.findByUsuario(usuario);
                    return ResponseEntity.ok().body(aluno);
                }
                case "TREINADOR" -> {
                    Treinador treinador = treinadorService.findByUsuario(usuario);
                    return ResponseEntity.ok().body(treinador);
                }
            }
        }
		return ResponseEntity.badRequest().body("Dados incorretos!");
	}
	
	@PutMapping("tornarPublico/{id}")
	public ResponseEntity<Usuario> tornarPublico(@PathVariable long id){
		Usuario usuarioUpdatado = usuarioService.tornarPublico(id);
		return new ResponseEntity<Usuario>(usuarioUpdatado, HttpStatus.OK);
	}
	
	@PutMapping("tornarPrivado/{id}")
	public ResponseEntity<Usuario> tornarPrivado(@PathVariable long id){
		Usuario usuarioUpdatado = usuarioService.tornarPrivado(id);
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
