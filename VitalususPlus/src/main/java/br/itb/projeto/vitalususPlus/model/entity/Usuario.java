package br.itb.projeto.vitalususPlus.model.entity;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

@Entity
@Table(name = "Usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="nome")
	@NotBlank(message = "campo não preenchido")
	@Pattern(regexp = "^[A-Z]+(.)*", message = "nome deve inicial com letras maiúsculas")
	private String nome;

	@Email(message ="campo inválido")
	@NotBlank(message = "campo não preenchido")
	@Column(name="email")
	private String email;

	@NotBlank(message = "campo não preenchido")
	@Column(name="senha")
	private String senha;
	
	@Column(name="nivelAcesso")
	private String nivelAcesso;

	@Lob
	@Column(name="foto")
	private byte[] foto;
	
	@Column(name="dataCadastro")
	private LocalDateTime dataCadastro;

	@Column(name="statusUsuario")
	private String statusUsuario;

	@Column(name="tipoUsuario")
	private String tipoUsuario;

	@OneToOne
	private ChaveSeguranca chaveSeguranca;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getStatusUsuario() {
		return statusUsuario;
	}

	public void setStatusUsuario(String statusUsuario) {
		this.statusUsuario = statusUsuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	
}
