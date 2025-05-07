package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut, que representa a rede social com funcionalidades de usuários, amizades, comunidades, recados, mensagens, fãs/ídolos, e mais.
 */
public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Usuario> usuarios       = new HashMap<>();
    private Map<String, Comunidade> comunidades = new HashMap<>();
    private transient Map<String, String> sessoes = new HashMap<>();
    private static final String ARQ = "jackut.dat";

    /**
     * Construtor da classe Jackut, que tenta carregar os dados previamente salvos.
     */
    public Jackut() {
        try { carregarDados(); }
        catch(Exception e) { reset(); }
    }

    /**
     * Reseta os dados do sistema, limpando os usuários, comunidades e sessões.
     */
    private void reset() {
        usuarios.clear();
        comunidades.clear();
        sessoes.clear();
    }

    /**
     * Zera o sistema, removendo todos os dados.
     */
    public void zerarSistema() { reset(); }

    // ** Usuários **

    /**
     * Cria um novo usuário com login, senha e nome.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome O nome do usuário.
     * @throws LoginInvalidoException Se o login for inválido.
     * @throws SenhaInvalidaException Se a senha for inválida.
     * @throws ContaComEsseNomeJaExisteException Se já existir um usuário com o mesmo login.
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
        if (usuarios.containsKey(login)) throw new ContaComEsseNomeJaExisteException();
        usuarios.put(login, new Usuario(login, senha, nome == null ? "" : nome));
    }

    /**
     * Abre uma sessão para um usuário com login e senha.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O ID da sessão gerada.
     * @throws LoginOuSenhaInvalidosException Se o login ou a senha estiverem incorretos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() ||
                senha  == null || senha.trim().isEmpty())
            throw new LoginOuSenhaInvalidosException();
        Usuario u = usuarios.get(login);
        if (u == null || !u.verificarSenha(senha))
            throw new LoginOuSenhaInvalidosException();
        String sid = UUID.randomUUID().toString();
        sessoes.put(sid, login);
        return sid;
    }

    /**
     * Retorna um atributo de um usuário, baseado no login e no nome do atributo.
     *
     * @param login O login do usuário.
     * @param atr O nome do atributo a ser recuperado.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String getAtributoUsuario(String login, String atr) {
        Usuario u = usuarios.get(login);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.getAtributo(atr);
    }

    /**
     * Edita o perfil de um usuário.
     *
     * @param sessao A sessão do usuário.
     * @param atr O atributo a ser editado.
     * @param val O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public void editarPerfil(String sessao, String atr, String val) {
        String lg = sessoes.get(sessao);
        if (lg == null) throw new UsuarioNaoCadastradoException();
        Usuario u = usuarios.get(lg);
        if (u == null) throw new UsuarioNaoCadastradoException();
        u.editarAtributo(atr, val);
    }

    // ** Amigos **

    /**
     * Adiciona um amigo a um usuário.
     *
     * @param sessao A sessão do usuário.
     * @param amigo O login do amigo a ser adicionado.
     * @throws FuncaoInvalidaException Se o amigo for inimigo.
     * @throws UsuarioNaoPodeAdicionarASiMesmoException Se o usuário tentar adicionar a si mesmo.
     * @throws UsuarioJaEstaAdicionadoException Se o amigo já for adicionado.
     * @throws UsuarioJaEstaAdicionadoEsperandoException Se o amigo já tiver um convite pendente.
     */
    public void adicionarAmigo(String sessao, String amigo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uAlvo = usuarios.get(amigo);
        if (uAlvo == null) throw new UsuarioNaoCadastradoException();

        if (uAlvo.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uAlvo.getNome() + " é seu inimigo.");
        }
        if (sol.equals(amigo)) throw new UsuarioNaoPodeAdicionarASiMesmoException();
        if (uSol.ehAmigo(amigo)) throw new UsuarioJaEstaAdicionadoException();
        if (uAlvo.temConvite(sol))
            throw new UsuarioJaEstaAdicionadoEsperandoException();
        if (uSol.temConvite(amigo)) {
            uSol.removerConvite(amigo);
            uSol.confirmarAmizade(amigo);
            uAlvo.confirmarAmizade(sol);
        } else {
            uAlvo.adicionarConvite(sol);
        }
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param l O login do usuário.
     * @param a O login do amigo a ser verificado.
     * @return True se os usuários forem amigos, caso contrário, false.
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não estiver cadastrado.
     */
    public boolean ehAmigo(String l, String a) {
        Usuario u = usuarios.get(l);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.ehAmigo(a);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     *
     * @param l O login do usuário.
     * @return Uma string com os logins dos amigos.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String getAmigos(String l) {
        Usuario u = usuarios.get(l);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return "{" + String.join(",", u.getAmigos()) + "}";
    }

    // ** Recados **

    /**
     * Envia um recado para outro usuário.
     *
     * @param sessao A sessão do usuário remetente.
     * @param dest O login do destinatário.
     * @param msg A mensagem a ser enviada.
     * @throws FuncaoInvalidaException Se o destinatário for inimigo.
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se o usuário tentar enviar um recado para si mesmo.
     * @throws UsuarioNaoCadastradoException Se o destinatário não estiver cadastrado.
     */
    public void enviarRecado(String sessao, String dest, String msg) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uDest = usuarios.get(dest);
        if (uDest == null) throw new UsuarioNaoCadastradoException();

        if (uDest.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uDest.getNome() + " é seu inimigo.");
        }
        if (sol.equals(dest)) throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        uDest.receberRecado(msg);
    }

    /**
     * Lê o recado de um usuário.
     *
     * @param sessao A sessão do usuário.
     * @return O conteúdo do recado.
     * @throws NaoHaRecadosException Se não houver recados.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String lerRecado(String sessao) {
        String lg = validar(sessao);
        Usuario u = usuarios.get(lg);
        String m = u.lerRecado();
        if (m == null) throw new NaoHaRecadosException();
        return m;
    }

    // ** Persistência **

    /**
     * Encerra o sistema, salvando os dados e limpando as sessões.
     */
    public void encerrarSistema() {
        salvarDados();
        sessoes.clear();
    }

    // ** Comunidades **

    /**
     * Cria uma nova comunidade.
     *
     * @param sessao A sessão do usuário que está criando a comunidade.
     * @param nome O nome da comunidade.
     * @param desc A descrição da comunidade.
     * @throws ComunidadeJaExisteException Se a comunidade já existir.
     */
    public void criarComunidade(String sessao, String nome, String desc) {
        String lg = validar(sessao);
        if (comunidades.containsKey(nome)) throw new ComunidadeJaExisteException();
        comunidades.put(nome, new Comunidade(nome, desc, lg));
        usuarios.get(lg).adicionarComunidadeParticipa(nome);
    }

    /**
     * Retorna as comunidades nas quais o usuário está participando.
     *
     * Este método verifica se a chave fornecida corresponde a uma sessão ativa. Caso a chave seja uma sessão, o login
     * do usuário será validado a partir da sessão. Se a chave não for uma sessão, ela é tratada como o login diretamente.
     * O método lança uma exceção se o usuário não estiver cadastrado.
     *
     * @param chave A chave que pode ser o login do usuário ou uma sessão ativa.
     * @return Uma string representando as comunidades nas quais o usuário está participando, separadas por vírgulas e
     * envolvidas por chaves (ex: "{comunidade1,comunidade2}").
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado no sistema.
     */
    public String getComunidades(String chave) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        Usuario u = usuarios.get(login);
        return "{" + String.join(",", u.getComunidadesParticipando()) + "}";
    }


    /**
     * Retorna a descrição de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return A descrição da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getDescricaoComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return c.getDescricao();
    }

    /**
     * Retorna o dono de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return O login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getDonoComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return c.getDono();
    }

    /**
     * Retorna os membros de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return Uma string com os logins dos membros.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getMembrosComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return "{" + String.join(",", c.getMembros()) + "}";
    }

    /**
     * Adiciona um usuário a uma comunidade.
     *
     * @param sessao A sessão do usuário que deseja participar.
     * @param nome O nome da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     * @throws UsuarioJaEstaNaComunidadeException Se o usuário já for membro da comunidade.
     */
    public void adicionarComunidade(String sessao, String nome) {
        String lg = validar(sessao);
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        if (c.getMembros().contains(lg)) throw new UsuarioJaEstaNaComunidadeException();
        c.adicionarMembro(lg);
        usuarios.get(lg).adicionarComunidadeParticipa(nome);
    }

    // ** Mensagens em comunidade **

    /**
     * Envia uma mensagem para todos os membros de uma comunidade, exceto inimigos.
     *
     * @param sessao A sessão do usuário que está enviando a mensagem.
     * @param com O nome da comunidade.
     * @param msg A mensagem a ser enviada.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     * @throws FuncaoInvalidaException Se o usuário for inimigo de algum membro da comunidade.
     */
    public void enviarMensagem(String sessao, String com, String msg) {
        String lg = validar(sessao);
        Comunidade c = comunidades.get(com);
        if (c == null) throw new ComunidadeNaoExisteException();
        for (String mb : c.getMembros()) {
            Usuario u = usuarios.get(mb);
            if (!u.ehInimigo(lg)) u.receberMensagem(msg);
        }
    }

    /**
     * Lê a mensagem recebida por um usuário.
     *
     * @param sessao A sessão do usuário.
     * @return O conteúdo da mensagem.
     * @throws NaoHaMensagensException Se não houver mensagens.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String lerMensagem(String sessao) {
        String lg = validar(sessao);
        Usuario u = usuarios.get(lg);
        String m  = u.lerMensagem();
        if (m == null) throw new NaoHaMensagensException();
        return m;
    }

    // ** Fãs/ídolos **

    /**
     * Adiciona um ídolo a um usuário.
     *
     * @param sessao A sessão do usuário.
     * @param idolo O login do ídolo.
     * @throws FuncaoInvalidaException Se o ídolo for inimigo.
     * @throws UsuarioNaoPodeSerFaDeSiMesmoException Se o usuário tentar ser fã de si mesmo.
     * @throws UsuarioJaEstaIdoloException Se o usuário já for ídolo do outro.
     * @throws UsuarioNaoCadastradoException Se o ídolo não estiver cadastrado.
     */
    public void adicionarIdolo(String sessao, String idolo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uId  = usuarios.get(idolo);
        if (uId == null) throw new UsuarioNaoCadastradoException();

        if (uId.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uId.getNome() + " é seu inimigo.");
        }
        if (sol.equals(idolo)) throw new UsuarioNaoPodeSerFaDeSiMesmoException();
        if (uSol.ehIdolo(idolo)) throw new UsuarioJaEstaIdoloException();
        uSol.adicionarIdolo(idolo);
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param chave A chave de sessão ou o login do usuário.
     * @param idolo O login do ídolo.
     * @return True se o usuário for fã do ídolo, caso contrário, false.
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não estiver cadastrado.
     */
    public boolean ehFa(String chave, String idolo) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login).ehIdolo(idolo);
    }

    /**
     * Retorna a lista de fãs de um ídolo.
     *
     * @param login O login do ídolo.
     * @return O conjunto de fãs do ídolo.
     * @throws UsuarioNaoCadastradoException Se o ídolo não estiver cadastrado.
     */
    public Set<String> getFas(String login) {
        if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        Set<String> fas = new LinkedHashSet<>();
        for (Usuario u : usuarios.values()) {
            if (u.ehIdolo(login)) fas.add(u.getLogin());
        }
        return fas;
    }

    // ** Paqueras **

    /**
     * Adiciona uma paquera para o usuário.
     *
     * @param sessao A sessão do usuário.
     * @param p O login da pessoa a ser adicionada como paquera.
     * @throws FuncaoInvalidaException Se a pessoa for inimiga.
     * @throws UsuarioNaoPodeSerPaqueraDeSiMesmoException Se o usuário tentar ser paquera de si mesmo.
     * @throws UsuarioJaEstaPaqueraException Se já existe um relacionamento de paquera.
     * @throws UsuarioNaoCadastradoException Se a pessoa não estiver cadastrada.
     */
    public void adicionarPaquera(String sessao, String p) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario up   = usuarios.get(p);
        if (up == null) throw new UsuarioNaoCadastradoException();

        if (up.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(up.getNome() + " é seu inimigo.");
        }
        if (sol.equals(p)) throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();
        if (uSol.ehPaquera(p)) throw new UsuarioJaEstaPaqueraException();
        uSol.adicionarPaquera(p);
        if (up.ehPaquera(sol)) {
            uSol.receberRecado(up.getNome() + " é seu paquera - Recado do Jackut.");
            up.receberRecado(uSol.getNome() + " é seu paquera - Recado do Jackut.");
        }
    }

    /**
     * Verifica se um usuário é paquera de outro.
     *
     * @param chave A chave de sessão ou o login do usuário.
     * @param p O login da pessoa a ser verificada.
     * @return True se os usuários forem paqueras, caso contrário, false.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public boolean ehPaquera(String chave, String p) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login).ehPaquera(p);
    }

    /**
     * Retorna a lista de paqueras de um usuário.
     *
     * @param chave A chave de sessão ou o login do usuário.
     * @return O conjunto de paqueras do usuário.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public Set<String> getPaqueras(String chave) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login).getPaqueras();
    }

    // ** Inimizades **

    /**
     * Adiciona um inimigo a um usuário.
     *
     * @param sessao A sessão do usuário.
     * @param inimigo O login do inimigo.
     * @throws UsuarioNaoPodeSerInimigoDeSiMesmoException Se o usuário tentar ser inimigo de si mesmo.
     * @throws UsuarioNaoCadastradoException Se o inimigo não estiver cadastrado.
     * @throws UsuarioJaEstaInimigoException Se já existe uma inimigo.
     */
    public void adicionarInimigo(String sessao, String inimigo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario ui   = usuarios.get(inimigo);
        if (ui == null) throw new UsuarioNaoCadastradoException();
        if (sol.equals(inimigo)) throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();
        if (uSol.ehInimigo(inimigo)) throw new UsuarioJaEstaInimigoException();
        uSol.adicionarInimigo(inimigo);
    }

    // ** Remoção de conta **

    /**
     * Remove um usuário do sistema, excluindo-o juntamente com suas sessões e dados.
     *
     * @param sessao A sessão do usuário a ser removido.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public void removerUsuario(String sessao) {
        String lg = validar(sessao);

        // 1) remove usuário e suas sessões
        usuarios.remove(lg);
        sessoes.entrySet().removeIf(e -> e.getValue().equals(lg));

        // 2) remove comunidades de que era dono
        comunidades.entrySet().removeIf(en -> en.getValue().getDono().equals(lg));

        // 3) limpa membros e histórico de comunidadesParticipando
        Collection<String> existentes = comunidades.keySet();
        for (Usuario u : usuarios.values()) {
            u.getAmigos().remove(lg);
            u.getPaqueras().remove(lg);
            u.limparRecados();
            u.limparMensagens();
            u.limparComunidadesParticipando(existentes);
        }
    }

    /**
     * Valida a sessão de um usuário.
     *
     * @param sessao A sessão a ser validada.
     * @return O login do usuário.
     * @throws UsuarioNaoCadastradoException Se a sessão não for válida.
     */
    private String validar(String sessao) {
        String lg = sessoes.get(sessao);
        if (lg == null) throw new UsuarioNaoCadastradoException();
        return lg;
    }

    /**
     * Salva os dados do sistema no arquivo.
     */
    private void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            out.writeObject(this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * Carrega os dados do sistema a partir do arquivo.
     *
     * @throws Exception Se houver falha ao carregar os dados.
     */
    private void carregarDados() throws Exception {
        File f = new File(ARQ);
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            Jackut p = (Jackut) in.readObject();
            this.usuarios    = p.usuarios;
            this.comunidades = p.comunidades;
        }
    }

}
