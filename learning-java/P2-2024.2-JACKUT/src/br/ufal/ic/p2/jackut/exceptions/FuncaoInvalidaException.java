// FuncaoInvalidaException.java
package br.ufal.ic.p2.jackut.exceptions;

public class FuncaoInvalidaException extends RuntimeException {
    public FuncaoInvalidaException(String detalhe) {
        super("Função inválida: " + detalhe);
    }
}
