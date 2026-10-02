package br.com.trabalho.api_sinan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotificacaoNaoEncontradaException.class)
    public ProblemDetail tratarNaoEncontrada(
            NotificacaoNaoEncontradaException exception) {

        ProblemDetail problema =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                );

        problema.setTitle("Notificação não encontrada");

        return problema;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail tratarRegraDeNegocio(
            IllegalArgumentException exception) {

        ProblemDetail problema =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()
                );

        problema.setTitle("Dados inválidos");

        return problema;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarValidacao(
            MethodArgumentNotValidException exception) {

        String mensagem = "Existem campos inválidos";

        if (!exception
                .getBindingResult()
                .getFieldErrors()
                .isEmpty()) {

            mensagem = exception
                    .getBindingResult()
                    .getFieldErrors()
                    .get(0)
                    .getDefaultMessage();
        }

        ProblemDetail problema =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        mensagem
                );

        problema.setTitle("Erro de validação");

        return problema;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail tratarJsonInvalido(
            HttpMessageNotReadableException exception) {

        ProblemDetail problema =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "O corpo da requisição possui dados inválidos"
                );

        problema.setTitle("Requisição inválida");

        return problema;
    }
}