package br.com.fiapbank.presentation;

import br.com.fiapbank.application.AutorizacaoService;
import br.com.fiapbank.application.ContaFactory;
import br.com.fiapbank.application.ContaService;
import br.com.fiapbank.infrastructure.ContaRepository;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.Dinheiro;
import br.com.fiapbank.model.Movimentacao;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TerminalBancarioController {

    private ContaService contaService;
    private AutorizacaoService autorizacaoService;
    private ContaFactory factory;
    private ContaRepository repositorio;
    private Scanner scanner;

    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String REGEX_SENHA = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$";

    public TerminalBancarioController(ContaFactory factory, ContaRepository repositorio) {
        this.factory = factory;
        this.repositorio = repositorio;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        exibirBoasVindas();

        // Passo 1: cadastrar nome
        String nomeCompleto = cadastrarNome();
        String primeiroNome = nomeCompleto.split(" ")[0];
        System.out.println("Olá, " + primeiroNome + "! Vamos cadastrar sua senha de acesso.");

        // Passo 2: cadastrar senha forte
        String senha = cadastrarSenha();

        // Passo 3: criar conta via Factory
        Cliente cliente = new Cliente(nomeCompleto);
        ContaAcesso acesso = new ContaAcesso(senha);
        Conta conta = factory.criarContaCorrente(cliente, acesso, Dinheiro.zero());
        repositorio.salvar(conta);

        // Passo 4: autenticar
        this.contaService = new ContaService(conta);
        this.autorizacaoService = new AutorizacaoService(conta);

        Boolean autenticado = realizarAutenticacao(primeiroNome);
        if (!autenticado) {
            System.out.println("\n  ACESSO BLOQUEADO. Entre em contato com o suporte do FIAP Bank.");
            return;
        }

        System.out.println("  Acesso autorizado. Bem-vindo(a), " + primeiroNome + "!");

        // Passo 5: menu principal
        exibirMenuPrincipal();
    }

    private void exibirBoasVindas() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        FIAP BANK - TERMINAL ATM          ║");
        System.out.println("║          Bem-vindo ao FIAP Bank          ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("\n  Por favor, identifique-se para continuar.");
    }

    private String cadastrarNome() {
        String nome = "";
        while (nome.isEmpty()) {
            System.out.print("  Nome completo: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("  O nome não pode ser vazio. Tente novamente.");
            }
        }
        return nome;
    }

    private String cadastrarSenha() {
        System.out.println("\n  Sua senha deve atender aos seguintes requisitos:");
        System.out.println("  - Mínimo de 8 caracteres");
        System.out.println("  - Pelo menos uma letra maiúscula");
        System.out.println("  - Pelo menos um número");
        System.out.println("  - Pelo menos um caractere especial: !@#$%^&*()-_+=?><");

        while (true) {
            System.out.print("\n  Defina uma senha segura: ");
            String senha = scanner.nextLine();
            if (senha.matches(REGEX_SENHA)) {
                System.out.println("  Senha registrada com sucesso!");
                return senha;
            } else {
                System.out.println("  Senha não atende aos requisitos. Tente novamente.");
            }
        }
    }

    private Boolean realizarAutenticacao(String primeiroNome) {
        System.out.println("\n  Por favor, insira sua senha para acessar sua conta.");
        while (!autorizacaoService.contaBloqueada()) {
            System.out.print("  Senha: ");
            String senhaDigitada = scanner.nextLine().trim();
            Boolean autenticado = autorizacaoService.autorizar(senhaDigitada);
            if (autenticado) {
                return Boolean.TRUE;
            } else {
                if (autorizacaoService.contaBloqueada()) {
                    break;
                }
                System.out.println("  Senha incorreta. Tente novamente.");
            }
        }
        return Boolean.FALSE;
    }

    public void exibirMenuPrincipal() {
        Integer opcao = 0;
        do {
            exibirOpcoes();
            opcao = lerOpcaoInteira("  Escolha uma opção: ");
            processarOpcao(opcao);
        } while (!opcao.equals(5));
    }

    private void exibirOpcoes() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║             MENU PRINCIPAL               ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  [ 1 ] Consultar Saldo                   ║");
        System.out.println("║  [ 2 ] Fazer Depósito                    ║");
        System.out.println("║  [ 3 ] Fazer Saque                       ║");
        System.out.println("║  [ 4 ] Histórico de Movimentações        ║");
        System.out.println("║  [ 5 ] Sair                              ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private void processarOpcao(Integer opcao) {
        switch (opcao) {
            case 1:
                exibirSaldo();
                break;
            case 2:
                realizarDeposito();
                break;
            case 3:
                realizarSaque();
                break;
            case 4:
                exibirMovimentacoes();
                break;
            case 5:
                System.out.println("\n  O FIAP Bank agradece a sua preferência. Até logo!\n");
                break;
            default:
                System.out.println("\n  Opção inválida. Escolha entre 1 e 5.");
        }
    }

    public void exibirSaldo() {
        Dinheiro saldo = contaService.obterSaldo();
        System.out.println("\n  ─────────────────────────────────────────");
        System.out.println("  SALDO DISPONÍVEL: " + saldo);
        System.out.println("  ─────────────────────────────────────────");
    }

    public void realizarDeposito() {
        System.out.println("\n  ─────────────────────────────────────────");
        System.out.println("  DEPÓSITO");
        BigDecimal valor = lerValorMonetario("  Informe o valor do depósito: R$ ");
        if (valor == null) return;
        try {
            contaService.realizarDeposito(new Dinheiro(valor));
            System.out.println("  Depósito de R$ " + valor + " realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("  Erro: " + e.getMessage());
        }
        System.out.println("  ─────────────────────────────────────────");
    }

    public void realizarSaque() {
        System.out.println("\n  ─────────────────────────────────────────");
        System.out.println("  SAQUE");
        BigDecimal valor = lerValorMonetario("  Informe o valor do saque: R$ ");
        if (valor == null) return;
        try {
            contaService.realizarSaque(new Dinheiro(valor));
            System.out.println("  Saque de R$ " + valor + " realizado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  Erro: " + e.getMessage());
        }
        System.out.println("  ─────────────────────────────────────────");
    }

    public void exibirMovimentacoes() {
        List<Movimentacao> movimentacoes = contaService.obterMovimentacoes();
        System.out.println("\n  ─────────────────────────────────────────");
        System.out.println("  HISTÓRICO DE MOVIMENTAÇÕES");
        System.out.println("  ─────────────────────────────────────────");
        if (movimentacoes.isEmpty()) {
            System.out.println("  Nenhuma movimentação registrada.");
        } else {
            for (Movimentacao m : movimentacoes) {
                String dataHora = m.getDataHora().format(FORMATADOR);
                String tipo = String.format("%-12s", m.getTipo().name());
                String valor = m.getValor().toString();
                System.out.println("  " + dataHora + " | " + tipo + " | " + valor);
            }
        }
        System.out.println("  ─────────────────────────────────────────");
    }

    private Integer lerOpcaoInteira(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("  Entrada inválida. Informe um número.");
            }
        }
    }

    private BigDecimal lerValorMonetario(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine().trim().replace(",", ".");
        try {
            BigDecimal valor = new BigDecimal(entrada);
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("  O valor deve ser positivo.");
                return null;
            }
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("  Valor inválido. Operação cancelada.");
            return null;
        }
    }
}