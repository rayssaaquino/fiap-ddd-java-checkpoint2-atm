package br.com.fiapbank.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta extends BaseEntity {

    protected Cliente cliente;
    protected Dinheiro saldo;
    protected Double taxa;
    protected StatusConta status;
    protected LocalDate dataAbertura;
    protected ContaAcesso contaAcesso;
    protected List<Movimentacao> movimentacoes;

    protected Conta(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo, Double taxaMensal) {
        super();
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente é obrigatório para abertura de conta.");
        }
        if (contaAcesso == null) {
            throw new IllegalArgumentException("Credenciais de acesso são obrigatórias.");
        }
        this.cliente = cliente;
        this.contaAcesso = contaAcesso;
        this.saldo = saldo != null ? saldo : Dinheiro.zero();
        this.taxa = taxaMensal != null ? taxaMensal : 0.0;
        this.status = StatusConta.ATIVA;
        this.dataAbertura = LocalDate.now();
        this.movimentacoes = new ArrayList<>();
    }

    // Template Method — define o algoritmo do saque
    public void realizarSaque(Dinheiro valor) {
        validarOperacao(valor);
        if (saldo.menorQue(valor)) {
            throw new IllegalStateException("Saldo insuficiente para realizar o saque.");
        }
        sacar(valor);
        registrarMovimentacao(TipoMovimentacao.SAQUE, valor);
        aplicarRegraDeTaxa();
    }

    public void realizarDeposito(Dinheiro valor) {
        validarOperacao(valor);
        depositar(valor);
        registrarMovimentacao(TipoMovimentacao.DEPOSITO, valor);
    }

    // Método abstrato — cada subclasse define sua taxa específica
    protected abstract void aplicarRegraDeTaxa();

    private void validarOperacao(Dinheiro valor) {
        if (valor == null || !valor.isPositivo()) {
            throw new IllegalArgumentException("Valor da operação deve ser positivo.");
        }
        if (status != StatusConta.ATIVA) {
            throw new IllegalStateException("Conta não está ativa para operações.");
        }
    }

    private void depositar(Dinheiro valor) {
        this.saldo = this.saldo.somar(valor);
    }

    private void sacar(Dinheiro valor) {
        this.saldo = this.saldo.subtrair(valor);
    }

    protected void registrarMovimentacao(TipoMovimentacao tipo, Dinheiro valor) {
        movimentacoes.add(Movimentacao.criar(valor, tipo));
    }

    public Dinheiro getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public StatusConta getStatus() {
        return status;
    }

    public ContaAcesso getContaAcesso() {
        return contaAcesso;
    }

    public List<Movimentacao> getMovimentacoes() {
        return new ArrayList<>(movimentacoes);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}