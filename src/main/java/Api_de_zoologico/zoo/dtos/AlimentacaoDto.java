package Api_de_zoologico.zoo.dtos;


public record AlimentacaoDto(
        String tipoComida,
        Double quantidadeDiaria,
        String unidadeMedida
) {}
