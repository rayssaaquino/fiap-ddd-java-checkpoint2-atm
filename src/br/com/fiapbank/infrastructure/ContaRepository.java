package br.com.fiapbank.infrastructure;

import br.com.fiapbank.model.Conta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContaRepository {

    private Map<UUID, Conta> armazenamento;

    public ContaRepository() {
        this.armazenamento = new HashMap<>();
    }

    public void salvar(Conta conta) {
        armazenamento.put(conta.getId(), conta);
    }

    public Conta buscarPorId(UUID id) {
        return armazenamento.get(id);
    }

    public Boolean existe(UUID id) {
        return armazenamento.containsKey(id);
    }
}