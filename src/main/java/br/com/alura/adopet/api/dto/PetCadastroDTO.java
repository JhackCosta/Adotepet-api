package br.com.alura.adopet.api.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetCadastroDTO(
        @NotNull TipoPet tipo,

        @NotBlank String nome,

        @NotBlank String raca,

        @NotNull Integer idade,

        @NotBlank String cor,

        @NotNull Float peso,
        Boolean adotado,

        @JsonBackReference("abrigo_pets") Abrigo abrigo,

        @JsonBackReference("adocao_pets") Adocao adocao) {
}
