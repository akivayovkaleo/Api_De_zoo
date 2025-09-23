# Documentação da API do Zoológico (Java/Spring)

Esta documentação detalha a API RESTful para gerenciamento de um zoológico, desenvolvida utilizando as tecnologias Java 17, Spring Boot, Maven e MySQL. A API oferece um conjunto robusto de funcionalidades para a administração de animais, habitats, eventos, funcionários e visitantes, com foco em segurança e desempenho.

## Visão Geral da Arquitetura

A API segue uma arquitetura em camadas, comum em aplicações Spring Boot, que promove a separação de responsabilidades e facilita a manutenção. As principais camadas incluem:

- **Controladores (Controllers):** Responsáveis por receber as requisições HTTP, processá-las e retornar as respostas. Utilizam anotações `@RestController` e `@RequestMapping`.
- **Serviços (Services):** Contêm a lógica de negócio da aplicação. Interagem com os repositórios para manipular dados e podem orquestrar operações mais complexas. Anotados com `@Service`.
- **Repositórios (Repositories):** Interface para a camada de persistência de dados, utilizando Spring Data JPA para abstrair as operações de banco de dados. Anotados com `@Repository`.
- **Modelos (Models/Entities):** Representam as entidades do banco de dados, mapeadas com anotações JPA como `@Entity` e `@Table`.
- **DTOs (Data Transfer Objects):** Objetos utilizados para transferir dados entre as camadas da aplicação e para a comunicação com o cliente, garantindo que apenas os dados necessários sejam expostos.
- **Configuração (Config):** Classes para configurar aspectos da aplicação, como segurança (Spring Security).
- **Segurança (Security):** Implementação de autenticação e autorização baseada em JWT (JSON Web Tokens) usando Spring Security.

## Tecnologias Utilizadas

A aplicação é construída sobre um stack tecnológico moderno e amplamente utilizado no ecossistema Java:

- **Java 17:** A versão LTS (Long-Term Support) da plataforma Java, oferecendo melhorias de desempenho e novas funcionalidades de linguagem.
- **Spring Boot:** Framework para facilitar a criação de aplicações Spring autônomas e prontas para produção, com configuração mínima.
- **Spring Web:** Módulo do Spring para construção de aplicações web, incluindo RESTful APIs.
- **Spring Data JPA:** Simplifica a implementação de camadas de acesso a dados usando JPA (Java Persistence API) e Hibernate.
- **Spring Security:** Framework de segurança robusto para autenticação e autorização.
- **Maven:** Ferramenta de automação de build e gerenciamento de dependências.
- **MySQL:** Sistema de gerenciamento de banco de dados relacional, utilizado para persistir os dados do zoológico.
- **JWT (JSON Web Tokens):** Padrão para criação de tokens de acesso seguros, utilizados para autenticação stateless.
- **Lombok:** Biblioteca para reduzir o código boilerplate em classes Java, através de anotações como `@Data` e `@RequiredArgsConstructor`.
- **Jakarta Validation (Bean Validation):** API para validação de dados em objetos Java, utilizada para garantir a integridade dos DTOs e modelos.

## Estrutura do Projeto

O projeto está organizado em pacotes lógicos, refletindo a arquitetura em camadas:

```
Api_de_zoologico.zoo/
├── src/
│   └── main/
│       └── java/
│           └── Api_de_zoologico/
│               └── zoo/
│                   ├── ZooApplication.java  # Ponto de entrada da aplicação Spring Boot
│                   ├── config/              # Classes de configuração (e.g., segurança, roles)
│                   ├── controllers/         # Endpoints da API REST
│                   ├── dtos/                # Objetos de Transferência de Dados
│                   ├── models/              # Entidades de banco de dados (JPA)
│                   ├── repositories/        # Interfaces de repositório (Spring Data JPA)
│                   ├── security/            # Classes relacionadas à segurança (JWT, UserDetailsService)
│                   └── services/            # Lógica de negócio
│                   └── utils/               # Classes utilitárias
├── pom.xml                                # Arquivo de configuração do Maven
└── ... (outros arquivos de configuração e recursos)
```

## Autenticação e Autorização

A API implementa um sistema de autenticação e autorização baseado em JWT e Spring Security. Os usuários devem se autenticar para obter um token JWT, que deve ser enviado em todas as requisições subsequentes para endpoints protegidos.

### Fluxo de Autenticação

1. O cliente envia credenciais (username/password) para o endpoint de login.
2. A API autentica o usuário e, se bem-sucedido, gera um token JWT.
3. O token JWT é retornado ao cliente.
4. Para acessar recursos protegidos, o cliente inclui o token JWT no cabeçalho `Authorization` (e.g., `Bearer <token>`).
5. O Spring Security intercepta a requisição, valida o token e verifica as permissões do usuário (`@PreAuthorize`).

### Roles de Usuário

O sistema define as seguintes roles para controle de acesso:

- **ROLE_ADMIN**: Acesso administrativo completo.
- **ROLE_CUIDADOR**: Permissões para gerenciar animais, alimentação e habitats.
- **ROLE_VETERINARIO**: Permissões para gerenciar animais e aspectos de saúde.
- **ROLE_GERENTE_EVENTOS**: Permissões para gerenciar eventos e visitantes.
- **ROLE_VISITANTE**: Permissões básicas, como visualizar informações públicas e gerenciar seu próprio perfil.

### Endpoint de Autenticação

#### `POST /auth/login`

Autentica um usuário e retorna um token JWT para acesso futuro.

**Request Body:**

```json
{
  "username": "seu_usuario",
  "password": "sua_senha"
}
```

