package br.com.msemail.service.impl;

import br.com.msemail.dto.EmailDTO;
import br.com.msemail.dto.SolicitacaoDTO;
import br.com.msemail.entity.Email;
import br.com.msemail.enums.StatusEmail;
import br.com.msemail.repository.EmailRepository;
import br.com.msemail.service.EmailService;
import br.com.msemail.service.UsuarioService;
import br.com.msemail.utils.Constantes;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailRepository repository;
    private final UsuarioService usuarioService;
    private final JavaMailSender emailSender;
    private final ModelMapper modelMapper;


    /**
     * Método que salva ou atualiza um usuario;
     * nem coloca no preUpdate da entity.
     * @param emailDTO
     * @return
     */
    @Override
    public ResponseEntity<?> sendEmail(EmailDTO emailDTO) {

        log.info("Enviando e-mail para o solicitante.");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDTO.getEmailFrom());
        message.setTo(emailDTO.getEmailTo());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getBody());

        ResponseEntity<?> sendEmailResponse = null;
        try {

            emailSender.send(message);
            emailDTO.setStatusEmail(StatusEmail.SENT);
            sendEmailResponse = ResponseEntity.status(HttpStatus.CREATED)
                    .body(Constantes.EMAIL_ENVIADO_COM_SUCESSO);

        } catch (MailSendException | MailParseException e) {

            log.info(Constantes.ERRO_ENVIAR_EMAIL);
            emailDTO.setStatusEmail(StatusEmail.ERROR);
            sendEmailResponse = ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Constantes.ERRO_ENVIAR_EMAIL);

        }  catch (MailAuthenticationException e) {
            log.info(Constantes.ERRO_ENVIAR_EMAIL);
            emailDTO.setStatusEmail(StatusEmail.ERROR);
            sendEmailResponse = ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
                    .body(Constantes.ERRO_ENVIAR_EMAIL + Constantes.ERRO_AUTENTICACAO);
        } catch (NullPointerException e) {
            log.info(Constantes.ERRO_ENVIAR_EMAIL);
            emailDTO.setStatusEmail(StatusEmail.ERROR);
            emailDTO.setEmailTo("ERRO");
            sendEmailResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Constantes.ERRO_ENVIAR_EMAIL +
                    Constantes.CAMPO_EMAIL_VAZIO);
        } catch (Exception e) {
            log.info(Constantes.ERRO_ENVIAR_EMAIL);
            emailDTO.setStatusEmail(StatusEmail.ERROR);
            sendEmailResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro desconhecido.");
        } finally {
            repository.save(modelMapper.map(emailDTO, Email.class));
        }

        return sendEmailResponse;
    }

    @Override
    public ResponseEntity<?> solicitacao(SolicitacaoDTO solicitacaoDTO) {

        try {

            ResponseEntity<?> usuarioResponse = usuarioService
                    .buscarPorEmail(solicitacaoDTO.getEmail());

            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setEmailFrom("nao_responda@GloboDyne.com");
            emailDTO.setOwnerRef("Empresa Brasileira de MSs");
            emailDTO.setSubject("NÃO RESPONDA");
            emailDTO.setEmailTo(solicitacaoDTO.getEmail());

            if (!usuarioResponse.getStatusCode().isError() &&
                    usuarioResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                log.info("Usuário não encontrado na base de dados.");
                emailDTO.setBody("Você realizou uma solicitação de cadastro em nosso sistema. " +
                        " A GloboDyne agradece!");
            } else {
                log.info("Usuário encontrado na base de dados.");
                emailDTO.setBody("O e-mail que solicitou cadastro já existe em nossa base de dados. " +
                        " A GloboDyne agradece!");
            }

            return sendEmail(emailDTO);

        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Constantes.SERVICO_USUARIO_INDISPONIVEL);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Constantes.ERRO_INTERNO);
        }
    }


    @Override
    public Optional<Email> findByEmailTo(String email) {
        return repository.findByEmailTo(email);
    }



}
