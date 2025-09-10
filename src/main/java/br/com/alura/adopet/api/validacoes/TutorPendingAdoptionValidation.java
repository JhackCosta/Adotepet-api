package br.com.alura.adopet.api.validacoes;

import org.springframework.stereotype.Component;

import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.TutorRepository;

@Component
public class TutorPendingAdoptionValidation implements AdoptionRequestValidation {

    private final TutorRepository tutorRepository;

    public TutorPendingAdoptionValidation(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public void validate(AdocaoRequestDto dto) {

        boolean tutorWaitingEvaluation = tutorRepository
                .existsByIdAndAdocoesStatus(dto.tutorId(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (tutorWaitingEvaluation) {
            throw new ValidationException("Tutor already has another adoption waiting for evaluation!");
        }
    }

}