**Exemplo de Resposta (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzZXVfdXN1YXJpbyIsImlhdCI6MTY3ODkwNTYwMCwiZXhwIjoxNjc4OTA5MjAwfQ.signature",
  "username": "seu_usuario"
}
```

**Exemplo de Resposta (401 Unauthorized):**

```json
{
  "message": "Credenciais inválidas"
}
```




## Estrutura do Projeto

O projeto Java/Spring segue uma estrutura de pacotes bem definida, organizada para refletir a arquitetura em camadas e as responsabilidades de cada componente. Esta organização facilita a navegação, a manutenção e a escalabilidade da aplicação.

```
Api_de_zoologico.zoo/
├── src/
│   └── main/
│       └── java/
│           └── Api_de_zoologico/
│               └── zoo/
│                   ├── ZooApplication.java  # Ponto de entrada da aplicação Spring Boot
│                   ├── config/              # Classes de configuração (e.g., segurança, roles)
│                   │   ├── RoleSeeder.java
│                   │   └── SecurityConfig.java
│                   ├── controllers/         # Endpoints da API REST
│                   │   ├── AlimentacaoController.java
│                   │   ├── AnimalController.java
│                   │   ├── AuthController.java
│                   │   ├── CuidadorController.java
│                   │   ├── EspecieController.java
│                   │   ├── EventoController.java
│                   │   ├── FuncionarioController.java
│                   │   ├── HabitatController.java
│                   │   ├── VeterinarioController.java
│                   │   └── VisitanteController.java
│                   ├── dtos/                # Objetos de Transferência de Dados
│                   │   ├── AlimentacaoDto.java
│                   │   ├── AnimalDto.java
│                   │   ├── AuthRequest.java
│                   │   ├── AuthResponse.java
│                   │   ├── CuidadorDto.java
│                   │   ├── EspecieDto.java
│                   │   ├── EventoDto.java
│                   │   ├── FuncionarioRequestDto.java
│                   │   ├── FuncionarioResponseDto.java
│                   │   ├── HabitatDto.java
│                   │   ├── RoleRequestDto.java
│                   │   ├── RoleResponseDto.java
│                   │   ├── VeterinarioDto.java
│                   │   ├── VisitanteDto.java
│                   │   ├── VisitanteRequestDto.java
│                   │   └── VisitanteResponseDto.java
│                   ├── models/              # Entidades de banco de dados (JPA)
│                   │   ├── Alimentacao.java
│                   │   ├── Animal.java
│                   │   ├── Cuidador.java
│                   │   ├── Especie.java
│                   │   ├── Evento.java
│                   │   ├── Funcionario.java
│                   │   ├── Habitat.java
│                   │   ├── Role.java
│                   │   ├── User.java
│                   │   ├── Veterinario.java
│                   │   └── Visitante.java
│                   ├── repositories/        # Interfaces de repositório (Spring Data JPA)
│                   │   ├── AlimentacaoRepository.java
│                   │   ├── AnimalRepository.java
│                   │   ├── CuidadorRepository.java
│                   │   ├── EspecieRepository.java
│                   │   ├── EventoRepository.java
│                   │   ├── FuncionarioRepository.java
│                   │   ├── HabitatRepository.java
│                   │   ├── RoleRepository.java
│                   │   ├── UserRepository.java
│                   │   ├── VeterinarioRepository.java
│                   │   └── VisitanteRepository.java
│                   ├── security/            # Classes relacionadas à segurança (JWT, UserDetailsService)
│                   │   ├── CustomUserDetailsService.java
│                   │   ├── JwtAuthenticationFilter.java
│                   │   └── JwtUtil.java
│                   ├── services/            # Lógica de negócio
│                   │   ├── AlimentacaoService.java
│                   │   ├── AnimalService.java
│                   │   ├── CuidadorService.java
│                   │   ├── EmailService.java
│                   │   ├── EspecieService.java
│                   │   ├── EventoService.java
│                   │   ├── FuncionarioService.java
│                   │   ├── HabitatService.java
│                   │   ├── JwtService.java
│                   │   ├── RoleService.java
│                   │   ├── VeterinarioService.java
│                   │   └── VisitanteService.java
│                   └── utils/               # Classes utilitárias
│                       └── RespostaUtil.java
├── pom.xml                                # Arquivo de configuração do Maven
└── ... (outros arquivos de configuração e recursos, como application.properties)
```

Cada diretório contém arquivos Java que implementam as funcionalidades específicas da camada. Por exemplo, `controllers` abriga as classes que definem os endpoints REST, `models` contém as entidades JPA que mapeiam as tabelas do banco de dados, e `services` encapsula a lógica de negócios.



## Modelos de Dados (Entidades JPA)

Os modelos de dados representam as entidades persistidas no banco de dados MySQL e são mapeados usando JPA (Java Persistence API) com Hibernate. Cada classe de modelo é anotada com `@Entity` e `@Table` para definir o mapeamento para as tabelas do banco de dados. Os relacionamentos entre as entidades são definidos usando anotações como `@OneToMany`, `@ManyToOne`, `@ManyToMany`, etc.

### `Alimentacao`

Representa o registro de uma alimentação de um animal específico.

```java
@Entity
@Table(name = "alimentacoes")
public class Alimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoComida;
    private double quantidade;
    private LocalDateTime dataAlimentacao;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único da alimentação (chave primária).
- `tipoComida`: Tipo de comida fornecida (ex: carne, vegetais, ração).
- `quantidade`: Quantidade de comida fornecida.
- `dataAlimentacao`: Data e hora em que a alimentação ocorreu.
- `animal`: Animal que recebeu a alimentação (relacionamento Many-to-One com `Animal`).

### `Animal`

Representa um animal individual no zoológico.

```java
@Entity
@Table(name = "animais")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int idade;
    private String especie;
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "habitat_id")
    private Habitat habitat;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alimentacao> alimentacoes;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do animal.
- `nome`: Nome do animal.
- `idade`: Idade do animal.
- `especie`: Espécie do animal (pode ser um nome comum ou científico).
- `localizacao`: Localização atual do animal no zoológico.
- `habitat`: Habitat onde o animal reside (relacionamento Many-to-One com `Habitat`).
- `alimentacoes`: Lista de registros de alimentação associados a este animal (relacionamento One-to-Many com `Alimentacao`).

### `Cuidador`

Representa um cuidador de animais, que é um tipo de funcionário.

```java
@Entity
@Table(name = "cuidadores")
public class Cuidador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String especialidade;
    private String turno;

    @OneToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do cuidador.
- `especialidade`: Área de especialização do cuidador (ex: felinos, aves).
- `turno`: Turno de trabalho do cuidador.
- `funcionario`: Dados gerais do funcionário associado (relacionamento One-to-One com `Funcionario`).

### `Especie`

Representa uma espécie de animal.

```java
@Entity
@Table(name = "especies")
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String familia;
    private String ordem;
    private String classe;
    private String descricao;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único da espécie.
- `nome`: Nome científico ou comum da espécie.
- `familia`: Família taxonômica.
- `ordem`: Ordem taxonômica.
- `classe`: Classe taxonômica.
- `descricao`: Descrição da espécie.

### `Evento`

Representa um evento ou atividade que ocorre no zoológico.

```java
@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private int capacidadeMaxima;
    private double preco;
    private String localizacao;

    @ManyToMany
    @JoinTable(name = "evento_visitante",
               joinColumns = @JoinColumn(name = "evento_id"),
               inverseJoinColumns = @JoinColumn(name = "visitante_id"))
    private Set<Visitante> visitantesInscritos;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do evento.
