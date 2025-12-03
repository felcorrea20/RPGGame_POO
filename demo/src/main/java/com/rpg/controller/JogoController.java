package com.rpg.controller;

import com.rpg.dto.AcaoRequest;
import com.rpg.dto.EstadoJogoResponse;
import com.rpg.dto.IniciarJogoRequest;
import com.rpg.model.EstadoJogo;
import com.rpg.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jogo")
@CrossOrigin(origins = "*")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @PostMapping("/iniciar")
    public ResponseEntity<EstadoJogoResponse> iniciarJogo(@RequestBody IniciarJogoRequest request) {
        try {
            EstadoJogo estado = jogoService.iniciarJogo(request.getClasseEscolhida());
            return ResponseEntity.ok(new EstadoJogoResponse(estado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/acao")
    public ResponseEntity<EstadoJogoResponse> executarAcao(@RequestBody AcaoRequest request) {
        try {
            EstadoJogo estado = jogoService.executarTurno(request.getIdJogo(), request.getAcao());
            return ResponseEntity.ok(new EstadoJogoResponse(estado));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/estado/{idJogo}")
    public ResponseEntity<EstadoJogoResponse> obterEstado(@PathVariable String idJogo) {
        EstadoJogo estado = jogoService.obterEstadoJogo(idJogo);
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new EstadoJogoResponse(estado));
    }

    @GetMapping("/classes")
    public ResponseEntity<String[]> listarClasses() {
        return ResponseEntity.ok(new String[]{"Guerreiro", "Mago", "Arqueiro"});
    }
}