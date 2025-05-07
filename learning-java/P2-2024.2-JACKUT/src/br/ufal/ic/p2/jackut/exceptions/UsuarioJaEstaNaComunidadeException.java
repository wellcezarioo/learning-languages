package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaNaComunidadeException extends RuntimeException {
    public UsuarioJaEstaNaComunidadeException() {
        super("Usuario já faz parte dessa comunidade."); // Com acento
    }
}