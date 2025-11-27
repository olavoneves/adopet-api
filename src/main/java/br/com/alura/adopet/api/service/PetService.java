package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<PetDTO> listarTodosDisponiveis() {
        return repository
                .findAllByAdotadoFalse()
                .stream()
                .map(pet -> new PetDTO(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado(), pet.getAbrigo(), pet.getAdocao()))
                .toList();
    }
}
