package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeAdicionarASiMesmoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeAdicionarASiMesmoException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
