package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizarTutorDTO;
import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.IValidationCadastrarTutor;
import br.com.alura.adopet.api.validations.ValidationEmailTutorJaCadastrado;
import br.com.alura.adopet.api.validations.ValidationTelefoneTutorJaCadastrado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository tutorRepository;

    @Spy
    private List<IValidationCadastrarTutor> validationsTutor = new ArrayList<>();

    @Mock
    private ValidationEmailTutorJaCadastrado validation01;

    @Mock
    private ValidationTelefoneTutorJaCadastrado validation02;

    @Mock
    private Tutor tutor;

    private CadastrarTutorDTO cadastrarTutorDTO;

    private AtualizarTutorDTO atualizarTutorDTO;

    @Captor
    private ArgumentCaptor<Tutor> tutorCaptor;

    @Test
    @DisplayName("Deveria validar regras de negócio")
    void validarCadastrarTutorCenario01() {

        this.cadastrarTutorDTO = new CadastrarTutorDTO("José", "11900075643", "jose@gmail.com");

        validationsTutor.add(validation01);
        validationsTutor.add(validation02);

        tutorService.cadastrar(cadastrarTutorDTO);

        then(validation01).should().validar(cadastrarTutorDTO);
        then(validation02).should().validar(cadastrarTutorDTO);
    }

    @Test
    @DisplayName("Deveria cadastrar tutor")
    void validarCadastrarTutorCenario02() {

        this.cadastrarTutorDTO = new CadastrarTutorDTO("José", "11900075643", "jose@gmail.com");

        tutorService.cadastrar(cadastrarTutorDTO);

        then(tutorRepository).should().save(tutorCaptor.capture());

        Tutor tutorSalvo = tutorCaptor.getValue();
        assertEquals(cadastrarTutorDTO.nome(), tutorSalvo.getNome());
        assertEquals(cadastrarTutorDTO.telefone(), tutorSalvo.getTelefone());
        assertEquals(cadastrarTutorDTO.email(), tutorSalvo.getEmail());
    }

    @Test
    @DisplayName("Deveria atualizar tutor")
    void validarAtualizarTutorCenario01() {

        // ARRANGE
        List<Adocao> adocoes = Arrays.asList(
                new Adocao(), new Adocao(), new Adocao(), new Adocao()
        );

        this.atualizarTutorDTO = new AtualizarTutorDTO(10L, adocoes);

        given(tutorRepository.getReferenceById(atualizarTutorDTO.idTutor())).willReturn(tutor);

        // ACT
        tutorService.atualizar(atualizarTutorDTO);

        // ASSERT
        then(tutor).should().atualizar(atualizarTutorDTO);
        then(tutorRepository).should().save(tutorCaptor.capture());
    }

}