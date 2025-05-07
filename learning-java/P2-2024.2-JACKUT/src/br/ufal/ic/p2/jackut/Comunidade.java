package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Comunidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    private String dono;
    private Set<String> membros = new LinkedHashSet<>();

    public Comunidade(String nome, String descricao, String dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros.add(dono);
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDono() { return dono; }
    public Set<String> getMembros() { return membros; }

    public void adicionarMembro(String login) {
        membros.add(login);
    }
}
