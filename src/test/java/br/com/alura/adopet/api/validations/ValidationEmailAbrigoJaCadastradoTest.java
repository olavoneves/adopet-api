package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
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
class ValidationEmailAbrigoJaCadastradoTest {

    @InjectMocks
    private ValidationEmailAbrigoJaCadastrado validationEmailCadastrado;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private CadastrarAbrigoDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso email exista")
    void validarCenario01() {

        // ARRANGE
        String email = "abrigo@gmail.com";
        when(dto.email()).thenReturn(email);
        given(abrigoRepository.existsByEmail(email)).willReturn(true);

        // ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validationEmailCadastrado.validar(dto));
    }

    @Test
    @DisplayName("Não deveria retornar exception caso email não exista")
    void validarCenario02() {

        // ARRANGE
        String email = "abrigo@gmail.com";
        when(dto.email()).thenReturn(email);
        given(abrigoRepository.existsByEmail(email)).willReturn(false);

        // ASSERT + ACT
        assertDoesNotThrow(() -> validationEmailCadastrado.validar(dto));
    }

}