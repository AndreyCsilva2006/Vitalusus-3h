package br.itb.projeto.vitalususPlus.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "Banco")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "campo não preenchido")
    @Column(name="numeroCartao", nullable = false)
    private String numeroCartao;

    @OneToOne
    @JoinColumn(name="treinador_id", nullable = false)
    private Treinador treinaodor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Treinador getTreinaodor() {
		return treinaodor;
	}

	public void setTreinaodor(Treinador treinaodor) {
		this.treinaodor = treinaodor;
	}
    
    
}
