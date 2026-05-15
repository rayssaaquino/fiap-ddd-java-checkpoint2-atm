package br.com.fiapbank.application;

import br.com.fiapbank.infrastructure.ContaRepository;
import br.com.fiapbank.presentation.TerminalBancarioController;

public class Main {

    public static void main(String[] args) {

        ContaRepository repositorio = new ContaRepository();
        ContaFactory factory = ContaFactory.getInstance();

        TerminalBancarioController terminal = new TerminalBancarioController(factory, repositorio);
        terminal.iniciar();
    }
}