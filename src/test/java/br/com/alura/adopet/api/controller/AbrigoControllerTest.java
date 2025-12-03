package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.dto.CadastrarPetDTO;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @Autowired
    private JacksonTester<CadastrarAbrigoDTO> cadastrarAbrigoJsonDTO;

    @Autowired
    private JacksonTester<CadastrarPetDTO> cadastrarPetJsonDTO;

    @Test
    @DisplayName("Deveria retornar status 200 caso liste abrigos")
    void listarAbrigosCenario01() throws Exception{

        // ARRANGE

        // ACT
        var response = mvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar status 200 caso cadastre abrigos")
    void cadastrarAbrigosCenario01() throws Exception{

        // ARRANGE
        CadastrarAbrigoDTO dto = new CadastrarAbrigoDTO("Abrigo 2", "11933304765", "abrigo02@abrigo.com");

        // ACT
        var response = mvc.perform(
                post("/abrigos")
                        .content(cadastrarAbrigoJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
        assertEquals("Abrigo Cadastrado com Sucesso", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar status 400 caso não cadastre abrigos")
    void cadastrarAbrigosCenario02() throws Exception{

        // ARRANGE
        CadastrarAbrigoDTO dto = new CadastrarAbrigoDTO("", "", "");

        // ACT
        var response = mvc.perform(
                post("/abrigos")
                        .content(cadastrarAbrigoJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar status 200 caso liste pets")
    void listarPetsCenario01() throws Exception{

        // ARRANGE

        // ACT
        var response = mvc.perform(
                get("/abrigos/{idOuNome}/pets", "2")
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar status 200 caso cadastre pet")
    void cadastrarPetsCenario01() throws Exception{

        // ARRANGE
        CadastrarPetDTO dto = new CadastrarPetDTO(TipoPet.CACHORRO, "Pipoca", "Vira-lata", 6, "branco", 18F);

        // ACT
        var response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", "2")
                        .content(cadastrarPetJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
        assertEquals("Pet Cadastrado com Sucesso", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar status 400 caso não cadastre pet")
    void cadastrarPetsCenario02() throws Exception{

        // ARRANGE
        CadastrarPetDTO dto = new CadastrarPetDTO(null, "", "", null, "", null);

        // ACT
        var response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", "2")
                        .content(cadastrarPetJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }
}