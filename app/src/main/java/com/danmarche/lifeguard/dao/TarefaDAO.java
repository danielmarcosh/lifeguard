package com.danmarche.lifeguard.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.danmarche.lifeguard.modelo.Tarefa;
import com.danmarche.lifeguard.persistencia.LifeGuardBDSQLite;

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
        // id_usuario,data,nome,descricao

        long id = this.banco.insert("tarefa", null, contentValues);

        tarefa.setId(id);
        fecharConexao();

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

    private void abrirConexao() {
        this.banco = this.lifeGuardBDSQLite.getWritableDatabase();
    }

    private void fecharConexao() {
        this.banco.close();
    }
}
