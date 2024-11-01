package br.itb.projeto.vitalususPlus.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "Patrocinador")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Patrocinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String statusPatrocinador;

    private byte[] foto;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getStatusPatrocinador() {
        return statusPatrocinador;
    }

    public void setStatusPatrocinador(String statusPatrocinador) {
        this.statusPatrocinador = statusPatrocinador;
    }
}
