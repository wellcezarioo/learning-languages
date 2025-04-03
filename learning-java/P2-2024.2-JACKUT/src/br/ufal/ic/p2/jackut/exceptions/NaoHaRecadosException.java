package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando não há recados para serem lidos.
 * Esta exceção é utilizada quando o usuário tenta ler um recado, mas sua fila de recados está vazia.
 */
public class NaoHaRecadosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção, com uma mensagem padrão.
     */
    public NaoHaRecadosException() {
        super("Não há recados.");
    }
}
