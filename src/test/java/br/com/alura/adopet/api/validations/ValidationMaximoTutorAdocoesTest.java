package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationMaximoTutorAdocoesTest {

    @InjectMocks
    private ValidationMaximoTutorAdocoes validationMaxAdocoes;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDTO dto;

    @Test
    @DisplayName("Deveria retornar exception caso existam mais de 5 adoções para o tutor")
    void validarCenario01() {

        // ARRANGE + ACT
        Long idTutor = 1L;
        List<Adocao> adocoes = Arrays.asList(
                new Adocao(), new Adocao(), new Adocao(), new Adocao(), new Adocao(), new Adocao()
        );

        when(dto.idTutor()).thenReturn(idTutor);
        when(adocaoRepository.findByTutorIdAndStatus(idTutor, StatusAdocao.APROVADO)).thenReturn(adocoes);

        // ASSERT
        assertThrows(ValidacaoException.class, () -> validationMaxAdocoes.validar(dto));

    }

    @Test
    @DisplayName("Não deveria retornar exception caso existam menos de 5 adoções para o tutor")
    void validarCenario02() {

        // ARRANGE + ACT
        Long idTutor = 1L;
        List<Adocao> adocoes = Arrays.asList(
                new Adocao(), new Adocao(), new Adocao(), new Adocao()
        );

        when(dto.idTutor()).thenReturn(idTutor);
        when(adocaoRepository.findByTutorIdAndStatus(idTutor, StatusAdocao.APROVADO)).thenReturn(adocoes);

        // ASSERT
        assertDoesNotThrow(() -> validationMaxAdocoes.validar(dto));

    }

    @Test
    @DisplayName("Deveria retornar exception caso existam exatamente 5 adoções para o tutor")
    void validarCenario03() {

        // ARRANGE + ACT
        Long idTutor = 1L;
        List<Adocao> adocoes = Arrays.asList(
                new Adocao(), new Adocao(), new Adocao(), new Adocao(), new Adocao()
        );

        when(dto.idTutor()).thenReturn(idTutor);
        when(adocaoRepository.findByTutorIdAndStatus(idTutor, StatusAdocao.APROVADO)).thenReturn(adocoes);

        // ASSERT
        assertThrows(ValidacaoException.class, () -> validationMaxAdocoes.validar(dto));

    }

}