package model;

import java.time.LocalDateTime;

public class Oficina extends Acao{

    private int duracaoHoras;
    private boolean kitMaterial;

    public Oficina(String titulo, String descricao, LocalDateTime data, int maxParticipantes, int duracaoHoras, boolean kitMaterial) {
        super(titulo, descricao, data, maxParticipantes);
        this.duracaoHoras = duracaoHoras;
        this.kitMaterial = kitMaterial;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(int duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public boolean isKitMaterial() {
        return kitMaterial;
    }

    public void setKitMaterial(boolean kitMaterial) {
        this.kitMaterial = kitMaterial;
    }

    @Override
    public int calcularPontuacao() {
        if(kitMaterial == true){
            return (3* duracaoHoras ) + 10;
        }
        return 3 * duracaoHoras;
    }
}
