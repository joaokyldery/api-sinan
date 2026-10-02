package br.com.trabalho.api_sinan.repository;

import br.com.trabalho.api_sinan.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository
         extends JpaRepository<Notificacao, Long> {
    
}
