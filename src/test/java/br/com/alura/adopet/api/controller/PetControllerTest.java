package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deveria retornar 200 caso tenha listagem de pets disponiveis")
    void listarPetsDisponiveisCenario01() throws Exception {

        // ARRANGE
        List<PetDTO> petsDTO = List.of(
                new PetDTO(1L, TipoPet.CACHORRO, "Doguin", "pastor", 8, "Caramelo", 12F, false, new Abrigo(), new Adocao())
                );

        when(petService.listarTodosDisponiveis()).thenReturn(petsDTO);

        // ACT
        var response = mvc.perform(
                get("/pets")
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
        assertEquals(objectMapper.writeValueAsString(petsDTO), response.getContentAsString());
    }

}