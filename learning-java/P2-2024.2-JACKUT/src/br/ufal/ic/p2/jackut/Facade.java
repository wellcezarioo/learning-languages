package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.util.Set;

public class Facade {
    private Jackut jackut = new Jackut();

    public void zerarSistema()                                { jackut.zerarSistema(); }
    public void criarUsuario(String l, String s, String n)    { jackut.criarUsuario(l, s, n); }
    public String abrirSessao(String l, String s)             { return jackut.abrirSessao(l, s); }
    public String getAtributoUsuario(String l, String a)      { return jackut.getAtributoUsuario(l, a); }
    public void editarPerfil(String sid, String a, String v)  { jackut.editarPerfil(sid, a, v); }

    public void adicionarAmigo(String sid, String a)          { jackut.adicionarAmigo(sid, a); }
    public boolean ehAmigo(String l, String a)                { return jackut.ehAmigo(l, a); }
    public String getAmigos(String l)                         { return jackut.getAmigos(l); }

    public void enviarRecado(String sid, String d, String r)  { jackut.enviarRecado(sid, d, r); }
    public String lerRecado(String sid)                       { return jackut.lerRecado(sid); }

    public void criarComunidade(String sid, String n, String d) { jackut.criarComunidade(sid, n, d); }
    public String getDescricaoComunidade(String n)             { return jackut.getDescricaoComunidade(n); }
    public String getDonoComunidade(String n)                  { return jackut.getDonoComunidade(n); }
    public String getMembrosComunidade(String n)               { return jackut.getMembrosComunidade(n); }

    public String getComunidades(String chave)                 { return jackut.getComunidades(chave); }
    public void adicionarComunidade(String sid, String n)     { jackut.adicionarComunidade(sid, n); }

    public void enviarMensagem(String sid, String com, String m) { jackut.enviarMensagem(sid, com, m); }
    public String lerMensagem(String sid)                       { return jackut.lerMensagem(sid); }

    // ?? Fãs / Ídolos ??
    public void adicionarIdolo(String sid, String idolo)       { jackut.adicionarIdolo(sid, idolo); }
    public boolean ehFa(String login, String idolo)            { return jackut.ehFa(login, idolo); }
    public String getFas(String login) {
        Set<String> fas = jackut.getFas(login);
        return "{" + String.join(",", fas) + "}";
    }

    // ?? Paqueras ??
    public void adicionarPaquera(String sid, String paquera)  { jackut.adicionarPaquera(sid, paquera); }
    public boolean ehPaquera(String login, String paquera)    { return jackut.ehPaquera(login, paquera); }
    public String getPaqueras(String login) {
        Set<String> ps = jackut.getPaqueras(login);
        return "{" + String.join(",", ps) + "}";
    }

    // ?? Inimizades ??
    public void adicionarInimigo(String sid, String inimigo)   { jackut.adicionarInimigo(sid, inimigo); }

    // ?? Remoção de conta ??
    public void removerUsuario(String sid)                     { jackut.removerUsuario(sid); }

    public void encerrarSistema()                              { jackut.encerrarSistema(); }
}
