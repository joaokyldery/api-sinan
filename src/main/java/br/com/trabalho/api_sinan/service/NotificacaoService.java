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

    public Notificacao buscarPorId(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
    }

    public Notificacao atualizar(Long id, Notificacao dados) {

    Notificacao notificacao = buscarPorId(id);

    notificacao.setAgravo(dados.getAgravo());
    notificacao.setDataNotificacao(dados.getDataNotificacao());
    notificacao.setNomePaciente(dados.getNomePaciente());
    notificacao.setDataNascimento(dados.getDataNascimento());
    notificacao.setNomeMae(dados.getNomeMae());
    notificacao.setIdade(dados.getIdade());
    notificacao.setSexo(dados.getSexo());
    notificacao.setGestante(dados.getGestante());
    notificacao.setPaisResidencia(dados.getPaisResidencia());
    notificacao.setUfResidencia(dados.getUfResidencia());
    notificacao.setMunicipioResidencia(dados.getMunicipioResidencia());

    return repository.save(notificacao);
    }
    
    public void excluir(Long id) {

    Notificacao notificacao = buscarPorId(id);

    repository.delete(notificacao);
    }
}