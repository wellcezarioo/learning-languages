// UsuarioJaEstaInimigoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaInimigoException extends RuntimeException {
    public UsuarioJaEstaInimigoException() {
        super("Usuário já está adicionado como inimigo.");
    }
}
