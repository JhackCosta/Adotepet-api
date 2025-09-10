package br.com.alura.adopet.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "adocoes")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tutor tutor;

    @OneToOne(fetch = FetchType.LAZY)
    private Pet pet;

    private String motivo;

    @Enumerated(EnumType.STRING)
    private StatusAdocao status;

    private String justificativaStatus;

    public Adocao(){}

    public Adocao(Tutor tutor, Pet pet, String motivo) {
        this.tutor = tutor;
        this.pet = pet;
        this.motivo = motivo;
        this.status = StatusAdocao.AGUARDANDO_AVALIACAO;
        this.data = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Adocao adocao = (Adocao) o;
        return Objects.equals(id, adocao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public LocalDateTime getDate() {
        return data;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getReason() {
        return motivo;
    }

    public StatusAdocao getStatus() {
        return status;
    }

    public String getJustificativaStatus() {
        return justificativaStatus;
    }

    public String getJustificationStatus() {
        return justificativaStatus;
    }

    public Pet getPet() {
        return pet;
    }

    public void marcarComoAprovado() {
       this.status = StatusAdocao.APROVADO;
    }

    public void markAsApproved() {
       this.status = StatusAdocao.APROVADO;
    }

    public void marcarComoReprovado(String justificativa) {
       this.status = StatusAdocao.REPROVADO;
       this.justificativaStatus = justificativa;
    }

    public void markAsRejected(String justification) {
       this.status = StatusAdocao.REPROVADO;
       this.justificativaStatus = justification;
    }
}
