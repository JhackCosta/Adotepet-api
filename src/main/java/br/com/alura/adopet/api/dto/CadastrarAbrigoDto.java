package br.com.alura.adopet.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.alura.adopet.api.model.Pet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastrarAbrigoDto(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}") String telefone,
        @NotBlank @Email String email,
        @JsonManagedReference("abrigo_pets") List<Pet> pets) {}
