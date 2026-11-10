# CitizenTech

Projeto Spring Boot para monitoramento de ocupacao de veiculos de transporte publico,
desenvolvido com **Java 21**, **Spring Boot 3.3.5**, **Maven**, **Thymeleaf**, **Spring Security**
e banco **Oracle (FIAP)**.

## Pre-requisitos

- **JDK 21** instalado (verifique com `java -version`)
- **Maven** (ou usar o wrapper/IDE)
- **IntelliJ IDEA** (Community e suficiente) recomendado
- Acesso ao Oracle da FIAP (host `oracle.fiap.com.br`, porta `1521`, SID `ORCL`)

## 1. Rodar o script SQL no Oracle

1. Abra o **SQL Developer** (ou SQL*Plus) e conecte com o seu RM e senha FIAP.
2. Abra o arquivo `database/script-oracle.sql`.
3. Execute o script completo (F5). Ele cria as 7 tabelas `DDDJ_*` e insere os dados de exemplo.

## 2. Configurar o application.properties

Edite `src/main/resources/application.properties` e troque as credenciais:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_RM
spring.datasource.password=SUA_SENHA
```

## 3. Rodar a aplicacao

Pelo Maven:

```bash
mvn spring-boot:run
```

Ou no IntelliJ: abra o projeto, aguarde a indexacao do Maven e rode a classe
`br.com.fiap.citizentech.CitizentechApplication`.

A aplicacao sobe em `http://localhost:8080`.

## Endpoints da API

| Metodo | Rota                          | Descricao                                  |
|--------|-------------------------------|--------------------------------------------|
| GET    | /api/linhas                   | Lista todas as linhas                      |
| GET    | /api/linhas/{id}              | Busca linha por id                         |
| POST   | /api/linhas                   | Cria linha (201)                           |
| PUT    | /api/linhas/{id}              | Atualiza linha                             |
| DELETE | /api/linhas/{id}              | Remove linha (204)                         |
| GET    | /api/leituras                 | Lista leituras com relacionamentos         |
| GET    | /api/leituras/{id}            | Busca leitura por id                       |
| POST   | /api/leituras                 | Cria leitura e gera StatusOcupacao         |
| PUT    | /api/leituras/{id}            | Atualiza leitura                           |
| DELETE | /api/leituras/{id}            | Remove leitura (204)                       |
| GET    | /api/leituras/status/{status} | Leituras por status (VAZIA/PARCIAL/CHEIA)  |
| GET    | /api/status                   | Lista status de ocupacao                   |
| GET    | /api/status/{id}              | Busca status por id                        |
| POST   | /api/status                   | Cria status                                |
| PUT    | /api/status/{id}              | Atualiza status                            |
| DELETE | /api/status/{id}              | Remove status (204)                        |
| GET    | /api/status/media-por-linha   | Media de ocupacao agrupada por linha       |

## Views (Thymeleaf)

- `/linhas` - CRUD de linhas
- `/leituras` - CRUD de leituras (badge colorido por status)
- `/status` - CRUD de status de ocupacao (badge com a cor da faixa)

## Regra de negocio

Ao criar uma leitura, o sistema calcula o percentual de ocupacao
(`contagemPessoas / capacidadeTotal * 100`), determina a faixa correspondente
(VAZIA / PARCIAL / CHEIA) e cria automaticamente um registro de StatusOcupacao.

## Observacao sobre Security

O **Spring Security esta configurado para liberar todos os endpoints** (permitAll),
com CSRF desabilitado e formLogin/httpBasic desativados - ideal para desenvolvimento
e apresentacao academica. Em producao, restrinja o acesso.