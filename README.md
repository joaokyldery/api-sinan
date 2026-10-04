# API REST - Sistema de Informação de Agravos de Notificação (SINAN)

Trabalho prático desenvolvido para a disciplina de *Programação para a Web I*
do curso de Análise e Desenvolvimento de Sistemas do *IFPB Campus Cajazeiras*.

## Integrantes

- Francisco Vitor Ferreira de Araujo
- João Kyldery Catanão da Silva
- Danylo Rodrigues da Silva

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Jakarta Validation
- Banco de dados H2
- Maven

## Como executar o projeto

### Pré-requisitos

É necessário ter o *Java 21* instalado.

### Clonar o repositório

```bash
git clone https://github.com/joaokyldery/api-sinan.git

## Interface Web

A aplicação também possui uma interface web desenvolvida em HTML, CSS e JavaScript puro.

Com a aplicação em execução, acesse:

- Página inicial: http://localhost:8080/
- Cadastro: http://localhost:8080/cadastro.html
- Consulta: http://localhost:8080/consulta.html

A interface consome a API REST utilizando a função fetch do JavaScript.

### Funcionalidades da interface

A interface permite:

- cadastrar notificações;
- consultar notificações;
- filtrar por agravo;
- filtrar pelo nome do paciente;
- consultar possíveis notificações duplicadas;
- editar notificações;
- excluir notificações.