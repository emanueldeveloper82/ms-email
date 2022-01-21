package br.com.msemail.service;

import br.com.msemail.dto.EmailDTO;
import br.com.msemail.dto.SolicitacaoDTO;
import br.com.msemail.entity.Email;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface EmailService {

    ResponseEntity<?> sendEmail(EmailDTO emailDTO);

    Optional<Email> findByEmailTo(String email);

    ResponseEntity<?> solicitacao(SolicitacaoDTO solicitacaoDTO);

}
