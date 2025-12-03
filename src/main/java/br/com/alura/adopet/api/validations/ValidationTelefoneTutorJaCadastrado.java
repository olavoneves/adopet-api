package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationTelefoneTutorJaCadastrado implements IValidationCadastrarTutor {

    @Autowired
    private TutorRepository repository;

    @Override
    public void validar(CadastrarTutorDTO dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());

        if (telefoneJaCadastrado) {
            throw new ValidacaoException("Telefone já está cadastrado para outro Tutor");
        }
    }
}
