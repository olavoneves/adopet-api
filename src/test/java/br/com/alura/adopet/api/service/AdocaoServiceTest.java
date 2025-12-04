package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.IValidationSolicitacaoAdocao;
import br.com.alura.adopet.api.validations.ValidationPetAdotado;
import br.com.alura.adopet.api.validations.ValidationTutorAguardando;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Spy
    private List<IValidationSolicitacaoAdocao> validationSolicitacaoAdocaos = new ArrayList<>();

    @Mock
    private ValidationPetAdotado validador01;

    @Mock
    private ValidationTutorAguardando validador02;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDTO dto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    @DisplayName("Deveria validar regras de negócio")
    void validarSolicitacaoCenario01() {

        // ARRANGE
        this.dto = new SolicitacaoAdocaoDTO(10l, 20l, "Motivo Qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        validationSolicitacaoAdocaos.add(validador01);
        validationSolicitacaoAdocaos.add(validador02);

        // ACT
        adocaoService.solicitar(dto);

        // ASSERT
        then(validador01).should().validar(dto);
        then(validador02).should().validar(dto);
    }

    @Test
    @DisplayName("Deveria salvar adoção no banco de dados")
    void validarSolicitacaoCenario02() {

        // ARRANGE
        this.dto = new SolicitacaoAdocaoDTO(10l, 20l, "Motivo Qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        // ACT
        adocaoService.solicitar(dto);

        // ASSERT
        then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        assertEquals(pet, adocaoSalva.getPet());
        assertEquals(tutor, adocaoSalva.getTutor());
        assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

}