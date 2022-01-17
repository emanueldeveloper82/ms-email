package br.com.msemail.service;

import br.com.msemail.dto.EmailDTO;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<?> sendEmail(EmailDTO emailDTO);

}
