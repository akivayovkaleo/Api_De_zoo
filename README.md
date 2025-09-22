# API Zoológico - Endpoints

## Animais

| Método   | Endpoint                               | Descrição                       |
| -------- | -------------------------------------- | ------------------------------- |
| `GET`    | `/animais`                             | Lista todos os animais          |
| `GET`    | `/animais/{id}`                        | Busca animal por ID             |
| `GET`    | `/animais/idade?idadeMin=2&idadeMax=5` | Filtra animais por faixa etária |
| `GET`    | `/animais/nome?nome=Leao`              | Busca animais por nome          |
| `GET`    | `/animais/especie?especie=Felidae`     | Filtra animais por espécie      |
| `POST`   | `/animais`                             | Cria novo animal                |
| `PUT`    | `/animais/{id}`                        | Atualiza animal existente       |
| `DELETE` | `/animais/{id}`                        | Deleta animal                   |

**Exemplo de requisição POST:**

```json
{
  "nome": "Girafa",
  "idade": 7,
  "habitat_id": 1,
  "especie_id": 1,
  "cuidador_id": 1,
  "alimentacao_id": 1
}
```

## Espécies

| Método   | Endpoint                            | Descrição                  |
| -------- | ----------------------------------- | -------------------------- |
| `GET`    | `/especies`                         | Lista todas espécies       |
| `GET`    | `/especies/{id}`                    | Busca espécie por ID       |
| `GET`    | `/especies/nome?nome=Leao`          | Filtra espécie por nome    |
| `GET`    | `/especies/familia?familia=Felidae` | Filtra espécie por família |
| `GET`    | `/especies/classe?classe=Mammalia`  | Filtra espécie por classe  |
| `POST`   | `/especies`                         | Cria nova espécie          |
| `PUT`    | `/especies/{id}`                    | Atualiza espécie existente |
| `DELETE` | `/especies/{id}`                    | Remove espécie             |

**Exemplo de requisição POST:**

```json
{
  "nome": "Panthera leo",
  "descricao": "Espécie de leão africano",
  "nomeCientifico": "Panthera leo",
  "familia": "Felidae",
  "ordem": "Carnivora",
  "classe": "Mammalia"
}
```

## Habitats

| Método   | Endpoint                        | Descrição                  |
| -------- | ------------------------------- | -------------------------- |
| `GET`    | `/habitats`                     | Lista todos habitats       |
| `GET`    | `/habitats/{id}`                | Busca habitat por ID       |
| `GET`    | `/habitats/tipo?tipo=Terrestre` | Filtra habitats por tipo   |
| `POST`   | `/habitats`                     | Cria novo habitat          |
| `PUT`    | `/habitats/{id}`                | Atualiza habitat existente |
| `DELETE` | `/habitats/{id}`                | Remove habitat             |

**Exemplo de requisição POST:**

```json
{
  "nome": "Floresta Tropical",
  "tipo": "Floresta",
  "capacidadeAnimal": 50
}
```

## Cuidadores

| Método   | Endpoint                                            | Descrição                           |
| -------- | --------------------------------------------------- | ----------------------------------- |
| `GET`    | `/cuidadores`                                       | Lista todos cuidadores              |
| `GET`    | `/cuidadores/{id}`                                  | Busca cuidador por ID               |
| `GET`    | `/cuidadores/especialidade?especialidade=Mamíferos` | Filtra cuidadores por especialidade |
| `GET`    | `/cuidadores/turno?turno=Manhã`                     | Filtra cuidadores por turno         |
| `POST`   | `/cuidadores`                                       | Cria novo cuidador                  |
| `PUT`    | `/cuidadores/{id}`                                  | Atualiza cuidador existente         |
| `DELETE` | `/cuidadores/{id}`                                  | Remove cuidador                     |

**Exemplo de requisição POST:**

```json
{
  "nome": "Ana Souza",
  "especialidade": "Mamíferos",
  "turno": "Manhã"
}
```

## Alimentações

| Método   | Endpoint             | Descrição                      |
| -------- | -------------------- | ------------------------------ |
| `GET`    | `/alimentacoes`      | Lista todas alimentações       |
| `GET`    | `/alimentacoes/{id}` | Busca alimentação por ID       |
| `POST`   | `/alimentacoes`      | Cria nova alimentação          |
| `PUT`    | `/alimentacoes/{id}` | Atualiza alimentação existente |
| `DELETE` | `/alimentacoes/{id}` | Remove alimentação             |

**Exemplo de requisição POST:**

```json
{
  "tipoComida": "Frutas",
  "quantidadeDiaria": 2.5,
  "animalId": 1
}
```

## Veterinários

| Método   | Endpoint                                  | Descrição                             |
| -------- | ----------------------------------------- | ------------------------------------- |
| `GET`    | `/veterinarios`                           | Lista todos veterinários              |
| `GET`    | `/veterinarios/{id}`                      | Busca veterinário por ID              |
| `GET`    | `/veterinarios?especialidade=Cardiologia` | Filtra veterinários por especialidade |
| `POST`   | `/veterinarios`                           | Cria novo veterinário                 |
| `PUT`    | `/veterinarios/{id}`                      | Atualiza veterinário existente        |
| `DELETE` | `/veterinarios/{id}`                      | Remove veterinário                    |

**Exemplo de requisição POST:**

```json
{
  "nome": "Dr. João Silva",
  "crmv": "SC-12345",
  "especialidade": "Cirurgia"
}
```