- `nome`: Nome do evento.
- `descricao`: Descrição detalhada do evento.
- `dataInicio`: Data e hora de início do evento.
- `dataFim`: Data e hora de término do evento.
- `capacidadeMaxima`: Número máximo de participantes.
- `preco`: Preço do ingresso para o evento.
- `localizacao`: Local onde o evento será realizado.
- `visitantesInscritos`: Conjunto de visitantes inscritos no evento (relacionamento Many-to-Many com `Visitante`).

### `Funcionario`

Representa um funcionário geral do zoológico.

```java
@Entity
@Table(name = "funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;
    private String telefone;
    private LocalDate dataContratacao;

    @OneToOne(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private User user;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do funcionário.
- `nome`: Nome completo do funcionário.
- `cpf`: CPF do funcionário.
- `cargo`: Cargo do funcionário (ex: Cuidador, Veterinário, Gerente).
- `telefone`: Número de telefone para contato.
- `dataContratacao`: Data de contratação do funcionário.
- `user`: Informações de usuário para login (relacionamento One-to-One com `User`).

### `Habitat`

Representa um habitat ou recinto no zoológico.

```java
@Entity
@Table(name = "habitats")
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private int capacidade;
    private String localizacao;
    private String descricao;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do habitat.
- `nome`: Nome do habitat.
- `tipo`: Tipo de ambiente (ex: floresta, deserto, aquático).
- `capacidade`: Capacidade máxima de animais ou visitantes.
- `localizacao`: Localização física do habitat no zoológico.
- `descricao`: Descrição do habitat.
- `animais`: Lista de animais que residem neste habitat (relacionamento One-to-Many com `Animal`).

### `Role`

Representa os papéis (roles) de usuário para controle de acesso.

```java
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do papel.
- `name`: Nome do papel (ex: ROLE_ADMIN, ROLE_CUIDADOR).

### `User`

Representa um usuário do sistema, com credenciais de login.

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "user")
    private Funcionario funcionario;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do usuário.
- `username`: Nome de usuário para login.
- `password`: Senha do usuário (criptografada).
- `roles`: Conjunto de papéis atribuídos ao usuário (relacionamento Many-to-Many com `Role`).
- `funcionario`: Funcionário associado a este usuário (relacionamento One-to-One com `Funcionario`).

### `Veterinario`

Representa um veterinário, que é um tipo de funcionário.

```java
@Entity
@Table(name = "veterinarios")
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String crmv;
    private String especialidade;

    @OneToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do veterinário.
- `crmv`: Número de registro no Conselho Regional de Medicina Veterinária.
- `especialidade`: Área de especialização do veterinário.
- `funcionario`: Dados gerais do funcionário associado (relacionamento One-to-One com `Funcionario`).

### `Visitante`

Representa um visitante do zoológico.

```java
@Entity
@Table(name = "visitantes")
public class Visitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private LocalDateTime dataCadastro;

    @ManyToMany(mappedBy = "visitantesInscritos")
    private Set<Evento> eventosInscritos;
    // ... getters e setters
}
```

**Atributos Principais:**
- `id`: Identificador único do visitante.
- `nome`: Nome completo do visitante.
- `cpf`: CPF do visitante.
- `email`: Endereço de e-mail.
- `telefone`: Número de telefone para contato.
- `dataNascimento`: Data de nascimento do visitante.
- `dataCadastro`: Data e hora do cadastro do visitante.
- `eventosInscritos`: Conjunto de eventos nos quais o visitante está inscrito (relacionamento Many-to-Many com `Evento`).




## Endpoints da API e Padrões de Resposta

Esta seção detalha os endpoints RESTful disponíveis na API, incluindo métodos HTTP, URIs, parâmetros de requisição, corpos de requisição (DTOs), exemplos de respostas e as roles de segurança necessárias para acessá-los. As respostas de erro seguem um padrão consistente para facilitar o tratamento por parte dos clientes.

### Padrões de Resposta de Erro

Em caso de erro, a API retorna um objeto JSON com uma estrutura padronizada, contendo uma mensagem de erro e, opcionalmente, detalhes adicionais.

**Exemplo de Resposta de Erro (400 Bad Request, 404 Not Found, 500 Internal Server Error):**

```json
{
  "message": "Mensagem de erro concisa",
  "details": "Detalhes adicionais sobre o erro, se disponíveis"
}
```

---

### `AlimentacaoController`

Gerencia as operações relacionadas à alimentação dos animais no zoológico.

#### `GET /alimentacoes`

Retorna uma lista paginada de todas as alimentações registradas, com opções de filtro.

- **Descrição:** Busca todas as alimentações ou filtra por tipo de comida ou ID do animal.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `tipoComida` (String): Filtra as alimentações pelo tipo de comida (ex: "carne", "vegetais").
    - `animalId` (Long): Filtra as alimentações associadas a um animal específico pelo seu ID.

- **Exemplo de Requisição:**
  ```http
  GET /alimentacoes?tipoComida=carne&animalId=1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "tipoComida": "Carne bovina",
      "quantidade": 5.0,
      "dataAlimentacao": "2023-01-15T10:00:00",
      "animal": {
        "id": 1,
        "nome": "Simba"
      }
    }
  ]
  ```

#### `GET /alimentacoes/{id}`

Retorna os detalhes de uma alimentação específica pelo seu ID.

- **Descrição:** Busca uma alimentação pelo seu identificador único.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID da alimentação a ser buscada.

- **Exemplo de Requisição:**
  ```http
  GET /alimentacoes/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "tipoComida": "Carne bovina",
    "quantidade": 5.0,
    "dataAlimentacao": "2023-01-15T10:00:00",
    "animal": {
      "id": 1,
      "nome": "Simba"
    }
  }
  ```

- **Exemplo de Resposta (404 Not Found):**
  ```json
  {
    "message": "Alimentação não encontrada",
    "details": "Não foi possível encontrar a alimentação com ID: 999."
  }
  ```

#### `POST /alimentacoes`

Cria um novo registro de alimentação.

