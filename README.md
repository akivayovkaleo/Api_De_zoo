1. Criar uma Espécie
POST /especies

Cria um novo registro de espécie.

Corpo da Requisição:

JSON

{
  "nome": "Leão-africano",
  "descricao": "Grandes felinos predadores",
  "nomeCientifico": "Panthera leo",
  "familia": "Felidae",
  "ordem": "Carnivora",
  "classe": "Mammalia"
}
2. Criar um Habitat
POST /habitats

Cria um novo registro de habitat.

Corpo da Requisição:

JSON

{
  "nome": "Floresta Tropical",
  "tipo": "Terrestre",
  "capacidadeAnimal": 50
}
3. Criar um Cuidador
POST /cuidadores

Cria um novo cuidador para os animais.

Corpo da Requisição:

JSON

{
  "nome": "João",
  "especialidade": "Felinos",
  "turno": "Manhã"
}
4. Criar uma Alimentação
POST /alimentacoes

Cria um novo plano de alimentação.

Corpo da Requisição:

JSON

{
  "tipoComida": "Frutas e vegetais",
  "frequencia": "Diariamente",
  "quantidade": "1 kg"
}
5. Criar um Veterinário
POST /veterinarios

Cria um novo registro para um veterinário.

Corpo da Requisição:

JSON

{
  "nome": "Dr. Carlos Santos",
  "especialidade": "Animais Exóticos",
  "crmv": "123456"
}
6. Criar um Animal
POST /animais

Cria um novo animal, vinculando-o a outros registros existentes.

Corpo da Requisição:

JSON

{
    "nome": "Leão",
    "idade": 5,
    "habitat_id": 2,
    "especie_id": 2,
    "cuidador_id": 2,
    "alimentacao_id": 53
}
