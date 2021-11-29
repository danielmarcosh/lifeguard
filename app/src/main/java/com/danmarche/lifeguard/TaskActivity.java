package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaskActivity extends AppCompatActivity {

    private Button voltar_homeBtn;
    private Button cadastrar_tarefaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        voltar_homeBtn = findViewById(R.id.btn_voltar_home);
        cadastrar_tarefaBtn = findViewById(R.id.btn_cadastrar_tarefa);

        initialize();
    }

    private void initialize() {
        voltar_homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_home = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(janela_home);
            }
        });
        cadastrar_tarefaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_cadastrar = new Intent(TaskActivity.this, RegisterTaskActivity.class);
                startActivity(janela_cadastrar);
            }
        });
    }
}