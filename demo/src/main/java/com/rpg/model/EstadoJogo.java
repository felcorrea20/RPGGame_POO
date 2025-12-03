package com.rpg.model;

import java.util.ArrayList;
import java.util.List;

public class EstadoJogo {
    private String idJogo;
    private Personagem jogador;
    private Personagem adversario;
    private List<String> historico;
    private boolean jogoFinalizado;
    private String vencedor;
    private boolean ultimaAcaoFoiCura;

    public EstadoJogo(String idJogo, Personagem jogador, Personagem adversario) {
        this.idJogo = idJogo;
        this.jogador = jogador;
        this.adversario = adversario;
        this.historico = new ArrayList<>();
        this.jogoFinalizado = false;
        this.vencedor = null;
        this.ultimaAcaoFoiCura = false;
    }

    public String getIdJogo() { return idJogo; }
    public Personagem getJogador() { return jogador; }
    public Personagem getAdversario() { return adversario; }
    public List<String> getHistorico() { return historico; }
    public boolean isJogoFinalizado() { return jogoFinalizado; }
    public String getVencedor() { return vencedor; }
    public boolean isUltimaAcaoFoiCura() { return ultimaAcaoFoiCura; }

    public void setJogoFinalizado(boolean jogoFinalizado) { this.jogoFinalizado = jogoFinalizado; }
    public void setVencedor(String vencedor) { this.vencedor = vencedor; }
    public void setUltimaAcaoFoiCura(boolean ultimaAcaoFoiCura) { this.ultimaAcaoFoiCura = ultimaAcaoFoiCura; }

    public void adicionarHistorico(String mensagem) {
        this.historico.add(mensagem);
    }
}