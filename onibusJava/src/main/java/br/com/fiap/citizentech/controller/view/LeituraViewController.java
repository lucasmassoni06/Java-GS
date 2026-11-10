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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/leituras")
public class LeituraViewController {

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
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("leituras/lista");
        mv.addObject("leituras", leituraRepository.findAll()
                .stream()
                .map(LeituraDTO::new)
                .toList());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("leituras/form");
        mv.addObject("leitura", new LeituraDTO());
        mv.addObject("cameras", cameraRepository.findAll());
        mv.addObject("operacoes", operacaoRepository.findAll());
        mv.addObject("acao", "/leituras");
        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid @ModelAttribute("leitura") LeituraDTO dto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("leituras/form");
            mv.addObject("cameras", cameraRepository.findAll());
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("acao", "/leituras");
            return mv;
        }

        Camera camera = cameraRepository.findById(dto.getIdCamera()).orElse(null);
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);

        if (camera == null || operacao == null) {
            ModelAndView mv = new ModelAndView("leituras/form");
            mv.addObject("cameras", cameraRepository.findAll());
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("acao", "/leituras");
            mv.addObject("erro", "Camera ou operacao invalida.");
            return mv;
        }

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

        return new ModelAndView("redirect:/leituras");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Leitura leitura = leituraRepository.findById(id).orElse(null);
        if (leitura == null) {
            return new ModelAndView("redirect:/leituras");
        }
        ModelAndView mv = new ModelAndView("leituras/form");
        mv.addObject("leitura", new LeituraDTO(leitura));
        mv.addObject("cameras", cameraRepository.findAll());
        mv.addObject("operacoes", operacaoRepository.findAll());
        mv.addObject("acao", "/leituras/atualizar/" + id);
        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id,
                                  @Valid @ModelAttribute("leitura") LeituraDTO dto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("leituras/form");
            mv.addObject("cameras", cameraRepository.findAll());
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("acao", "/leituras/atualizar/" + id);
            return mv;
        }

        Camera camera = cameraRepository.findById(dto.getIdCamera()).orElse(null);
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);

        if (camera == null || operacao == null) {
            ModelAndView mv = new ModelAndView("leituras/form");
            mv.addObject("cameras", cameraRepository.findAll());
            mv.addObject("operacoes", operacaoRepository.findAll());
            mv.addObject("acao", "/leituras/atualizar/" + id);
            mv.addObject("erro", "Camera ou operacao invalida.");
            return mv;
        }

        Leitura leitura = dto.toEntity(camera, operacao);
        leitura.setIdLeitura(id);
        leituraRepository.save(leitura);

        return new ModelAndView("redirect:/leituras");
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        try {
            leituraRepository.deleteById(id);
        } catch (Exception e) {
            return new ModelAndView("redirect:/leituras?erro=vinculo");
        }
        return new ModelAndView("redirect:/leituras");
    }
}