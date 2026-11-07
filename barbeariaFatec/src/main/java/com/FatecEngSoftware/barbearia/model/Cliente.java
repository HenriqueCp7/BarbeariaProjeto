package com.FatecEngSoftware.barbearia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Pattern;

@Entity 
@Table(name = "clientes")
@Getter 
@Setter
@NoArgsConstructor 
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome e obrigatorio")
    @Column(nullable = false, length = 50)
    private String nome;

    @NotBlank(message = "O sobrenome e obrigatorio")
    @Column(nullable = false, length = 50)
    private String sobrenome;

    @NotBlank(message = "O numero de telefone e obrigatorio")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 digitos, apenas numeros")
    @Column(nullable = false, length = 11)
    private String numeroTelefone;
}
