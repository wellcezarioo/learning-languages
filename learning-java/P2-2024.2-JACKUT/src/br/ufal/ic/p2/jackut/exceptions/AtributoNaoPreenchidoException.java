package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um atributo requerido não foi preenchido.
 * Esta exceção é utilizada quando o usuário tenta acessar um atributo que não foi definido.
 */
public class AtributoNaoPreenchidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
