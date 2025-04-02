package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaAdicionadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaEstaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
