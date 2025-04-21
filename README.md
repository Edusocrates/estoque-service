# 📦 estoque-service

`estoque-service` é um microserviço responsável pelo **gerenciamento de estoque de produtos**, sendo parte da arquitetura distribuída de uma aplicação de e-commerce. Ele mantém o controle da **quantidade disponível por SKU**, oferece funcionalidades de manipulação do estoque e realiza comunicação assíncrona com outros microsserviços por meio do **RabbitMQ**.

---

## ✅ Funcionalidades

- Cadastrar novo estoque
- Atualizar estoque diretamente
- Creditar e debitar unidades do estoque
- Verificar disponibilidade por SKU
- Buscar informações de estoque por SKU
- Reagir a eventos de criação de produto via RabbitMQ

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** (migração de banco)
- **RabbitMQ** (mensageria)
- **Docker e Docker Compose**
- **JUnit 5** (testes unitários)
- **Lombok**

---

## 🧱 Arquitetura

Este microserviço segue os princípios da **Clean Architecture** e **SOLID**, dividido em camadas:
- **Domínio** (entidades e regras de negócio)
- **Casos de uso (UseCases)**
- **Interfaces de entrada (Handlers/Controllers)**
- **Interfaces de saída (Gateways, Repositories, Mensageria)**
- **Infraestrutura (JPA, RabbitMQ, Configs)**


---


## 📦 Endpoints REST

| Método | Endpoint                        | Descrição                         |
|--------|----------------------------------|-----------------------------------|
| POST   | `/estoque-service/estoques`                 | Cadastrar novo estoque            |
| PUT    | `/estoque-service/estoques/{sku}/atualizar` | Atualizar quantidade diretamente  |
| PUT    | `/estoque-service/estoques/{sku}/creditar`  | Creditar quantidade ao estoque    |
| PUT    | `/estoque-service/estoques/{sku}/debitar`   | Debitar quantidade do estoque     |
| GET    | `/estoque-service/estoques/{sku}`           | Consultar estoque por SKU         |
| GET    | `/estoque-service/estoques/{sku}/disponivel`| Verificar disponibilidade         |

---

## 📨 Mensageria com RabbitMQ

- **Escuta de eventos**:
    - `ProdutoCriadoListener`: ouve mensagens do tipo `ProdutoCriadoEvent` e cria registros de estoque automaticamente.

- **Envio de eventos**:
    - `EstoqueProducer`: envia atualizações de estoque ou notificações para outros serviços.

---

## 🗃️ Banco de Dados

- Banco: **PostgreSQL**
- Gerenciado via **Flyway**, com os seguintes scripts:
    - `V1__create_estoques_table.sql`: Criação da tabela `estoques`
    - `V2__insert_mock_estoques.sql`: Dados mockados para desenvolvimento

---
## 🐳 Docker
- O projeto pode ser executado em um contêiner Docker, utilizando o seguinte comando:
```bash
docker-compose up --build
```

- O arquivo `docker-compose.yml` contém as configurações para o banco de dados PostgreSQL e o RabbitMQ.
- A aplicação estará disponível em `http://localhost:8080/estoque-service` e o RabbitMQ em `http://localhost:15672` (usuário: `guest`, senha: `guest`).
- Certifique-se de que as portas não estejam em uso antes de iniciar o contêiner.

- Para parar os contêineres, utilize:
```bash
docker-compose down -v
```

- O comando `-v` remove os volumes, garantindo que os dados sejam limpos ao parar o contêiner.

## 🧪 Testes
- O projeto inclui testes unitários e de integração utilizando o JUnit 5.
- Para executar os testes, utilize o seguinte comando:
```bash
./mvnw test
```

- Os testes estão localizados na pasta `src/test/java/com/estoque/service`.


---

## 🧩 Integração com outros microsserviços
- O microsserviço de estoque se integra com o microsserviço de pedidos e produtos através do RabbitMQ.
- Os eventos são enviados e recebidos em tópicos específicos, permitindo comunicação assíncrona e desacoplada entre os serviços.
- A comunicação via RabbitMQ é configurada no arquivo `application.yml`, onde são definidos os tópicos e as filas correspondentes.
- Os eventos são serializados e deserializados utilizando o formato JSON, facilitando a interoperabilidade entre os microsserviços.

## 🧠 Regras de negócio
- O estoque deve ser criado com uma quantidade inicial maior que zero.
- Não é permitido debitar mais unidades do que a quantidade disponível no estoque.
- O estoque deve ser atualizado corretamente ao receber eventos de criação de produto.
- A consulta de disponibilidade deve retornar verdadeiro se a quantidade for maior que zero.

---

## Acesso ao banco de dados (PostgreSQL)
- Host: `localhost`
- Porta: `5432`
- Usuário: `postgres`
- Senha: `postgres`
- Banco: `estoque_db`
- Tabela: `estoques`
- URL: `jdbc:postgresql://localhost:5432/estoque_db`
- Driver: `PostgreSQL`
- JDBC URL: `jdbc:postgresql://localhost:5432/estoque_db`
- Ferramenta: `DBeaver` ou `pgAdmin`
- Certifique-se de que o banco de dados esteja em execução antes de iniciar o serviço.

## Acesso ao RabbitMQ
- Host: `localhost`
- Porta: `15672`
- Usuário: `guest`
- Senha: `guest`
- URL: `http://localhost:15672`
- Certifique-se de que o RabbitMQ esteja em execução antes de iniciar o serviço.

---

## ✍️ Autor
- Eduardo Sócrates Caria
- GitHub: https://github.com/Edusocrates
- RM: 358568
- Turma: 6ADJT
- Grupo 15
