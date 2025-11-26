package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;

import java.util.List;

public record AbrigoDTO(Long id,
                        String nome,
                        String telefone,
                        String email,
                        List<Pet> pets) {
    public AbrigoDTO(Long id, String nome, String telefone, String email, List<Pet> pets) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.pets = pets;
    }
}
