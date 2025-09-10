package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.AdocaoRequestDto;

public interface AdoptionRequestValidation {

    void validate(AdocaoRequestDto dto);

}
