package br.itb.projeto.vitalususPlus.rest.controller;

import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.service.AdminService;
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
@RequestMapping("/vitalusus/admin")
public class AdminController {
    private AdminService adminService;
    private UsuarioService usuarioService;

    public AdminController(AdminService adminService, UsuarioService usuarioService) {
        super();
        this.adminService = adminService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("findAll")
    public ResponseEntity<List<Admin>> findAll() {
        List<Admin> administradores = this.adminService.findAll();
        return new ResponseEntity<List<Admin>>(administradores, HttpStatus.OK);
    }

    @PostMapping("findById")
    public ResponseEntity<Admin> findById(@RequestParam long id) {
        Admin admin = this.adminService.findById(id);
        return new ResponseEntity<Admin>(admin, HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<Admin> findId(@PathVariable long id) {
        Admin admin = this.adminService.findById(id);
        return new ResponseEntity<Admin>(admin, HttpStatus.OK);
    }

    @PostMapping("post")
    public ResponseEntity<Admin> salvarAdmin(@RequestBody @Valid Admin admin) {
        Admin adminSalvo = this.adminService.save(admin);
        return new ResponseEntity<Admin>(adminSalvo, HttpStatus.OK);
    }
    @PutMapping("updateFix/{id}")
    public ResponseEntity<Admin> updateFix(@PathVariable long id) {
        Admin adminUpdatado = this.adminService.updateFix(id);
        return new ResponseEntity<Admin>(adminUpdatado, HttpStatus.OK);
    }
    @PutMapping("addUsuariosAdministrados/{id}")
    public ResponseEntity<Admin> addUsuariosAdministrados(@PathVariable long id, @RequestBody Admin admin) {
        Admin adminUpdatado = this.adminService.addUsuariosAdministrados(id, admin);
        return new ResponseEntity<Admin>(adminUpdatado, HttpStatus.OK);
    }
    @PutMapping("removeUsuariosAdministrados/{id}")
    public ResponseEntity<Admin> removeUsuariosAdministrados(@PathVariable long id, @RequestBody Usuario usuario) {
        Admin adminUpdatado = this.adminService.removeUsuariosAdministrados(id, usuario);
        return new ResponseEntity<Admin>(adminUpdatado, HttpStatus.OK);
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
