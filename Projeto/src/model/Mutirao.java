package model;

import java.time.LocalDateTime;

public class Mutirao extends Acao{
    private int duracaoHoras;

    public Mutirao(String titulo, String descricao, LocalDateTime data, int maxParticipantes, int duracaoHoras) {
        super(titulo, descricao, data, maxParticipantes);
        this.duracaoHoras = duracaoHoras;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(int duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    @Override
    public int calcularPontuacao() {
        return 4 * duracaoHoras;

    }
}
