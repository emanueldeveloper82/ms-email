package br.com.msemail.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidade que representa um usuario
 * @author emanuel developer
 *
 */
@EqualsAndHashCode
@ToString
@Data
@Table(name = "USUARIO", schema = "MS_USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String rg;
    private String telefone;
    private String email;
    private LocalDate dataCadastro;
    private LocalDate dataAtualizacao;


}
