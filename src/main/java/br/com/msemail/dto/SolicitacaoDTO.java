package br.com.msemail.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SolicitacaoDTO {
   @Email
   private String email;
}
