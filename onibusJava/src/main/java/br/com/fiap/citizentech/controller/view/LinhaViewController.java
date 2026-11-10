package br.com.fiap.citizentech.controller.view;

import br.com.fiap.citizentech.model.dto.LinhaDTO;
import br.com.fiap.citizentech.model.entity.Linha;
import br.com.fiap.citizentech.model.repository.LinhaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/linhas")
public class LinhaViewController {

    @Autowired
    private LinhaRepository linhaRepository;

    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("linhas/lista");
        mv.addObject("linhas", linhaRepository.findAll()
                .stream()
                .map(LinhaDTO::new)
                .toList());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("linhas/form");
        mv.addObject("linha", new LinhaDTO());
        mv.addObject("acao", "/linhas");
        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid @ModelAttribute("linha") LinhaDTO dto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("linhas/form");
            mv.addObject("acao", "/linhas");
            return mv;
        }
        linhaRepository.save(dto.toEntity());
        return new ModelAndView("redirect:/linhas");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Linha linha = linhaRepository.findById(id).orElse(null);
        if (linha == null) {
            return new ModelAndView("redirect:/linhas");
        }
        ModelAndView mv = new ModelAndView("linhas/form");
        mv.addObject("linha", new LinhaDTO(linha));
        mv.addObject("acao", "/linhas/atualizar/" + id);
        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id,
                                  @Valid @ModelAttribute("linha") LinhaDTO dto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("linhas/form");
            mv.addObject("acao", "/linhas/atualizar/" + id);
            return mv;
        }
        Linha linha = dto.toEntity();
        linha.setIdLinha(id);
        linhaRepository.save(linha);
        return new ModelAndView("redirect:/linhas");
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        try {
            linhaRepository.deleteById(id);
        } catch (Exception e) {
            return new ModelAndView("redirect:/linhas?erro=vinculo");
        }
        return new ModelAndView("redirect:/linhas");
    }
}