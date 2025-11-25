package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;

public interface IValidationSolicitacaoAdocao {
    void validar(SolicitacaoAdocaoDTO dto);
}
