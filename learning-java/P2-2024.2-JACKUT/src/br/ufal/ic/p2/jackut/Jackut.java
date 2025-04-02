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
 * Responsável pela lógica de negócio do sistema, gerenciando usuários e sessões.
 */
public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa de login -> Usuario
    private Map<String, Usuario> usuarios;

    // Mapa de sessaoId -> login (não persistimos sessões, pois está transient)
    private transient Map<String, String> sessoes;

    // Arquivo de persistência
    private static final String ARQ_PERSISTENCIA = "jackut.dat";

    public Jackut() {
        // Tenta carregar os dados do arquivo, se existir
        carregarDadosDoArquivo();
        if (this.usuarios == null) {
            this.usuarios = new HashMap<>();
        }
        // Sessões sempre iniciam vazias a cada execução
        this.sessoes = new HashMap<>();
    }

    // -------------------------------------------------
    //  Métodos principais (User Stories 1 a 4)
    // -------------------------------------------------

    /**
     * Zera o sistema em memória (usado em testes).
     */
    public void zerarSistema() {
        usuarios.clear();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    /**
     * Cria um usuário, verificando regras de login e senha.
     * User Story 1
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
     * Abre uma sessão, retornando um ID.
     * Se login ou senha forem vazios, ou se não baterem, lança "Login ou senha inválidos."
     * User Story 1
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() ||
                senha == null || senha.trim().isEmpty()) {
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
     * Lança "Usuário não cadastrado." se não existe login.
     * User Story 1, 2
     */
    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        // Se o atributo não existir, o próprio usuario.getAtributo lança AtributoNaoPreenchidoException
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita (ou cria) um atributo do perfil, se a sessão pertencer ao usuário.
     * Lança "Usuário não cadastrado." se a sessãoId for inválida.
     * User Story 2
     */
    public void editarPerfil(String sessaoId, String atributo, String valor) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            // O script espera "Usuário não cadastrado." se a sessão é inválida
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        usuario.editarAtributo(atributo, valor);
    }

    /**
     * Adiciona um amigo (envia convite ou, se já houver convite inverso, confirma amizade).
     * Lança erros conforme user story 3.
     */
    public void adicionarAmigo(String sessaoId, String amigo) {
        String loginSolicitante = sessoes.get(sessaoId);
        if (loginSolicitante == null) {
            // Se a sessão é inválida, o script espera "Usuário não cadastrado."
            throw new UsuarioNaoCadastradoException();
        }
        if (loginSolicitante.equals(amigo)) {
            // "Usuário não pode adicionar a si mesmo como amigo."
            throw new UsuarioNaoPodeAdicionarASiMesmoException();
        }
        Usuario solicitante = usuarios.get(loginSolicitante);
        Usuario usuarioAlvo = usuarios.get(amigo);
        if (usuarioAlvo == null) {
            throw new UsuarioNaoCadastradoException();
        }
        // 1) Se já são amigos, "Usuário já está adicionado como amigo."
        if (solicitante.ehAmigo(amigo)) {
            throw new UsuarioJaEstaAdicionadoException();
        }
        // 2) Se o alvo tem convite de mim,
        //    "Usuário já está adicionado como amigo, esperando aceitação do convite."
        if (usuarioAlvo.temConvite(loginSolicitante)) {
            throw new UsuarioJaEstaAdicionadoEsperandoException();
        }
        // 3) Se eu tenho convite do alvo, então confirmamos amizade
        if (solicitante.temConvite(amigo)) {
            solicitante.removerConvite(amigo);
            solicitante.confirmarAmizade(amigo);
            usuarioAlvo.confirmarAmizade(loginSolicitante);
            return;
        }
        // 4) Caso contrário, envio convite
        usuarioAlvo.adicionarConvite(loginSolicitante);
    }

    /**
     * Retorna true se login e amigo são amigos.
     * Lança "Usuário não cadastrado." se login não existe.
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.ehAmigo(amigo);
    }

    /**
     * Retorna os amigos do usuário em formato {amigo1,amigo2}.
     * Lança "Usuário não cadastrado." se login não existe.
     */
    public String getAmigos(String login) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
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
     * Envia um recado de quem está na sessão para 'destinatario'.
     * Lança "Sessão inválida." ou "Usuário não cadastrado." ou "Usuário não pode enviar recado para si mesmo."
     */
    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            // Pelos testes, "Sessão inválida." ou "Usuário não cadastrado."?
            // O código original usava "Sessão inválida."
            // Mas os scripts de teste usam "Usuário não cadastrado." quando a sessão é inválida.
            throw new UsuarioNaoCadastradoException();
        }
        if (login.equals(destinatario)) {
            // "Usuário não pode enviar recado para si mesmo."
            throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        }
        Usuario userDest = usuarios.get(destinatario);
        if (userDest == null) {
            throw new UsuarioNaoCadastradoException();
        }
        userDest.receberRecado(recado);
    }

    /**
     * Lê o primeiro recado do usuário (dono da sessão).
     * Lança "Sessão inválida." ou "Usuário não cadastrado." ou "Não há recados."
     */
    public String lerRecado(String sessaoId) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            // De novo, substituímos por "Usuário não cadastrado." para bater com o script.
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        String recado = usuario.lerRecado();
        if (recado == null) {
            throw new NaoHaRecadosException();
        }
        return recado;
    }

    /**
     * Encerra o sistema, salvando os dados em arquivo e limpando as sessões.
     */
    public void encerrarSistema() {
        salvarDadosNoArquivo();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    // -------------------------------------------------
    //  Métodos privados de persistência
    // -------------------------------------------------

    /**
     * Carrega dados do arquivo (se existir).
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
     * Salva dados no arquivo `jackut.dat`.
     */
    private void salvarDadosNoArquivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQ_PERSISTENCIA))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
