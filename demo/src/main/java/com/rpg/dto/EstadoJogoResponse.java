package com.rpg.dto;

import com.rpg.model.EstadoJogo;
import com.rpg.model.Personagem;
import java.util.List;

public class EstadoJogoResponse {
    private String idJogo;
    private PersonagemDTO jogador;
    private PersonagemDTO adversario;
    private List<String> historico;
    private boolean jogoFinalizado;
    private String vencedor;

    public EstadoJogoResponse(EstadoJogo estado) {
        this.idJogo = estado.getIdJogo();
        this.jogador = new PersonagemDTO(estado.getJogador());
        this.adversario = new PersonagemDTO(estado.getAdversario());
        this.historico = estado.getHistorico();
        this.jogoFinalizado = estado.isJogoFinalizado();
        this.vencedor = estado.getVencedor();
    }

    public String getIdJogo() { return idJogo; }
    public PersonagemDTO getJogador() { return jogador; }
    public PersonagemDTO getAdversario() { return adversario; }
    public List<String> getHistorico() { return historico; }
    public boolean isJogoFinalizado() { return jogoFinalizado; }
    public String getVencedor() { return vencedor; }

    public static class PersonagemDTO {
        private String nome;
        private double vida;
        private double vidaMaxima;
        private double ataque;
        private double defesa;
        private double cura;
        private double taxaSucesso;

        public PersonagemDTO(Personagem p) {
            this.nome = p.getNome();
            this.vida = Math.round(p.getVida() * 100.0) / 100.0;
            this.vidaMaxima = p.getVidaMaxima();
            this.ataque = p.getAtaque();
            this.defesa = p.getDefesa();
            this.cura = p.getCura();
            this.taxaSucesso = p.getTaxaSucesso();
        }

        public String getNome() { return nome; }
        public double getVida() { return vida; }
        public double getVidaMaxima() { return vidaMaxima; }
        public double getAtaque() { return ataque; }
        public double getDefesa() { return defesa; }
        public double getCura() { return cura; }
        public double getTaxaSucesso() { return taxaSucesso; }
    }
}