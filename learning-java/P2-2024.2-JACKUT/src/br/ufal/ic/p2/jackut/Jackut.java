package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.*;
import java.util.*;

public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String,Usuario> usuarios       = new HashMap<>();
    private Map<String,Comunidade> comunidades = new HashMap<>();
    private transient Map<String,String> sessoes = new HashMap<>();
    private static final String ARQ = "jackut.dat";

    public Jackut() {
        try { carregarDados(); }
        catch(Exception e) { reset(); }
    }

    private void reset() {
        usuarios.clear();
        comunidades.clear();
        sessoes.clear();
    }

    public void zerarSistema() { reset(); }

    // ?? Usuários ??
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
        if (usuarios.containsKey(login)) throw new ContaComEsseNomeJaExisteException();
        usuarios.put(login, new Usuario(login, senha, nome == null ? "" : nome));
    }

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

    public String getAtributoUsuario(String login, String atr) {
        Usuario u = usuarios.get(login);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.getAtributo(atr);
    }

    public void editarPerfil(String sessao, String atr, String val) {
        String lg = sessoes.get(sessao);
        if (lg == null) throw new UsuarioNaoCadastradoException();
        Usuario u = usuarios.get(lg);
        if (u == null) throw new UsuarioNaoCadastradoException();
        u.editarAtributo(atr, val);
    }

    // ?? Amigos ??
    public void adicionarAmigo(String sessao, String amigo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uAlvo = usuarios.get(amigo);
        if (uAlvo == null) throw new UsuarioNaoCadastradoException();

        // bloqueio por inimizade (se o alvo considera ?sol? inimigo)
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

    public boolean ehAmigo(String l, String a) {
        Usuario u = usuarios.get(l);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.ehAmigo(a);
    }

    public String getAmigos(String l) {
        Usuario u = usuarios.get(l);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return "{" + String.join(",", u.getAmigos()) + "}";
    }

    // ?? Recados ??
    public void enviarRecado(String sessao, String dest, String msg) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uDest = usuarios.get(dest);
        if (uDest == null) throw new UsuarioNaoCadastradoException();

        // bloqueio por inimizade
        if (uDest.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uDest.getNome() + " é seu inimigo.");
        }
        if (sol.equals(dest)) throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        uDest.receberRecado(msg);
    }

    public String lerRecado(String sessao) {
        String lg = validar(sessao);
        Usuario u = usuarios.get(lg);
        String m = u.lerRecado();
        if (m == null) throw new NaoHaRecadosException();
        return m;
    }

    // ?? Persistência ??
    public void encerrarSistema() {
        salvarDados();
        sessoes.clear();
    }

    // ?? Comunidades ??
    public void criarComunidade(String sessao, String nome, String desc) {
        String lg = validar(sessao);
        if (comunidades.containsKey(nome)) throw new ComunidadeJaExisteException();
        comunidades.put(nome, new Comunidade(nome, desc, lg));
        usuarios.get(lg).adicionarComunidadeParticipa(nome);
    }

    public String getDescricaoComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return c.getDescricao();
    }

    public String getDonoComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return c.getDono();
    }

    public String getMembrosComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return "{" + String.join(",", c.getMembros()) + "}";
    }

    public void adicionarComunidade(String sessao, String nome) {
        String lg = validar(sessao);
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        if (c.getMembros().contains(lg)) throw new UsuarioJaEstaNaComunidadeException();
        c.adicionarMembro(lg);
        usuarios.get(lg).adicionarComunidadeParticipa(nome);
    }

    // ?? getComunidades unificado (sessionId ou login) ??
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

    // ?? Mensagens em comunidade ??
    public void enviarMensagem(String sessao, String com, String msg) {
        String lg = validar(sessao);
        Comunidade c = comunidades.get(com);
        if (c == null) throw new ComunidadeNaoExisteException();
        for (String mb : c.getMembros()) {
            Usuario u = usuarios.get(mb);
            if (!u.ehInimigo(lg)) u.receberMensagem(msg);
        }
    }

    public String lerMensagem(String sessao) {
        String lg = validar(sessao);
        Usuario u = usuarios.get(lg);
        String m  = u.lerMensagem();
        if (m == null) throw new NaoHaMensagensException();
        return m;
    }

    // ?? Fãs/ídolos ??
    public void adicionarIdolo(String sessao, String idolo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uId  = usuarios.get(idolo);
        if (uId == null) throw new UsuarioNaoCadastradoException();

        // bloqueio por inimizade
        if (uId.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uId.getNome() + " é seu inimigo.");
        }
        if (sol.equals(idolo)) throw new UsuarioNaoPodeSerFaDeSiMesmoException();
        if (uSol.ehIdolo(idolo)) throw new UsuarioJaEstaIdoloException();
        uSol.adicionarIdolo(idolo);
    }

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

    public Set<String> getFas(String login) {
        if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        Set<String> fas = new LinkedHashSet<>();
        for (Usuario u : usuarios.values()) {
            if (u.ehIdolo(login)) fas.add(u.getLogin());
        }
        return fas;
    }

    // ?? Paqueras ??
    public void adicionarPaquera(String sessao, String p) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario up   = usuarios.get(p);
        if (up == null) throw new UsuarioNaoCadastradoException();

        // bloqueio por inimizade
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

    // ?? Inimizades ??
    public void adicionarInimigo(String sessao, String inimigo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario ui   = usuarios.get(inimigo);
        if (ui == null) throw new UsuarioNaoCadastradoException();
        if (sol.equals(inimigo)) throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();
        if (uSol.ehInimigo(inimigo)) throw new UsuarioJaEstaInimigoException();
        uSol.adicionarInimigo(inimigo);
    }

    // ?? Remoção de conta ??
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

    private String validar(String sessao) {
        String lg = sessoes.get(sessao);
        if (lg == null) throw new UsuarioNaoCadastradoException();
        return lg;
    }

    private void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            out.writeObject(this);
        } catch(Exception e) { e.printStackTrace(); }
    }

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
