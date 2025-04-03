package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando o login ou a senha fornecidos são inválidos.
 * Essa exceção é usada quando um usuário tenta fazer login com credenciais incorretas.
 */
public class LoginOuSenhaInvalidosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inválidos.");
    }
}
