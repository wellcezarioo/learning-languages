package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário já está adicionado como amigo.
 * Esta exceção ocorre quando há uma tentativa de adicionar um usuário que já foi adicionado à lista de amigos.
 */
public class UsuarioJaEstaAdicionadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public UsuarioJaEstaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
