package br.com.alura.adopet.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    public List<Abrigo> listar() {
        return repository.findAll();
    }

    @Transactional
    public void cadastrar(Abrigo abrigo) {
        boolean nomeJaCadastrado = repository.existsByNome(abrigo.getNome());
        boolean telefoneJaCadastrado = repository.existsByTelefone(abrigo.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(abrigo.getEmail());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            repository.save(abrigo);
        }
    }

    public List<Pet> listarPets(String idOuNome) {

        List<Pet> pets;

        try {
            Long id = Long.parseLong(idOuNome);
            pets = repository.getReferenceById(id).getPets();
            return pets;
        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException("Abrigo não encontrado");
        } catch (NumberFormatException e) {
            try {
                pets = repository.findByNome(idOuNome).getPets();
                return pets;
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Abrigo não encontrado");
            }
        }
    }

    @Transactional
    public void cadastrarPet(String idOuNome,Pet pet) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);
            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            repository.save(abrigo);
        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException("Abrigo não encontrado");
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = repository.findByNome(idOuNome);
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                repository.save(abrigo);
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Abrigo não encontrado");
            }
        }
    }

}
