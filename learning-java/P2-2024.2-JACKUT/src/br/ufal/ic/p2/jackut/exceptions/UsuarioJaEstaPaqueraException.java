// UsuarioJaEstaPaqueraException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaPaqueraException extends RuntimeException {
    public UsuarioJaEstaPaqueraException() {
        super("Usuário já está adicionado como paquera.");
    }
}
