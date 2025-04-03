package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando há uma tentativa de criar uma conta com um nome de usuário já existente.
 * Esta exceção é usada quando o login fornecido já está em uso no sistema.
 */
public class ContaComEsseNomeJaExisteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public ContaComEsseNomeJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
