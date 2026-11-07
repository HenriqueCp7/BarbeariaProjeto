package com.FatecEngSoftware.barbearia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FatecEngSoftware.barbearia.dto.AgendamentoRequestDTO;
import com.FatecEngSoftware.barbearia.dto.AtualizarStatusDTO;
import com.FatecEngSoftware.barbearia.model.Agendamento;
import com.FatecEngSoftware.barbearia.repository.AgendamentoRepository;
import com.FatecEngSoftware.barbearia.service.AgendamentoService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;
    private final AgendamentoRepository repository;

    public AgendamentoController(AgendamentoService service, AgendamentoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping 
    public ResponseEntity<Agendamento> criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO agendamento) {
        Agendamento salvo = service.criarAgendamento(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping 
    public List<Agendamento> listarAgendamentos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
            public ResponseEntity<Agendamento> listarAgendamentoPorId(@PathVariable Long id) {
            return repository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable Long id, @Valid @RequestBody AgendamentoRequestDTO dados) {
        return ResponseEntity.ok(service.atualizarAgendamento(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Agendamento> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusDTO dados) {
        return ResponseEntity.ok(service.atualizarStatus(id, dados.status()));
    }
}
