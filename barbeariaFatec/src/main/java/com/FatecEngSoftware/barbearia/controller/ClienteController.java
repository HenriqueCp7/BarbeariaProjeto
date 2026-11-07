package com.FatecEngSoftware.barbearia.controller;

import java.util.List;
import com.FatecEngSoftware.barbearia.repository.ClienteRepository;
import com.FatecEngSoftware.barbearia.model.Cliente;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

        private final ClienteRepository repository;

        public ClienteController(ClienteRepository repository) {
            this.repository = repository;
        }

        @PostMapping 
        public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
            Cliente salvo = repository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        }

        @GetMapping
        public List<Cliente> listarClientes() {
            return repository.findAll();
        }

        @GetMapping("/{id}")
            public ResponseEntity<Cliente> listarClientePorId(@PathVariable Long id) {
            return repository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente dados) {
            return repository.findById(id)
                    .map(cliente -> {
                        cliente.setNome(dados.getNome());
                        cliente.setSobrenome(dados.getSobrenome());
                        cliente.setNumeroTelefone(dados.getNumeroTelefone());
                        return ResponseEntity.ok(repository.save(cliente));
                    })
                    .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
            if (!repository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
}   
