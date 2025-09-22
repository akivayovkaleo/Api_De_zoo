| Método   | Endpoint                                                | Descrição                             |
| -------- | ------------------------------------------------------- | ------------------------------------- |
| `GET`    | `/animais`                                              | Lista todos os animais                |
| `GET`    | `/animais/{id}`                                         | Busca animal por ID                   |
| `GET`    | `/animais?idadeMin=2&idadeMax=5`                  | Filtra animais por faixa etária       |
| `GET`    | `/animais?nome=Leao`                               | Busca animais por nome                |
| `GET`    | `/animais?especie=Felidae`                      | Filtra animais por espécie            |
| `POST`   | `/animais`                                              | Cria novo animal                      |
| `PUT`    | `/animais/{id}`                                         | Atualiza animal existente             |
| `DELETE` | `/animais/{id}`                                         | Deleta animal                         |

{
  "nome": "Girafa",
  "idade": 7,
  "habitat_id": 1,
  "especie_id": 1,
  "cuidador_id": 1,
  "alimentacao_id": 1
}

| Método   | Endpoint                                              | Descrição                             |
| -------- | ----------------------------------------------------- | ------------------------------------- |
| `GET`    | `/especies`                                           | Lista todas espécies              |
| `GET`    | `/especies/{id}`                                      | Busca veterinário por ID              |
| `GET`    | `/especies/nome?nome=Leao`                            | Filtra espécie por nome               |
| `GET`    | `/especies?familia=Felidae`                 | Filtra espécie por familia |
| `GET`    | `/especies?classe=Mammalia`                     | Filtra espécie por classe             |
| `POST`   | `/especies`                                           | Cria novo espécie                 |
| `PUT`    | `/especies/{id}`                                      | Atualiza espécie existente        |
| `DELETE` | `/especies/{id}`                                      | Remove espécie                    |

{
  "nome": "Panthera leo",
  "descricao": "Espécie de leão africano",
  "nomeCientifico": "Panthera leo",
  "familia": "Felidae",
  "ordem": "Carnivora",
  "classe": "Mammalia"
}



| Método   | Endpoint                                                | Descrição                             |
| -------- | ------------------------------------------------------- | ------------------------------------- |
| `GET`    | `/habitats`                                             | Lista todos habitats              |
| `GET`    | `/habitats/{id}`                                        | Busca habitats por ID              |
| `GET`    | `/habitats?tipo=Terrestre`                              | Filtra habitats por tipo               |     |
| `POST`   | `/habitats`                                             | Cria novo habitat                |
| `PUT`    | `/habitats/{id}`                                        | Atualiza habitat existente        |
| `DELETE` | `/habitats/{id}`                                        | Remove habitat                    |

{
  "nome": "Floresta Tropical",
  "tipo": "Floresta",
  "capacidadeAnimal": 50
}


| Método   | Endpoint                            | Descrição                             |
| -------- |-------------------------------------| ------------------------------------- |
| `GET`    | `/cuidadores`                       | Lista todos cuidadores              |
| `GET`    | `/cuidadores/{id}`                  | Busca cuidadores por ID              |
| `GET`    | `/cuidadores?especialidade=Felinos` | Filtra cuidadores por especialidade               |     |
| `GET`    | `/cuidadores?turno=Manhã`           | Filtra cuidadores por turno               |     |
| `POST`   | `/cuidadores`                       | Cria novo cuidador                |
| `PUT`    | `/cuidadores/{id}`                  | Atualiza cuidador existente        |
| `DELETE` | `/cuidadores/{id}`                  | Remove cuidador                    |

{
  "nome": "Ana Souza",
  "especialidade": "Mamíferos",
  "turno": "Manhã"
}


| Método   | Endpoint                                                | Descrição                      |
| -------- | ------------------------------------------------------- |--------------------------------|
| `GET`    | `/alimentacoes`                                             | Lista todos alimentações       |
| `GET`    | `/alimentacoes/{id}`                                        | Busca alimentação por ID       |
| `POST`   | `/alimentacoes`                                             | Cria nova alimentação          |
| `PUT`    | `/alimentacoes/{id}`                                        | Atualiza alimentação existente |
| `DELETE` | `/alimentacoes/{id}`                                        | Remove alimentação             |

{
  "tipoComida": "Frutas",
  "quantidadeDiaria": 2.5,
  "animalId": 1
}


| Método   | Endpoint                                  | Descrição                              |
| -------- |-------------------------------------------|----------------------------------------|
| `GET`    | `/veterinarios`                           | Lista todos veterinários               |
| `GET`    | `/veterinarios/{id}`                      | Busca veterinário por ID               |
| `GET`    | `/veterinarios?especialidade=Cirurgia`    |  Filtra veterinários por especialidade |
| `POST`   | `/veterinarios`                           | Cria novo veterinário                  |
| `PUT`    | `/veterinarios/{id}`                      | Atualiza veterinário existente         |
| `DELETE` | `/veterinarios/{id}`                      | Remove veterinário                     |

{
  "nome": "Dr. João Silva",
  "crmv": "SC-12345",
  "especialidade": "Cirurgia"
}