package br.itb.projeto.vitalususPlus.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Videoaula")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Videoaula {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@NotBlank(message = "campo não preenchido")
	private String titulo;
	private long likes;
	private long deslikes;
	
	@Lob
	private byte[] video;

	@Lob
	private byte[] thumbnail;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToMany
	@JoinTable(name="Aluno_videoaula",
			joinColumns = {@JoinColumn(name="videoaula_id")},
			inverseJoinColumns = {@JoinColumn(name="aluno_id")})
	private List<Aluno> alunos;
	private Integer visualizacoes;

	@ManyToOne
	@JoinColumn(name = "canal_id")
	private Canal canal;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany
	@JoinTable(name="Likes",
			joinColumns = {@JoinColumn(name="videoaula_id")},
			inverseJoinColumns = {@JoinColumn(name="aluno_id")}
	)
	private List<Aluno> alunosLikes;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany
	@JoinTable(name="Deslikes",
			joinColumns = {@JoinColumn(name="videoaula_id")},
			inverseJoinColumns = {@JoinColumn(name="aluno_id")}
	)
	private List<Aluno> alunosDeslikes;
	
	private LocalDateTime dataPubli;
	
	private String categoria;
	
	private String tipoVideoaula;

	@JsonIgnore
	@OneToMany(mappedBy = "videoaula", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comentario> comentarios;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public long getDeslikes() {
		return deslikes;
	}

	public void setDeslikes(long deslikes) {
		this.deslikes = deslikes;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Integer getVisualizacoes() {
		return visualizacoes;
	}

	public void setVisualizacoes(Integer visualizacoes) {
		this.visualizacoes = visualizacoes;
	}

	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbail) {
		this.thumbnail = thumbail;
	}

	public byte[] getVideo() {
		return video;
	}

	public void setVideo(byte[] video) {
		this.video = video;
	}

	public List<Aluno> getAlunosLikes() {
		return alunosLikes;
	}

	public void setAlunosLikes(List<Aluno> alunosLikes) {
		this.alunosLikes = alunosLikes;
	}

	public List<Aluno> getAlunosDeslikes() {
		return alunosDeslikes;
	}

	public void setAlunosDeslikes(List<Aluno> alunosDeslikes) {
		this.alunosDeslikes = alunosDeslikes;
	}

	public LocalDateTime getDataPubli() {
		return dataPubli;
	}

	public void setDataPubli(LocalDateTime dataPubli) {
		this.dataPubli = dataPubli;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTipoVideoaula() {
		return tipoVideoaula;
	}

	public void setTipoVideoaula(String tipoVideoaula) {
		this.tipoVideoaula = tipoVideoaula;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}


	
	
}
