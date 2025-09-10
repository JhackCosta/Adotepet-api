package br.com.alura.adopet.api.validacoes;

import org.springframework.stereotype.Component;

import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;

@Component
public class PetPendingAdoptionValidation implements AdoptionRequestValidation {

    private final AdocaoRepository repository;

    public PetPendingAdoptionValidation(AdocaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(AdocaoRequestDto dto) {

        boolean petHasPendingAdoption = repository
                .existsByPetIdAndStatus(dto.petId(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petHasPendingAdoption) {
            throw new ValidationException("Pet is already waiting for evaluation to be adopted!");
        }
    }

}
