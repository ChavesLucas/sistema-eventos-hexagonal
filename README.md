# Sistema de Cadastro e Notificação de Eventos - Hexagonal

## 📌 Sobre o Projeto
Este projeto foi desenvolvido por **Lucas da Costa Chaves** com o objetivo de aplicar os conceitos da **Arquitetura Hexagonal (Ports and Adapters)** em Java.  

A proposta é criar um sistema simples de **cadastro e gerenciamento de eventos**, permitindo que usuários:  
- cadastrem-se,  
- criem eventos,  
- consultem eventos (futuros, em andamento e passados),  
- confirmem presença,  
- cancelem participação,  
- e tenham os dados persistidos em arquivos locais.  

A arquitetura escolhida destaca a separação clara entre **regras de negócio (domínio)**, **casos de uso (aplicação)**, **interfaces (ports)** e **adapters** de entrada/saída.  

---

## ⚙️ Funcionalidades
- Cadastro de usuários com dados básicos (nome, e-mail, telefone).  
- Criação de eventos com local, categoria, data e horário.  
- Consulta de eventos organizados por status (futuros, em andamento e passados).  
- Confirmação e cancelamento de participação em eventos.  
- Persistência em arquivos de texto (`users.data`, `events.data`).  

---

## 🏛️ Estrutura do Projeto
O projeto segue o padrão **Hexagonal Architecture (Ports and Adapters)**, com a seguinte organização de pacotes:

```text
src/
 ├── application/         # Casos de uso (Application Services)
 ├── domain/              # Entidades e interfaces (User, Event, EventCategory, Ports)
 ├── adapters/
 │    ├── in/             # Adapters de entrada (Console CLI)
 │    └── out/            # Adapters de saída (FileUserRepository, FileEventRepository)
 └── util/                # Utilitários (manipulação de arquivos, datas, etc.)
```

---

## 🔄 Diagrama da Arquitetura

```text
                       +----------------------+
                       |     Console CLI      |
                       |  (Adapter de Entrada)|
                       +----------------------+
                                 |
                         Porta de Entrada
                                 v
                       +----------------------+
                       | Application Service  |
                       | (Casos de Uso)       |
                       +----------------------+
                                 |
                    --------------------------------
                    |                              |
         Porta de Saída (Interface)       Porta de Saída (Interface)
                    |                              |
          +--------------------+          +----------------------+
          | UserRepositoryPort |          | EventRepositoryPort |
          +--------------------+          +----------------------+
                    |                              |
          Adapter de Saída                 Adapter de Saída
                    |                              |
          +--------------------+          +----------------------+
          | FileUserRepository |          | FileEventRepository |
          | (Serialização)     |          | (Serialização)       |
          +--------------------+          +----------------------+
```

---

## 🛠️ Tecnologias Utilizadas
- **Java 8+**  
- **API java.time** para manipulação de datas e horários  
- **Console CLI** para entrada de dados  
- **Persistência em arquivos de texto**  

---

## ▶️ Como Executar
1. Clone o repositório:  
   ```bash
   git clone https://github.com/lucasdacostachaves/sistema-eventos-hexagonal.git
   ```
2. Compile o projeto:  
   ```bash
   javac -d bin src/**/*.java
   ```
3. Execute o sistema:  
   ```bash
   java -cp bin sistema.eventos.hexagonal.application.Main
   ```

---

## 🚀 Melhorias Futuras
- Interface gráfica (JavaFX ou Swing).  
- Persistência em banco de dados (SQLite, MySQL).  
- Suporte a múltiplos usuários e login.  
- Implementação de testes automatizados.  

---

📌 Autor: **Lucas da Costa Chaves**
