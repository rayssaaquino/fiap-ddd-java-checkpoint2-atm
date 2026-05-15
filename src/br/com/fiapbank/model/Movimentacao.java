package br.com.fiapbank.model;

import java.time.LocalDateTime;

public class Movimentacao {

    private LocalDateTime dataHora;
    private TipoMovimentacao tipo;
    private Dinheiro valor;

    public Movimentacao(LocalDateTime dataHora, Dinheiro valor, TipoMovimentacao tipo) {
        this.dataHora = dataHora;
        this.valor = valor;
        this.tipo = tipo;
    }

    public static Movimentacao criar(Dinheiro valor, TipoMovimentacao tipo) {
        return new Movimentacao(LocalDateTime.now(), valor, tipo);
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public Dinheiro getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movimentacao that = (Movimentacao) obj;
        return dataHora != null && dataHora.equals(that.dataHora)
                && tipo == that.tipo
                && valor != null && valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        Integer hash = 31;
        hash = 31 * hash + (dataHora != null ? dataHora.hashCode() : 0);
        hash = 31 * hash + (tipo != null ? tipo.hashCode() : 0);
        hash = 31 * hash + (valor != null ? valor.hashCode() : 0);
        return hash;
    }
}