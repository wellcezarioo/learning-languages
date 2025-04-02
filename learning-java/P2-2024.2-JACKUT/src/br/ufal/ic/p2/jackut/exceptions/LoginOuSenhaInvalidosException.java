package br.ufal.ic.p2.jackut.exceptions;

public class LoginOuSenhaInvalidosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inválidos.");
    }
}
