package com.danmarche.lifeguard.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.danmarche.lifeguard.modelo.Usuario;
import com.danmarche.lifeguard.persistencia.LifeGuardBDSQLite;

public class UsuarioDAO {
    private SQLiteDatabase banco;
    private LifeGuardBDSQLite lifeGuardBDSQLite;

    private Context context;

    public UsuarioDAO(Context context) {
        this.context = context;

        this.lifeGuardBDSQLite = new LifeGuardBDSQLite(context);
        abrirConexao();
    }

    public long inserir(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", usuario.getNome());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("senha", usuario.getSenha());

        long id = this.banco.insert("usuario", null, contentValues);

        usuario.setId(id);
        fecharConexao();

        return id;
    }

    public boolean autenticar(String email, String senha) {
        boolean resultado = false;
        Cursor tulpas = this.banco.rawQuery("SELECT email, senha FROM usuario WHERE email = ? AND senha = ?", new String[]{email, senha});

        if (tulpas.moveToFirst()) {
            resultado = true;

        }
        Log.d("Resultado: ", String.valueOf(resultado));
        tulpas.close();
        fecharConexao();
        return resultado;
    }


    private void abrirConexao() {
        this.banco = this.lifeGuardBDSQLite.getWritableDatabase();
    }

    private void fecharConexao() {
        this.banco.close();
    }
}
