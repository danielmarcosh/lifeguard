package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.danmarche.lifeguard.dao.TarefaDAO;
import com.danmarche.lifeguard.dao.UsuarioDAO;
import com.danmarche.lifeguard.modelo.Tarefa;
import com.danmarche.lifeguard.modelo.Usuario;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button ir_tarefasBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ir_tarefasBtn = findViewById(R.id.btn_tarefas);

        initialize();

        testeInserir();

    }

    private void initialize() {
        ir_tarefasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_tarefas = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(janela_tarefas);
            }
        });
    }

    private void testeInserir() {
        //Usuario usuario = new Usuario("Daniel", "daniel@email.com", "123");
        //UsuarioDAO usuarioDAO = new UsuarioDAO(MainActivity.this);

        //long id = usuarioDAO.inserir(usuario);


        // Log.d("Usuario: ", usuario.toString());

        Tarefa tarefa = new Tarefa("Trabalho 1", "A fazer", new Date().getTime(), 1);
        TarefaDAO tarefaDAO = new TarefaDAO(MainActivity.this);
        tarefaDAO.inserir(tarefa);


        Log.d("Tarefa: ", tarefa.toString());
    }
}