- **Descrição:** Adiciona uma nova alimentação para um animal.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_ADMIN`
- **Corpo da Requisição (`AlimentacaoDto`):**
  ```json
  {
    "animalId": 1,
    "tipoComida": "Vegetais frescos",
    "quantidade": 2.5,
    "dataAlimentacao": "2023-01-16T09:30:00"
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "tipoComida": "Vegetais frescos",
    "quantidade": 2.5,
    "dataAlimentacao": "2023-01-16T09:30:00",
    "animal": {
      "id": 1,
      "nome": "Simba"
    }
  }
  ```

#### `PUT /alimentacoes/{id}`

Atualiza um registro de alimentação existente.

- **Descrição:** Modifica os detalhes de uma alimentação específica.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID da alimentação a ser atualizada.
- **Corpo da Requisição (`AlimentacaoDto`):**
  ```json
  {
    "animalId": 1,
    "tipoComida": "Vegetais orgânicos",
    "quantidade": 3.0,
    "dataAlimentacao": "2023-01-16T09:30:00"
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "tipoComida": "Vegetais orgânicos",
    "quantidade": 3.0,
    "dataAlimentacao": "2023-01-16T09:30:00",
    "animal": {
      "id": 1,
      "nome": "Simba"
    }
  }
  ```

#### `DELETE /alimentacoes/{id}`

Remove um registro de alimentação.

- **Descrição:** Exclui uma alimentação pelo seu identificador único.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID da alimentação a ser removida.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Alimentação removida com sucesso"
  }
  ```

- **Exemplo de Resposta (404 Not Found):**
  ```json
  {
    "message": "Erro ao remover alimentação",
    "details": "Não foi possível remover a alimentação com ID: 999. Alimentação não encontrada."
  }
  ```




---

### `AnimalController`

Gerencia as operações relacionadas aos animais do zoológico.

#### `GET /animais`

Retorna uma lista de todos os animais.

- **Descrição:** Busca todos os animais cadastrados no zoológico.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`

- **Exemplo de Requisição:**
  ```http
  GET /animais
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Simba",
      "idade": 5,
      "especie": "Leão",
      "localizacao": "Savana Africana",
      "habitat": {
        "id": 1,
        "nome": "Savana Africana"
      }
    }
  ]
  ```

#### `GET /animais/{id}`

Retorna um animal específico pelo seu ID.

- **Descrição:** Busca um animal pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Path:**
    - `id` (Long): O ID do animal a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /animais/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Simba",
    "idade": 5,
    "especie": "Leão",
    "localizacao": "Savana Africana",
    "habitat": {
      "id": 1,
      "nome": "Savana Africana"
    }
  }
  ```

- **Exemplo de Resposta (404 Not Found):**
  ```json
  {
    "message": "Animal não encontrado",
    "details": "Não foi possível encontrar o animal com ID: 999."
  }
  ```

#### `GET /animais/idade`

Retorna uma lista de animais filtrados por faixa etária.

- **Descrição:** Filtra animais com base em uma idade mínima e máxima.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Query:**
    - `idadeMin` (int): Idade mínima para o filtro.
    - `idadeMax` (int): Idade máxima para o filtro.

- **Exemplo de Requisição:**
  ```http
  GET /animais/idade?idadeMin=3&idadeMax=8
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Simba",
      "idade": 5,
      "especie": "Leão",
      "localizacao": "Savana Africana",
      "habitat": {
        "id": 1,
        "nome": "Savana Africana"
      }
    }
  ]
  ```

#### `GET /animais/nome`

Retorna uma lista de animais filtrados por nome.

- **Descrição:** Busca animais cujo nome contém a string fornecida (case-insensitive).
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Query:**
    - `nome` (String): O nome ou parte do nome do animal.

- **Exemplo de Requisição:**
  ```http
  GET /animais/nome?nome=Simba
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Simba",
      "idade": 5,
      "especie": "Leão",
      "localizacao": "Savana Africana",
      "habitat": {
        "id": 1,
        "nome": "Savana Africana"
      }
    }
  ]
  ```

#### `GET /animais/especie`

Retorna uma lista de animais filtrados por espécie.

- **Descrição:** Busca animais de uma espécie específica (case-insensitive).
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Query:**
    - `nome` (String): O nome da espécie.

- **Exemplo de Requisição:**
  ```http
  GET /animais/especie?nome=Leão
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Simba",
      "idade": 5,
      "especie": "Leão",
      "localizacao": "Savana Africana",
      "habitat": {
        "id": 1,
        "nome": "Savana Africana"
      }
    }
  ]
  ```

#### `POST /animais`

Cria um novo animal.

- **Descrição:** Adiciona um novo animal ao zoológico.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Corpo da Requisição (`AnimalDto`):**
  ```json
  {
    "nome": "Nala",
    "idade": 4,
    "especie": "Leoa",
    "localizacao": "Savana Africana",
    "habitatId": 1
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "nome": "Nala",
    "idade": 4,
    "especie": "Leoa",
    "localizacao": "Savana Africana",
    "habitat": {
      "id": 1,
      "nome": "Savana Africana"
    }
  }
  ```

#### `PUT /animais/{id}`

Atualiza um animal existente.

- **Descrição:** Modifica os detalhes de um animal específico.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Path:**
    - `id` (Long): O ID do animal a ser atualizado.
- **Corpo da Requisição (`AnimalDto`):**
  ```json
  {
    "nome": "Simba Adulto",
    "idade": 6,
    "localizacao": "Recinto Principal"
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Simba Adulto",
    "idade": 6,
    "especie": "Leão",
    "localizacao": "Recinto Principal",
    "habitat": {
      "id": 1,
      "nome": "Savana Africana"
    }
  }
  ```

#### `DELETE /animais/{id}`

Remove um animal.

- **Descrição:** Exclui um animal pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Path:**
    - `id` (Long): O ID do animal a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Animal removido com sucesso"
  }
  ```

- **Exemplo de Resposta (404 Not Found):**
  ```json
  {
    "message": "Erro ao remover animal",
    "details": "Não foi possível remover o animal com ID: 999. Animal não encontrado."
  }
  ```




---

### `AuthController`

Gerencia a autenticação de usuários e a emissão de tokens JWT.

#### `POST /auth/login`

Autentica um usuário com base em seu nome de usuário e senha, e retorna um token JWT para acesso a recursos protegidos.

- **Descrição:** Realiza o login do usuário e gera um token de autenticação.
- **Roles Requeridas:** Nenhuma (endpoint público para autenticação)
- **Corpo da Requisição (`AuthRequest`):**
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3ODkwNTYwMCwiZXhwIjoxNjc4OTA5MjAwfQ.signature",
    "username": "admin"
  }
  ```

- **Exemplo de Resposta (401 Unauthorized):**
  ```json
  {
    "message": "Credenciais inválidas"
  }
  ```




