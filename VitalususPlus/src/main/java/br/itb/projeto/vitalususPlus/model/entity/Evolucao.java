package br.itb.projeto.vitalususPlus.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Evolucao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Evolucao{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="imc", nullable = false)
	private float imc;

	@Column(name = "met_basal", nullable = false)
	private float metBasal;

	@Column(name = "peso_atual",nullable = false)
	private float pesoAtual;

	@Column(name= "altura_atual", nullable = false)
	private float alturaAtual;

	@OneToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getImc() {
		return imc;
	}

	public void setImc(float imc) {
		this.imc = imc;
	}

	public float getMetBasal() {
		return metBasal;
	}

	public void setMetBasal(float metBasal) {
		this.metBasal = metBasal;
	}

	public float getPesoAtual() {
		return pesoAtual;
	}

	public void setPesoAtual(float pesoAtual) {
		this.pesoAtual = pesoAtual;
	}

	public float getAlturaAtual() {
		return alturaAtual;
	}

	public void setAlturaAtual(float alturaAtual) {
		this.alturaAtual = alturaAtual;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	

}
