package br.com.msemail.dto;

import br.com.msemail.enums.StatusEmail;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class EmailDTO {

    private Long id;

    @NotBlank
    private String ownerRef;

    @NotBlank
    @Email
    private String emailFrom;

    @NotBlank
    @Email
    private String emailTo;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;

    private LocalDateTime emailSendDate;
    private StatusEmail statusEmail;



}
