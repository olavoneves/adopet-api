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
        return repository
                .findAll()
                .stream()
                .map(abrigo -> new AbrigoDTO(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail(), abrigo.getPets()))
                .toList();
    }

    public void cadastrar(CadastrarAbrigoDTO dto) {
        validationsCadastrarAbrigo.forEach(validation -> validation.validar(dto));
        Abrigo abrigo = new Abrigo(dto);
        repository.save(abrigo);
    }

    public List<PetDTO> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            return repository
                    .getReferenceById(id).getPets()
                    .stream()
                    .map(pet -> new PetDTO(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado(), pet.getAbrigo(), pet.getAdocao()))
                    .toList();

        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException(enfe.getMessage());

        } catch (NumberFormatException e) {
            try {
                return repository
                        .findByNome(idOuNome).getPets()
                        .stream()
                        .map(pet -> new PetDTO(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado(), pet.getAbrigo(), pet.getAdocao()))
                        .toList();

            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException(enfe.getMessage());
            }
        }
    }

    public void cadastrarPet(String idOuNome, CadastrarPetDTO dto) {
        Abrigo abrigo;
        Pet pet;

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
                pet = new Pet(dto, abrigo);
                abrigo.getPets().add(pet);

            } catch (EntityNotFoundException e) {
                throw new ValidacaoException(e.getMessage());
            }
        } else {
            try {
                abrigo = repository.findByNome(idOuNome);
                pet = new Pet(dto, abrigo);
                abrigo.getPets().add(pet);

            } catch (EntityNotFoundException e) {
                throw new ValidacaoException(e.getMessage());
            }
        }

        petRepository.save(pet);
        repository.save(abrigo);
    }
}
