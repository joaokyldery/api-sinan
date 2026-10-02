package br.com.trabalho.api_sinan.service;

import br.com.trabalho.api_sinan.exception.NotificacaoNaoEncontradaException;
import br.com.trabalho.api_sinan.model.Notificacao;
import br.com.trabalho.api_sinan.repository.NotificacaoRepository;

import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public Notificacao cadastrar(Notificacao notificacao) {

        validarRegras(notificacao);

        return repository.save(notificacao);
    }

    public List<Notificacao> listar(
            String agravo,
            String nomePaciente,
            boolean duplicadas) {

        List<Notificacao> todas = repository.findAll();

        List<Notificacao> resultado = todas
                .stream()
                .filter(notificacao ->
                        agravo == null
                                || agravo.isBlank()
                                || normalizar(notificacao.getAgravo())
                                .contains(normalizar(agravo)))
                .filter(notificacao ->
                        nomePaciente == null
                                || nomePaciente.isBlank()
                                || normalizar(notificacao.getNomePaciente())
                                .contains(normalizar(nomePaciente)))
                .toList();

        if (duplicadas) {

            Set<Long> idsDuplicados =
                    encontrarIdsDuplicados(todas);

            resultado = resultado
                    .stream()
                    .filter(notificacao ->
                            idsDuplicados.contains(
                                    notificacao.getId()))
                    .toList();
        }

        return resultado;
    }

    public Notificacao buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new NotificacaoNaoEncontradaException(id));
    }

    public Notificacao atualizar(
            Long id,
            Notificacao dados) {

        Notificacao notificacao = buscarPorId(id);

        notificacao.setAgravo(dados.getAgravo());
        notificacao.setDataNotificacao(
                dados.getDataNotificacao());

        notificacao.setNomePaciente(
                dados.getNomePaciente());

        notificacao.setDataNascimento(
                dados.getDataNascimento());

        notificacao.setNomeMae(dados.getNomeMae());
        notificacao.setIdade(dados.getIdade());
        notificacao.setSexo(dados.getSexo());
        notificacao.setGestante(dados.getGestante());

        notificacao.setPaisResidencia(
                dados.getPaisResidencia());

        notificacao.setUfResidencia(
                dados.getUfResidencia());

        notificacao.setMunicipioResidencia(
                dados.getMunicipioResidencia());

        validarRegras(notificacao);

        return repository.save(notificacao);
    }

    public void excluir(Long id) {

        Notificacao notificacao = buscarPorId(id);

        repository.delete(notificacao);
    }

    private void validarRegras(
            Notificacao notificacao) {

        // RN02 - Idade
        if (notificacao.getDataNascimento() == null
                && notificacao.getIdade() == null) {

            throw new IllegalArgumentException(
                    "A idade é obrigatória quando a data de nascimento não é informada"
            );
        }

        // RN02 - Gestante
        if ("F".equalsIgnoreCase(
                notificacao.getSexo())
                && vazio(notificacao.getGestante())) {

            throw new IllegalArgumentException(
                    "O campo gestante é obrigatório para o sexo feminino"
            );
        }

        // RN03 - Residência
        boolean residenteBrasil =
                vazio(notificacao.getPaisResidencia())
                || "Brasil".equalsIgnoreCase(
                        notificacao
                                .getPaisResidencia()
                                .trim()
                );

        if (residenteBrasil
                && vazio(
                        notificacao.getUfResidencia())) {

            throw new IllegalArgumentException(
                    "A UF é obrigatória para residentes no Brasil"
            );
        }

        if (!vazio(notificacao.getUfResidencia())
                && vazio(
                        notificacao
                                .getMunicipioResidencia())) {

            throw new IllegalArgumentException(
                    "O município é obrigatório quando a UF é informada"
            );
        }
    }

    private boolean vazio(String texto) {

        return texto == null || texto.isBlank();
    }

    private String normalizar(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .trim()
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }

    private Set<Long> encontrarIdsDuplicados(
            List<Notificacao> notificacoes) {

        Set<Long> idsDuplicados = new HashSet<>();

        for (int i = 0;
             i < notificacoes.size();
             i++) {

            for (int j = i + 1;
                 j < notificacoes.size();
                 j++) {

                Notificacao primeira =
                        notificacoes.get(i);

                Notificacao segunda =
                        notificacoes.get(j);

                if (saoDuplicadas(
                        primeira,
                        segunda)) {

                    idsDuplicados.add(
                            primeira.getId());

                    idsDuplicados.add(
                            segunda.getId());
                }
            }
        }

        return idsDuplicados;
    }

    private boolean saoDuplicadas(
            Notificacao primeira,
            Notificacao segunda) {

        if (possuiCampoDeDuplicidadeVazio(primeira)
                || possuiCampoDeDuplicidadeVazio(segunda)) {

            return false;
        }

        boolean mesmosDados =
                normalizar(primeira.getAgravo())
                        .equals(
                                normalizar(
                                        segunda.getAgravo()))
                &&
                normalizar(
                        primeira.getNomePaciente())
                        .equals(
                                normalizar(
                                        segunda
                                                .getNomePaciente()))
                &&
                primeira.getDataNascimento()
                        .equals(
                                segunda.getDataNascimento())
                &&
                normalizar(primeira.getNomeMae())
                        .equals(
                                normalizar(
                                        segunda.getNomeMae()));

        long diferencaDias =
                Math.abs(
                        ChronoUnit.DAYS.between(
                                primeira
                                        .getDataNotificacao(),
                                segunda
                                        .getDataNotificacao()
                        )
                );

        return mesmosDados
                && diferencaDias <= 3;
    }

    private boolean possuiCampoDeDuplicidadeVazio(
            Notificacao notificacao) {

        return vazio(notificacao.getAgravo())
                || vazio(
                        notificacao.getNomePaciente())
                || notificacao.getDataNascimento()
                == null
                || vazio(notificacao.getNomeMae())
                || notificacao
                        .getDataNotificacao()
                == null;
    }
}