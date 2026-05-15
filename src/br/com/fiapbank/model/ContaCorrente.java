package br.com.fiapbank.model;

public class ContaCorrente extends Conta {

    public static final Double TAXA_MANUTENCAO = -25.00;

    public ContaCorrente(Cliente cliente, Dinheiro saldo) {
        super(cliente, new ContaAcesso("1234"), saldo, TAXA_MANUTENCAO);
    }

    public ContaCorrente(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        super(cliente, contaAcesso, saldo, TAXA_MANUTENCAO);
    }

    @Override
    protected void aplicarRegraDeTaxa() {
        Dinheiro taxaSaque = new Dinheiro(String.valueOf(Math.abs(TAXA_MANUTENCAO)));
        if (saldo.maiorOuIgualQue(taxaSaque)) {
            saldo = saldo.subtrair(taxaSaque);
            registrarMovimentacao(TipoMovimentacao.TAXA, taxaSaque);
        }
    }

    public void aplicarTaxaMensal() {
        Dinheiro taxaMensal = new Dinheiro(String.valueOf(Math.abs(TAXA_MANUTENCAO)));
        if (saldo.maiorOuIgualQue(taxaMensal)) {
            saldo = saldo.subtrair(taxaMensal);
            registrarMovimentacao(TipoMovimentacao.TAXA, taxaMensal);
        }
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