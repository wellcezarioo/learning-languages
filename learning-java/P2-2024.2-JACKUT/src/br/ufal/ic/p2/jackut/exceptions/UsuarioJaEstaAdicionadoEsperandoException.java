package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaAdicionadoEsperandoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaEstaAdicionadoEsperandoException() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}
