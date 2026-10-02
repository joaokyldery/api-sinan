package br.com.trabalho.api_sinan.service;

import br.com.trabalho.api_sinan.model.Notificacao;
import br.com.trabalho.api_sinan.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public Notificacao cadastrar(Notificacao notificacao) {
        return repository.save(notificacao);
    }

    public List<Notificacao> listar() {
        return repository.findAll();
    }
}