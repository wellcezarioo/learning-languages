package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Representa um usuário no sistema Jackut.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private List<String> amigos;
    private Queue<String> recados;
    private Map<String, String> atributos;           // Atributos arbitrários (descricao, estadoCivil, etc.)
    private Set<String> convitesPendentes;          // Convites de amizade pendentes (logins de quem enviou)

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.amigos = new ArrayList<>();
        this.recados = new LinkedList<>();
        this.atributos = new HashMap<>();
        this.convitesPendentes = new HashSet<>();
    }

    public String getLogin() {
        return login;
    }

    /**
     * Verifica se a senha fornecida confere com a senha deste usuário.
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Retorna o nome do usuário (é um atributo especial que sempre existe).
     */
    public String getNome() {
        return nome;
    }

    /**
     * Cria ou edita um atributo do perfil.
     * Caso desejasse permitir editar o próprio 'nome', bastaria checar se atributo é "nome".
     */
    public void editarAtributo(String atributo, String valor) {
        if ("nome".equals(atributo)) {
            // Se quiser permitir editar o nome, bastaria: this.nome = valor;
            // Mas, pelas user stories, "nome" é definido no construtor e não costuma mudar.
            // Caso não deseje permitir, simplesmente ignore ou trate de outra forma.
            this.nome = valor;
        } else {
            atributos.put(atributo, valor);
        }
    }

    /**
     * Retorna o valor de um atributo do perfil.
     * Se o atributo for "nome", retorna o nome.
     * Se não estiver definido (e não for "nome"), lança AtributoNaoPreenchidoException.
     */
    public String getAtributo(String atributo) {
        if ("nome".equals(atributo)) {
            return this.nome;
        }
        if (atributos.containsKey(atributo)) {
            return atributos.get(atributo);
        }
        throw new AtributoNaoPreenchidoException(); // "Atributo não preenchido."
    }

    // --------------------- Métodos de amizade ------------------------

    /**
     * Verifica se 'amigo' está na lista de amigos deste usuário.
     */
    public boolean ehAmigo(String amigo) {
        return amigos.contains(amigo);
    }

    /**
     * Confirma a amizade com outro usuário (adicionando-o aos amigos).
     */
    public void confirmarAmizade(String amigo) {
        if (!amigos.contains(amigo)) {
            amigos.add(amigo);
        }
    }

    public List<String> getAmigos() {
        return amigos;
    }

    // --------------------- Métodos de convites -----------------------

    public void adicionarConvite(String deUsuario) {
        convitesPendentes.add(deUsuario);
    }

    public void removerConvite(String deUsuario) {
        convitesPendentes.remove(deUsuario);
    }

    public boolean temConvite(String deUsuario) {
        return convitesPendentes.contains(deUsuario);
    }

    // --------------------- Métodos de recados ------------------------

    /**
     * Adiciona um recado à fila do usuário.
     */
    public void receberRecado(String mensagem) {
        recados.add(mensagem);
    }

    /**
     * Retorna (e remove) o primeiro recado da fila ou null se não houver.
     */
    public String lerRecado() {
        return recados.poll(); // retorna null se estiver vazio
    }
}
