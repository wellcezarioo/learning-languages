package br.ufal.ic.p2.jackut.exceptions;

public class NaoHaMensagensException extends RuntimeException {
    public NaoHaMensagensException() {
        super("Não há mensagens.");
    }
}
