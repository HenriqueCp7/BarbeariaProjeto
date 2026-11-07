package com.FatecEngSoftware.barbearia.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicos")
@Getter
@Setter
@NoArgsConstructor 
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do servico e obrigatorio")
    @Column(nullable = false, length = 50)
    private String nomeDoServico;

    @NotNull(message = "O preco e obrigatorio")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull(message = "A duracao e obrigatoria")
    @Min(value = 1, message = "A duracao minima e de 1 minuto")
    @Max(value = 600, message = "A duracao maxima e de 600 minutos")
    private Integer duracaoMinutos;
}
