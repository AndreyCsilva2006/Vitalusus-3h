package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.model.entity.Admin;
import br.itb.projeto.vitalususPlus.model.repository.AdminRepository;
import br.itb.projeto.vitalususPlus.model.repository.AdministradoRepository;
import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private UsuarioService usuarioService;
    private AdministradoRepository administradoRepository;

    public AdminService(AdminRepository adminRepository, UsuarioService usuarioService, AdministradoRepository administradoRepository) {
        super();
        this.adminRepository = adminRepository;
        this.usuarioService = usuarioService;
        this.administradoRepository = administradoRepository;
    }
    public List<Admin> findAll(){
        List<Admin> listaAdministradores = adminRepository.findAll();
        return listaAdministradores;
    }
    public Admin findById(long id) {
        Optional<Admin> admin = this.adminRepository.findById(id);
        return admin.orElseThrow(() -> new RuntimeException(
                "Admin não encontrado"
        ));
    }
    public Admin findByUsuario(Usuario usuario) {
        Admin admin = this.adminRepository.findByUsuario(usuario);
        return admin;
    }
    public Admin save(Admin admin){
        admin.setId(null);
        if (admin.getListaUsuarios()==null){
            admin.setListaUsuarios(new ArrayList<>());
        }
        Usuario usuario = admin.getUsuario();
        if(admin != null) {
        usuario.setTipoUsuario("ADMINISTRADOR");
        usuario.setNivelAcesso("ADMIN");
        usuarioService.save(usuario);
        }
        admin.setNumeroUsuarios(admin.getListaUsuarios().size());
        return adminRepository.save(admin);
    }
    public Admin updateFix(long id) {
        Optional<Admin> _admin = this.adminRepository.findById(id);
        if (_admin.isPresent()) {
            Admin adminUpdatado = _admin.get();
            if (adminUpdatado.getListaUsuarios() == null) {
                adminUpdatado.setListaUsuarios(new ArrayList<>());
            }
            adminUpdatado.setNumeroUsuarios(adminUpdatado.getListaUsuarios().size());
            return adminRepository.save(adminUpdatado);
        }
        return null;
    }
    public Admin addUsuariosAdministrados(long id, long usuarioId) {
        Optional<Admin> _admin = this.adminRepository.findById(id);
        if (_admin.isPresent()) {
            Admin adminUpdatado = _admin.get();
            Usuario usuario = usuarioService.findById(usuarioId);
            if (!adminUpdatado.getListaUsuarios().contains(usuario)) {
                adminUpdatado.getListaUsuarios().add(usuario);
                adminUpdatado = updateFix(adminUpdatado.getId());
                return adminRepository.save(adminUpdatado);
            }
            else throw new RuntimeException("O usuário " + usuario.getNome() + " já é administrado pelo administrador " + adminUpdatado.getUsuario().getNome());
        }
        return null;
    }
    public Admin removeUsuariosAdministrados(long id, long usuarioId) {
        Optional<Admin> _admin = this.adminRepository.findById(id);
        if (_admin.isPresent()) {
            Admin adminUpdatado = _admin.get();
            Usuario usuario = usuarioService.findById(id);
            List<Administrado> administrado = administradoRepository.findAllByUsuarioAndAdmin(usuario, adminUpdatado);
            for (Administrado value : administrado) {
                adminUpdatado.getListaUsuarios().remove(value.getUsuario());
            }
            adminUpdatado = updateFix(adminUpdatado.getId());
            return adminRepository.save(adminUpdatado);
        }
        return null;
    }
    @Transactional
	public Admin sigin(String email, String senha) {
		Usuario usuario = usuarioService.findByEmail(email);
		if (usuario != null) {
			if (usuario.getStatusUsuario().equals("ATIVO")) {
				byte[] decodedPass = Base64.getDecoder().decode(usuario.getSenha());
				if (new String(decodedPass).equals(senha) && usuario.getTipoUsuario().equals("ADMINISTRADOR")) {
						Admin admin = adminRepository.findByUsuario(usuario);
						return admin;
					}
				return null;
				}
			}
			
		return null;
	}
}
