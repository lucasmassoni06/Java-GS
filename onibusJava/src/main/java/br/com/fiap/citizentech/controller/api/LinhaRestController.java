package br.com.fiap.citizentech.controller.api;

import br.com.fiap.citizentech.model.dto.LinhaDTO;
import br.com.fiap.citizentech.model.repository.LinhaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/linhas")
public class LinhaRestController {

    private final LinhaRepository linhaRepository;

    public LinhaRestController(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    @GetMapping
    public List<LinhaDTO> listar() {
        return linhaRepository.findAll()
                .stream()
                .map(LinhaDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinhaDTO> buscar(@PathVariable Long id) {
        return linhaRepository.findById(id)
                .map(l -> ResponseEntity.ok(new LinhaDTO(l)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LinhaDTO> criar(@RequestBody @Valid LinhaDTO dto) {
        var salva = linhaRepository.save(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new LinhaDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinhaDTO> atualizar(@PathVariable Long id,
                                              @RequestBody @Valid LinhaDTO dto) {
        if (!linhaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dto.setIdLinha(id);
        var salva = linhaRepository.save(dto.toEntity());
        return ResponseEntity.ok(new LinhaDTO(salva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!linhaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        linhaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}