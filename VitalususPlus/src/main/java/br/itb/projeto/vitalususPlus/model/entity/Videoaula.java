package br.itb.projeto.vitalususPlus.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
	
	@ManyToMany
	@JoinTable(name="aluno_videoaula",
			joinColumns = {@JoinColumn(name="videoaula_id")},
			inverseJoinColumns = {@JoinColumn(name="aluno_id")})
	private List<Aluno> alunos;
	private Integer visualizacoes;

	@OneToOne
	@JoinColumn(name = "canal_id")
	private Canal canal;

	@ManyToMany
	@JoinTable(name="Likes",
			joinColumns = {@JoinColumn(name="videoaula_id")},
			inverseJoinColumns = {@JoinColumn(name="aluno_id")}
	)
	private List<Aluno> alunosLikes;

	@ManyToMany
	@JoinTable(name="Deslikes",
			joinColumns = {@JoinColumn(name="videoaula_id")},
			inverseJoinColumns = {@JoinColumn(name="aluno_id")}
	)
	private List<Aluno> alunosDeslikes;

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
}
