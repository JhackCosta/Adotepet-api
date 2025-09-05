package br.com.alura.adopet.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;


@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<Pet> listarTodosDisponiveis() {

        List<Pet> pets = repository.findAll();
        List<Pet> disponiveis = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdotado()) {
                disponiveis.add(pet);
            }
        }

        return disponiveis;
    }

}
