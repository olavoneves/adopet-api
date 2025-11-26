package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDTO;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.dto.CadastrarPetDTO;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validations.IValidationCadastrarAbrigo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private List<IValidationCadastrarAbrigo> validationsCadastrarAbrigo;

    public List<AbrigoDTO> listar() {
        List<Abrigo> abrigos = repository.findAll();
        List<AbrigoDTO> abrigosDTO = new ArrayList<>();
        for (Abrigo abrigo : abrigos) {
            AbrigoDTO abrigoDTO = new AbrigoDTO(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail(), abrigo.getPets());
            abrigosDTO.add(abrigoDTO);
        }

        return abrigosDTO;
    }

    public void cadastrar(CadastrarAbrigoDTO dto) {
        validationsCadastrarAbrigo.forEach(validation -> validation.validar(dto));
        Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());
        repository.save(abrigo);
    }

    public List<PetDTO> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = repository.getReferenceById(id).getPets();
            List<PetDTO> petsDTOS = new ArrayList<>();
            for (Pet pet : pets) {
                PetDTO petDTO = new PetDTO(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado(), pet.getAbrigo(), pet.getAdocao());
                petsDTOS.add(petDTO);
            }
            return petsDTOS;

        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException(enfe.getMessage());

        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = repository.findByNome(idOuNome).getPets();
                List<PetDTO> petsDTOS = new ArrayList<>();
                for (Pet pet : pets) {
                    PetDTO petDTO = new PetDTO(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado(), pet.getAbrigo(), pet.getAdocao());
                    petsDTOS.add(petDTO);
                }
                return petsDTOS;

            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException(enfe.getMessage());
            }
        }
    }

    public void cadastrarPet(String idOuNome, CadastrarPetDTO dto) {
        Abrigo abrigo;

        boolean isNumero = true;
        Long id = null;

        try {
            id = Long.parseLong(idOuNome);
        } catch (NumberFormatException e) {
            isNumero = false;
        }

        if (isNumero) {
            try {
                abrigo = repository.getReferenceById(id);
            } catch (EntityNotFoundException e) {
                throw new ValidacaoException(e.getMessage());
            }
        } else {
            try {
                abrigo = repository.findByNome(idOuNome);
            } catch (EntityNotFoundException e) {
                throw new ValidacaoException(e.getMessage());
            }
        }

        Pet pet = new Pet(dto.tipo(), dto.nome(), dto.raca(), dto.idade(), dto.cor(), dto.peso(), abrigo);
        abrigo.getPets().add(pet);

        petRepository.save(pet);
        repository.save(abrigo);
    }
}
