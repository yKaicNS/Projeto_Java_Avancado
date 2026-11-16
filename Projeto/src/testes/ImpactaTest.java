package testes;

import exceptions.AcaoLotadaException;
import exceptions.EmailDuplicadoException;
import exceptions.InscricaoDuplicadaException;
import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import service.Impacta;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ImpactaTest {
    private Impacta sistema;
    private static final String DATA = "2026-12-09T08:00:00";

    public ImpactaTest() {
    }

    @BeforeEach
    public void antes() {
        this.sistema = new Impacta();
    }

    @Test
    @DisplayName("Deve criar um voluntário com os dados informados")
    public void testeCriarVoluntario() {
        Voluntario voluntario = new Voluntario("Ana", "ana@email.com", "123");
        Assertions.assertEquals("Ana", voluntario.getNome());
        Assertions.assertEquals("ana@email.com", voluntario.getEmail());
        Assertions.assertEquals("123", voluntario.getMatricula());
        Assertions.assertEquals(0, voluntario.getPontuacaoAcumulada());
        Assertions.assertEquals(0, voluntario.getQuantidadeAcoes());
    }

    @Test
    @DisplayName("Deve permitir criar um voluntário com construtor vazio")
    public void testeConstrutorVazioVoluntario() {
        Voluntario voluntario = new Voluntario();
        Assertions.assertNull(voluntario.getNome());
        Assertions.assertNull(voluntario.getEmail());
        Assertions.assertNull(voluntario.getMatricula());
        Assertions.assertEquals(0, voluntario.getPontuacaoAcumulada());
        Assertions.assertEquals(0, voluntario.getQuantidadeAcoes());
    }

    @Test
    @DisplayName("Deve atualizar nome e email do voluntário")
    public void testeAtualizarVoluntario() {
        Voluntario voluntario = new Voluntario("Ana", "ana@email.com", "123");
        voluntario.setNome("Ana Maria");
        voluntario.setEmail("novo@email.com");
        Assertions.assertEquals("Ana Maria", voluntario.getNome());
        Assertions.assertEquals("novo@email.com", voluntario.getEmail());
    }

    @Test
    @DisplayName("Deve acumular pontuação e quantidade de ações")
    public void testePontuacaoEQuantidadeAcoes() {
        Voluntario voluntario = new Voluntario("Ana", "ana@email.com", "123");
        voluntario.adicionarPontuacao(10);
        voluntario.adicionarPontuacao(5);
        voluntario.incrementarQuantidadeAcoes();
        voluntario.incrementarQuantidadeAcoes();
        Assertions.assertEquals(15, voluntario.getPontuacaoAcumulada());
        Assertions.assertEquals(2, voluntario.getQuantidadeAcoes());
    }

    @Test
    @DisplayName("Deve calcular a pontuação do plantio")
    public void testePontuacaoPlantio() {
        Plantio plantio = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 20);
        Assertions.assertEquals(20, plantio.getQtdMudas());
        Assertions.assertEquals(45, plantio.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve atualizar a quantidade de mudas")
    public void testeAtualizarQuantidadeMudas() {
        Plantio plantio = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 20);
        plantio.setQtdMudas(1);
        Assertions.assertEquals(1, plantio.getQtdMudas());
        Assertions.assertEquals(7, plantio.calcularPontuacao());
    }

    @Test
    @DisplayName("Plantio deve ser uma ação")
    public void testePlantioHerdaAcao() {
        Plantio plantio = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 5);
        Assertions.assertTrue(plantio instanceof Acao);
    }

    @Test
    @DisplayName("Deve calcular a pontuação do mutirão")
    public void testePontuacaoMutirao() {
        Mutirao mutirao = new Mutirao("Mutirão", "Limpeza", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 3);
        Assertions.assertEquals(3, mutirao.getDuracaoHoras());
        Assertions.assertEquals(12, mutirao.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve atualizar a duração do mutirão")
    public void testeAtualizarDuracaoMutirao() {
        Mutirao mutirao = new Mutirao("Mutirão", "Limpeza", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 3);
        mutirao.setDuracaoHoras(8);
        Assertions.assertEquals(8, mutirao.getDuracaoHoras());
        Assertions.assertEquals(32, mutirao.calcularPontuacao());
    }

    @Test
    @DisplayName("Mutirão deve ser uma ação")
    public void testeMutiraoHerdaAcao() {
        Mutirao mutirao = new Mutirao("Mutirão", "Limpeza", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 3);
        Assertions.assertTrue(mutirao instanceof Acao);
    }

    @Test
    @DisplayName("Deve calcular pontuação da oficina com kit")
    public void testePontuacaoOficinaComKit() {
        Oficina oficina = new Oficina("Oficina", "Reciclagem", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 2, true);
        Assertions.assertTrue(oficina.isKitMaterial());
        Assertions.assertEquals(16, oficina.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve calcular pontuação da oficina sem kit")
    public void testePontuacaoOficinaSemKit() {
        Oficina oficina = new Oficina("Oficina", "Reciclagem", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 2, false);
        Assertions.assertFalse(oficina.isKitMaterial());
        Assertions.assertEquals(6, oficina.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve atualizar duração e disponibilidade de kit da oficina")
    public void testeAtualizarOficina() {
        Oficina oficina = new Oficina("Oficina", "Reciclagem", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 2, false);
        oficina.setDuracaoHoras(5);
        oficina.setKitMaterial(true);
        Assertions.assertEquals(5, oficina.getDuracaoHoras());
        Assertions.assertTrue(oficina.isKitMaterial());
        Assertions.assertEquals(25, oficina.calcularPontuacao());
    }

    @Test
    @DisplayName("Oficina deve ser uma ação")
    public void testeOficinaHerdaAcao() {
        Oficina oficina = new Oficina("Oficina", "Reciclagem", LocalDateTime.parse("2026-12-09T08:00:00"), 10, 2, true);
        Assertions.assertTrue(oficina instanceof Acao);
    }

    @Test
    @DisplayName("Deve criar ação com identificadores sequenciais")
    public void testeIdAcao() {
        Acao primeira = new Plantio("Ação 1", "Descrição 1", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        Acao segunda = new Plantio("Ação 2", "Descrição 2", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        Assertions.assertEquals(primeira.getId() + 1, segunda.getId());
    }

    @Test
    @DisplayName("Deve atualizar os dados básicos da ação")
    public void testeAtualizarDadosAcao() {
        Acao acao = new Plantio("Título", "Descrição", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        LocalDateTime novaData = LocalDateTime.parse("2027-01-01T10:30:00");
        acao.setTitulo("Novo título");
        acao.setDescricao("Nova descrição");
        acao.setMaxParticipantes(9);
        acao.setData(novaData);
        Assertions.assertEquals("Novo título", acao.getTitulo());
        Assertions.assertEquals("Nova descrição", acao.getDescricao());
        Assertions.assertEquals(9, acao.getMaxParticipantes());
        Assertions.assertEquals(novaData, acao.getData());
    }

    @Test
    @DisplayName("Lista de inscritos deve começar vazia")
    public void testeListaInscritosVazia() {
        Acao acao = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        Assertions.assertTrue(acao.getInscritos().isEmpty());
    }

    @Test
    @DisplayName("Deve adicionar voluntários à ação")
    public void testeAdicionarInscritos() {
        Acao acao = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        Voluntario ana = new Voluntario("Ana", "ana@email.com", "1");
        Voluntario bia = new Voluntario("Bia", "bia@email.com", "2");
        acao.addInscritos(ana);
        acao.addInscritos(bia);
        Assertions.assertEquals(2, acao.getInscritos().size());
        Assertions.assertTrue(acao.getInscritos().contains(ana));
        Assertions.assertTrue(acao.getInscritos().contains(bia));
    }

    @Test
    @DisplayName("Deve exibir cabeçalho quando não há inscritos")
    public void testeExibirInscritosVazio() {
        Acao acao = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        String resultado = acao.exibirInscritos();
        Assertions.assertTrue(resultado.contains("Voluntários cadastrados nesta ação"));
        Assertions.assertFalse(resultado.contains("Dados do 1"));
    }

    @Test
    @DisplayName("Deve exibir os dados dos voluntários inscritos")
    public void testeExibirInscritos() {
        Acao acao = new Plantio("Plantio", "Mudas", LocalDateTime.parse("2026-12-09T08:00:00"), 5, 1);
        Voluntario voluntario = new Voluntario("Ana", "ana@email.com", "1");
        acao.addInscritos(voluntario);
        String resultado = acao.exibirInscritos();
        Assertions.assertTrue(resultado.contains("Dados do 1° voluntário"));
        Assertions.assertTrue(resultado.contains("ana@email.com"));
        Assertions.assertTrue(resultado.contains("Ana"));
    }

    @Test
    @DisplayName("Exceção de email duplicado deve guardar a mensagem")
    public void testeMensagemEmailDuplicadoException() {
        EmailDuplicadoException excecao = new EmailDuplicadoException("Email duplicado");
        Assertions.assertEquals("Email duplicado", excecao.getMessage());
        Assertions.assertTrue(excecao instanceof Exception);
    }

    @Test
    @DisplayName("Exceção de inscrição duplicada deve guardar a mensagem")
    public void testeMensagemInscricaoDuplicadaException() {
        InscricaoDuplicadaException excecao = new InscricaoDuplicadaException("Inscrição duplicada");
        Assertions.assertEquals("Inscrição duplicada", excecao.getMessage());
        Assertions.assertTrue(excecao instanceof Exception);
    }

    @Test
    @DisplayName("Exceção de ação lotada deve guardar a mensagem")
    public void testeMensagemAcaoLotadaException() {
        AcaoLotadaException excecao = new AcaoLotadaException("Ação lotada");
        Assertions.assertEquals("Ação lotada", excecao.getMessage());
        Assertions.assertTrue(excecao instanceof Exception);
    }

    @Test
    @DisplayName("Deve cadastrar um voluntário")
    public void testeCadastrarVoluntario() throws EmailDuplicadoException {
        Assertions.assertTrue(this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1"));
        Assertions.assertTrue(this.sistema.exibirVoluntario("ana@email.com").contains("Nome do voluntário: Ana"));
    }

    @Test
    @DisplayName("Não deve cadastrar email duplicado")
    public void testeEmailDuplicado() throws EmailDuplicadoException {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        EmailDuplicadoException excecao = (EmailDuplicadoException)Assertions.assertThrows(EmailDuplicadoException.class, () -> this.sistema.cadastrarVoluntario("Outra", "ana@email.com", "2"));
        Assertions.assertEquals("O email informado já está em uso, informe outro!", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve exibir mensagem quando voluntário não existe")
    public void testeExibirVoluntarioInexistente() {
        Assertions.assertEquals("Voluntário não encontrado!", this.sistema.exibirVoluntario("x@email.com"));
    }

    @Test
    @DisplayName("Busca de voluntário deve ignorar maiúsculas e minúsculas")
    public void testeExibirVoluntarioIgnoraCase() throws EmailDuplicadoException {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        Assertions.assertTrue(this.sistema.exibirVoluntario("ANA@EMAIL.COM").contains("Nome do voluntário: Ana"));
    }

    @Test
    @DisplayName("Lista de voluntários vazia deve retornar array vazio")
    public void testeListarVoluntariosVazio() {
        Assertions.assertEquals(0, this.sistema.listarVoluntarios().length);
    }

    @Test
    @DisplayName("Deve listar voluntários cadastrados")
    public void testeListarVoluntarios() throws EmailDuplicadoException {
        this.sistema.cadastrarVoluntario("Carlos", "carlos@email.com", "10");
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "11");
        this.sistema.cadastrarVoluntario("Bruno", "bruno@email.com", "12");
        String[] lista = this.sistema.listarVoluntarios();
        Assertions.assertEquals(3, lista.length);
        Assertions.assertTrue(lista[0].contains("Ana"));
        Assertions.assertTrue(lista[1].contains("Bruno"));
        Assertions.assertTrue(lista[2].contains("Carlos"));
    }

    @Test
    @DisplayName("Deve cadastrar plantio, mutirão e oficina")
    public void testeCadastrarAcoes() {
        int plantio = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 3);
        int mutirao = this.sistema.cadastrarMutirao("Mutirão", "Limpeza", "2026-12-09T08:00:00", 5, 3);
        int oficina = this.sistema.cadastrarOficina("Oficina", "Reciclagem", "2026-12-09T08:00:00", 5, 3, true);
        Assertions.assertTrue(plantio < mutirao);
        Assertions.assertTrue(mutirao < oficina);
        Assertions.assertFalse(this.sistema.exibirDetalhesAcao(plantio).equals("Ação não encontrada!"));
        Assertions.assertFalse(this.sistema.exibirDetalhesAcao(mutirao).equals("Ação não encontrada!"));
        Assertions.assertFalse(this.sistema.exibirDetalhesAcao(oficina).equals("Ação não encontrada!"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data do plantio for inválida")
    public void testeDataInvalidaPlantio() {
        Assertions.assertThrows(DateTimeParseException.class, () -> this.sistema.cadastrarPlantio("Plantio", "Mudas", "09/12/2026", 5, 3));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data do mutirão for inválida")
    public void testeDataInvalidaMutirao() {
        Assertions.assertThrows(DateTimeParseException.class, () -> this.sistema.cadastrarMutirao("Mutirão", "Limpeza", "data inválida", 5, 3));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data da oficina for inválida")
    public void testeDataInvalidaOficina() {
        Assertions.assertThrows(DateTimeParseException.class, () -> this.sistema.cadastrarOficina("Oficina", "Reciclagem", "", 5, 3, false));
    }

    @Test
    @DisplayName("Deve inscrever voluntário em uma ação")
    public void testeInscricaoValida() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        int id = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 10);
        Assertions.assertTrue(this.sistema.inscreverVoluntario("ana@email.com", id));
        Assertions.assertTrue(this.sistema.exibirVoluntario("ana@email.com").contains("Pontuação do voluntário: 25"));
        Assertions.assertTrue(this.sistema.exibirVoluntario("ana@email.com").contains("Participação em ações: 1"));
        Assertions.assertTrue(this.sistema.exibirDetalhesAcao(id).contains("ana@email.com"));
    }

    @Test
    @DisplayName("Deve retornar false para email inexistente")
    public void testeInscricaoEmailInexistente() throws Exception {
        int id = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 10);
        Assertions.assertFalse(this.sistema.inscreverVoluntario("naoexiste@email.com", id));
    }

    @Test
    @DisplayName("Deve retornar false para ação inexistente")
    public void testeInscricaoAcaoInexistente() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        Assertions.assertFalse(this.sistema.inscreverVoluntario("ana@email.com", 99999));
    }

    @Test
    @DisplayName("Não deve permitir inscrição duplicada")
    public void testeInscricaoDuplicada() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        int id = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 10);
        this.sistema.inscreverVoluntario("ana@email.com", id);
        InscricaoDuplicadaException excecao = (InscricaoDuplicadaException)Assertions.assertThrows(InscricaoDuplicadaException.class, () -> this.sistema.inscreverVoluntario("ana@email.com", id));
        Assertions.assertEquals("Voluntário já está inscrito nessa ação!", excecao.getMessage());
        Assertions.assertTrue(this.sistema.exibirVoluntario("ana@email.com").contains("Pontuação do voluntário: 25"));
    }

    @Test
    @DisplayName("Não deve permitir inscrição quando a ação estiver lotada")
    public void testeAcaoLotada() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        this.sistema.cadastrarVoluntario("Bia", "bia@email.com", "2");
        int id = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 1, 10);
        this.sistema.inscreverVoluntario("ana@email.com", id);
        AcaoLotadaException excecao = (AcaoLotadaException)Assertions.assertThrows(AcaoLotadaException.class, () -> this.sistema.inscreverVoluntario("bia@email.com", id));
        Assertions.assertEquals("As vagas dessa ação já foram preenchidas!", excecao.getMessage());
        Assertions.assertTrue(this.sistema.exibirVoluntario("bia@email.com").contains("Pontuação do voluntário: 0"));
        Assertions.assertTrue(this.sistema.exibirVoluntario("bia@email.com").contains("Participação em ações: 0"));
    }

    @Test
    @DisplayName("Deve acumular pontuação e participações em ações diferentes")
    public void testePontuacaoAcumulada() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        int plantio = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 10);
        int oficina = this.sistema.cadastrarOficina("Oficina", "Reciclagem", "2026-12-09T08:00:00", 5, 2, true);
        this.sistema.inscreverVoluntario("ana@email.com", plantio);
        this.sistema.inscreverVoluntario("ana@email.com", oficina);
        String dados = this.sistema.exibirVoluntario("ana@email.com");
        Assertions.assertTrue(dados.contains("Pontuação do voluntário: 41"));
        Assertions.assertTrue(dados.contains("Participação em ações: 2"));
    }

    @Test
    @DisplayName("Inscrição deve diferenciar maiúsculas e minúsculas no email")
    public void testeInscricaoEmailCaseSensitive() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        int id = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 10);
        Assertions.assertFalse(this.sistema.inscreverVoluntario("ANA@email.com", id));
    }

    @Test
    @DisplayName("Ação com zero vagas deve ser considerada lotada")
    public void testeAcaoSemVagas() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        int id = this.sistema.cadastrarMutirao("Mutirão", "Limpeza", "2026-12-09T08:00:00", 0, 1);
        Assertions.assertThrows(AcaoLotadaException.class, () -> this.sistema.inscreverVoluntario("ana@email.com", id));
    }

    @Test
    @DisplayName("Deve informar quando a ação não existe")
    public void testeDetalhesAcaoInexistente() {
        Assertions.assertEquals("Ação não encontrada!", this.sistema.exibirDetalhesAcao(99999));
    }

    @Test
    @DisplayName("Deve exibir detalhes de um plantio")
    public void testeDetalhesPlantio() {
        int id = this.sistema.cadastrarPlantio("Plantio X", "Descrição P", "2026-12-09T08:00:00", 7, 4);
        String detalhes = this.sistema.exibirDetalhesAcao(id);
        Assertions.assertTrue(detalhes.contains("Id da ação: " + id));
        Assertions.assertTrue(detalhes.contains("Título: Plantio X"));
        Assertions.assertTrue(detalhes.contains("Descrição: Descrição P"));
        Assertions.assertTrue(detalhes.contains("Quantidade de mudas: 4"));
        Assertions.assertTrue(detalhes.contains("Pontuação da ação: 13"));
    }

    @Test
    @DisplayName("Deve exibir detalhes de um mutirão")
    public void testeDetalhesMutirao() {
        int id = this.sistema.cadastrarMutirao("Mutirão X", "Descrição M", "2026-12-09T08:00:00", 8, 6);
        String detalhes = this.sistema.exibirDetalhesAcao(id);
        Assertions.assertTrue(detalhes.contains("Título: Mutirão X"));
        Assertions.assertTrue(detalhes.contains("Duração em horas: 6"));
        Assertions.assertTrue(detalhes.contains("Pontuação da ação: 24"));
        Assertions.assertFalse(detalhes.contains("Quantidade de mudas"));
        Assertions.assertFalse(detalhes.contains("Kit de material"));
    }

    @Test
    @DisplayName("Deve exibir detalhes de uma oficina")
    public void testeDetalhesOficina() {
        int id = this.sistema.cadastrarOficina("Oficina X", "Descrição O", "2026-12-09T08:00:00", 9, 3, true);
        String detalhes = this.sistema.exibirDetalhesAcao(id);
        Assertions.assertTrue(detalhes.contains("Título: Oficina X"));
        Assertions.assertTrue(detalhes.contains("Duração em horas: 3"));
        Assertions.assertTrue(detalhes.contains("Kit de material: true"));
        Assertions.assertTrue(detalhes.contains("Pontuação da ação: 19"));
    }

    @Test
    @DisplayName("Deve exibir voluntário inscrito nos detalhes da ação")
    public void testeDetalhesComInscrito() throws Exception {
        this.sistema.cadastrarVoluntario("Ana", "ana@email.com", "1");
        int id = this.sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-09T08:00:00", 5, 4);
        this.sistema.inscreverVoluntario("ana@email.com", id);
        String detalhes = this.sistema.exibirDetalhesAcao(id);
        Assertions.assertTrue(detalhes.contains("Dados do 1° voluntário"));
        Assertions.assertTrue(detalhes.contains("Nome do voluntário: Ana"));
        Assertions.assertTrue(detalhes.contains("ana@email.com"));
    }
}
