package br.com.fiap.citizentech.controller.api;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leituras")
public class LeituraRestController {

    private final LeituraRepository leituraRepository;
    private final CameraRepository cameraRepository;
    private final OperacaoRepository operacaoRepository;
    private final FaixaOcupacaoRepository faixaRepository;
    private final StatusOcupacaoRepository statusRepository;

    public LeituraRestController(LeituraRepository leituraRepository,
                                 CameraRepository cameraRepository,
                                 OperacaoRepository operacaoRepository,
                                 FaixaOcupacaoRepository faixaRepository,
                                 StatusOcupacaoRepository statusRepository) {
        this.leituraRepository = leituraRepository;
        this.cameraRepository = cameraRepository;
        this.operacaoRepository = operacaoRepository;
        this.faixaRepository = faixaRepository;
        this.statusRepository = statusRepository;
    }

    @GetMapping
    public List<LeituraDTO> listar() {
        return leituraRepository.findAll()
                .stream()
                .map(LeituraDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraDTO> buscar(@PathVariable Long id) {
        return leituraRepository.findById(id)
                .map(l -> ResponseEntity.ok(new LeituraDTO(l)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LeituraDTO> criar(@RequestBody @Valid LeituraDTO dto) {
        Camera camera = cameraRepository.findById(dto.getIdCamera()).orElse(null);
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);

        if (camera == null || operacao == null) {
            return ResponseEntity.badRequest().build();
        }

        Leitura salva = leituraRepository.save(dto.toEntity(camera, operacao));

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

        return ResponseEntity.status(HttpStatus.CREATED).body(new LeituraDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeituraDTO> atualizar(@PathVariable Long id,
                                                @RequestBody @Valid LeituraDTO dto) {
        if (!leituraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Camera camera = cameraRepository.findById(dto.getIdCamera()).orElse(null);
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);

        if (camera == null || operacao == null) {
            return ResponseEntity.badRequest().build();
        }

        dto.setIdLeitura(id);
        Leitura salva = leituraRepository.save(dto.toEntity(camera, operacao));
        return ResponseEntity.ok(new LeituraDTO(salva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!leituraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        leituraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}