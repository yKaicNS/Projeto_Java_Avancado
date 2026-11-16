package service;

import exceptions.AcaoLotadaException;
import exceptions.EmailDuplicadoException;
import exceptions.InscricaoDuplicadaException;
import model.*;

import java.time.LocalDateTime;
import java.util.*;

public class Impacta {
    private Map<String, Voluntario> voluntarios= new HashMap<>();
    private Map <Integer, Acao> acoes= new HashMap<>();


    public boolean cadastrarVoluntario(String nome, String email, String matricula) throws EmailDuplicadoException {

        for(var entry : voluntarios.entrySet()){
            if(entry.getKey().equals(email)){
               throw new EmailDuplicadoException("O email informado já está em uso, informe outro!");
            }
        }

        Voluntario voluntario = new Voluntario(nome, email, matricula);
        voluntarios.put(email, voluntario);
        return true;

    }

    public String exibirVoluntario(String email){
        for(var entry : voluntarios.entrySet()){
            if(entry.getKey().equalsIgnoreCase(email)){
                return "Nome do voluntário: " + entry.getValue().getNome() + ", Email: " + entry.getValue().getEmail() + ", Matrícula: " + entry.getValue().getMatricula() + ", Pontuação do voluntário: " + entry.getValue().getPontuacaoAcumulada() + ", Participação em ações: " + entry.getValue().getQuantidadeAcoes();
            }
        }

        return "Voluntário não encontrado!";
    }

    public String[] listarVoluntarios() {
        if (voluntarios.isEmpty()) {
            return new String[0];
        }

        List<Voluntario> ordenados = new ArrayList<>(voluntarios.values());

        ordenados.sort(
                Comparator.comparingInt(Voluntario::getPontuacaoAcumulada)
                        .reversed()
                        .thenComparing(Voluntario::getNome)
        );

        String[] lista = new String[ordenados.size()];
        int i = 0;

        for (Voluntario voluntario : ordenados) {
            lista[i] = "Nome do voluntário: " + voluntario.getNome() + ", Email: " + voluntario.getEmail() + ", Matrícula: " + voluntario.getMatricula() + ", Pontuação do voluntário: " + voluntario.getPontuacaoAcumulada() + ", Participação em ações: " + voluntario.getQuantidadeAcoes();

            i++;
        }

        return lista;
    }

    public int cadastrarPlantio(String titulo, String descricao, String data, int maxParticipantes, int qtdMudas){
        Plantio plantio= new Plantio(titulo, descricao, LocalDateTime.parse(data), maxParticipantes, qtdMudas);
        acoes.put(plantio.getId(), plantio);
        return plantio.getId();
    }
    public int cadastrarMutirao(String titulo, String descricao, String data, int maxParticipantes, int duracaoHoras){
        Mutirao mutirao= new Mutirao(titulo,descricao, LocalDateTime.parse(data), maxParticipantes, duracaoHoras);
        acoes.put(mutirao.getId(), mutirao);
        return mutirao.getId();
    }
    public int cadastrarOficina(String titulo, String descricao, String data, int maxParticipantes, int duracaoHoras, boolean kitMaterial) {
        Oficina oficina= new Oficina(titulo,descricao, LocalDateTime.parse(data), maxParticipantes,duracaoHoras, kitMaterial);
        acoes.put(oficina.getId(), oficina);
        return oficina.getId();
    }

    public boolean inscreverVoluntario(String email, int idAcao) throws InscricaoDuplicadaException, AcaoLotadaException {
        if(!voluntarios.containsKey(email) || !acoes.containsKey(idAcao)){
            return false;
        }

        for(var entry : acoes.get(idAcao).getInscritos()){
            if(entry.getEmail().equals(email)){
                throw new InscricaoDuplicadaException("Voluntário já está inscrito nessa ação!");
            }
        }

        int maxParticipantes= acoes.get(idAcao).getMaxParticipantes();
        int inscritos= acoes.get(idAcao).getInscritos().size();

        if(inscritos>=maxParticipantes){
            throw new AcaoLotadaException("As vagas dessa ação já foram preenchidas!");
        }

        Voluntario voluntario= voluntarios.get(email);
        voluntario.adicionarPontuacao(acoes.get(idAcao).calcularPontuacao());
        voluntario.incrementarQuantidadeAcoes();
        acoes.get(idAcao).addInscritos(voluntario);


        return true;
    }

    public String exibirDetalhesAcao(int idAcao) {
        if (!acoes.containsKey(idAcao)) {
            return "Ação não encontrada!";
        }

        Acao acao = acoes.get(idAcao);

        String detalhes = "Id da ação: " + acao.getId() + "\nTítulo: " + acao.getTitulo() + "\nDescrição: " + acao.getDescricao() + "\nData: " + acao.getData() + "\nCapacidade máxima de voluntários: " + acao.getMaxParticipantes() + "\nPontuação da ação: " + acao.calcularPontuacao();

        if (acao instanceof Plantio plantio) {
            detalhes += "\nQuantidade de mudas: " + plantio.getQtdMudas();

        } else if (acao instanceof Mutirao mutirao) {
            detalhes += "\nDuração em horas: " + mutirao.getDuracaoHoras();

        } else if (acao instanceof Oficina oficina) {
            detalhes += "\nDuração em horas: " + oficina.getDuracaoHoras()
                    + "\nKit de material: " + oficina.isKitMaterial();
        }

        detalhes += "\n" + acao.exibirInscritos();

        return detalhes;
    }




}
