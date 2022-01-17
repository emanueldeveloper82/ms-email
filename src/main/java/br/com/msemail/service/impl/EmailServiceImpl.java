package br.com.msemail.service.impl;

import br.com.msemail.dto.EmailDTO;
import br.com.msemail.entity.Email;
import br.com.msemail.enums.StatusEmail;
import br.com.msemail.repository.EmailRepository;
import br.com.msemail.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository repository;
    private final JavaMailSender emailSender;
    private final ModelMapper modelMapper;


    /**
     * Método que salva ou atualiza um usuario;
     * nem coloca no preUpdate da entity. Ver isso depois!!!!
     * @param emailDTO
     * @return
     */
    @Override
    public ResponseEntity<?> sendEmail(EmailDTO emailDTO) {

        //Nesse trecho, irei buscar através do MS de usuário se existe o e-mail cadastrado;
//        Optional<Email> emailOptional = Optional.empty();
//
//        if(emailDTO.getId() != null) {
//            emailOptional = repository.findById(emailDTO.getId());
//        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailDTO.getEmailFrom());
            message.setTo(emailDTO.getEmailTo());
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getBody());
            emailSender.send(message);
            emailDTO.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailDTO.setStatusEmail(StatusEmail.SENT);
            return ResponseEntity.badRequest().body(EmailDTO.class);
        } finally {
            return ResponseEntity.ok().body(repository.save(modelMapper.map(emailDTO, Email.class)));
        }
    }
}
