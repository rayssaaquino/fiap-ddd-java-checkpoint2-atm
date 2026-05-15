package br.com.fiapbank.application;

import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.ContaCorrente;
import br.com.fiapbank.model.ContaPoupanca;
import br.com.fiapbank.model.Dinheiro;

public class ContaFactory {

    private static ContaFactory instance;

    private ContaFactory() {
    }

    public static ContaFactory getInstance() {
        if (instance == null) {
            instance = new ContaFactory();
        }
        return instance;
    }

    public Conta criarContaCorrente(Cliente cliente, Dinheiro saldo) {
        ContaAcesso acesso = new ContaAcesso("1234");
        return new ContaCorrente(cliente, acesso, saldo);
    }

    public Conta criarContaCorrente(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        return new ContaCorrente(cliente, contaAcesso, saldo);
    }

    public Conta criarContaPoupanca(Cliente cliente, Dinheiro saldo) {
        ContaAcesso acesso = new ContaAcesso("1234");
        return new ContaPoupanca(cliente, acesso, saldo);
    }

    public Conta criarContaPoupanca(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        return new ContaPoupanca(cliente, contaAcesso, saldo);
    }
}