package br.com.alura.adopet.api.validacoes;

import org.springframework.stereotype.Component;

import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

@Component
public class PetAvailabilityValidation implements AdoptionRequestValidation {

    private final PetRepository petRepository;

    public PetAvailabilityValidation(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void validate(AdocaoRequestDto dto) {
        Pet pet = petRepository.getReferenceById(dto.petId());

        if (pet.getAdopted()) {
            throw new ValidationException("Pet has already been adopted!");
        }
    }

}
