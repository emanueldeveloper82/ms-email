package br.com.msemail.entity;

import br.com.msemail.enums.StatusEmail;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "MS_EMAIL", name = "EMAIL")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_SEQ")
    @SequenceGenerator(name = "EMAIL_SEQ", sequenceName = "EMAIL_SEQ", schema = "MS_EMAIL", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OWNER_REF")
    private String ownerRef;

    @Column(name = "EMAIL_FROM")
    private String emailFrom;

    @Column(name = "EMAIL_TO")
    private String emailTo;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "BODY", columnDefinition = "TEXT")
    private String body;

    @Column(name = "EMAIL_SEND_DATE")
    private LocalDateTime emailSendDate;

    @Column(name = "STATUS_EMAIL")
    private StatusEmail statusEmail;

    @Transient
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        this.setEmailSendDate(LocalDateTime.now());
    }
}
