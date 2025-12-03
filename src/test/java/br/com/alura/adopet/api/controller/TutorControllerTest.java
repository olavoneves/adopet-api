package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizarTutorDTO;
import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.service.TutorService;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService tutorService;

    @Autowired
    private JacksonTester<CadastrarTutorDTO> cadastrarTutorJsonDTO;

    @Autowired
    private JacksonTester<AtualizarTutorDTO> atualizarTutorJsonDTO;

    @Test
    @DisplayName("Deveria retornar 200 caso tutor seja cadastrado")
    void cadastrarTutorCenario01() throws Exception {

        // ARRANGE
        CadastrarTutorDTO dto = new CadastrarTutorDTO("Roberto", "11922234435", "robertao@gmail.com");

        // ACT
        var response = mvc.perform(
                post("/tutores")
                        .content(cadastrarTutorJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
        assertEquals("Tutor Cadastrado com Sucesso", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar 400 caso tutor não seja cadastrado")
    void cadastrarTutorCenario02() throws Exception {

        // ARRANGE
        CadastrarTutorDTO dto = new CadastrarTutorDTO("", "", "");

        // ACT
        var response = mvc.perform(
                post("/tutores")
                        .content(cadastrarTutorJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar 200 caso tutor seja atualizado")
    void atualizarTutorCenario01() throws Exception {

        // ARRANGE
        AtualizarTutorDTO dto = new AtualizarTutorDTO(2L, new ArrayList<Adocao>());

        // ACT
        var response = mvc.perform(
                put("/tutores")
                        .content(atualizarTutorJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
        assertEquals("Tutor Atualizado com Sucesso", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar 400 caso tutor não seja atualizado")
    void atualizarTutorCenario02() throws Exception {

        // ARRANGE
        AtualizarTutorDTO dto = new AtualizarTutorDTO(null, null);

        // ACT
        var response = mvc.perform(
                put("/tutores")
                        .content(atualizarTutorJsonDTO.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }
}