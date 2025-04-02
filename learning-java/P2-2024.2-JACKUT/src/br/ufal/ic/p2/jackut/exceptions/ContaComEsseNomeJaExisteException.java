package br.ufal.ic.p2.jackut.exceptions;

public class ContaComEsseNomeJaExisteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ContaComEsseNomeJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
