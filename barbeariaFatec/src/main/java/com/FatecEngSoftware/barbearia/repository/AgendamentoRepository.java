package com.FatecEngSoftware.barbearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.FatecEngSoftware.barbearia.model.Agendamento;
import com.FatecEngSoftware.barbearia.model.StatusAgendamento;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByBarbeiro_IdAndStatusNot(Long barbeiroId, StatusAgendamento status);
}
