package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.IValidationSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private List<IValidationSolicitacaoAdocao> validations;

    public void solicitar(SolicitacaoAdocaoDTO dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        validations.forEach(validation -> validation.validar(dto));

        Adocao adocao = new Adocao();
        adocao.setData(LocalDateTime.now());
        adocao.setTutor(tutor);
        adocao.setPet(pet);
        adocao.setMotivo(dto.motivo());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        repository.save(adocao);

        String to = adocao.getPet().getAbrigo().getEmail();
        String subject = "Solicitação de adoção";
        String text = "Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.";
        mailService.sendMail(to, subject, text);
    }

    public void aprovar(AprovacaoAdocaoDTO dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.setStatus(StatusAdocao.APROVADO);

        String to = adocao.getTutor().getEmail();
        String subject = "Adoção aprovada";
        String text = "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.";
        mailService.sendMail(to, subject, text);
    }

    public void reprovar(ReprovacaoAdocaoDTO dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.setJustificativaStatus(dto.justificativa());
        adocao.setStatus(StatusAdocao.REPROVADO);

        String to = adocao.getTutor().getEmail();
        String subject = "Adoção reprovada";
        String text = "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus();
        mailService.sendMail(to, subject, text);
    }
}