---

### `CuidadorController`

Gerencia as operações relacionadas aos cuidadores de animais.

#### `GET /cuidadores`

Retorna uma lista de todos os cuidadores, com opções de filtro.

- **Descrição:** Busca todos os cuidadores ou filtra por especialidade, turno ou nome.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `especialidade` (String): Filtra cuidadores por especialidade (ex: "felinos", "aves").
    - `turno` (String): Filtra cuidadores por turno de trabalho (ex: "manhã", "tarde").
    - `nome` (String): Filtra cuidadores pelo nome do funcionário associado.

- **Exemplo de Requisição:**
  ```http
  GET /cuidadores?especialidade=felinos
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "especialidade": "Felinos",
      "turno": "manhã",
      "funcionario": {
        "id": 1,
        "nome": "João Silva",
        "cargo": "Cuidador"
      }
    }
  ]
  ```

#### `GET /cuidadores/{id}`

Retorna um cuidador específico pelo seu ID.

- **Descrição:** Busca um cuidador pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do cuidador a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /cuidadores/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "especialidade": "Felinos",
    "turno": "manhã",
    "funcionario": {
      "id": 1,
      "nome": "João Silva",
      "cargo": "Cuidador"
    }
  }
  ```

#### `POST /cuidadores`

Cria um novo cuidador.

- **Descrição:** Adiciona um novo cuidador ao sistema.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Corpo da Requisição (`CuidadorDto`):**
  ```json
  {
    "funcionarioId": 1,
    "especialidade": "Aves",
    "turno": "tarde"
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "especialidade": "Aves",
    "turno": "tarde",
    "funcionario": {
      "id": 1,
      "nome": "João Silva",
      "cargo": "Cuidador"
    }
  }
  ```

#### `PUT /cuidadores/{id}`

Atualiza um cuidador existente.

- **Descrição:** Modifica os detalhes de um cuidador específico.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do cuidador a ser atualizado.
- **Corpo da Requisição (`CuidadorDto`):**
  ```json
  {
    "especialidade": "Aves Exóticas",
    "turno": "noite"
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "especialidade": "Aves Exóticas",
    "turno": "noite",
    "funcionario": {
      "id": 1,
      "nome": "João Silva",
      "cargo": "Cuidador"
    }
  }
  ```

#### `DELETE /cuidadores/{id}`

Remove um cuidador.

- **Descrição:** Exclui um cuidador pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do cuidador a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Cuidador removido com sucesso"
  }
  ```




---

### `EspecieController`

Gerencia as operações relacionadas às espécies de animais.

#### `GET /especies`

Retorna uma lista de todas as espécies, com opções de filtro.

- **Descrição:** Busca todas as espécies ou filtra por nome, família, ordem ou classe.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_VETERINARIO`, `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `nome` (String): Filtra espécies pelo nome (ex: "Leão").
    - `familia` (String): Filtra espécies pela família taxonômica (ex: "Felidae").
    - `ordem` (String): Filtra espécies pela ordem taxonômica (ex: "Carnivora").
    - `classe` (String): Filtra espécies pela classe taxonômica (ex: "Mammalia").

- **Exemplo de Requisição:**
  ```http
  GET /especies?classe=Mammalia&familia=Felidae
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Panthera leo",
      "familia": "Felidae",
      "ordem": "Carnivora",
      "classe": "Mammalia",
      "descricao": "O leão é uma espécie de felino encontrada na África e Ásia."
    }
  ]
  ```

#### `GET /especies/{id}`

Retorna uma espécie específica pelo seu ID.

- **Descrição:** Busca uma espécie pelo seu identificador único.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_VETERINARIO`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID da espécie a ser buscada.

- **Exemplo de Requisição:**
  ```http
  GET /especies/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Panthera leo",
    "familia": "Felidae",
    "ordem": "Carnivora",
    "classe": "Mammalia",
    "descricao": "O leão é uma espécie de felino encontrada na África e Ásia."
  }
  ```

#### `POST /especies`

Cria uma nova espécie.

- **Descrição:** Adiciona uma nova espécie ao sistema.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_VETERINARIO`, `ROLE_ADMIN`
- **Corpo da Requisição (`EspecieDto`):**
  ```json
  {
    "nome": "Panthera tigris",
    "familia": "Felidae",
    "ordem": "Carnivora",
    "classe": "Mammalia",
    "descricao": "O tigre é o maior felino do mundo."
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "nome": "Panthera tigris",
    "familia": "Felidae",
    "ordem": "Carnivora",
    "classe": "Mammalia",
    "descricao": "O tigre é o maior felino do mundo."
  }
  ```

#### `PUT /especies/{id}`

Atualiza uma espécie existente.

- **Descrição:** Modifica os detalhes de uma espécie específica.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_VETERINARIO`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID da espécie a ser atualizada.
- **Corpo da Requisição (`EspecieDto`):**
  ```json
  {
    "descricao": "O tigre é o maior felino do mundo, conhecido por suas listras distintivas."
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "nome": "Panthera tigris",
    "familia": "Felidae",
    "ordem": "Carnivora",
    "classe": "Mammalia",
    "descricao": "O tigre é o maior felino do mundo, conhecido por suas listras distintivas."
  }
  ```

#### `DELETE /especies/{id}`

Remove uma espécie.

- **Descrição:** Exclui uma espécie pelo seu identificador único.
- **Roles Requeridas:** `ROLE_CUIDADOR`, `ROLE_VETERINARIO`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID da espécie a ser removida.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Espécie removida com sucesso"
  }
  ```




---

### `EventoController`

Gerencia as operações relacionadas aos eventos e atividades do zoológico.

#### `GET /eventos`

Retorna uma lista de todos os eventos, com opções de filtro.

- **Descrição:** Busca todos os eventos ou filtra por nome, período de datas, eventos lotados ou futuros.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `nome` (String): Filtra eventos pelo nome.
    - `dataInicio` (LocalDateTime): Data e hora de início do período para filtro (formato ISO 8601, ex: `2023-01-01T00:00:00`).
    - `dataFim` (LocalDateTime): Data e hora de fim do período para filtro (formato ISO 8601, ex: `2023-01-31T23:59:59`).
    - `lotados` (Boolean): Se `true`, retorna apenas eventos que atingiram sua capacidade máxima.
    - `futuros` (Boolean): Se `true`, retorna apenas eventos que ainda não ocorreram.

