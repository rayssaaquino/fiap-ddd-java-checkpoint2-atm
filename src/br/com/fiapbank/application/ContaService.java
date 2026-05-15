package br.com.fiapbank.application;

import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.Dinheiro;
import br.com.fiapbank.model.Movimentacao;

import java.util.List;

public class ContaService {

    private Conta conta;

    public ContaService(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        this.conta = conta;
    }

    public void realizarDeposito(Dinheiro valor) {
        conta.realizarDeposito(valor);
    }

    public void realizarSaque(Dinheiro valor) {
        conta.realizarSaque(valor);
    }

    public Dinheiro obterSaldo() {
        return conta.getSaldo();
    }

    public List<Movimentacao> obterMovimentacoes() {
        return conta.getMovimentacoes();
    }
}