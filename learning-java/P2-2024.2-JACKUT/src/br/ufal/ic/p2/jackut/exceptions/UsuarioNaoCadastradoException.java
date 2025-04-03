package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário não está cadastrado no sistema.
 * Essa exceção é usada quando um usuário tenta realizar uma ação (como login ou edição de perfil) e o login fornecido não corresponde a um usuário registrado.
 */
public class UsuarioNaoCadastradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
