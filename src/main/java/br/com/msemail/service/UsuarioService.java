package br.com.msemail.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "usuarioclient")
public interface UsuarioService {

    @GetMapping(value = "/api/usuario/{email}")
    ResponseEntity<?> buscarPorEmail(@PathVariable(value = "email") String email);

}
