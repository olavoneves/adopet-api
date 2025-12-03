package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
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
class ValidationPetAguardandoTest {

    @InjectMocks
    private ValidationPetAguardando validation;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso pet esteja aguardando avaliação")
    void validarCenario01() {

        Long idPet = 1L;
        when(dto.idPet()).thenReturn(idPet);
        given(adocaoRepository.existsByPetIdAndStatus(idPet, StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        assertThrows(ValidacaoException.class, () -> validation.validar(dto));

    }

    @Test
    @DisplayName("Não deveria retornar exception caso pet não esteja aguardando avaliação")
    void validarCenario02() {

        Long idPet = 1L;
        when(dto.idPet()).thenReturn(idPet);
        given(adocaoRepository.existsByPetIdAndStatus(idPet, StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        assertDoesNotThrow(() -> validation.validar(dto));

    }

}