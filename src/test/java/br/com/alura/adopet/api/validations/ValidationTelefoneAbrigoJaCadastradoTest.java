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
class ValidationTelefoneAbrigoJaCadastradoTest {

    @InjectMocks
    private ValidationTelefoneAbrigoJaCadastrado validationTelefoneCadastrado;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private CadastrarAbrigoDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso telefone exista")
    void validarCenario01() {

        // ARRANGE
        String telefone = "11966674354";
        when(dto.telefone()).thenReturn(telefone);
        given(abrigoRepository.existsByTelefone(telefone)).willReturn(true);

        // ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validationTelefoneCadastrado.validar(dto));
    }

    @Test
    @DisplayName("Não deveria retornar exception caso telefone não exista")
    void validarCenario02() {

        // ARRANGE
        String telefone = "11966674354";
        when(dto.telefone()).thenReturn(telefone);
        given(abrigoRepository.existsByTelefone(telefone)).willReturn(false);

        // ASSERT + ACT
        assertDoesNotThrow(() -> validationTelefoneCadastrado.validar(dto));
    }

}