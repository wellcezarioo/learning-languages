package br.ufal.ic.p2.jackut.exceptions;

public class LoginInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
