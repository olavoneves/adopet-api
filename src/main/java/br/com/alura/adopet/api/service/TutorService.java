package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizarTutorDTO;
import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.IValidationCadastrarTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Autowired
    private List<IValidationCadastrarTutor> validationsCadastrarTutor;

    public void cadastrar(CadastrarTutorDTO dto) {
        validationsCadastrarTutor.forEach(validation -> validation.validar(dto));
        Tutor tutor = new Tutor(dto);
        repository.save(tutor);
    }

    public void atualizar(AtualizarTutorDTO dto) {
        Tutor tutor = repository.getReferenceById(dto.idTutor());
        tutor.atualizar(dto);
        repository.save(tutor);
    }
}
