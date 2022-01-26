package com.danmarche.lifeguard.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.danmarche.lifeguard.modelo.Tarefa;
import com.danmarche.lifeguard.persistencia.LifeGuardBDSQLite;

import java.sql.Array;
import java.util.ArrayList;

public class TarefaDAO {
    private SQLiteDatabase banco;
    private LifeGuardBDSQLite lifeGuardBDSQLite;

    private Context context;

    public TarefaDAO(Context context) {
        this.context = context;
        this.lifeGuardBDSQLite = new LifeGuardBDSQLite(context);
        abrirConexao();
    }

    public long inserir(Tarefa tarefa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_usuario", tarefa.getIdUsuario());
        contentValues.put("data", tarefa.getData());
        contentValues.put("nome", tarefa.getNome());
        contentValues.put("descricao", tarefa.getDescricao());

        long id = this.banco.insert("tarefa", null, contentValues);

        tarefa.setId(id);

        return id;
    }

    public Tarefa buscar(long id) {
        Tarefa resultado = null;

        String atributos[] = {"id", "nome", "descricao", "data", "id_usuario"};
        Cursor tuplas = banco.query("tarefa", atributos, "id=" + id, null,
                null, null, null);
        tuplas.moveToFirst();
        if (!tuplas.isNull(0)) {
            resultado = new Tarefa(tuplas.getLong(0), tuplas.getString(1),
                    tuplas.getString(2), tuplas.getLong(3), tuplas.getLong(4));
        }
        return resultado;
    }

    public ArrayList<Tarefa> getAll() {
        ArrayList<Tarefa> lista = new ArrayList<>();
        String atributos[] = {"id", "nome", "descricao", "data", "id_usuario"};
        Cursor tuplas = banco.query("tarefa", atributos, null, null,
                null, null, null);

        while (tuplas.moveToNext()) {
            String nome = tuplas.getString(1);
            String descricao = tuplas.getString(2);
            Long data = tuplas.getLong(3);
            Long usuario_id = tuplas.getLong(4);
            Tarefa tarefa = new Tarefa(nome, descricao, data, usuario_id);
            tarefa.setId(tuplas.getLong(0));

            lista.add(tarefa);
        }
        return lista;
    }

    public void deletarTarefa(long id) {
        banco.delete("tarefa", "id=" + id, null);
    }

    private void abrirConexao() {
        this.banco = this.lifeGuardBDSQLite.getWritableDatabase();
    }

    private void fecharConexao() {
        this.banco.close();
    }
}