- **Exemplo de Requisição:**
  ```http
  GET /eventos?nome=Palestra&futuros=true
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Palestra sobre Conservação",
      "descricao": "Palestra educativa sobre conservação de espécies",
      "dataInicio": "2024-03-01T14:00:00",
      "dataFim": "2024-03-01T16:00:00",
      "capacidadeMaxima": 50,
      "preco": 20.00,
      "localizacao": "Auditório Principal",
      "lotado": false,
      "futuro": true
    }
  ]
  ```

#### `GET /eventos/{id}`

Retorna um evento específico pelo seu ID.

- **Descrição:** Busca um evento pelo seu identificador único.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do evento a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /eventos/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Palestra sobre Conservação",
    "descricao": "Palestra educativa sobre conservação de espécies",
    "dataInicio": "2024-03-01T14:00:00",
    "dataFim": "2024-03-01T16:00:00",
    "capacidadeMaxima": 50,
    "preco": 20.00,
    "localizacao": "Auditório Principal",
    "lotado": false,
    "futuro": true
  }
  ```

#### `POST /eventos`

Cria um novo evento.

- **Descrição:** Adiciona um novo evento ao sistema.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Corpo da Requisição (`EventoDto`):**
  ```json
  {
    "nome": "Alimentação dos Leões",
    "descricao": "Demonstração educativa da alimentação dos felinos",
    "dataInicio": "2024-02-15T15:00:00",
    "dataFim": "2024-02-15T16:00:00",
    "capacidadeMaxima": 30,
    "preco": 15.00,
    "localizacao": "Savana Africana"
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "nome": "Alimentação dos Leões",
    "descricao": "Demonstração educativa da alimentação dos felinos",
    "dataInicio": "2024-02-15T15:00:00",
    "dataFim": "2024-02-15T16:00:00",
    "capacidadeMaxima": 30,
    "preco": 15.00,
    "localizacao": "Savana Africana",
    "lotado": false,
    "futuro": true
  }
  ```

#### `PUT /eventos/{id}`

Atualiza um evento existente.

- **Descrição:** Modifica os detalhes de um evento específico.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do evento a ser atualizado.
- **Corpo da Requisição (`EventoDto`):**
  ```json
  {
    "nome": "Alimentação dos Leões (Edição Especial)",
    "preco": 20.00
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "nome": "Alimentação dos Leões (Edição Especial)",
    "descricao": "Demonstração educativa da alimentação dos felinos",
    "dataInicio": "2024-02-15T15:00:00",
    "dataFim": "2024-02-15T16:00:00",
    "capacidadeMaxima": 30,
    "preco": 20.00,
    "localizacao": "Savana Africana",
    "lotado": false,
    "futuro": true
  }
  ```

#### `DELETE /eventos/{id}`

Remove um evento.

- **Descrição:** Exclui um evento pelo seu identificador único.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do evento a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Evento removido com sucesso"
  }
  ```

#### `POST /eventos/{eventoId}/visitantes/{visitanteId}`

Adiciona um visitante a um evento.

- **Descrição:** Inscreve um visitante em um evento específico.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `eventoId` (Long): O ID do evento.
    - `visitanteId` (Long): O ID do visitante.

- **Exemplo de Requisição:**
  ```http
  POST /eventos/1/visitantes/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Palestra sobre Conservação",
    "capacidadeMaxima": 50,
    "inscritos": 1,
    "lotado": false,
    "futuro": true
  }
  ```

#### `DELETE /eventos/{eventoId}/visitantes/{visitanteId}`

Remove um visitante de um evento.

- **Descrição:** Desinscreve um visitante de um evento específico.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `eventoId` (Long): O ID do evento.
    - `visitanteId` (Long): O ID do visitante.

- **Exemplo de Requisição:**
  ```http
  DELETE /eventos/1/visitantes/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Palestra sobre Conservação",
    "capacidadeMaxima": 50,
    "inscritos": 0,
    "lotado": false,
    "futuro": true
  }
  ```




---

### `FuncionarioController`

Gerencia as operações relacionadas aos funcionários gerais do zoológico.

#### `GET /funcionarios`

Retorna uma lista de todos os funcionários, com opções de filtro.

- **Descrição:** Busca todos os funcionários ou filtra por cargo, nome ou CPF.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `cargo` (String): Filtra funcionários pelo cargo (ex: "Cuidador", "Veterinário").
    - `nome` (String): Filtra funcionários pelo nome.
    - `cpf` (String): Filtra funcionários pelo CPF.

- **Exemplo de Requisição:**
  ```http
  GET /funcionarios?cargo=Cuidador
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "João Silva",
      "cpf": "123.456.789-00",
      "cargo": "Cuidador",
      "telefone": "(11) 99999-9999",
      "email": "joao.silva@zoo.com",
      "dataContratacao": "2020-03-15",
      "salario": 3500.00
    }
  ]
  ```

#### `GET /funcionarios/{id}`

Retorna um funcionário específico pelo seu ID.

- **Descrição:** Busca um funcionário pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do funcionário a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /funcionarios/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "cargo": "Cuidador",
    "telefone": "(11) 99999-9999",
    "email": "joao.silva@zoo.com",
    "dataContratacao": "2020-03-15",
    "salario": 3500.00
  }
  ```

#### `POST /funcionarios`

Cria um novo funcionário.

- **Descrição:** Adiciona um novo funcionário ao sistema.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Corpo da Requisição (`FuncionarioRequestDto`):**
  ```json
  {
    "nome": "Maria Santos",
    "cpf": "987.654.321-00",
    "cargo": "Veterinário",
    "telefone": "(11) 88888-8888",
    "email": "maria.santos@zoo.com",
    "dataContratacao": "2021-06-01",
    "salario": 8000.00,
    "username": "maria.santos",
    "password": "senhaSegura"
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "nome": "Maria Santos",
    "cpf": "987.654.321-00",
    "cargo": "Veterinário",
    "telefone": "(11) 88888-8888",
    "email": "maria.santos@zoo.com",
    "dataContratacao": "2021-06-01",
    "salario": 8000.00
  }
  ```

#### `PUT /funcionarios/{id}`

Atualiza um funcionário existente.

- **Descrição:** Modifica os detalhes de um funcionário específico.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do funcionário a ser atualizado.
- **Corpo da Requisição (`FuncionarioRequestDto`):**
  ```json
  {
    "cargo": "Veterinário Sênior",
    "salario": 9500.00
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "nome": "Maria Santos",
    "cpf": "987.654.321-00",
    "cargo": "Veterinário Sênior",
    "telefone": "(11) 88888-8888",
    "email": "maria.santos@zoo.com",
    "dataContratacao": "2021-06-01",
    "salario": 9500.00
  }
  ```

#### `DELETE /funcionarios/{id}`

Remove um funcionário.

- **Descrição:** Exclui um funcionário pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do funcionário a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Funcionário removido com sucesso"
  }
  ```




