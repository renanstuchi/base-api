
## Base API feita em Java

A API foi criada utilizando as seguintes tecnologias:
- Java JDK 8
- Maven
- Jersey 2
- Lombok 
- Slf4j (possui fácil integração com ferramentas de visualização como o Rapid7)
- Google Guice
- Dropwizard Metrics
- JDBI 3
- Jackson 2
- Mockito (JUnit Tests)
- Flyway (Controle de versão de Databases)
- MySql

## Banco de Dados e Flyway

- Tanto a API quanto o Flyway estão com a configuração padrão de acesso ao `MySQL`, que assume o usuário e senha: `--user=root` `--password=(branco)`

## Execução local pelo console

Para rodara  API local, será necessário possuir o `Java 8`, `Maven` e `MySQL` instalados.
Para facilitar o uso, scripts foram criados com os comandos específicos para cada passo.
>  Primeiro passo: baixar todas as dependências do projeto.
- `mvnEclipse` rodar para baixar todas as dependências do projeto.

> Segundo passo: criar o Banco de Dados no `MySQL` local.
- `updateDatabase` rodar parar executar o Flyway e atualizar a base de dados de acordo com os scripts existentes em `src/main/resources/db/migration`. Importante lembrar que, esse script deve ser executado toda vez que um novo script `.sql` for criado na API.

> Terceiro passo: rodar a API
- `debug` rodar para executar a api localmente.


## Deploy em container Docker

Para facilitar o teste desta API, um deploy realizado em container Docker foi criado. 
A única dependência para realizar este passo é possuir o Docker instalado localmente.
Para facilitar o uso, scripts bash foram criados com os comandos específicos para cada passo.

> Primeiro passo: criar o container. **Este passo deve ser executado apenas em 2 ocasiões: na primeira vez em que o container for executado na máquina. Ou se o arquivo pom.xml dao projeto sofrer alteração.**
- `builddocker` rodar para criar o container com todas as dependências necessárias.

> Segundo passo: executar o container.
- `rundeploy` rodar para deployar o código dentro do container.

Em caso de sucesso, a mensagem `Starting API` poderá ser vista no console, e a api ficará disponível através da url `http://localhost:8080/base-api`
Caso contrário, verifique no próprio console os erros encontrados no build.

## Deploy local

Para realizar o deploy local da API, será necessário ter um Java Servlet instalado, Apache Tomcat, por exemplo.
Para executar o deploy basta executar o comando `mvn clean package` na raiz do projeto.
Isso irá gerar um arquivo `.war` na pasta `target` do projeto.
Basta copiar e enviar para o Java Servlet instalado (Tomcat).

> Importante: se houver alteração/criação de novos scripts `.sql`, lembre-se de executar o script `updateDatabase` também.

Em caso de sucesso, a API ficará disponível através da url configurada no seu Java Servlet. Na configuração padrão seria `http://localhost:8080/base-api`

## Models existentes na API

Atualmente a API possui apenas um model do tipo `Task`, com as seguintes propriedades:
- `id` (integer)
- `name` (varchar 1024)
- `description` (varchar 1024)
- `assignedTo` (varchar 256)
- `status` (varchar 128) (possíveis valores: OPEN, PENDING, IN_PROGRESS, READY_TO_TEST, READY_TO_MERGE, COMPLETED)
- `created` (timestamp)
- `updated` (timestamp)

## Endpoints de Tasks disponíveis

> Os endpoints de `POST` e `PUT` esperam uma request com payload do tipo `application/json`

`POST` `/task` para criar uma nova Task.
`PUT` `/task/{id}` para atualizar uma Task existente.
`DELETE` `/task/{id}` para deletar uma Task existente.
`GET` `/task/{id}` para buscar uma Task específica por ID.
`GET` `/task` para buscar todas as Tasks existentes.

## Endpoints de Métricas e HealthCheck da API

`GET` `/metrics/metrics?pretty=true`
`GET` `/metrics/healthcheck`
`GET` `/metrics/threads`
`GET` `/metrics/ping`

## Exemplos de uso

```
Criando uma task:
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Task1","description":"My description", "assignedTo": "Me", "status": "OPEN"}' \
  http://localhost:8080/base-api/task

Atualizando uma task
curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"status": "COMPLETED"}' \
  http://localhost:8080/base-api/task/1

Deletando uma task
curl --header "Content-Type: application/json" \
  --request DELETE \
  http://localhost:8080/base-api/task/1
```

## Obervação sobre a segurança da API

Este projeto inicial não possui nenhuma segurança para controle de acesso (token ou login). Portanto, os endpoints podem ser acessados livremente.

## Observação sobre o build da API

O build está configurado para rodar os Testes Unitários, portanto, o build só terá sucesso se todos os testes estiverem corretos.