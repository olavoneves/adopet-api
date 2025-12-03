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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationEmailTutorJaCadastradoTest {

    @InjectMocks
    private ValidationEmailTutorJaCadastrado validationEmailCadastrado;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private CadastrarTutorDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso email exista")
    void validarCenario01() {

        String email = "tutor@gmail.com";
        when(dto.email()).thenReturn(email);
        given(tutorRepository.existsByEmail(email)).willReturn(true);

        assertThrows(ValidacaoException.class, () -> validationEmailCadastrado.validar(dto));

    }

    @Test
    @DisplayName("Não deveria retornar exception caso email não exista")
    void validarCenario02() {

        String email = "tutor@gmail.com";
        when(dto.email()).thenReturn(email);
        given(tutorRepository.existsByEmail(email)).willReturn(false);

        assertDoesNotThrow(() -> validationEmailCadastrado.validar(dto));

    }

}