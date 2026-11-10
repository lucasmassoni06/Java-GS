package br.com.fiap.citizentech.controller.view;

import br.com.fiap.citizentech.model.dto.StatusOcupacaoDTO;
import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.repository.FaixaOcupacaoRepository;
import br.com.fiap.citizentech.model.repository.OperacaoRepository;
import br.com.fiap.citizentech.model.repository.StatusOcupacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/status")
public class StatusOcupacaoViewController {

    @Autowired
    private StatusOcupacaoRepository statusRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;

    @Autowired
    private FaixaOcupacaoRepository faixaRepository;

    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("status/lista");
        mv.addObject("status", statusRepository.findAll()
                .stream()
                .map(StatusOcupacaoDTO::new)
                .toList());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("status/form");
        mv.addObject("status", new StatusOcupacaoDTO());
        mv.addObject("operacoes", operacaoRepository.findAll());
        mv.addObject("faixas", faixaRepository.findAll());
        mv.addObject("acao", "/status");
        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid @ModelAttribute("status") StatusOcupacaoDTO dto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("status/form");
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("faixas", faixaRepository.findAll());
            mv.addObject("acao", "/status");
            return mv;
        }

        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa()).orElse(null);

        if (operacao == null || faixa == null) {
            ModelAndView mv = new ModelAndView("status/form");
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("faixas", faixaRepository.findAll());
            mv.addObject("acao", "/status");
            mv.addObject("erro", "Operacao ou faixa invalida.");
            return mv;
        }

        statusRepository.save(dto.toEntity(operacao, faixa));
        return new ModelAndView("redirect:/status");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        var status = statusRepository.findById(id).orElse(null);
        if (status == null) {
            return new ModelAndView("redirect:/status");
        }
        ModelAndView mv = new ModelAndView("status/form");
        mv.addObject("status", new StatusOcupacaoDTO(status));
        mv.addObject("operacoes", operacaoRepository.findAll());
        mv.addObject("faixas", faixaRepository.findAll());
        mv.addObject("acao", "/status/atualizar/" + id);
        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id,
                                  @Valid @ModelAttribute("status") StatusOcupacaoDTO dto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("status/form");
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("faixas", faixaRepository.findAll());
            mv.addObject("acao", "/status/atualizar/" + id);
            return mv;
        }

        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa()).orElse(null);

        if (operacao == null || faixa == null) {
            ModelAndView mv = new ModelAndView("status/form");
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("faixas", faixaRepository.findAll());
            mv.addObject("acao", "/status/atualizar/" + id);
            mv.addObject("erro", "Operacao ou faixa invalida.");
            return mv;
        }

        var status = dto.toEntity(operacao, faixa);
        status.setIdStatus(id);
        statusRepository.save(status);
        return new ModelAndView("redirect:/status");
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        try {
            statusRepository.deleteById(id);
        } catch (Exception e) {
            return new ModelAndView("redirect:/status?erro=vinculo");
        }
        return new ModelAndView("redirect:/status");
    }
}