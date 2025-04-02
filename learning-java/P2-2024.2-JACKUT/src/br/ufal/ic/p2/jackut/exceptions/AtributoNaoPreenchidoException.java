package br.ufal.ic.p2.jackut.exceptions;

public class AtributoNaoPreenchidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
