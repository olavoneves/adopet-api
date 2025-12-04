package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.dto.CadastrarPetDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validations.IValidationCadastrarAbrigo;
import br.com.alura.adopet.api.validations.ValidationEmailAbrigoJaCadastrado;
import br.com.alura.adopet.api.validations.ValidationNomeAbrigoJaCadastrado;
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
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Spy
    private List<IValidationCadastrarAbrigo> validationsAbrigo = new ArrayList<>();

    @Mock
    private ValidationEmailAbrigoJaCadastrado validation01;

    @Mock
    private ValidationNomeAbrigoJaCadastrado validation02;

    @Mock
    private Abrigo abrigo;

    private CadastrarAbrigoDTO cadastrarAbrigoDTO;

    private CadastrarPetDTO cadastrarPetDTO;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    @Captor
    private ArgumentCaptor<Pet> petCaptor;

    @Test
    @DisplayName("Deveria validar regras de negócio")
    void validarCadastroDeAbrigoCenario01() {

        // ARRANGE
        this.cadastrarAbrigoDTO = new CadastrarAbrigoDTO("Abrigo 300", "11900075643", "abrigo300@gmail.com");

        validationsAbrigo.add(validation01);
        validationsAbrigo.add(validation02);

        // ACT
        abrigoService.cadastrar(cadastrarAbrigoDTO);

        // ASSERT
        then(validation01).should().validar(cadastrarAbrigoDTO);
        then(validation02).should().validar(cadastrarAbrigoDTO);

    }

    @Test
    @DisplayName("Deveria cadastrar abrigo")
    void validarCadastroDeAbrigoCenario02() {

        // ARRANGE
        this.cadastrarAbrigoDTO = new CadastrarAbrigoDTO("Abrigo 300", "11900075643", "abrigo300@gmail.com");

        // ACT
        abrigoService.cadastrar(cadastrarAbrigoDTO);

        // ASSERT
        then(abrigoRepository).should().save(abrigoCaptor.capture());
        Abrigo abrigoSalvo = abrigoCaptor.getValue();
        assertEquals(cadastrarAbrigoDTO.nome(), abrigoSalvo.getNome());
        assertEquals(cadastrarAbrigoDTO.telefone(), abrigoSalvo.getTelefone());
        assertEquals(cadastrarAbrigoDTO.email(), abrigoSalvo.getEmail());

    }

    @Test
    @DisplayName("Deveria cadastrar pet")
    void validarCadastroDePetCenario01() {

        // ARRANGE
        this.cadastrarPetDTO = new CadastrarPetDTO(TipoPet.GATO, "Catchup", "gray", 2, "cinza", 10F);
        String idOuNome = "Abrigo 300";

        boolean isNumero = true;
        Long id = null;

        try {
            id = Long.parseLong(idOuNome);
        } catch (NumberFormatException e) {
            isNumero = false;
        }

        if (isNumero) {
            given(abrigoRepository.getReferenceById(id)).willReturn(abrigo);
        } else {
            given(abrigoRepository.findByNome(idOuNome)).willReturn(abrigo);
        }

        // ACT
        abrigoService.cadastrarPet(idOuNome, cadastrarPetDTO);

        // ASSERT
        then(petRepository).should().save(petCaptor.capture());
        then(abrigoRepository).should().save(abrigoCaptor.capture());

        Pet petSalvo = petCaptor.getValue();
        assertEquals(cadastrarPetDTO.tipo(), petSalvo.getTipo());
        assertEquals(cadastrarPetDTO.nome(), petSalvo.getNome());
        assertEquals(cadastrarPetDTO.raca(), petSalvo.getRaca());
        assertEquals(cadastrarPetDTO.idade(), petSalvo.getIdade());
        assertEquals(cadastrarPetDTO.cor(), petSalvo.getCor());
        assertEquals(cadastrarPetDTO.peso(), petSalvo.getPeso());

    }

}