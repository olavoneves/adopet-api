package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationNomeAbrigoJaCadastrado implements IValidationCadastrarAbrigo {

    @Autowired
    private AbrigoRepository repository;

    @Override
    public void validar(CadastrarAbrigoDTO dto) {
        boolean nomeJaCadastrado = repository.existsByNome(dto.nome());

        if (nomeJaCadastrado) {
            throw new ValidacaoException("Nome já cadastrado em outro abrigo");
        }
    }
}
