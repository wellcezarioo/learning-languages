package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaAdicionadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaEstaAdicionadoException() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}
