package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário já está adicionado como amigo e está aguardando a aceitação do convite.
 * Essa exceção é usada quando há uma tentativa de adicionar um usuário que já foi convidado, mas o convite ainda está pendente.
 */
public class UsuarioJaEstaAdicionadoEsperandoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public UsuarioJaEstaAdicionadoEsperandoException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
