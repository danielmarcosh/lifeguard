package com.danmarche.lifeguard.modelo;

import java.util.Date;

public class Tarefa {
    private long id;
    private String nome;
    private String descricao;
    private Long data;
    private long id_usuario;

    public Tarefa(String name, String descricao, Long data, long usuario_id) {
        this.nome = name;
        this.descricao = descricao;
        this.data = data;
        this.id_usuario = usuario_id;
    }

    public Tarefa(long id, String name, String descricao, Long data, long usuario_id) {
        this.id = id;
        this.nome = name;
        this.descricao = descricao;
        this.data = data;
        this.id_usuario = usuario_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public long getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(long usuario_id) {
        this.id_usuario = usuario_id;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                ", id_usuario=" + id_usuario +
                '}';
    }
}
