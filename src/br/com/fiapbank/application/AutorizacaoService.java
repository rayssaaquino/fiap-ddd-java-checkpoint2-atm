package br.com.fiapbank.application;

import br.com.fiapbank.model.Conta;

public class AutorizacaoService {

    private Conta conta;

    public AutorizacaoService(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        this.conta = conta;
    }

    public Boolean autorizar(String senha) {
        return conta.getContaAcesso().validarSenha(senha);
    }

    public Boolean contaBloqueada() {
        return conta.getContaAcesso().isBloqueado();
    }
}