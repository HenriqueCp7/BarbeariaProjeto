package com.FatecEngSoftware.barbearia.controller;


import java.util.List;
import com.FatecEngSoftware.barbearia.repository.ServicoRepository;
import com.FatecEngSoftware.barbearia.model.Servico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoRepository repository;

    public ServicoController(ServicoRepository repository) {
        this.repository = repository;
    }

    @PostMapping 
    public ResponseEntity<Servico> criarServico(@Valid @RequestBody Servico servico) {
        Servico salvo = repository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping 
    public List<Servico> listarServicos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
            public ResponseEntity<Servico> listarClientePorId(@PathVariable Long id) {
            return repository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @Valid @RequestBody Servico dados) {
        return repository.findById(id)
            .map(servico -> {
                servico.setNomeDoServico(dados.getNomeDoServico());
                servico.setPreco(dados.getPreco());
                servico.setDuracaoMinutos(dados.getDuracaoMinutos());
                return ResponseEntity.ok(repository.save(servico));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
