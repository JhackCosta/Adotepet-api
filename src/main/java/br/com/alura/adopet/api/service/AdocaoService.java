package br.com.alura.adopet.api.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.AdocaoNegativeResponseDto;
import br.com.alura.adopet.api.dto.AdocaoPositveResponseDto;
import br.com.alura.adopet.api.dto.AdocaoRequestDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.AdoptionRequestValidation;

@Service
public class AdocaoService {

    private final AdocaoRepository repository;
    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;
    private final EmailService emailService;
    private final List<AdoptionRequestValidation> validations;

    public AdocaoService(AdocaoRepository repository, PetRepository petRepository,
                          TutorRepository tutorRepository, EmailService emailService,
                          List<AdoptionRequestValidation> validations) {
        this.repository = repository;
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
        this.emailService = emailService;
        this.validations = validations;
    }

    public void requestAdoption(AdocaoRequestDto dto) {

        Pet pet = petRepository.getReferenceById(dto.petId());
        Tutor tutor = tutorRepository.getReferenceById(dto.tutorId());

        validations.forEach(v -> v.validate(dto));

        Adocao adocao = new Adocao(tutor, pet, dto.reason());

        repository.save(adocao);

        emailService.sendEmail(
                adocao.getPet().getShelter().getEmail(),
                "Adoption Request",
                "Hello " + adocao.getPet().getShelter().getName()
                        + "!\n\nAn adoption request was registered today for the pet: " + adocao.getPet().getName()
                        + ". \nPlease evaluate for approval or rejection.");

    }

    public void approveAdoption(AdocaoPositveResponseDto dto) {
        Adocao adocao = repository.getReferenceById(dto.adoptionId());
        adocao.markAsApproved();

        emailService.sendEmail(
                adocao.getPet().getShelter().getEmail(),
                "Adoption Approved",
                "Congratulations " + adocao.getTutor().getName() + "!\n\nYour adoption of pet " + adocao.getPet().getName()
                        + ", requested on "
                        + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        + ", has been approved.\nPlease contact the shelter "
                        + adocao.getPet().getShelter().getName() + " to schedule picking up your pet.");

    }

    public void rejectAdoption(AdocaoNegativeResponseDto dto) {

        Adocao adocao = repository.getReferenceById(dto.adoptionId());
        adocao.markAsRejected(dto.justification());

        emailService.sendEmail(
        adocao.getPet().getShelter().getEmail(),
        "Adoption Rejected",
        "Hello " + adocao.getTutor().getName() + "!\n\nUnfortunately your adoption of pet " + adocao.getPet().getName()
                + ", requested on " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                + ", has been rejected by shelter " + adocao.getPet().getShelter().getName()
                + " with the following justification: " + adocao.getJustificationStatus());

    }

}
