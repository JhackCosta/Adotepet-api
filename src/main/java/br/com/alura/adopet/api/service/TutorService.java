package br.com.alura.adopet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.AtualizarCadastroTutorDto;
import br.com.alura.adopet.api.dto.CadastrarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public void cadastrar(CadastrarTutorDto dto) {

        Tutor tutor = new Tutor();
        tutor.setNome(dto.nome());
        tutor.setTelefone(dto.telefone());
        tutor.setEmail(dto.email());
        tutor.setAdocoes(dto.adocoes());

        boolean telefoneJaCadastrado = repository.existsByTelefone(tutor.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(tutor.getEmail());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(tutor);
        }
    }

    public void atualizar(AtualizarCadastroTutorDto dto) {

        Tutor tutor = repository.getReferenceById(dto.id());

        tutor.setNome(dto.nome());
        tutor.setTelefone(dto.telefone());
        tutor.setEmail(dto.email());
        tutor.setAdocoes(dto.adocoes());

        repository.save(tutor);
    }

}
