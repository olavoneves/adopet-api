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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationPetAdotadoTest {

    @InjectMocks
    private ValidationPetAdotado validation;

    @Mock
    private PetRepository petRepository;

    @Mock
    private SolicitacaoAdocaoDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso pet já tenha sido adotado")
    void validarCenario01() {

        // ARRANGE
        Long idPet = 1L;
        when(dto.idPet()).thenReturn(idPet);
        given(petRepository.existsByIdAndAdotadoTrue(idPet)).willReturn(true);


        // ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validation.validar(dto));
    }

    @Test
    @DisplayName("Deveria retornar exception caso pet não exista")
    void validarCenario02() {

        // ARRANGE
        Long idPet = 1L;
        when(dto.idPet()).thenReturn(idPet);
        given(petRepository.existsById(idPet)).willReturn(false);


        // ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validation.validar(dto));
    }

}