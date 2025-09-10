package br.com.alura.adopet.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.adopet.api.dto.AdocaoNegativeResponseDto;
import br.com.alura.adopet.api.dto.AdocaoPositveResponseDto;
import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    private AdocaoService service;

    public AdocaoController(AdocaoService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid AdocaoRequestDto dto) {
        try {
            this.service.requestAdoption(dto);
            return ResponseEntity.ok("Adoção solicitada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AdocaoPositveResponseDto dto) {

        this.service.approveAdoption(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid AdocaoNegativeResponseDto dto) {

        this.service.rejectAdoption(dto);

        return ResponseEntity.ok().build();
    }

}
