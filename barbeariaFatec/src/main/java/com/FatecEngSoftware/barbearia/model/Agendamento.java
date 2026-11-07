package com.FatecEngSoftware.barbearia.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@NoArgsConstructor 
public class Agendamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O cliente e obrigatorio")
    @ManyToOne 
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "O barbeiro e obrigatorio")
    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    @NotNull(message = "O servico e obrigatorio")
    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @NotNull(message = "O status e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    @NotNull(message = "A data e hora sao obrigatorios")
    @Future(message = "Digite uma data e hora que ainda nao ocorreram")
    @Column(nullable = false)
    private LocalDateTime dataHora;
}
