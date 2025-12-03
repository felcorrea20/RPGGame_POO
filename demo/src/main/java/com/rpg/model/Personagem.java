package com.rpg.model;

public abstract class Personagem {
    protected String nome;
    protected double vida;
    protected double vidaMaxima;
    protected double ataque;
    protected double defesa;
    protected double cura;
    protected double taxaSucesso;

    public Personagem(String nome, double vida, double ataque, double defesa, double cura, double taxaSucesso) {
        this.nome = nome;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.cura = cura;
        this.taxaSucesso = taxaSucesso;
    }

    public double atacar(Personagem alvo) {
        double random = Math.random() * 100;
        if (random <= this.taxaSucesso) {
            double dano = this.ataque - alvo.getDefesa();
            if (dano > 0) {
                alvo.receberDano(dano);
                return dano;
            }
        }
        return 0;
    }

    public double curar() {
        double random = Math.random() * 100;
        if (random <= this.taxaSucesso) {
            double curaReal = Math.min(this.cura, this.vidaMaxima - this.vida);
            this.vida += curaReal;
            return curaReal;
        }
        return 0;
    }

    public void receberDano(double dano) {
        this.vida -= dano;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public String getNome() { return nome; }
    public double getVida() { return vida; }
    public double getVidaMaxima() { return vidaMaxima; }
    public double getAtaque() { return ataque; }
    public double getDefesa() { return defesa; }
    public double getCura() { return cura; }
    public double getTaxaSucesso() { return taxaSucesso; }
}