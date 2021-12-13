package com.danmarche.lifeguard.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LifeGuardBDSQLite extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "lifeguard";
    private static final String TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS usuario (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT UNIQUE, senha TEXT)";
    private static final String TABELA_TAREFA = "CREATE TABLE IF NOT EXISTS tarefa (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, data BIGINT, " +
            "id_usuario INTEGER, FOREIGN KEY(id_usuario) REFERENCES usuario(id))";

    public LifeGuardBDSQLite(@Nullable Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABELA_TAREFA);
        sqLiteDatabase.execSQL(TABELA_USUARIO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
