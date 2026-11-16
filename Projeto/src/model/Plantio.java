package model;

import java.time.LocalDateTime;

public class Plantio extends Acao{
    private int qtdMudas;

    public Plantio(String titulo, String descricao, LocalDateTime data, int maxParticipantes, int qtdMudas) {
        super(titulo, descricao, data, maxParticipantes);
        this.qtdMudas = qtdMudas;
    }

    public int getQtdMudas() {
        return qtdMudas;
    }

    public void setQtdMudas(int qtdMudas) {
        this.qtdMudas = qtdMudas;
    }

    @Override
    public int calcularPontuacao() {
        return (2*qtdMudas) + 5;
    }
}
