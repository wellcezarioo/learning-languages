// UsuarioJaEstaIdoloException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaIdoloException extends RuntimeException {
    public UsuarioJaEstaIdoloException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
