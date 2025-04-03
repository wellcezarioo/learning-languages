package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta enviar um recado para si mesmo.
 * Esta exceção é usada quando um usuário tenta enviar um recado para o próprio login, o que não é permitido.
 */
public class UsuarioNaoPodeEnviarRecadoParaSiMesmoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public UsuarioNaoPodeEnviarRecadoParaSiMesmoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
