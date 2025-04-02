package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoCadastradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
