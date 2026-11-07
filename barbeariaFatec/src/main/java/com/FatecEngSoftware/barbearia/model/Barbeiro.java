package com.FatecEngSoftware.barbearia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "barbeiros")
@Getter
@Setter
@NoArgsConstructor
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome e obrigatorio")
    @Column(nullable = false, length = 50)
    private String nome;

    @NotNull(message = "O status e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBarbeiro status;
}
