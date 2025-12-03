package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidationTelefoneTutorJaCadastradoTest {

    @InjectMocks
    private ValidationTelefoneTutorJaCadastrado validationTelefoneCadastrado;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private CadastrarTutorDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso telefone exista")
    void validarCenario01() {

        given(tutorRepository.existsByTelefone(dto.telefone())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> validationTelefoneCadastrado.validar(dto));

    }

    @Test
    @DisplayName("Não deveria retornar exception caso telefone não exista")
    void validarCenario02() {

        given(tutorRepository.existsByTelefone(dto.telefone())).willReturn(false);

        assertDoesNotThrow(() -> validationTelefoneCadastrado.validar(dto));

    }

}