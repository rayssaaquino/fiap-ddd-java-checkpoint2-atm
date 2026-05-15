package br.com.fiapbank.model;

public class Cliente extends BaseEntity {

    private String nomeCompleto;

    public Cliente(String nomeCompleto) {
        super();
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome completo do cliente é obrigatório.");
        }
        this.nomeCompleto = nomeCompleto.trim();
    }

    public String obterPrimeiroNome() {
        String[] partes = nomeCompleto.split(" ");
        return partes[0];
    }

    public String getNomeCompleto() {
        return nomeCompleto;
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