package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.danmarche.lifeguard.dao.TarefaDAO;
import com.danmarche.lifeguard.dao.UsuarioDAO;
import com.danmarche.lifeguard.modelo.Tarefa;

import java.util.Calendar;
import java.util.Date;

public class RegisterTaskActivity extends AppCompatActivity {

    private Button voltar_tarefaBtn;
    private Button adicionar_tarefaBtn;
    private EditText nome_tarefaEditText;
    private EditText descricao_tarefaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_task);

        voltar_tarefaBtn = findViewById(R.id.btn_voltar_registrar);
        adicionar_tarefaBtn = findViewById(R.id.btn_add_tarefa);
        nome_tarefaEditText = findViewById(R.id.cadastro_nome_tarefa);
        descricao_tarefaEditText = findViewById(R.id.cadastro_descricao_tarefa);

        initialize();
    }

    private void initialize() {
        voltar_tarefaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_tarefas = new Intent(RegisterTaskActivity.this, TaskActivity.class);
                startActivity(janela_tarefas);
            }
        });

        adicionar_tarefaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarTarefa();
            }
        });

    }


    private void cadastrarTarefa() {
        InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);

        if (nome_tarefaEditText.getText().length() == 0) {
            nome_tarefaEditText.setError("O campo Nome não pode ser vazio");
        } else if (descricao_tarefaEditText.getText().length() == 0) {
            descricao_tarefaEditText.setError("O campo Descricao não pode ser vazio");
        } else {
            String nome = String.valueOf(nome_tarefaEditText.getText());
            String descricao = String.valueOf(descricao_tarefaEditText.getText());

            Date dataAtual = new Date();
//                    String myFormat = "yyyy-MM-dd";
//                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
//                    String dataFormatada = sdf.format(dataAtual);
            long idUser = getIdUsuario();
            Tarefa tarefa = new Tarefa(nome, descricao, dataAtual.getTime(), idUser);
            Log.d("Tarefa ", tarefa.toString());
            salvarTarefa(tarefa);

            Intent janela_tarefas = new Intent(RegisterTaskActivity.this, TaskActivity.class);
            startActivity(janela_tarefas);
        }

    }

    private long getIdUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(RegisterTaskActivity.this);
        return usuarioDAO.getUser();
    }

    private void salvarTarefa(Tarefa tarefa) {
        TarefaDAO tarefaDAO = new TarefaDAO(RegisterTaskActivity.this);
        tarefaDAO.inserir(tarefa);

    }
}