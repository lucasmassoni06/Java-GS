package br.com.fiap.citizentech.controller.api;

import br.com.fiap.citizentech.exception.ResourceNotFoundException;
import br.com.fiap.citizentech.model.dto.LinhaDTO;
import br.com.fiap.citizentech.model.entity.Linha;
import br.com.fiap.citizentech.model.repository.LinhaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/linhas")
public class LinhaRestController {

    @Autowired
    private LinhaRepository linhaRepository;

    @GetMapping
    public List<LinhaDTO> listar() {
        return linhaRepository.findAll()
                .stream()
                .map(LinhaDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinhaDTO> buscar(@PathVariable Long id) {
        Linha linha = linhaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Linha", id));
        return ResponseEntity.ok(new LinhaDTO(linha));
    }

    @PostMapping
    public ResponseEntity<LinhaDTO> criar(@RequestBody @Valid LinhaDTO dto) {
        Linha salva = linhaRepository.save(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new LinhaDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinhaDTO> atualizar(@PathVariable Long id,
                                              @RequestBody @Valid LinhaDTO dto) {
        if (!linhaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Linha", id);
        }
        Linha linha = dto.toEntity();
        linha.setIdLinha(id);
        Linha salva = linhaRepository.save(linha);
        return ResponseEntity.ok(new LinhaDTO(salva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!linhaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Linha", id);
        }
        try {
            linhaRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }
}