// UsuarioNaoPodeSerPaqueraDeSiMesmoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends RuntimeException {
    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
