package br.com.fiap.citizentech.controller.api;

import br.com.fiap.citizentech.exception.ResourceNotFoundException;
import br.com.fiap.citizentech.model.dto.LeituraDTO;
import br.com.fiap.citizentech.model.entity.Camera;
import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Leitura;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.entity.StatusOcupacao;
import br.com.fiap.citizentech.model.repository.CameraRepository;
import br.com.fiap.citizentech.model.repository.FaixaOcupacaoRepository;
import br.com.fiap.citizentech.model.repository.LeituraRepository;
import br.com.fiap.citizentech.model.repository.OperacaoRepository;
import br.com.fiap.citizentech.model.repository.StatusOcupacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leituras")
public class LeituraRestController {

    @Autowired
    private LeituraRepository leituraRepository;

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;

    @Autowired
    private FaixaOcupacaoRepository faixaRepository;

    @Autowired
    private StatusOcupacaoRepository statusRepository;

    @GetMapping
    public List<LeituraDTO> listar() {
        return leituraRepository.findAll()
                .stream()
                .map(LeituraDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraDTO> buscar(@PathVariable Long id) {
        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura", id));
        return ResponseEntity.ok(new LeituraDTO(leitura));
    }

    @PostMapping
    public ResponseEntity<LeituraDTO> criar(@RequestBody @Valid LeituraDTO dto) {
        Camera camera = cameraRepository.findById(dto.getIdCamera())
                .orElseThrow(() -> new ResourceNotFoundException("Camera", dto.getIdCamera()));
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao())
                .orElseThrow(() -> new ResourceNotFoundException("Operacao", dto.getIdOperacao()));

        Leitura salva = leituraRepository.save(dto.toEntity(camera, operacao));

        if (operacao.getVeiculo() != null) {
            Integer capacidade = operacao.getVeiculo().getCapacidadeTotal();
            Double percentual = LeituraDTO.calcularPercentual(salva.getContagemPessoas(), capacidade);
            FaixaOcupacao faixa = LeituraDTO.escolherFaixa(percentual, faixaRepository.findAll());

            if (faixa != null) {
                StatusOcupacao status = new StatusOcupacao();
                status.setOperacao(operacao);
                status.setFaixa(faixa);
                status.setDataHoraReferencia(salva.getDataHoraLeitura());
                status.setPercentualOcupacao(percentual);
                status.setAtualizadoEm(LocalDateTime.now());
                statusRepository.save(status);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new LeituraDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeituraDTO> atualizar(@PathVariable Long id,
                                                @RequestBody @Valid LeituraDTO dto) {
        if (!leituraRepository.existsById(id)) {
            throw new ResourceNotFoundException("Leitura", id);
        }

        Camera camera = cameraRepository.findById(dto.getIdCamera())
                .orElseThrow(() -> new ResourceNotFoundException("Camera", dto.getIdCamera()));
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao())
                .orElseThrow(() -> new ResourceNotFoundException("Operacao", dto.getIdOperacao()));

        Leitura leitura = dto.toEntity(camera, operacao);
        leitura.setIdLeitura(id);
        Leitura salva = leituraRepository.save(leitura);
        return ResponseEntity.ok(new LeituraDTO(salva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!leituraRepository.existsById(id)) {
            throw new ResourceNotFoundException("Leitura", id);
        }
        leituraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}