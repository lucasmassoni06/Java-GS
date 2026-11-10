package br.com.fiap.citizentech.controller.view;

import br.com.fiap.citizentech.model.dto.StatusOcupacaoDTO;
import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.repository.FaixaOcupacaoRepository;
import br.com.fiap.citizentech.model.repository.OperacaoRepository;
import br.com.fiap.citizentech.model.repository.StatusOcupacaoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/status")
public class StatusOcupacaoViewController {

    private final StatusOcupacaoRepository statusRepository;
    private final OperacaoRepository operacaoRepository;
    private final FaixaOcupacaoRepository faixaRepository;

    public StatusOcupacaoViewController(StatusOcupacaoRepository statusRepository,
                                        OperacaoRepository operacaoRepository,
                                        FaixaOcupacaoRepository faixaRepository) {
        this.statusRepository = statusRepository;
        this.operacaoRepository = operacaoRepository;
        this.faixaRepository = faixaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("status", statusRepository.findAll()
                .stream()
                .map(StatusOcupacaoDTO::new)
                .toList());
        return "status/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("status", new StatusOcupacaoDTO());
        model.addAttribute("operacoes", operacaoRepository.findAll());
        model.addAttribute("faixas", faixaRepository.findAll());
        return "status/form";
    }

    @PostMapping
    public String criar(@ModelAttribute("status") @Valid StatusOcupacaoDTO dto,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("operacoes", operacaoRepository.findAll());
            model.addAttribute("faixas", faixaRepository.findAll());
            return "status/form";
        }
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa()).orElse(null);

        if (operacao == null || faixa == null) {
            redirect.addFlashAttribute("erro", "Operacao ou faixa invalida");
            return "redirect:/status/novo";
        }

        dto.setAtualizadoEm(java.time.LocalDateTime.now());

        statusRepository.save(dto.toEntity(operacao, faixa));
        redirect.addFlashAttribute("sucesso", "Status registrado");
        return "redirect:/status";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        return statusRepository.findById(id)
                .map(s -> {
                    model.addAttribute("status", new StatusOcupacaoDTO(s));
                    model.addAttribute("operacoes", operacaoRepository.findAll());
                    model.addAttribute("faixas", faixaRepository.findAll());
                    return "status/form";
                })
                .orElse("redirect:/status");
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute("status") @Valid StatusOcupacaoDTO dto,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("operacoes", operacaoRepository.findAll());
            model.addAttribute("faixas", faixaRepository.findAll());
            return "status/form";
        }
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa()).orElse(null);

        if (operacao == null || faixa == null) {
            redirect.addFlashAttribute("erro", "Operacao ou faixa invalida");
            return "redirect:/status";
        }

        dto.setIdStatus(id);
        dto.setAtualizadoEm(java.time.LocalDateTime.now());
        statusRepository.save(dto.toEntity(operacao, faixa));
        redirect.addFlashAttribute("sucesso", "Status atualizado");
        return "redirect:/status";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
        statusRepository.deleteById(id);
        redirect.addFlashAttribute("sucesso", "Status removido");
        return "redirect:/status";
    }
}