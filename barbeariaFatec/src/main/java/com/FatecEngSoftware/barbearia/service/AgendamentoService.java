
package com.FatecEngSoftware.barbearia.service;

import com.FatecEngSoftware.barbearia.dto.AgendamentoRequestDTO;
import com.FatecEngSoftware.barbearia.model.*;
import com.FatecEngSoftware.barbearia.repository.*;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            ClienteRepository clienteRepository,
            BarbeiroRepository barbeiroRepository,
            ServicoRepository servicoRepository) {

        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public Agendamento criarAgendamento(AgendamentoRequestDTO dados) {
        
        validarDataFutura(dados.dataHora());

        Cliente cliente = buscarCliente(dados.clienteId());
        Barbeiro barbeiro = buscarBarbeiro(dados.barbeiroId());
        Servico servico = buscarServico(dados.servicoId());

        validarBarbeiroDisponivel(barbeiro);

        validarConflitoHorario(barbeiro, servico, dados.dataHora(), null);

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);
        agendamento.setDataHora(dados.dataHora());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamentoRepository.save(agendamento);
    }


    @Transactional
    public Agendamento atualizarAgendamento(Long id, AgendamentoRequestDTO dados) {
        
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        if (agendamento.getStatus() != StatusAgendamento.AGENDADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possivel editar agendamentos com o status 'Agendado'");
        }

        validarDataFutura(dados.dataHora());

        Cliente cliente = buscarCliente(dados.clienteId());
        Barbeiro barbeiro = buscarBarbeiro(dados.barbeiroId());
        Servico servico = buscarServico(dados.servicoId());
        
        validarBarbeiroDisponivel(barbeiro);

        validarConflitoHorario(barbeiro, servico, dados.dataHora(), id);

        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);
        agendamento.setDataHora(dados.dataHora());

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento atualizarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        if (agendamento.getStatus() != StatusAgendamento.AGENDADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Agendamento já está " + agendamento.getStatus() + " e não pode ser alterado");
        }

        agendamento.setStatus(novoStatus);
        return agendamentoRepository.save(agendamento);
    }

    private void validarDataFutura(LocalDateTime dataHora) {
        if (!dataHora.isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe uma data e hora que ainda não ocorreram");
        }
    }

    private void validarBarbeiroDisponivel(Barbeiro barbeiro) {
        if (barbeiro.getStatus() != StatusBarbeiro.DISPONIVEL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel agendar um barbeiro indisponível");
        }
    }

    private Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    private Barbeiro buscarBarbeiro(Long id) {
        return barbeiroRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbeiro não encontrado"));
    }

    private Servico buscarServico(Long id) {
        return servicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
    }

    private void validarConflitoHorario(Barbeiro barbeiro, Servico servico,
                                    LocalDateTime inicio, Long ignorarId) {

    LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());

    boolean conflito = agendamentoRepository
        .findByBarbeiro_IdAndStatusNot(barbeiro.getId(), StatusAgendamento.CANCELADO)
        .stream()
        .filter(a -> !a.getId().equals(ignorarId))
        .anyMatch(a -> {
            LocalDateTime aInicio = a.getDataHora();
            LocalDateTime aFim = aInicio.plusMinutes(a.getServico().getDuracaoMinutos());
            return inicio.isBefore(aFim) && fim.isAfter(aInicio);
        });

    if (conflito) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT,
            "O barbeiro já possui um agendamento que conflita com esse horário");
    }
}
}