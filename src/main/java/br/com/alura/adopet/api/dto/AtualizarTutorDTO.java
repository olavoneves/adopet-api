package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Adocao;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AtualizarTutorDTO(@NotNull Long idTutor,
                                @NotNull List<Adocao> adocoes) {
}
