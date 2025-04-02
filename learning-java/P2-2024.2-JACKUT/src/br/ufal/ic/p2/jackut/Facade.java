package br.ufal.ic.p2.jackut;

/**
 * Facade que expõe os métodos do sistema para os testes de aceitação.
 * Não precisa capturar exceções aqui; basta deixar propagar,
 * pois o EasyAccept vai tratar a mensagem que chegar ao console.
 */

public class Facade {
    private Jackut jackut;

    public Facade() {
        // Instancia (ou carrega) o Jackut, o qual faz o carregamento de dados persistidos, caso existam.
        this.jackut = new Jackut();
    }

    public void zerarSistema() {
        jackut.zerarSistema();
    }

    public void criarUsuario(String login, String senha, String nome) {
        jackut.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) {
        return jackut.abrirSessao(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) {
        return jackut.getAtributoUsuario(login, atributo);
    }

    public void editarPerfil(String sessaoId, String atributo, String valor) {
        jackut.editarPerfil(sessaoId, atributo, valor);
    }

    public void adicionarAmigo(String sessaoId, String amigo) {
        jackut.adicionarAmigo(sessaoId, amigo);
    }

    public boolean ehAmigo(String login, String amigo) {
        return jackut.ehAmigo(login, amigo);
    }

    public String getAmigos(String login) {
        return jackut.getAmigos(login);
    }

    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        jackut.enviarRecado(sessaoId, destinatario, recado);
    }

    public String lerRecado(String sessaoId) {
        return jackut.lerRecado(sessaoId);
    }

    public void encerrarSistema() {
        jackut.encerrarSistema();
    }
}
