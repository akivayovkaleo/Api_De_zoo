# API de Zoológico 🐾

Esta API gerencia animais, espécies, habitats, cuidadores, alimentações e veterinários de um zoológico.

---

## Sumário

* [Animais](#animais)
* [Espécies](#espécies)
* [Habitats](#habitats)
* [Cuidadores](#cuidadores)
* [Alimentações](#alimentações)
* [Veterinários](#veterinários)

---

## Animais

| Método   | Endpoint                         | Descrição                       |
| -------- | -------------------------------- | ------------------------------- |
| `GET`    | `/animais`                       | Lista todos os animais          |
| `GET`    | `/animais/{id}`                  | Busca animal por ID             |
| `GET`    | `/animais?idadeMin=2&idadeMax=5` | Filtra animais por faixa etária |
| `GET`    | `/animais?nome=Leao`             | Busca animais por nome          |
| `GET`    | `/animais?especie=Felidae`       | Filtra animais por espécie      |
| `POST`   | `/animais`                       | Cria novo animal                |
| `PUT`    | `/animais/{id}`                  | Atualiza animal existente       |
| `DELETE` | `/animais/{id}`                  | Deleta animal                   |

**Exemplo `POST /animais`:**

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
---

## Espécies

| Método   | Endpoint                    | Descrição                  |
| -------- | --------------------------- | -------------------------- |
| `GET`    | `/especies`                 | Lista todas as espécies    |
| `GET`    | `/especies/{id}`            | Busca espécie por ID       |
| `GET`    | `/especies/nome?nome=Leao`  | Filtra espécie por nome    |
| `GET`    | `/especies?familia=Felidae` | Filtra espécie por família |
| `GET`    | `/especies?classe=Mammalia` | Filtra espécie por classe  |
| `POST`   | `/especies`                 | Cria nova espécie          |
| `PUT`    | `/especies/{id}`            | Atualiza espécie existente |
| `DELETE` | `/especies/{id}`            | Remove espécie             |

**Exemplo `POST /especies`:**

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
---

## Habitats

| Método   | Endpoint                   | Descrição                  |
| -------- | -------------------------- | -------------------------- |
| `GET`    | `/habitats`                | Lista todos os habitats    |
| `GET`    | `/habitats/{id}`           | Busca habitat por ID       |
| `GET`    | `/habitats?tipo=Terrestre` | Filtra habitats por tipo   |
| `POST`   | `/habitats`                | Cria novo habitat          |
| `PUT`    | `/habitats/{id}`           | Atualiza habitat existente |
| `DELETE` | `/habitats/{id}`           | Remove habitat             |

**Exemplo `POST /habitats`:**

```json
{
  "nome": "Floresta Tropical",
  "tipo": "Floresta",
  "capacidadeAnimal": 5
}
```

---

## Cuidadores

| Método   | Endpoint                            | Descrição                           |
| -------- | ----------------------------------- | ----------------------------------- |
| `GET`    | `/cuidadores`                       | Lista todos os cuidadores           |
| `GET`    | `/cuidadores/{id}`                  | Busca cuidador por ID               |
| `GET`    | `/cuidadores?especialidade=Felinos` | Filtra cuidadores por especialidade |
| `GET`    | `/cuidadores?turno=Manhã`           | Filtra cuidadores por turno         |
| `POST`   | `/cuidadores`                       | Cria novo cuidador                  |
| `PUT`    | `/cuidadores/{id}`                  | Atualiza cuidador existente         |
| `DELETE` | `/cuidadores/{id}`                  | Remove cuidador                     |

**Exemplo `POST /cuidadores`:**

```json
{
  "nome": "Ana Souza",
  "especialidade": "Mamíferos",
  "turno": "Manhã"
  "email": "fernandamartins.rm@gmail.com"
}
```

---

## Alimentações

| Método   | Endpoint             | Descrição                      |
| -------- | -------------------- | ------------------------------ |
| `GET`    | `/alimentacoes`      | Lista todas as alimentações    |
| `GET`    | `/alimentacoes/{id}` | Busca alimentação por ID       |
| `GET`    | `/alimentacoes?animal_id=5` | Busca alimentações de um animal       |
| `POST`   | `/alimentacoes`      | Cria nova alimentação          |
| `PUT`    | `/alimentacoes/{id}` | Atualiza alimentação existente |
| `DELETE` | `/alimentacoes/{id}` | Remove alimentação             |

**Exemplo `POST /alimentacoes`:**

```json
{
  "tipoComida": "Frutas",
  "quantidadeDiaria": 2.5
}
```


---

## Veterinários

| Método   | Endpoint                               | Descrição                             |
| -------- | -------------------------------------- | ------------------------------------- |
| `GET`    | `/veterinarios`                        | Lista todos os veterinários           |
| `GET`    | `/veterinarios/{id}`                   | Busca veterinário por ID              |
| `GET`    | `/veterinarios?especialidade=Cirurgia` | Filtra veterinários por especialidade |
| `POST`   | `/veterinarios`                        | Cria novo veterinário                 |
| `PUT`    | `/veterinarios/{id}`                   | Atualiza veterinário existente        |
| `DELETE` | `/veterinarios/{id}`                   | Remove veterinário                    |

**Exemplo `POST /veterinarios`:**

```json
{
  "nome": "Dr. João Silva",
  "crmv": "SC-12345",
  "especialidade": "Cirurgia"
}
```

---

**Observações:**

* Todos os endpoints que recebem dados (`POST` e `PUT`) aceitam JSON no corpo da requisição.
* Filtros são opcionais, passados via query parameters (`?param=value`).
* IDs referenciam entidades existentes n
