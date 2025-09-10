package br.com.alura.adopet.api.validacoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @Mock
    private AdocaoRepository adocaoRepository;

    @InjectMocks
    private TutorAdoptionLimitValidation validacao;

    @Test
    void shouldNotThrowExceptionWhenTutorHasLessThan5Adoptions() {
        // Given
        AdocaoRequestDto dto = new AdocaoRequestDto(1L, 1L, "Adoption reason");
        when(adocaoRepository.countByTutor_IdAndStatus(1L, StatusAdocao.APROVADO))
                .thenReturn(3L);

        // When & Then
        assertDoesNotThrow(() -> validacao.validate(dto));
    }

    @Test
    void shouldThrowExceptionWhenTutorHas5ApprovedAdoptions() {
        // Given
        AdocaoRequestDto dto = new AdocaoRequestDto(1L, 1L, "Adoption reason");
        when(adocaoRepository.countByTutor_IdAndStatus(1L, StatusAdocao.APROVADO))
                .thenReturn(5L);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validacao.validate(dto));

        assert(exception.getMessage().contains("Tutor has reached the maximum limit of 5 adoptions!"));
    }

    @Test
    void shouldThrowExceptionWhenTutorHasMoreThan5ApprovedAdoptions() {
        // Given
        AdocaoRequestDto dto = new AdocaoRequestDto(2L, 2L, "Another reason");
        when(adocaoRepository.countByTutor_IdAndStatus(2L, StatusAdocao.APROVADO))
                .thenReturn(7L);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validacao.validate(dto));

        assert(exception.getMessage().contains("Tutor has reached the maximum limit of 5 adoptions!"));
    }

    @Test
    void shouldNotThrowExceptionWhenTutorHasNoApprovedAdoptions() {
        // Given
        AdocaoRequestDto dto = new AdocaoRequestDto(3L, 3L, "First pet");
        when(adocaoRepository.countByTutor_IdAndStatus(3L, StatusAdocao.APROVADO))
                .thenReturn(0L);

        // When & Then
        assertDoesNotThrow(() -> validacao.validate(dto));
    }
}
