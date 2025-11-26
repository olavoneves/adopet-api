package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.dto.CadastrarPetDTO;
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

    public List<Abrigo> listar() {
        return repository.findAll();
    }

    public void cadastrar(CadastrarAbrigoDTO dto) {
        validationsCadastrarAbrigo.forEach(validation -> validation.validar(dto));

        Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());

        repository.save(abrigo);
    }

    public List<Pet> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            return repository.getReferenceById(id).getPets();

        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException(enfe.getMessage());

        } catch (NumberFormatException e) {
            try {
                return repository.findByNome(idOuNome).getPets();

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