---

### `HabitatController`

Gerencia as operações relacionadas aos habitats dos animais no zoológico.

#### `GET /habitats`

Retorna uma lista de todos os habitats, com opções de filtro.

- **Descrição:** Busca todos os habitats ou filtra por nome, tipo ou localização.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Query (Opcionais):**
    - `nome` (String): Filtra habitats pelo nome.
    - `tipo` (String): Filtra habitats pelo tipo (ex: "Terrestre", "Aquático").
    - `localizacao` (String): Filtra habitats pela localização.

- **Exemplo de Requisição:**
  ```http
  GET /habitats?tipo=Terrestre
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Savana Africana",
      "tipo": "Terrestre",
      "capacidade": 15,
      "localizacao": "Setor Norte",
      "descricao": "Habitat que simula as condições da savana africana."
    }
  ]
  ```

#### `GET /habitats/{id}`

Retorna um habitat específico pelo seu ID.

- **Descrição:** Busca um habitat pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`, `ROLE_VETERINARIO`
- **Parâmetros de Path:**
    - `id` (Long): O ID do habitat a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /habitats/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Savana Africana",
    "tipo": "Terrestre",
    "capacidade": 15,
    "localizacao": "Setor Norte",
    "descricao": "Habitat que simula as condições da savana africana."
  }
  ```

#### `POST /habitats`

Cria um novo habitat.

- **Descrição:** Adiciona um novo habitat ao zoológico.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`
- **Corpo da Requisição (`HabitatDto`):**
  ```json
  {
    "nome": "Floresta Tropical",
    "tipo": "Terrestre",
    "capacidade": 10,
    "localizacao": "Setor Sul",
    "descricao": "Habitat que simula uma floresta tropical densa."
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "nome": "Floresta Tropical",
    "tipo": "Terrestre",
    "capacidade": 10,
    "localizacao": "Setor Sul",
    "descricao": "Habitat que simula uma floresta tropical densa."
  }
  ```

#### `PUT /habitats/{id}`

Atualiza um habitat existente.

- **Descrição:** Modifica os detalhes de um habitat específico.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`
- **Parâmetros de Path:**
    - `id` (Long): O ID do habitat a ser atualizado.
- **Corpo da Requisição (`HabitatDto`):**
  ```json
  {
    "capacidade": 12,
    "descricao": "Habitat que simula uma floresta tropical densa e úmida."
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "nome": "Floresta Tropical",
    "tipo": "Terrestre",
    "capacidade": 12,
    "localizacao": "Setor Sul",
    "descricao": "Habitat que simula uma floresta tropical densa e úmida."
  }
  ```

#### `DELETE /habitats/{id}`

Remove um habitat.

- **Descrição:** Exclui um habitat pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`, `ROLE_CUIDADOR`
- **Parâmetros de Path:**
    - `id` (Long): O ID do habitat a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Habitat removido com sucesso"
  }
  ```




---

### `VeterinarioController`

Gerencia as operações relacionadas aos veterinários do zoológico.

#### `GET /veterinarios`

Retorna uma lista de todos os veterinários, com opções de filtro.

- **Descrição:** Busca todos os veterinários ou filtra por especialidade ou nome.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `especialidade` (String): Filtra veterinários pela especialidade (ex: "Animais Selvagens", "Cirurgia").
    - `nome` (String): Filtra veterinários pelo nome do funcionário associado.

- **Exemplo de Requisição:**
  ```http
  GET /veterinarios?especialidade=Animais Selvagens
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "crmv": "SP-12345",
      "especialidade": "Animais Selvagens",
      "funcionario": {
        "id": 2,
        "nome": "Maria Santos",
        "cargo": "Veterinário"
      }
    }
  ]
  ```

#### `GET /veterinarios/{id}`

Retorna um veterinário específico pelo seu ID.

- **Descrição:** Busca um veterinário pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do veterinário a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /veterinarios/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "crmv": "SP-12345",
    "especialidade": "Animais Selvagens",
    "funcionario": {
      "id": 2,
      "nome": "Maria Santos",
      "cargo": "Veterinário"
    }
  }
  ```

#### `POST /veterinarios`

Cria um novo veterinário.

- **Descrição:** Adiciona um novo veterinário ao sistema.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Corpo da Requisição (`VeterinarioDto`):**
  ```json
  {
    "funcionarioId": 2,
    "crmv": "SP-67890",
    "especialidade": "Cirurgia"
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "crmv": "SP-67890",
    "especialidade": "Cirurgia",
    "funcionario": {
      "id": 2,
      "nome": "Maria Santos",
      "cargo": "Veterinário"
    }
  }
  ```

#### `PUT /veterinarios/{id}`

Atualiza um veterinário existente.

- **Descrição:** Modifica os detalhes de um veterinário específico.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do veterinário a ser atualizado.
- **Corpo da Requisição (`VeterinarioDto`):**
  ```json
  {
    "especialidade": "Clínica Geral"
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "crmv": "SP-67890",
    "especialidade": "Clínica Geral",
    "funcionario": {
      "id": 2,
      "nome": "Maria Santos",
      "cargo": "Veterinário"
    }
  }
  ```

#### `DELETE /veterinarios/{id}`

Remove um veterinário.

- **Descrição:** Exclui um veterinário pelo seu identificador único.
- **Roles Requeridas:** `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do veterinário a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Veterinário removido com sucesso"
  }
  ```




---

### `VisitanteController`

Gerencia as operações relacionadas aos visitantes do zoológico.

#### `GET /visitantes`

Retorna uma lista de todos os visitantes, com opções de filtro.

- **Descrição:** Busca todos os visitantes ou filtra por nome, CPF ou e-mail.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Query (Opcionais):**
    - `nome` (String): Filtra visitantes pelo nome.
    - `cpf` (String): Filtra visitantes pelo CPF.
    - `email` (String): Filtra visitantes pelo e-mail.

- **Exemplo de Requisição:**
  ```http
  GET /visitantes?nome=Carlos
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  [
    {
      "id": 1,
      "nome": "Carlos Oliveira",
      "cpf": "111.222.333-44",
      "email": "carlos@email.com",
      "telefone": "(11) 77777-7777",
      "dataNascimento": "1985-05-20",
      "dataCadastro": "2024-01-10T10:00:00"
    }
  ]
  ```

#### `GET /visitantes/{id}`

Retorna um visitante específico pelo seu ID.

- **Descrição:** Busca um visitante pelo seu identificador único.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do visitante a ser buscado.

- **Exemplo de Requisição:**
  ```http
  GET /visitantes/1
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 1,
    "nome": "Carlos Oliveira",
    "cpf": "111.222.333-44",
    "email": "carlos@email.com",
    "telefone": "(11) 77777-7777",
    "dataNascimento": "1985-05-20",
    "dataCadastro": "2024-01-10T10:00:00"
  }
  ```

#### `POST /visitantes`

Cria um novo visitante.

- **Descrição:** Adiciona um novo visitante ao sistema.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Corpo da Requisição (`VisitanteRequestDto`):**
  ```json
  {
    "nome": "Ana Costa",
    "cpf": "555.666.777-88",
    "email": "ana@email.com",
    "telefone": "(11) 66666-6666",
    "dataNascimento": "1990-08-15"
  }
  ```

- **Exemplo de Resposta (201 Created):**
  ```json
  {
    "id": 2,
    "nome": "Ana Costa",
    "cpf": "555.666.777-88",
    "email": "ana@email.com",
    "telefone": "(11) 66666-6666",
    "dataNascimento": "1990-08-15",
    "dataCadastro": "2024-01-10T10:00:00"
  }
  ```

#### `PUT /visitantes/{id}`

Atualiza um visitante existente.

- **Descrição:** Modifica os detalhes de um visitante específico.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do visitante a ser atualizado.
- **Corpo da Requisição (`VisitanteRequestDto`):**
  ```json
  {
    "email": "ana.costa.atualizado@email.com",
    "telefone": "(11) 55555-5555"
  }
  ```

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "id": 2,
    "nome": "Ana Costa",
    "cpf": "555.666.777-88",
    "email": "ana.costa.atualizado@email.com",
    "telefone": "(11) 55555-5555",
    "dataNascimento": "1990-08-15",
    "dataCadastro": "2024-01-10T10:00:00"
  }
  ```

