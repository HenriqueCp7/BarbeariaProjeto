package com.FatecEngSoftware.barbearia.controller;

import java.util.List;
import com.FatecEngSoftware.barbearia.repository.BarbeiroRepository;
import com.FatecEngSoftware.barbearia.model.Barbeiro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/barbeiros")
public class BarbeiroController {

    private final BarbeiroRepository repository;

    public BarbeiroController(BarbeiroRepository repository) {
        this.repository = repository;
    }

    @PostMapping 
    public ResponseEntity<Barbeiro> criarBarbeiro(@Valid @RequestBody Barbeiro barbeiro) {
        Barbeiro salvo = repository.save(barbeiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Barbeiro> listarBarbeiros() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
            public ResponseEntity<Barbeiro> listarBarbeiroPorId(@PathVariable Long id) {
            return repository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbeiro> atualizarBarbeiro(@PathVariable Long id, @Valid @RequestBody Barbeiro dados) {
        return repository.findById(id)
            .map(barbeiro -> {
                barbeiro.setNome(dados.getNome());
                barbeiro.setStatus(dados.getStatus());
                return ResponseEntity.ok(repository.save(barbeiro));
            })
            .orElse(ResponseEntity.notFound().build());
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBarbeiro(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
