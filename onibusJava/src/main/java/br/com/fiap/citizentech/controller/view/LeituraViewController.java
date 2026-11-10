package br.com.fiap.citizentech.controller.view;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/leituras")
public class LeituraViewController {

    private final LeituraRepository leituraRepository;
    private final CameraRepository cameraRepository;
    private final OperacaoRepository operacaoRepository;
    private final FaixaOcupacaoRepository faixaRepository;
    private final StatusOcupacaoRepository statusRepository;

    public LeituraViewController(LeituraRepository leituraRepository,
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
    public String listar(Model model) {
        List<LeituraDTO> leituras = leituraRepository.findAll()
                .stream()
                .map(LeituraDTO::new)
                .toList();
        model.addAttribute("leituras", leituras);
        return "leituras/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("leitura", new LeituraDTO());
        model.addAttribute("cameras", cameraRepository.findAll());
        model.addAttribute("operacoes", operacaoRepository.findAll());
        return "leituras/form";
    }

    @PostMapping
    public String criar(@ModelAttribute("leitura") @Valid LeituraDTO dto,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("cameras", cameraRepository.findAll());
            model.addAttribute("operacoes", operacaoRepository.findAll());
            return "leituras/form";
        }

        Camera camera = cameraRepository.findById(dto.getIdCamera()).orElse(null);
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);

        if (camera == null || operacao == null) {
            redirect.addFlashAttribute("erro", "Camera ou operacao invalida");
            return "redirect:/leituras/novo";
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

        redirect.addFlashAttribute("sucesso", "Leitura registrada com sucesso");
        return "redirect:/leituras";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        return leituraRepository.findById(id)
                .map(l -> {
                    model.addAttribute("leitura", new LeituraDTO(l));
                    model.addAttribute("cameras", cameraRepository.findAll());
                    model.addAttribute("operacoes", operacaoRepository.findAll());
                    return "leituras/form";
                })
                .orElse("redirect:/leituras");
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute("leitura") @Valid LeituraDTO dto,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("cameras", cameraRepository.findAll());
            model.addAttribute("operacoes", operacaoRepository.findAll());
            return "leituras/form";
        }
        Camera camera = cameraRepository.findById(dto.getIdCamera()).orElse(null);
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);

        if (camera == null || operacao == null) {
            redirect.addFlashAttribute("erro", "Camera ou operacao invalida");
            return "redirect:/leituras";
        }

        dto.setIdLeitura(id);
        leituraRepository.save(dto.toEntity(camera, operacao));
        redirect.addFlashAttribute("sucesso", "Leitura atualizada");
        return "redirect:/leituras";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
        leituraRepository.deleteById(id);
        redirect.addFlashAttribute("sucesso", "Leitura removida");
        return "redirect:/leituras";
    }
}