package br.itb.projeto.vitalususPlus.service;

import br.itb.projeto.vitalususPlus.model.entity.*;
import br.itb.projeto.vitalususPlus.model.entity.Treinador;
import br.itb.projeto.vitalususPlus.model.repository.TreinadorRepository;
import br.itb.projeto.vitalususPlus.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class TreinadorService {
    private TreinadorRepository treinadorRepository;
    private UsuarioService usuarioService;

    public TreinadorService(TreinadorRepository treinadorRepository,
                            UsuarioService usuarioService
                            ) {
        super();
        this.treinadorRepository = treinadorRepository;
        this.usuarioService = usuarioService;
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
    public Treinador updateSenha(Long id, Treinador treinador) {
        Optional<Treinador> _treinador = treinadorRepository.findById(id);
        if (_treinador.isPresent()) {
            Treinador treinadorUpdatado = _treinador.get();
            String senha = Base64.getEncoder().encodeToString(treinadorUpdatado.getUsuario().getSenha().getBytes());
            treinadorUpdatado.getUsuario().setSenha(senha);
            return treinadorRepository.save(treinadorUpdatado);
        }
        return treinadorRepository.save(treinador);
    }
    @Transactional
    public Treinador updateFoto(Long id, Treinador treinador) {
        Optional<Treinador> _treinador = treinadorRepository.findById(id);
        if (_treinador.isPresent()) {
            Treinador treinadorUpdatado = _treinador.get();
            treinadorUpdatado.getUsuario().setFoto(treinador.getUsuario().getFoto());
            return treinadorRepository.save(treinadorUpdatado);
        }
        return null;
    }
	@Transactional
	public Treinador sigin(String email, String senha) {
		Usuario usuario = usuarioService.findByEmail(email);
		if (usuario != null) {
			if (usuario.getStatusUsuario().equals("ATIVO")) {
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
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataNascimento = treinador.getDataNasc().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        treinador.getUsuario().setIdade(Period.between(dataNascimento, dataAtual).getYears());
        usuarioService.save(usuario);
        return treinadorRepository.save(treinador);
    }
}
