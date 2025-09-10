package br.com.alura.adopet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.adopet.api.dto.AbrigoInputDto;
import br.com.alura.adopet.api.dto.AbrigoResponseDto;
import br.com.alura.adopet.api.dto.PetInputDto;
import br.com.alura.adopet.api.dto.PetResponseDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService service;

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<AbrigoResponseDto>> listar() {
        List<AbrigoResponseDto> abrigos = service.listar();
        return ResponseEntity.ok(abrigos);
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody @Valid AbrigoInputDto dto) {

        try {
            service.cadastrar(dto);
            return ResponseEntity.ok().body("Abrigo cadastrado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetResponseDto>> listarPets(@PathVariable String idOuNome) {

        try {
            List<PetResponseDto> pets = service.listarPetsPorIdOuNome(idOuNome);
            return ResponseEntity.ok(pets);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid PetInputDto dto) {

        try {

            Abrigo abrigo = service.carregarAbrigo(idOuNome);
            petService.cadastrarPet(abrigo, dto);
            return ResponseEntity.ok().body("Pet cadastrado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
