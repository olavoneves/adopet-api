package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidationPetAdotadoTest {

    @InjectMocks
    private ValidationPetAdotado validation;

    @Mock
    private PetRepository petRepository;

    @Mock
    private SolicitacaoAdocaoDTO dto;

    @Test
    @DisplayName("Deveria permitir a solicitação de adoção do pet")
    void validarCenario01() {

        // ARRANGE
        given(petRepository.existsByIdAndAdotadoTrue(dto.idPet())).willReturn(false);

        // ASSERT + ACT
        assertDoesNotThrow(() -> validation.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir a solicitação de adoção do pet")
    void validarCenario02() {

        // ARRANGE
        given(petRepository.existsByIdAndAdotadoTrue(dto.idPet())).willReturn(true);


        // ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validation.validar(dto));
    }

}