package br.com.fiapbank.model;

public class ContaPoupanca extends Conta {

    public static final Double RENDIMENTO_MENSAL = 1.0;

    public ContaPoupanca(Cliente cliente, Dinheiro saldo) {
        super(cliente, new ContaAcesso("1234"), saldo, RENDIMENTO_MENSAL);
    }

    public ContaPoupanca(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        super(cliente, contaAcesso, saldo, RENDIMENTO_MENSAL);
    }

    @Override
    protected void aplicarRegraDeTaxa() {
        // Poupança não cobra taxa no saque — sem encargo adicional
    }

    public void aplicarTaxaMensal() {
        Dinheiro rendimento = calcularRendimento();
        saldo = saldo.somar(rendimento);
        registrarMovimentacao(TipoMovimentacao.RENDIMENTO, rendimento);
    }

    private Dinheiro calcularRendimento() {
        java.math.BigDecimal fator = java.math.BigDecimal.valueOf(RENDIMENTO_MENSAL / 100.0);
        java.math.BigDecimal valorRendimento = saldo.getValor().multiply(fator);
        return new Dinheiro(valorRendimento);
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