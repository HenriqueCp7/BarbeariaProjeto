package com.FatecEngSoftware.barbearia.dto;

import com.FatecEngSoftware.barbearia.model.StatusAgendamento;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
    @NotNull(message = "O status é obrigatório")
    StatusAgendamento status
) {}