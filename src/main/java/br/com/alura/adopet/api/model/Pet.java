package br.com.alura.adopet.api.model;

import java.util.Objects;

import br.com.alura.adopet.api.dto.PetInputDto;
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
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    private String nome;

    private String raca;

    private Integer idade;

    private String cor;

    private Float peso;

    private boolean adotado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private Adocao adocao;

    public Pet(PetInputDto dto, Abrigo abrigo) {
        this.tipo = dto.tipo();
        this.nome = dto.nome();
        this.raca = dto.raca();
        this.idade = dto.idade();
        this.cor = dto.cor();
        this.peso = dto.peso();
        this.adotado = false;
        this.abrigo = abrigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPet getType() {
        return tipo;
    }

    public void setType(TipoPet tipo) {
        this.tipo = tipo;
    }

    public String getName() {
        return nome;
    }

    public void setName(String nome) {
        this.nome = nome;
    }

    public String getBreed() {
        return raca;
    }

    public void setBreed(String raca) {
        this.raca = raca;
    }

    public Integer getAge() {
        return idade;
    }

    public void setAge(Integer idade) {
        this.idade = idade;
    }

    public String getColor() {
        return cor;
    }

    public void setColor(String cor) {
        this.cor = cor;
    }

    public Float getWeight() {
        return peso;
    }

    public void setWeight(Float peso) {
        this.peso = peso;
    }

    public boolean getAdopted() {
        return adotado;
    }

    public void setAdopted(boolean adotado) {
        this.adotado = adotado;
    }

    public Abrigo getShelter() {
        return abrigo;
    }

    public void setShelter(Abrigo abrigo) {
        this.abrigo = abrigo;
    }

    public Adocao getAdoption() {
        return adocao;
    }

    public void setAdoption(Adocao adocao) {
        this.adocao = adocao;
    }
}
