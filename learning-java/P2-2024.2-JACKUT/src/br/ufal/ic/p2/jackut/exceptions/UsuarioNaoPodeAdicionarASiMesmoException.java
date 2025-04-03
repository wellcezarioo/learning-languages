package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta adicionar a si mesmo como amigo.
 * Esta exceção ocorre quando um usuário tenta enviar um convite de amizade para si próprio, o que não é permitido.
 */
public class UsuarioNaoPodeAdicionarASiMesmoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public UsuarioNaoPodeAdicionarASiMesmoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
