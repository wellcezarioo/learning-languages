package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;

import java.io.Serializable;
import java.util.*;

/**
 * Classe que representa um usuário no sistema Jackut.
 * Gerencia informações como login, senha, nome, atributos de perfil, amigos, recados e convites pendentes.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private List<String> amigos;
    private Queue<String> recados;
    private Map<String, String> atributos;
    private Set<String> convitesPendentes;

    /**
     * Construtor da classe Usuario.
     * Inicializa o usuário com um login, senha e nome.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome O nome do usuário.
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.amigos = new ArrayList<>();
        this.recados = new LinkedList<>();
        this.atributos = new HashMap<>();
        this.convitesPendentes = new HashSet<>();
    }

    /**
     * Verifica se a senha fornecida é válida para este usuário.
     *
     * @param senha A senha a ser verificada.
     * @return Retorna true se a senha for válida, caso contrário, false.
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Edita ou cria um atributo no perfil do usuário.
     *
     * @param atributo O nome do atributo.
     * @param valor O valor do atributo.
     */
    public void editarAtributo(String atributo, String valor) {
        if ("nome".equals(atributo)) {
            this.nome = valor;
        } else {
            atributos.put(atributo, valor);
        }
    }

    /**
     * Obtém o valor de um atributo do perfil do usuário.
     *
     * @param atributo O nome do atributo.
     * @return O valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo solicitado não foi preenchido.
     */
    public String getAtributo(String atributo) {
        if ("nome".equals(atributo)) {
            return this.nome;
        }
        if (atributos.containsKey(atributo)) {
            return atributos.get(atributo);
        }
        throw new AtributoNaoPreenchidoException();
    }

    /**
     * Verifica se o usuário é amigo de outro usuário.
     *
     * @param amigo O login do outro usuário.
     * @return Retorna true se o usuário for amigo do outro, caso contrário, false.
     */
    public boolean ehAmigo(String amigo) {
        return amigos.contains(amigo);
    }

    /**
     * Adiciona um amigo à lista de amigos do usuário.
     *
     * @param amigo O login do amigo a ser adicionado.
     */
    public void confirmarAmizade(String amigo) {
        if (!amigos.contains(amigo)) {
            amigos.add(amigo);
        }
    }

    /**
     * Retorna a lista de amigos do usuário.
     *
     * @return A lista de amigos do usuário.
     */
    public List<String> getAmigos() {
        return amigos;
    }

    /**
     * Adiciona um convite de amizade à lista de convites pendentes do usuário.
     *
     * @param deUsuario O login do usuário que enviou o convite.
     */
    public void adicionarConvite(String deUsuario) {
        convitesPendentes.add(deUsuario);
    }

    /**
     * Remove um convite de amizade da lista de convites pendentes do usuário.
     *
     * @param deUsuario O login do usuário que enviou o convite.
     */
    public void removerConvite(String deUsuario) {
        convitesPendentes.remove(deUsuario);
    }

    /**
     * Verifica se o usuário tem um convite pendente de outro usuário.
     *
     * @param deUsuario O login do usuário que enviou o convite.
     * @return Retorna true se o convite estiver pendente, caso contrário, false.
     */
    public boolean temConvite(String deUsuario) {
        return convitesPendentes.contains(deUsuario);
    }

    /**
     * Adiciona um recado à fila de recados do usuário.
     *
     * @param mensagem O conteúdo do recado.
     */
    public void receberRecado(String mensagem) {
        recados.add(mensagem);
    }

    /**
     * Retorna e remove o primeiro recado da fila de recados do usuário.
     *
     * @return O conteúdo do primeiro recado, ou null se não houver recados.
     */
    public String lerRecado() {
        return recados.poll();
    }
}
