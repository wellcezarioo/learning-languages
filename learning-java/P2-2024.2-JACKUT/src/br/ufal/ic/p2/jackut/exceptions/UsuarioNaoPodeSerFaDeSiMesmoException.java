// UsuarioNaoPodeSerFaDeSiMesmoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeSerFaDeSiMesmoException extends RuntimeException {
    public UsuarioNaoPodeSerFaDeSiMesmoException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
