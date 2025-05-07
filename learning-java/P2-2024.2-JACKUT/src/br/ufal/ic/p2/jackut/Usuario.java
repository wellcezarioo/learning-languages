package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import java.io.Serializable;
import java.util.*;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;

    private List<String> amigos          = new ArrayList<>();
    private Queue<String> recados        = new LinkedList<>();
    private Map<String,String> atributos = new HashMap<>();
    private Set<String> convitesPendentes = new HashSet<>();

    private Set<String> idolos    = new LinkedHashSet<>();
    private Set<String> paqueras  = new LinkedHashSet<>();
    private Set<String> inimigos  = new LinkedHashSet<>();
    private Queue<String> mensagens = new LinkedList<>();

    // Histórico de comunidades na ordem de ingresso
    private Set<String> comunidadesParticipando = new LinkedHashSet<>();

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome  = nome;
    }

    public String getLogin() { return login; }
    public String getNome()  { return nome; }

    public boolean verificarSenha(String s) { return this.senha.equals(s); }

    // Perfil
    public void editarAtributo(String atributo, String valor) {
        if ("nome".equals(atributo)) this.nome = valor;
        else atributos.put(atributo, valor);
    }
    public String getAtributo(String atributo) {
        if ("nome".equals(atributo)) return nome;
        if (atributos.containsKey(atributo)) return atributos.get(atributo);
        throw new AtributoNaoPreenchidoException();
    }

    // Amigos
    public boolean ehAmigo(String a) { return amigos.contains(a); }
    public void confirmarAmizade(String a) {
        if (!amigos.contains(a)) amigos.add(a);
    }
    public List<String> getAmigos() { return amigos; }
    public void adicionarConvite(String de) { convitesPendentes.add(de); }
    public boolean temConvite(String de)    { return convitesPendentes.contains(de); }
    public void removerConvite(String de)   { convitesPendentes.remove(de); }

    // Recados privados
    public void receberRecado(String msg) { recados.add(msg); }
    public String lerRecado()             { return recados.poll(); }
    public void limparRecados()           { recados.clear(); }

    // Mensagens de comunidade
    public void receberMensagem(String msg) { mensagens.add(msg); }
    public String lerMensagem()              { return mensagens.poll(); }
    public void limparMensagens()            { mensagens.clear(); }

    // Fãs/ídolos
    public void adicionarIdolo(String idolo) { idolos.add(idolo); }
    public boolean ehIdolo(String idolo)     { return idolos.contains(idolo); }

    // Paqueras
    public void adicionarPaquera(String p) { paqueras.add(p); }
    public boolean ehPaquera(String p)     { return paqueras.contains(p); }
    public Set<String> getPaqueras()       { return paqueras; }

    // Inimizades
    public void adicionarInimigo(String inimigo) { inimigos.add(inimigo); }
    public boolean ehInimigo(String i)            { return inimigos.contains(i); }

    // Comunidades
    public void adicionarComunidadeParticipa(String nomeComunidade) {
        comunidadesParticipando.add(nomeComunidade);
    }
    public Set<String> getComunidadesParticipando() {
        return comunidadesParticipando;
    }
    public void limparComunidadesParticipando(Collection<String> existentes) {
        comunidadesParticipando.retainAll(existentes);
    }
}
