package br.com.alura.adopet.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.PetInputDto;
import br.com.alura.adopet.api.dto.PetResponseDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<PetResponseDto> listarTodosDisponiveis() {
        return repository
                .findAllByAdotadoFalse()
                .stream()
                .map(PetResponseDto::new)
                .toList();
    }

    public void cadastrarPet(Abrigo abrigo, PetInputDto dto) {
        repository.save(new Pet(dto, abrigo));
    }

}
