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
 * Respons�vel pela l�gica de neg�cio do sistema, gerenciando usu�rios e sess�es.
 */
public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa de login -> Usuario
    private Map<String, Usuario> usuarios;

    // Mapa de sessaoId -> login (n�o persistimos sess�es, pois est� transient)
    private transient Map<String, String> sessoes;

    // Arquivo de persist�ncia
    private static final String ARQ_PERSISTENCIA = "jackut.dat";

    public Jackut() {
        // Tenta carregar os dados do arquivo, se existir
        carregarDadosDoArquivo();
        if (this.usuarios == null) {
            this.usuarios = new HashMap<>();
        }
        // Sess�es sempre iniciam vazias a cada execu��o
        this.sessoes = new HashMap<>();
    }

    // -------------------------------------------------
    //  M�todos principais (User Stories 1 a 4)
    // -------------------------------------------------

    /**
     * Zera o sistema em mem�ria (usado em testes).
     */
    public void zerarSistema() {
        usuarios.clear();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    /**
     * Cria um usu�rio, verificando regras de login e senha.
     * User Story 1
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException(); // "Login inv�lido."
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException(); // "Senha inv�lida."
        }
        if (usuarios.containsKey(login)) {
            throw new ContaComEsseNomeJaExisteException(); // "Conta com esse nome j� existe."
        }
        if (nome == null) {
            nome = "";
        }
        Usuario novo = new Usuario(login, senha, nome);
        usuarios.put(login, novo);
    }

    /**
     * Abre uma sess�o, retornando um ID.
     * Se login ou senha forem vazios, ou se n�o baterem, lan�a "Login ou senha inv�lidos."
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
     * Retorna o valor de um atributo do usu�rio especificado.
     * Lan�a "Usu�rio n�o cadastrado." se n�o existe login.
     * User Story 1, 2
     */
    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        // Se o atributo n�o existir, o pr�prio usuario.getAtributo lan�a AtributoNaoPreenchidoException
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita (ou cria) um atributo do perfil, se a sess�o pertencer ao usu�rio.
     * Lan�a "Usu�rio n�o cadastrado." se a sess�oId for inv�lida.
     * User Story 2
     */
    public void editarPerfil(String sessaoId, String atributo, String valor) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            // O script espera "Usu�rio n�o cadastrado." se a sess�o � inv�lida
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        usuario.editarAtributo(atributo, valor);
    }

    /**
     * Adiciona um amigo (envia convite ou, se j� houver convite inverso, confirma amizade).
     * Lan�a erros conforme user story 3.
     */
    public void adicionarAmigo(String sessaoId, String amigo) {
        String loginSolicitante = sessoes.get(sessaoId);
        if (loginSolicitante == null) {
            // Se a sess�o � inv�lida, o script espera "Usu�rio n�o cadastrado."
            throw new UsuarioNaoCadastradoException();
        }
        if (loginSolicitante.equals(amigo)) {
            // "Usu�rio n�o pode adicionar a si mesmo como amigo."
            throw new UsuarioNaoPodeAdicionarASiMesmoException();
        }
        Usuario solicitante = usuarios.get(loginSolicitante);
        Usuario usuarioAlvo = usuarios.get(amigo);
        if (usuarioAlvo == null) {
            throw new UsuarioNaoCadastradoException();
        }
        // 1) Se j� s�o amigos, "Usu�rio j� est� adicionado como amigo."
        if (solicitante.ehAmigo(amigo)) {
            throw new UsuarioJaEstaAdicionadoException();
        }
        // 2) Se o alvo tem convite de mim,
        //    "Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite."
        if (usuarioAlvo.temConvite(loginSolicitante)) {
            throw new UsuarioJaEstaAdicionadoEsperandoException();
        }
        // 3) Se eu tenho convite do alvo, ent�o confirmamos amizade
        if (solicitante.temConvite(amigo)) {
            solicitante.removerConvite(amigo);
            solicitante.confirmarAmizade(amigo);
            usuarioAlvo.confirmarAmizade(loginSolicitante);
            return;
        }
        // 4) Caso contr�rio, envio convite
        usuarioAlvo.adicionarConvite(loginSolicitante);
    }

    /**
     * Retorna true se login e amigo s�o amigos.
     * Lan�a "Usu�rio n�o cadastrado." se login n�o existe.
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.ehAmigo(amigo);
    }

    /**
     * Retorna os amigos do usu�rio em formato {amigo1,amigo2}.
     * Lan�a "Usu�rio n�o cadastrado." se login n�o existe.
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
     * Envia um recado de quem est� na sess�o para 'destinatario'.
     * Lan�a "Sess�o inv�lida." ou "Usu�rio n�o cadastrado." ou "Usu�rio n�o pode enviar recado para si mesmo."
     */
    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            // Pelos testes, "Sess�o inv�lida." ou "Usu�rio n�o cadastrado."?
            // O c�digo original usava "Sess�o inv�lida."
            // Mas os scripts de teste usam "Usu�rio n�o cadastrado." quando a sess�o � inv�lida.
            throw new UsuarioNaoCadastradoException();
        }
        if (login.equals(destinatario)) {
            // "Usu�rio n�o pode enviar recado para si mesmo."
            throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        }
        Usuario userDest = usuarios.get(destinatario);
        if (userDest == null) {
            throw new UsuarioNaoCadastradoException();
        }
        userDest.receberRecado(recado);
    }

    /**
     * L� o primeiro recado do usu�rio (dono da sess�o).
     * Lan�a "Sess�o inv�lida." ou "Usu�rio n�o cadastrado." ou "N�o h� recados."
     */
    public String lerRecado(String sessaoId) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            // De novo, substitu�mos por "Usu�rio n�o cadastrado." para bater com o script.
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
     * Encerra o sistema, salvando os dados em arquivo e limpando as sess�es.
     */
    public void encerrarSistema() {
        salvarDadosNoArquivo();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    // -------------------------------------------------
    //  M�todos privados de persist�ncia
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
