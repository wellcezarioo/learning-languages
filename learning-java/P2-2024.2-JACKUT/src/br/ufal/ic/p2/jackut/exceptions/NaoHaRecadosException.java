package br.ufal.ic.p2.jackut.exceptions;

public class NaoHaRecadosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NaoHaRecadosException() {
        super("Não há recados.");
    }
}
