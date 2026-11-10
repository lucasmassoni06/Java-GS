package br.com.fiap.citizentech.controller.view;

import br.com.fiap.citizentech.model.dto.LinhaDTO;
import br.com.fiap.citizentech.model.repository.LinhaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/linhas")
public class LinhaViewController {

    private final LinhaRepository linhaRepository;

    public LinhaViewController(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("linhas", linhaRepository.findAll()
                .stream()
                .map(LinhaDTO::new)
                .toList());
        return "linhas/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("linha", new LinhaDTO());
        return "linhas/form";
    }

    @PostMapping
    public String criar(@ModelAttribute("linha") @Valid LinhaDTO dto,
                        BindingResult result,
                        RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "linhas/form";
        }
        linhaRepository.save(dto.toEntity());
        redirect.addFlashAttribute("sucesso", "Linha cadastrada");
        return "redirect:/linhas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        return linhaRepository.findById(id)
                .map(l -> {
                    model.addAttribute("linha", new LinhaDTO(l));
                    return "linhas/form";
                })
                .orElse("redirect:/linhas");
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute("linha") @Valid LinhaDTO dto,
                            BindingResult result,
                            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "linhas/form";
        }
        dto.setIdLinha(id);
        linhaRepository.save(dto.toEntity());
        redirect.addFlashAttribute("sucesso", "Linha atualizada");
        return "redirect:/linhas";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
        linhaRepository.deleteById(id);
        redirect.addFlashAttribute("sucesso", "Linha removida");
        return "redirect:/linhas";
    }
}