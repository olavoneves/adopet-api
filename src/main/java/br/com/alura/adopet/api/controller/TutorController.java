package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizarTutorDTO;
import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarTutorDTO dto) {
        try {
            this.service.cadastrar(dto);
            return ResponseEntity.ok("Tutor Cadastrado com Sucesso");

        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarTutorDTO dto) {
        try {
            this.service.atualizar(dto);
            return ResponseEntity.ok("Tutor Atualizado com Sucesso");

        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
