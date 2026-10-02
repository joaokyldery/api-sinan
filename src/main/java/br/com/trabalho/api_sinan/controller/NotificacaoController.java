package br.com.trabalho.api_sinan.controller;

import br.com.trabalho.api_sinan.model.Notificacao;
import br.com.trabalho.api_sinan.service.NotificacaoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Notificacao> cadastrar(
            @Valid @RequestBody Notificacao notificacao) {

        Notificacao novaNotificacao = service.cadastrar(notificacao);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaNotificacao);
    }

    @GetMapping
    public ResponseEntity<List<Notificacao>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacao> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Notificacao notificacao) {

        return ResponseEntity.ok(
                service.atualizar(id, notificacao)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}