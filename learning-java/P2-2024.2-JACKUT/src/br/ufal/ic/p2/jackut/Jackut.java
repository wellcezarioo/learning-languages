package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Classe responsável pela lógica de negócio do sistema Jackut.
 * Gerencia os usuários, sessões de login, e interações entre usuários, adição de amigos, envio de recados,
 * e a manipulação de atributos de perfil. Além disso, gerencia a persistência dos dados em um arquivo.
 */
public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Usuario> usuarios;  // Mapa de login -> Usuario
    private transient Map<String, String> sessoes;  // Mapa de sessaoId -> login
    private static final String ARQ_PERSISTENCIA = "jackut.dat";  // Arquivo de persistência

    /**
     * Construtor da classe Jackut.
     * Tenta carregar os dados persistidos a partir de um arquivo, se existir.
     * Caso contrário, inicializa as coleções de usuários e sessões.
     */
    public Jackut() {
        carregarDadosDoArquivo();
        if (this.usuarios == null) {
            this.usuarios = new HashMap<>();
        }
        this.sessoes = new HashMap<>();
    }

    //  Métodos principais

    /**
     * Zera os dados do sistema em memória. Usado principalmente em testes.
     * Limpa as listas de usuários e sessões.
     */
    public void zerarSistema() {
        usuarios.clear();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    /**
     * Cria um novo usuário no sistema, verificando as regras de login e senha.
     * Lança exceções caso o login, senha ou nome sejam inválidos ou se o login já estiver em uso.
     *
     * @param login O login do novo usuário.
     * @param senha A senha do novo usuário.
     * @param nome O nome do novo usuário.
     * @throws LoginInvalidoException Se o login for inválido.
     * @throws SenhaInvalidaException Se a senha for inválida.
     * @throws ContaComEsseNomeJaExisteException Se já existir uma conta com o mesmo login.
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException(); // "Login inválido."
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException(); // "Senha inválida."
        }
        if (usuarios.containsKey(login)) {
            throw new ContaComEsseNomeJaExisteException(); // "Conta com esse nome já existe."
        }
        if (nome == null) {
            nome = "";
        }
        Usuario novo = new Usuario(login, senha, nome);
        usuarios.put(login, novo);
    }

    /**
     * Abre uma sessão para o usuário, verificando login e senha.
     * Retorna um ID de sessão único se as credenciais estiverem corretas.
     * Lança exceção se o login ou senha estiverem inválidos.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return Um ID único de sessão.
     * @throws LoginOuSenhaInvalidosException Se o login ou senha estiverem incorretos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginOuSenhaInvalidosException();
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null || !usuario.verificarSenha(senha)) {
            throw new LoginOuSenhaInvalidosException();
        }
        String sessaoId = UUID.randomUUID().toString();
        sessoes.put(sessaoId, login);
        return sessaoId;
    }

    /**
     * Retorna o valor de um atributo do usuário especificado.
     * Lança "Usuário não cadastrado" se o login não for encontrado.
     *
     * @param login O login do usuário.
     * @param atributo O atributo desejado.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     * @throws AtributoNaoPreenchidoException Se o atributo não estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita (ou cria) um atributo do perfil do usuário com base na sessão ativa.
     * Lança "Usuário não cadastrado" se a sessão for inválida.
     *
     * @param sessaoId O ID da sessão do usuário.
     * @param atributo O atributo a ser editado.
     * @param valor O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se a sessão não for válida ou o usuário não estiver cadastrado.
     */
    public void editarPerfil(String sessaoId, String atributo, String valor) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        usuario.editarAtributo(atributo, valor);
    }

    /**
     * Adiciona um amigo ao usuário, enviando um convite ou confirmando amizade, dependendo do caso.
     * Lança erros conforme os requisitos da User Story 3.
     *
     * @param sessaoId O ID da sessão do usuário que está adicionando o amigo.
     * @param amigo O login do amigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o usuário não estiver cadastrado.
     * @throws UsuarioNaoPodeAdicionarASiMesmoException Se o usuário tentar adicionar a si mesmo como amigo.
     * @throws UsuarioJaEstaAdicionadoEsperandoException Se o amigo já estiver esperando aceitação do convite.
     * @throws UsuarioJaEstaAdicionadoException Se o amigo já estiver adicionado como amigo.
     */
    public void adicionarAmigo(String sessaoId, String amigo) {
        String loginSolicitante = sessoes.get(sessaoId);
        if (loginSolicitante == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        if (loginSolicitante.equals(amigo)) {
            throw new UsuarioNaoPodeAdicionarASiMesmoException(); // "Usuário não pode adicionar a si mesmo como amigo."
        }
        Usuario solicitante = usuarios.get(loginSolicitante);
        Usuario usuarioAlvo = usuarios.get(amigo);
        if (usuarioAlvo == null) {
            throw new UsuarioNaoCadastradoException();
        }
        if (solicitante.ehAmigo(amigo)) {
            throw new UsuarioJaEstaAdicionadoException(); // "Usuário já está adicionado como amigo."
        }
        if (usuarioAlvo.temConvite(loginSolicitante)) {
            throw new UsuarioJaEstaAdicionadoEsperandoException(); // "Usuário já está adicionado como amigo, esperando aceitação do convite."
        }
        if (solicitante.temConvite(amigo)) {
            solicitante.removerConvite(amigo);
            solicitante.confirmarAmizade(amigo);
            usuarioAlvo.confirmarAmizade(loginSolicitante);
            return;
        }
        usuarioAlvo.adicionarConvite(loginSolicitante);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login O login do usuário.
     * @param amigo O login do amigo a ser verificado.
     * @return Retorna true se os usuários forem amigos, caso contrário, false.
     * @throws UsuarioNaoCadastradoException Se o login não estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        return usuario.ehAmigo(amigo);
    }

    /**
     * Retorna os amigos de um usuário no formato de conjunto {amigo1, amigo2}.
     *
     * @param login O login do usuário.
     * @return A lista de amigos do usuário no formato de conjunto.
     * @throws UsuarioNaoCadastradoException Se o login não estiver cadastrado.
     */
    public String getAmigos(String login) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        var lista = usuario.getAmigos();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Envia um recado de um usuário para outro destinatário.
     * Lança exceções apropriadas caso a sessão seja inválida ou o usuário tente enviar um recado para si mesmo.
     *
     * @param sessaoId O ID da sessão do usuário que está enviando o recado.
     * @param destinatario O login do destinatário.
     * @param recado O conteúdo do recado.
     * @throws UsuarioNaoCadastradoException Se o destinatário não estiver cadastrado.
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se o usuário tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        if (login.equals(destinatario)) {
            throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException(); // "Usuário não pode enviar recado para si mesmo."
        }
        Usuario userDest = usuarios.get(destinatario);
        if (userDest == null) {
            throw new UsuarioNaoCadastradoException();
        }
        userDest.receberRecado(recado);
    }

    /**
     * Lê o primeiro recado da fila de recados do usuário.
     * Lança exceções apropriadas se a sessão for inválida ou se não houver recados.
     *
     * @param sessaoId O ID da sessão do usuário.
     * @return O conteúdo do primeiro recado.
     * @throws UsuarioNaoCadastradoException Se o login não estiver cadastrado.
     * @throws NaoHaRecadosException Se não houver recados na fila.
     */
    public String lerRecado(String sessaoId) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usuário não cadastrado."
        }
        String recado = usuario.lerRecado();
        if (recado == null) {
            throw new NaoHaRecadosException(); // "Não há recados."
        }
        return recado;
    }

    /**
     * Encerra o sistema, salvando os dados no arquivo e limpando as sessões.
     */
    public void encerrarSistema() {
        salvarDadosNoArquivo();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    //  Métodos privados de persistência

    /**
     * Carrega os dados do arquivo de persistência, se existir.
     * Caso o arquivo esteja corrompido ou não exista, os dados do sistema são carregados como vazios.
     */
    private void carregarDadosDoArquivo() {
        File f = new File(ARQ_PERSISTENCIA);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
                Jackut jackutPersistido = (Jackut) in.readObject();
                this.usuarios = jackutPersistido.usuarios;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Salva os dados do sistema no arquivo de persistência `jackut.dat`.
     */
    private void salvarDadosNoArquivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQ_PERSISTENCIA))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
