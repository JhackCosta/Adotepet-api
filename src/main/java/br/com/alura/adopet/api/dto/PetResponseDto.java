package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

public record PetResponseDto(Long id, TipoPet type, String name, String breed, Integer age) {

    public PetResponseDto(Pet pet) {
        this(pet.getId(), pet.getType(), pet.getName(), pet.getBreed(), pet.getAge());
    }

}
