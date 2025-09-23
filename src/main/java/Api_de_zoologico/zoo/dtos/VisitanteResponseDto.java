package Api_de_zoologico.zoo.dtos;

public record VisitanteResponseDto(
        Long id,
        String nome,
        Integer idade,
        String documento,
        String username
) {}