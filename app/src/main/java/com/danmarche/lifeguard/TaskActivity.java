package com.danmarche.lifeguard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.danmarche.lifeguard.dao.TarefaDAO;
import com.danmarche.lifeguard.modelo.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class TaskActivity extends AppCompatActivity {
    private FloatingActionButton cadastrar_tarefaBtn;
    private ArrayList<Tarefa> listaDeTarefas;

    private LinearLayout listView;
    private LayoutInflater inflater;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.id = extras.getInt("id");
            Log.d("Usuario ID: ", String.valueOf(extras));
        }

        cadastrar_tarefaBtn = findViewById(R.id.btn_cadastrar_tarefa);
        listView = findViewById(R.id.lista_tarefas);

        initialize();

        carregarListaTarefas();
    }

    private void initialize() {
        inflater = (LayoutInflater) TaskActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        cadastrar_tarefaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_cadastrar = new Intent(TaskActivity.this, RegisterTaskActivity.class);
                Log.d("INDO PARA TELA DE CADASTRO DE TAREFAS. ID ", String.valueOf(id));
                janela_cadastrar.putExtra("id", id);
                startActivity(janela_cadastrar);
            }
        });
    }

    private void carregarListaTarefas() {
        TarefaDAO tarefaDAO = new TarefaDAO(TaskActivity.this);
        listaDeTarefas = tarefaDAO.getAll();
        Collections.sort(listaDeTarefas);

        carregarListaUI();
    }

    private void carregarListaUI() {
        for (Tarefa t : listaDeTarefas) {
            View itemView = inflater.inflate(R.layout.item_tarefa, null);
            ((TextView) itemView.findViewById(R.id.item_nome)).setText(t.getNome());
            Date data = new Date(t.getData());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            ((TextView) itemView.findViewById(R.id.item_data)).setText(df.format(data));
            ((TextView) itemView.findViewById(R.id.item_descricao)).setText(t.getDescricao());
            ((Button) itemView.findViewById(R.id.item_excluir)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    excluirTarefa(t.getId());
                }
            });

            listView.addView(itemView);
        }

    }

    private void excluirTarefa(long id) {
        DialogInterface.OnClickListener caixaDeDialago = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    TarefaDAO tarefaDAO = new TarefaDAO(TaskActivity.this);
                    tarefaDAO.deletarTarefa(id);
                    carregarListaTarefas();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        builder.setMessage("Você deseja excluir esta Tarefa?")
                .setPositiveButton("Sim", caixaDeDialago)
                .setNegativeButton("Não", caixaDeDialago).show();
    }
}