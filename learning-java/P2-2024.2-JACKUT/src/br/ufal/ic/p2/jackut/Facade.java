package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.util.Set;

/**
 * A classe {@code Facade} atua como uma interface de acesso para as funcionalidades do sistema Jackut.
 * Ela abstrai a complexidade do funcionamento interno do sistema, fornecendo métodos para gerenciar
 * usuários, amigos, comunidades e outros aspectos relacionados à rede social.
 * <p>
 * Esta classe delega as chamadas aos métodos correspondentes da classe {@link Jackut}, que gerencia
 * a lógica do sistema.
 * </p>
 *
 * @author [Seu Nome]
 * @version 1.0
 */
public class Facade {

    private Jackut jackut = new Jackut();

    /**
     * Zera o sistema, removendo todos os dados existentes.
     */
    public void zerarSistema() {
        jackut.zerarSistema();
    }

    /**
     * Cria um novo usuário no sistema com o login, senha e nome fornecidos.
     *
     * @param l o login do novo usuário
     * @param s a senha do novo usuário
     * @param n o nome do novo usuário
     */
    public void criarUsuario(String l, String s, String n) {
        jackut.criarUsuario(l, s, n);
    }

    /**
     * Abre uma sessão para o usuário fornecido com login e senha.
     *
     * @param l o login do usuário
     * @param s a senha do usuário
     * @return uma string representando a sessão do usuário
     */
    public String abrirSessao(String l, String s) {
        return jackut.abrirSessao(l, s);
    }

    /**
     * Obtém o valor de um atributo de um usuário.
     *
     * @param l o login do usuário
     * @param a o nome do atributo a ser obtido
     * @return o valor do atributo solicitado
     */
    public String getAtributoUsuario(String l, String a) {
        return jackut.getAtributoUsuario(l, a);
    }

    /**
     * Edita o perfil de um usuário.
     *
     * @param sid a sessão do usuário
     * @param a o atributo a ser editado
     * @param v o novo valor do atributo
     */
    public void editarPerfil(String sid, String a, String v) {
        jackut.editarPerfil(sid, a, v);
    }

    /**
     * Adiciona um amigo a um usuário.
     *
     * @param sid a sessão do usuário
     * @param a o login do amigo a ser adicionado
     */
    public void adicionarAmigo(String sid, String a) {
        jackut.adicionarAmigo(sid, a);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param l o login do usuário
     * @param a o login do possível amigo
     * @return {@code true} se forem amigos, {@code false} caso contrário
     */
    public boolean ehAmigo(String l, String a) {
        return jackut.ehAmigo(l, a);
    }

    /**
     * Obtém os amigos de um usuário.
     *
     * @param l o login do usuário
     * @return uma string com os amigos do usuário
     */
    public String getAmigos(String l) {
        return jackut.getAmigos(l);
    }

    /**
     * Envia um recado de um usuário para outro.
     *
     * @param sid a sessão do usuário que está enviando o recado
     * @param d o destinatário do recado
     * @param r o conteúdo do recado
     */
    public void enviarRecado(String sid, String d, String r) {
        jackut.enviarRecado(sid, d, r);
    }

    /**
     * Lê os recados de um usuário.
     *
     * @param sid a sessão do usuário
     * @return o conteúdo dos recados do usuário
     */
    public String lerRecado(String sid) {
        return jackut.lerRecado(sid);
    }

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param sid a sessão do usuário
     * @param n o nome da comunidade
     * @param d a descrição da comunidade
     */
    public void criarComunidade(String sid, String n, String d) {
        jackut.criarComunidade(sid, n, d);
    }

    /**
     * Obtém a descrição de uma comunidade.
     *
     * @param n o nome da comunidade
     * @return a descrição da comunidade
     */
    public String getDescricaoComunidade(String n) {
        return jackut.getDescricaoComunidade(n);
    }

    /**
     * Obtém o dono de uma comunidade.
     *
     * @param n o nome da comunidade
     * @return o login do dono da comunidade
     */
    public String getDonoComunidade(String n) {
        return jackut.getDonoComunidade(n);
    }

    /**
     * Obtém os membros de uma comunidade.
     *
     * @param n o nome da comunidade
     * @return uma string com os membros da comunidade
     */
    public String getMembrosComunidade(String n) {
        return jackut.getMembrosComunidade(n);
    }

    /**
     * Obtém as comunidades associadas a uma chave de busca.
     *
     * @param chave a chave de busca para localizar comunidades
     * @return uma string com as comunidades encontradas
     */
    public String getComunidades(String chave) {
        return jackut.getComunidades(chave);
    }

    /**
     * Adiciona um usuário a uma comunidade.
     *
     * @param sid a sessão do usuário
     * @param n o nome da comunidade
     */
    public void adicionarComunidade(String sid, String n) {
        jackut.adicionarComunidade(sid, n);
    }

    /**
     * Envia uma mensagem a uma comunidade.
     *
     * @param sid a sessão do usuário
     * @param com o nome da comunidade
     * @param m o conteúdo da mensagem
     */
    public void enviarMensagem(String sid, String com, String m) {
        jackut.enviarMensagem(sid, com, m);
    }

    /**
     * Lê as mensagens de um usuário.
     *
     * @param sid a sessão do usuário
     * @return o conteúdo das mensagens do usuário
     */
    public String lerMensagem(String sid) {
        return jackut.lerMensagem(sid);
    }

    /**
     * Adiciona um ídolo a um usuário.
     *
     * @param sid a sessão do usuário
     * @param idolo o login do ídolo a ser adicionado
     */
    public void adicionarIdolo(String sid, String idolo) {
        jackut.adicionarIdolo(sid, idolo);
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param login o login do fã
     * @param idolo o login do ídolo
     * @return {@code true} se o usuário for fã do ídolo, {@code false} caso contrário
     */
    public boolean ehFa(String login, String idolo) {
        return jackut.ehFa(login, idolo);
    }

    /**
     * Obtém os fãs de um usuário.
     *
     * @param login o login do ídolo
     * @return uma string com os fãs do usuário
     */
    public String getFas(String login) {
        Set<String> fas = jackut.getFas(login);
        return "{" + String.join(",", fas) + "}";
    }

    /**
     * Adiciona uma paquera a um usuário.
     *
     * @param sid a sessão do usuário
     * @param paquera o login da paquera a ser adicionada
     */
    public void adicionarPaquera(String sid, String paquera) {
        jackut.adicionarPaquera(sid, paquera);
    }

    /**
     * Verifica se um usuário tem uma paquera.
     *
     * @param login o login do usuário
     * @param paquera o login da paquera
     * @return {@code true} se for paquera, {@code false} caso contrário
     */
    public boolean ehPaquera(String login, String paquera) {
        return jackut.ehPaquera(login, paquera);
    }

    /**
     * Obtém as paqueras de um usuário.
     *
     * @param login o login do usuário
     * @return uma string com as paqueras do usuário
     */
    public String getPaqueras(String login) {
        Set<String> ps = jackut.getPaqueras(login);
        return "{" + String.join(",", ps) + "}";
    }

    /**
     * Adiciona um inimigo a um usuário.
     *
     * @param sid a sessão do usuário
     * @param inimigo o login do inimigo a ser adicionado
     */
    public void adicionarInimigo(String sid, String inimigo) {
        jackut.adicionarInimigo(sid, inimigo);
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param sid a sessão do usuário a ser removido
     */
    public void removerUsuario(String sid) {
        jackut.removerUsuario(sid);
    }

    /**
     * Encerra o sistema, apagando todos os dados.
     */
    public void encerrarSistema() {
        jackut.encerrarSistema();
    }
}
