package br.itb.projeto.vitalususPlus.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Aluno_segue_canal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Seguidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="seguidor_id")
    private Aluno aluno;

    @OneToOne
    @JoinColumn(name="canal_id")
    private Canal canal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }
}
