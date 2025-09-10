package br.com.alura.adopet.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.AbrigoInputDto;
import br.com.alura.adopet.api.dto.AbrigoResponseDto;
import br.com.alura.adopet.api.dto.PetResponseDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private PetRepository petRepository;

    public List<AbrigoResponseDto> listar() {
        return repository
                .findAll()
                .stream()
                .map(AbrigoResponseDto::new)
                .toList();
    }

    public void cadastrar(AbrigoInputDto dto) {
        boolean jaCadastrado = repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email());

        if (jaCadastrado) {
            throw new ValidationException("Dados já cadastrados para outro abrigo!");
        }

        repository.save(new Abrigo(dto));
    }

    public List<PetResponseDto> listarPetsPorIdOuNome(String idOuNome) {

        Abrigo abrigo = carregarAbrigo(idOuNome);

        return petRepository.findByAbrigo(abrigo)
                .stream()
                .map(PetResponseDto::new)
                .toList();
    }


    public Abrigo carregarAbrigo(String idOuNome) {
        Abrigo abrigo;

        try {
            Long id = Long.parseLong(idOuNome);
            abrigo = repository.findById(id).orElseThrow(() -> new ValidationException("Abrigo não encontrado!"));
        } catch (NumberFormatException e) {
            abrigo = repository.findByNome(idOuNome);
        }

        return abrigo;
    }
}
