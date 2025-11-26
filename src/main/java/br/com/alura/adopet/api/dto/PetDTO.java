package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.TipoPet;

public record PetDTO(Long id,
                     TipoPet tipo,
                     String nome,
                     String raca,
                     Integer idade,
                     String cor,
                     Float peso,
                     Boolean adotado,
                     Abrigo abrigo,
                     Adocao adocao) {
    public PetDTO(Long id, TipoPet tipo, String nome, String raca, Integer idade, String cor, Float peso, Boolean adotado, Abrigo abrigo, Adocao adocao) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
        this.adotado = adotado;
        this.abrigo = abrigo;
        this.adocao = adocao;
    }
}
