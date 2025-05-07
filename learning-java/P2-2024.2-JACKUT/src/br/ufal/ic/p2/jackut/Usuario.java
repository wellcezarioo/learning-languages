package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import java.io.Serializable;
import java.util.*;

/**
 * Classe que representa um usuário na rede social Jackut.
 * Contém informações de login, senha, nome, amigos, recados, atributos, convites pendentes,
 * fãs, ídolos, paqueras, inimigos e comunidades participando.
 *
 * A classe fornece métodos para gerenciar e acessar essas informações.
 */
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

    /**
     * Constrói um novo usuário com as informações fornecidas.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome  = nome;
    }

    /**
     * @return O login do usuário
     */
    public String getLogin() { return login; }

    /**
     *
     * @return O nome do usuário
     */
    public String getNome()  { return nome; }

    /**
     * Verifica se a senha fornecida corresponde à senha do usuário.
     *
     * @param s Senha a ser verificada
     * @return True se a senha for válida, false caso contrário
     */
    public boolean verificarSenha(String s) { return this.senha.equals(s); }

    // Perfil

    /**
     * Edita um atributo do usuário.
     *
     * @param atributo O nome do atributo a ser editado
     * @param valor O valor a ser atribuído ao atributo
     */
    public void editarAtributo(String atributo, String valor) {
        if ("nome".equals(atributo)) this.nome = valor;
        else atributos.put(atributo, valor);
    }

    /**
     *
     * @param atributo O nome do atributo
     * @return O valor do atributo
     * @throws AtributoNaoPreenchidoException Se o atributo não estiver preenchido
     */
    public String getAtributo(String atributo) {
        if ("nome".equals(atributo)) return nome;
        if (atributos.containsKey(atributo)) return atributos.get(atributo);
        throw new AtributoNaoPreenchidoException();
    }

    // Amigos

    /**
     * Verifica se o usuário é amigo de outro usuário.
     *
     * @param a Login do amigo
     * @return True se forem amigos, false caso contrário
     */
    public boolean ehAmigo(String a) { return amigos.contains(a); }

    /**
     * Confirma uma amizade com outro usuário.
     *
     * @param a Login do amigo
     */
    public void confirmarAmizade(String a) {
        if (!amigos.contains(a)) amigos.add(a);
    }

    /**
     * Retorna a lista de amigos do usuário.
     *
     * @return Lista de amigos
     */
    public List<String> getAmigos() { return amigos; }

    /**
     * Adiciona um convite pendente de amizade.
     *
     * @param de Login do usuário que enviou o convite
     */
    public void adicionarConvite(String de) { convitesPendentes.add(de); }

    /**
     * Verifica se há um convite pendente de amizade.
     *
     * @param de Login do usuário que enviou o convite
     * @return True se houver convite pendente, false caso contrário
     */
    public boolean temConvite(String de)    { return convitesPendentes.contains(de); }

    /**
     * Remove um convite pendente de amizade.
     *
     * @param de Login do usuário que enviou o convite
     */
    public void removerConvite(String de)   { convitesPendentes.remove(de); }

    // Recados privados

    /**
     * Recebe um recado privado.
     *
     * @param msg O recado a ser recebido
     */
    public void receberRecado(String msg) { recados.add(msg); }

    /**
     * Lê o próximo recado privado.
     *
     * @return O recado lido, ou null se não houver recados
     */
    public String lerRecado()             { return recados.poll(); }

    /**
     * Limpa todos os recados privados.
     */
    public void limparRecados()           { recados.clear(); }

    // Mensagens de comunidade

    /**
     * Recebe uma mensagem de comunidade.
     *
     * @param msg A mensagem a ser recebida
     */
    public void receberMensagem(String msg) { mensagens.add(msg); }

    /**
     * Lê a próxima mensagem de comunidade.
     *
     * @return A mensagem lida, ou null se não houver mensagens
     */
    public String lerMensagem()              { return mensagens.poll(); }

    /**
     * Limpa todas as mensagens de comunidade.
     */
    public void limparMensagens()            { mensagens.clear(); }

    // Fãs/ídolos

    /**
     * Adiciona um ídolo ao usuário.
     *
     * @param idolo O login do ídolo
     */
    public void adicionarIdolo(String idolo) { idolos.add(idolo); }

    /**
     * Verifica se o usuário é ídolo de outro usuário.
     *
     * @param idolo O login do ídolo
     * @return True se for ídolo, false caso contrário
     */
    public boolean ehIdolo(String idolo)     { return idolos.contains(idolo); }

    // Paqueras

    /**
     * Adiciona uma paquera ao usuário.
     *
     * @param p O login da paquera
     */
    public void adicionarPaquera(String p) { paqueras.add(p); }

    /**
     * Verifica se o usuário tem uma paquera.
     *
     * @param p O login da paquera
     * @return True se for paquera, false caso contrário
     */
    public boolean ehPaquera(String p)     { return paqueras.contains(p); }

    /**
     * Retorna o conjunto de paqueras do usuário.
     *
     * @return O conjunto de paqueras
     */
    public Set<String> getPaqueras()       { return paqueras; }

    // Inimizades

    /**
     * Adiciona um inimigo ao usuário.
     *
     * @param inimigo O login do inimigo
     */
    public void adicionarInimigo(String inimigo) { inimigos.add(inimigo); }

    /**
     * Verifica se o usuário tem um inimigo.
     *
     * @param i O login do inimigo
     * @return True se for inimigo, false caso contrário
     */
    public boolean ehInimigo(String i)            { return inimigos.contains(i); }

    // Comunidades

    /**
     * Adiciona o usuário a uma comunidade.
     *
     * @param nomeComunidade O nome da comunidade
     */
    public void adicionarComunidadeParticipa(String nomeComunidade) {
        comunidadesParticipando.add(nomeComunidade);
    }

    /**
     * Retorna as comunidades que o usuário participa.
     *
     * @return O conjunto de comunidades que o usuário participa
     */
    public Set<String> getComunidadesParticipando() {
        return comunidadesParticipando;
    }

    /**
     * Limpa as comunidades nas quais o usuário participa, mantendo apenas as que estão presentes na coleção fornecida.
     *
     * @param existentes Coleção de comunidades existentes a serem mantidas
     */
    public void limparComunidadesParticipando(Collection<String> existentes) {
        comunidadesParticipando.retainAll(existentes);
    }
}
