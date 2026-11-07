package com.FatecEngSoftware.barbearia.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
    @NotNull(message = "O ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "O ID do barbeiro é obrigatório")
    Long barbeiroId,

    @NotNull(message = "O ID do serviço é obrigatório")
    Long servicoId,

    @NotNull(message = "A data e hora são obrigatórias")
    LocalDateTime dataHora
) {}