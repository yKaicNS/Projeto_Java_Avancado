package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Acao {
    private static int contadorId = 0;
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private int maxParticipantes;
    private List <Voluntario> inscritos= new ArrayList<>();

    public Acao(String titulo, String descricao, LocalDateTime data, int maxParticipantes) {
        this.id = ++contadorId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.maxParticipantes = maxParticipantes;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public void setMaxParticipantes(int maxParticipantes) {
        this.maxParticipantes = maxParticipantes;
    }

    public List<Voluntario> getInscritos() {
        return inscritos;
    }

    public void addInscritos(Voluntario voluntario){
        this.inscritos.add(voluntario);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public abstract int calcularPontuacao();


    public String exibirInscritos(){
        String dados= "------------------------------------\nVoluntários cadastrados nesta ação:\n";
        int i= 1;
        for(Voluntario voluntario : inscritos){
           dados+= "Dados do "+ i++ + "° voluntário:\nEmail do voluntário: " + voluntario.getEmail() + "\nNome do voluntário: " + voluntario.getNome() + "\nPontuação do voluntário: " + voluntario.getPontuacaoAcumulada() + "\nQuantidade de participação em ações: "+ voluntario.getQuantidadeAcoes() + "\n---------------------------\n";
        }

        return dados;
    }


}
