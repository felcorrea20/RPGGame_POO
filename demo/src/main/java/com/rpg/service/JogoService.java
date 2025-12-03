package com.rpg.service;

import com.rpg.model.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JogoService {
    private Map<String, EstadoJogo> jogosAtivos = new HashMap<>();

    public EstadoJogo iniciarJogo(String classeEscolhida) {
        Personagem jogador = criarPersonagem(classeEscolhida);
        Personagem adversario = criarAdversarioAleatorio();

        String idJogo = UUID.randomUUID().toString();
        EstadoJogo estado = new EstadoJogo(idJogo, jogador, adversario);

        estado.adicionarHistorico("=== JOGO INICIADO ===");
        estado.adicionarHistorico("Jogador escolheu: " + jogador.getNome());
        estado.adicionarHistorico("Adversário: " + adversario.getNome());
        estado.adicionarHistorico("");

        jogosAtivos.put(idJogo, estado);
        return estado;
    }

    public EstadoJogo executarTurno(String idJogo, String acaoJogador) {
        EstadoJogo estado = jogosAtivos.get(idJogo);

        if (estado == null) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        if (estado.isJogoFinalizado()) {
            throw new IllegalStateException("Jogo já finalizado");
        }

        if (acaoJogador.equalsIgnoreCase("curar") && estado.isUltimaAcaoFoiCura()) {
            throw new IllegalArgumentException("Não é possível curar duas vezes seguidas");
        }

        estado.adicionarHistorico("--- Turno " + ((estado.getHistorico().size() / 4) + 1) + " ---");

        executarAcaoJogador(estado, acaoJogador);

        // verificar se adversário morreu
        if (!estado.getAdversario().estaVivo()) {
            estado.setJogoFinalizado(true);
            estado.setVencedor("Jogador");
            estado.adicionarHistorico("");
            estado.adicionarHistorico("=== VITÓRIA! ===");
            estado.adicionarHistorico("O jogador venceu a batalha!");
            return estado;
        }

        executarAcaoAdversario(estado);

        // verificar se jogador morreu
        if (!estado.getJogador().estaVivo()) {
            estado.setJogoFinalizado(true);
            estado.setVencedor("Adversário");
            estado.adicionarHistorico("");
            estado.adicionarHistorico("=== DERROTA! ===");
            estado.adicionarHistorico("O adversário venceu a batalha!");
            return estado;
        }

        estado.adicionarHistorico("");
        return estado;
    }

    private void executarAcaoJogador(EstadoJogo estado, String acao) {
        Personagem jogador = estado.getJogador();
        Personagem adversario = estado.getAdversario();

        if (acao.equalsIgnoreCase("atacar")) {
            double dano = jogador.atacar(adversario);
            if (dano > 0) {
                estado.adicionarHistorico(String.format("Jogador atacou e causou %.2f de dano!", dano));
            } else {
                estado.adicionarHistorico("Jogador atacou mas errou!");
            }
            estado.setUltimaAcaoFoiCura(false);
        } else if (acao.equalsIgnoreCase("curar")) {
            double cura = jogador.curar();
            if (cura > 0) {
                estado.adicionarHistorico(String.format("Jogador se curou em %.2f pontos de vida!", cura));
            } else {
                estado.adicionarHistorico("Jogador tentou se curar mas falhou!");
            }
            estado.setUltimaAcaoFoiCura(true);
        }
    }

    private void executarAcaoAdversario(EstadoJogo estado) {
        Personagem adversario = estado.getAdversario();
        Personagem jogador    = estado.getJogador();

        boolean deveAtacar = adversario.getVida() > adversario.getVidaMaxima() * 0.5 || Math.random() < 0.6;

        if (deveAtacar) {
            double dano = adversario.atacar(jogador);
            if (dano > 0) {
                estado.adicionarHistorico(String.format("Adversário atacou e causou %.2f de dano!", dano));
            } else {
                estado.adicionarHistorico("Adversário atacou mas errou!");
            }
        } else {
            double cura = adversario.curar();
            if (cura > 0) {
                estado.adicionarHistorico(String.format("Adversário se curou em %.2f pontos de vida!", cura));
            } else {
                estado.adicionarHistorico("Adversário tentou se curar mas falhou!");
            }
        }
    }

    private Personagem criarPersonagem(String classe) {
        return switch (classe.toLowerCase()) {
            case "guerreiro" -> new Guerreiro();
            case "mago" -> new Mago();
            case "arqueiro" -> new Arqueiro();
            default -> throw new IllegalArgumentException("Classe inválida: " + classe);
        };
    }

    private Personagem criarAdversarioAleatorio() {
        String[] classes = {"guerreiro", "mago", "arqueiro"};
        int random = (int) (Math.random() * classes.length);
        return criarPersonagem(classes[random]);
    }

    public EstadoJogo obterEstadoJogo(String idJogo) {
        return jogosAtivos.get(idJogo);
    }
}