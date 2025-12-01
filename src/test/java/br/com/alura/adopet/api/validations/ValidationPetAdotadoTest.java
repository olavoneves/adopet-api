package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidationPetAdotadoTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private ValidationPetAdotado validation;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDTO dto;

    @Test
    @DisplayName("Deveria permitir a solicitação de adoção do pet")
    void validarCenario01() {

        // ARRANGE
        given(petRepository.getReferenceById(pet.getId())).willReturn(pet);
        given(pet.getAdotado()).willReturn(false);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validation.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir a solicitação de adoção do pet")
    void validarCenario02() {

        // ARRANGE
        given(petRepository.getReferenceById(pet.getId())).willReturn(pet);
        given(pet.getAdotado()).willReturn(true);

        // ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validation.validar(dto));
    }

}