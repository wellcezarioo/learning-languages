package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeEnviarRecadoParaSiMesmoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoPodeEnviarRecadoParaSiMesmoException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}
