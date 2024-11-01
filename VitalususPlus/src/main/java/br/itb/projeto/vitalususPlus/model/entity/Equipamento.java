package br.itb.projeto.vitalususPlus.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Equipamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="patrocinador_id")
    private Patrocinador patrocinador;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String statusEquipamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patrocinador getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatusEquipamento() {
        return statusEquipamento;
    }

    public void setStatusEquipamento(String statusEquipamento) {
        this.statusEquipamento = statusEquipamento;
    }
}
