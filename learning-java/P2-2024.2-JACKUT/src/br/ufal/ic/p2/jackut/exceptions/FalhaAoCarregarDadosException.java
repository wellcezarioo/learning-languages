package br.ufal.ic.p2.jackut.exceptions;

public class FalhaAoCarregarDadosException extends RuntimeException {
    public FalhaAoCarregarDadosException() {
        super("Falha ao carregar dados do sistema.");
    }
}