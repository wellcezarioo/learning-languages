package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaAdicionadoEsperandoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioJaEstaAdicionadoEsperandoException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
