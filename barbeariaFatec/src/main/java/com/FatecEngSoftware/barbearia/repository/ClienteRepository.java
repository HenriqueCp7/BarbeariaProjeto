package com.FatecEngSoftware.barbearia.repository;
import com.FatecEngSoftware.barbearia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}