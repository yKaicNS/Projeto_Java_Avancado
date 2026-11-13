package model;

public class Voluntario {
    private String nome;
    private String email;
    private String matricula;
    private int pontuacaoAcumulada;
    private int quantidadeAcoes;

    public Voluntario(String nome, String email, String matricula) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
    }

    public Voluntario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void incrementarQuantidadeAcoes(){
        this.quantidadeAcoes++;
    }
    public void adicionarPontuacao(int pontuacao){
        this.pontuacaoAcumulada+= pontuacao;
    }

    public int getPontuacaoAcumulada() {
        return pontuacaoAcumulada;
    }

    public int getQuantidadeAcoes() {
        return quantidadeAcoes;
    }
}
