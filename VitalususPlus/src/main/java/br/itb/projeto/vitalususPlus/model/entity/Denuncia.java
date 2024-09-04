package br.itb.projeto.vitalususPlus.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Denuncia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Denuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name="usuarioDenunciado_id")
    private Usuario usuarioDenunciado;

    private String mensagem;
    
    private LocalDateTime dataDenuncia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioDenunciado() {
        return usuarioDenunciado;
    }

    public void setUsuarioDenunciado(Usuario usuarioDenunciado) {
        this.usuarioDenunciado = usuarioDenunciado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

	public LocalDateTime getDataDenuncia() {
		return dataDenuncia;
	}

	public void setDataDenuncia(LocalDateTime dataDenuncia) {
		this.dataDenuncia = dataDenuncia;
	}
    
}
