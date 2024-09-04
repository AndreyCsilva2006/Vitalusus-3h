package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.Canal;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.model.entity.Usuario;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;
import br.itb.projeto.vitalususPlus.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class TreinadorService {
    private TreinadorRepository treinadorRepository;
    private UsuarioService usuarioService;
    private CanalService canalService;

    public TreinadorService(TreinadorRepository treinadorRepository,
                            UsuarioService usuarioService,
                            CanalService canalService) {
        super();
        this.treinadorRepository = treinadorRepository;
        this.usuarioService = usuarioService;
        this.canalService = canalService;
    }
    public List<Treinador> findAll(){
        List<Treinador> listaTreinadores = treinadorRepository.findAll();
        return listaTreinadores;
    }
    public Treinador findByUsuario(Usuario usuario) {
		Treinador treinador = this.treinadorRepository.findByUsuario(usuario);
		return treinador;
	}
    
	@Transactional
	public Treinador sigin(String email, String senha) {
		Usuario usuario = usuarioService.findByEmail(email);
		if (usuario != null) {
			if (!usuario.getStatusUsuario().equals("INATIVO")) {
				byte[] decodedPass = Base64.getDecoder().decode(usuario.getSenha());
				if (new String(decodedPass).equals(senha) && usuario.getTipoUsuario().equals("TREINADOR")) {
						Treinador treinador = treinadorRepository.findByUsuario(usuario);
						return treinador;
					}
				return null;
				}
			}
			
		return null;
	}
	
    public Treinador findById(long id) {
        Optional<Treinador> treinador = this.treinadorRepository.findById(id);
        return treinador.orElseThrow(() -> new RuntimeException(
                "treinador não encontrado"
        ));
    }
    public Treinador save(Treinador treinador){
        treinador.setId(null);
        Usuario usuario = treinador.getUsuario();
        usuario.setTipoUsuario("TREINADOR");
        usuario.setNivelAcesso("USER");
        usuarioService.save(usuario);
        return treinadorRepository.save(treinador);
    }
}