#### `DELETE /visitantes/{id}`

Remove um visitante.

- **Descrição:** Exclui um visitante pelo seu identificador único.
- **Roles Requeridas:** `ROLE_GERENTE_EVENTOS`, `ROLE_ADMIN`
- **Parâmetros de Path:**
    - `id` (Long): O ID do visitante a ser removido.

- **Exemplo de Resposta (200 OK):**
  ```json
  {
    "mensagem": "Visitante removido com sucesso"
  }
  ```




## Exemplos de Integração Java/Spring

Esta seção demonstra como integrar-se com a API do zoológico utilizando um cliente Java, com foco em aplicações Spring Boot e o uso de `RestTemplate` ou `WebClient` para realizar requisições HTTP.

### Configuração do Projeto Maven

Certifique-se de que seu `pom.xml` inclua as seguintes dependências para um cliente REST básico e manipulação de JSON:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <!-- Para WebClient (Spring WebFlux) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
</dependencies>
```

### Exemplo de Cliente REST com `RestTemplate`

`RestTemplate` é uma classe síncrona do Spring para realizar requisições HTTP. É mais simples para casos de uso básicos.

```java
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ZooApiClient {

    private final String BASE_URL = "http://localhost:8080"; // Assumindo que a API está rodando na porta 8080
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private String jwtToken = ""; // Armazenar o token JWT após o login

    public void login(String username, String password) {
        String url = BASE_URL + "/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            this.jwtToken = root.path("token").asText();
            System.out.println("Login bem-sucedido. Token JWT: " + jwtToken);
        } catch (Exception e) {
            System.err.println("Erro no login: " + e.getMessage());
        }
    }

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        }
        return headers;
    }

    public void listarAnimais() {
        String url = BASE_URL + "/animais";
        HttpEntity<String> entity = new HttpEntity<>(getAuthHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("Lista de Animais: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Erro ao listar animais: " + e.getMessage());
        }
    }

    public void criarAnimal(String nome, int idade, String especie, Long habitatId) {
        String url = BASE_URL + "/animais";
        HttpHeaders headers = getAuthHeaders();

        String requestBody = String.format("{\"nome\": \"%s\", \"idade\": %d, \"especie\": \"%s\", \"habitatId\": %d}", nome, idade, especie, habitatId);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Animal criado: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Erro ao criar animal: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ZooApiClient client = new ZooApiClient();

        // 1. Fazer login
        client.login("admin", "admin123");

        // 2. Listar animais (requer autenticação)
        client.listarAnimais();

        // 3. Criar um animal (requer autenticação)
        // Assumindo que o habitat com ID 1 já existe
        client.criarAnimal("Leãozinho", 1, "Leão", 1L);
    }
}
```

### Exemplo de Cliente REST com `WebClient`

`WebClient` é uma interface reativa e não bloqueante do Spring WebFlux, ideal para aplicações que exigem alta performance e escalabilidade.

```java
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

public class ZooApiReactiveClient {

    private final String BASE_URL = "http://localhost:8080";
    private WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();
    private String jwtToken = "";

    public Mono<Void> login(String username, String password) {
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
        return webClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(jsonNode -> {
                    this.jwtToken = jsonNode.path("token").asText();
                    System.out.println("Login bem-sucedido. Token JWT: " + jwtToken);
                })
                .then();
    }

    public Mono<String> listarAnimais() {
        return webClient.get()
                .uri("/animais")
                .headers(headers -> headers.setBearerAuth(jwtToken))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Lista de Animais: " + response));
    }

    public Mono<String> criarAnimal(String nome, int idade, String especie, Long habitatId) {
        String requestBody = String.format("{\"nome\": \"%s\", \"idade\": %d, \"especie\": \"%s\", \"habitatId\": %d}", nome, idade, especie, habitatId);
        return webClient.post()
                .uri("/animais")
                .headers(headers -> headers.setBearerAuth(jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Animal criado: " + response));
    }

    public static void main(String[] args) {
        ZooApiReactiveClient client = new ZooApiReactiveClient();

        client.login("admin", "admin123")
              .then(client.listarAnimais())
              .then(client.criarAnimal("Zebra", 2, "Equus quagga", 1L))
              .block(); // Bloqueia para garantir que todas as operações reativas sejam concluídas
    }
}
```

Estes exemplos fornecem uma base para interagir com a API do zoológico a partir de aplicações Java, utilizando as ferramentas e padrões recomendados pelo ecossistema Spring.

