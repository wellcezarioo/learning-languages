package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um login inválido é fornecido.
 * Esta exceção ocorre quando o login fornecido para a criação de conta ou para o acesso ao sistema é inválido.
 */
public class LoginInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
