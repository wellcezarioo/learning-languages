package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * A classe Facade fornece uma interface simples para interagir com o sistema Jackut.
 * Ela expõe os métodos necessários para realizar ações no sistema, como criar usuários,
 * gerenciar sessões, adicionar amigos e manipular perfis.
 * Esta classe não lida com exceções diretamente, deixando-as propagar para que o EasyAccept
 * capture as mensagens de erro apropriadas durante os testes de aceitação.
 */
public class Facade {
    private Jackut jackut;

    /**
     * Construtor da classe Facade que inicializa o sistema Jackut.
     * O Jackut é carregado com os dados persistidos, se houver.
     */
    public Facade() {
        this.jackut = new Jackut();
    }

    /**
     * Zera todos os dados do sistema, limpando usuários e sessões.
     * Usado principalmente em testes de aceitação.
     */
    public void zerarSistema() {
        jackut.zerarSistema();
    }

    /**
     * Cria um novo usuário no sistema com login, senha e nome fornecidos.
     *
     * @param login O login do novo usuário.
     * @param senha A senha do novo usuário.
     * @param nome O nome do novo usuário.
     */
    public void criarUsuario(String login, String senha, String nome) {
        jackut.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessão para o usuário com o login e senha fornecidos.
     * Retorna um ID de sessão único para o usuário autenticado.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O ID da sessão.
     * @throws LoginOuSenhaInvalidosException Se o login ou a senha forem inválidos.
     */
    public String abrirSessao(String login, String senha) {
        return jackut.abrirSessao(login, senha);
    }

    /**
     * Obtém o valor de um atributo de um usuário especificado.
     *
     * @param login O login do usuário.
     * @param atributo O nome do atributo desejado.
     * @return O valor do atributo solicitado.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     * @throws AtributoNaoPreenchidoException Se o atributo solicitado não estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {
        return jackut.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita ou cria um atributo de perfil para o usuário com a sessão aberta.
     *
     * @param sessaoId O ID da sessão do usuário.
     * @param atributo O nome do atributo a ser editado.
     * @param valor O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     */
    public void editarPerfil(String sessaoId, String atributo, String valor) {
        jackut.editarPerfil(sessaoId, atributo, valor);
    }

    /**
     * Adiciona um amigo ao usuário que está com a sessão aberta.
     * Se o usuário já tiver uma amizade ou convite pendente, uma exceção será lançada.
     *
     * @param sessaoId O ID da sessão do usuário.
     * @param amigo O login do amigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o usuário não estiver cadastrado.
     * @throws UsuarioJaEstaAdicionadoEsperandoException Se o amigo já está esperando a aceitação do convite.
     * @throws UsuarioJaEstaAdicionadoException Se o amigo já for amigo do usuário.
     */
    public void adicionarAmigo(String sessaoId, String amigo) {
        jackut.adicionarAmigo(sessaoId, amigo);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login O login do primeiro usuário.
     * @param amigo O login do segundo usuário.
     * @return Retorna true se os usuários forem amigos, caso contrário, false.
     * @throws UsuarioNaoCadastradoException Se qualquer um dos usuários não estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) {
        return jackut.ehAmigo(login, amigo);
    }

    /**
     * Obtém a lista de amigos de um usuário.
     *
     * @param login O login do usuário.
     * @return A lista de amigos do usuário em formato de string.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String getAmigos(String login) {
        return jackut.getAmigos(login);
    }

    /**
     * Envia um recado de um usuário para outro.
     *
     * @param sessaoId O ID da sessão do usuário que está enviando o recado.
     * @param destinatario O login do destinatário do recado.
     * @param recado O conteúdo do recado.
     * @throws UsuarioNaoCadastradoException Se a sessão ou o destinatário não estiverem cadastrados.
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se o usuário tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        jackut.enviarRecado(sessaoId, destinatario, recado);
    }

    /**
     * Lê o primeiro recado da fila de recados do usuário que está com a sessão aberta.
     *
     * @param sessaoId O ID da sessão do usuário.
     * @return O conteúdo do recado.
     * @throws UsuarioNaoCadastradoException Se a sessão não for válida.
     * @throws NaoHaRecadosException Se não houver recados.
     */
    public String lerRecado(String sessaoId) {
        return jackut.lerRecado(sessaoId);
    }

    /**
     * Encerra o sistema, salvando os dados persistidos e limpando as sessões.
     */
    public void encerrarSistema() {
        jackut.encerrarSistema();
    }
}
