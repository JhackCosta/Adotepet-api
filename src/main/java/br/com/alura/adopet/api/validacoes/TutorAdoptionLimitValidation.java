package br.com.alura.adopet.api.validacoes;

import org.springframework.stereotype.Component;

import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;

@Component
public class TutorAdoptionLimitValidation implements AdoptionRequestValidation {

    private static final int MAX_ADOPTION_LIMIT = 5;
    private static final String ERROR_MESSAGE = "Tutor has reached the maximum limit of %d adoptions!";

    private final AdocaoRepository adocaoRepository;

    public TutorAdoptionLimitValidation(AdocaoRepository adocaoRepository) {
        this.adocaoRepository = adocaoRepository;
    }

    @Override
    public void validate(AdocaoRequestDto dto) {
        long approvedAdoptionsCount = adocaoRepository
                .countByTutor_IdAndStatus(dto.tutorId(), StatusAdocao.APROVADO);

        if (approvedAdoptionsCount >= MAX_ADOPTION_LIMIT) {
            throw new ValidationException(
                String.format(ERROR_MESSAGE, MAX_ADOPTION_LIMIT)
            );
        }
    }
}
