package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando uma senha inválida é fornecida.
 * Esta exceção ocorre quando a senha fornecida para o login ou criação de conta é inválida.
 */
public class SenhaInvalidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
