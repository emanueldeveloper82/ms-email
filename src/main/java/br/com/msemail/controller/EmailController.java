package br.com.msemail.controller;

import br.com.msemail.dto.EmailDTO;
import br.com.msemail.dto.SolicitacaoDTO;
import br.com.msemail.service.EmailService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/email/")
@Api(value = "API de Solicitação de Cadastro de Usuario")
@CrossOrigin(origins = "*")
public class EmailController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);
    private final EmailService service;

//    @PostMapping("/sendEmail")
//    public ResponseEntity<?> sendingEmail(@RequestBody @Valid EmailDTO emailDto) {
//        return ResponseEntity.ok(service.sendEmail(emailDto));
//    }


//    //TODO: Serviço para teste. Deve ser retirado.
//    @PostMapping("/sendEmail")
//    public ResponseEntity<?> sendingEmail() {
//        log.info("Enviando e-mail para o solicitante.");
//        EmailDTO emailDTO = new EmailDTO();
//        emailDTO.setEmailFrom("teste@teste.com");
//        //emailDTO.setEmailTo("jajajaja");
//        emailDTO.setSubject("TESTE");
//        emailDTO.setBody("dsnfjhbdjfbjdbf");
//
//        return service.sendEmail(emailDTO);
//
//    }

    @PostMapping("/solicitacao")
    public ResponseEntity<?> solicitacao(@RequestBody @Valid SolicitacaoDTO solicitacaoDTO) {
        return service.solicitacao(solicitacaoDTO);
    }

}
