package br.com.sistemaingressos.controller;

import br.com.sistemaingressos.model.Transacao;
import br.com.sistemaingressos.model.Ingresso;
import br.com.sistemaingressos.model.Usuario;
import br.com.sistemaingressos.service.TransacaoService;
import br.com.sistemaingressos.repository.IngressoRepository;
import br.com.sistemaingressos.repository.UsuarioRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // LISTAR transações
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("transacoes", transacaoService.listarTodas());
        return "transacao/listar";
    }

    // FORMULÁRIO nova transação
    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("transacao", new Transacao());
        model.addAttribute("ingressos", ingressoRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "transacao/formulario";
    }

    // SALVAR transação
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Transacao transacao) {
        transacaoService.salvar(transacao);
        return "redirect:/transacoes";
    }

    // EDITAR transação
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Transacao transacao = transacaoService.buscarPorId(id);
        model.addAttribute("transacao", transacao);
        model.addAttribute("ingressos", ingressoRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "transacao/formulario";
    }

    // EXCLUIR transação
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        transacaoService.excluir(id);
        return "redirect:/transacoes";
    }
}
