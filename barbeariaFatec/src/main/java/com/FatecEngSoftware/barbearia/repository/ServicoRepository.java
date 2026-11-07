package com.FatecEngSoftware.barbearia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.FatecEngSoftware.barbearia.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {}