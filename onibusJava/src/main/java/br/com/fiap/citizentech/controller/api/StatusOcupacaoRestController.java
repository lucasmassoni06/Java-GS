package br.com.fiap.citizentech.controller.api;

import br.com.fiap.citizentech.exception.ResourceNotFoundException;
import br.com.fiap.citizentech.model.dto.StatusOcupacaoDTO;
import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.entity.StatusOcupacao;
import br.com.fiap.citizentech.model.repository.FaixaOcupacaoRepository;
import br.com.fiap.citizentech.model.repository.OperacaoRepository;
import br.com.fiap.citizentech.model.repository.StatusOcupacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusOcupacaoRestController {

    @Autowired
    private StatusOcupacaoRepository statusRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;

    @Autowired
    private FaixaOcupacaoRepository faixaRepository;

    @GetMapping
    public List<StatusOcupacaoDTO> listar() {
        return statusRepository.findAll()
                .stream()
                .map(StatusOcupacaoDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusOcupacaoDTO> buscar(@PathVariable Long id) {
        StatusOcupacao status = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StatusOcupacao", id));
        return ResponseEntity.ok(new StatusOcupacaoDTO(status));
    }

    @PostMapping
    public ResponseEntity<StatusOcupacaoDTO> criar(@RequestBody @Valid StatusOcupacaoDTO dto) {
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao())
                .orElseThrow(() -> new ResourceNotFoundException("Operacao", dto.getIdOperacao()));
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa())
                .orElseThrow(() -> new ResourceNotFoundException("FaixaOcupacao", dto.getIdFaixa()));

        StatusOcupacao salvo = statusRepository.save(dto.toEntity(operacao, faixa));
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusOcupacaoDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusOcupacaoDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid StatusOcupacaoDTO dto) {
        if (!statusRepository.existsById(id)) {
            throw new ResourceNotFoundException("StatusOcupacao", id);
        }

        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao())
                .orElseThrow(() -> new ResourceNotFoundException("Operacao", dto.getIdOperacao()));
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa())
                .orElseThrow(() -> new ResourceNotFoundException("FaixaOcupacao", dto.getIdFaixa()));

        StatusOcupacao status = dto.toEntity(operacao, faixa);
        status.setIdStatus(id);
        StatusOcupacao salvo = statusRepository.save(status);
        return ResponseEntity.ok(new StatusOcupacaoDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!statusRepository.existsById(id)) {
            throw new ResourceNotFoundException("StatusOcupacao", id);
        }
        try {
            statusRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }
}