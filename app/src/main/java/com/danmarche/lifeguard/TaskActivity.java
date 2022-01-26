package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskActivity extends AppCompatActivity {
    private FloatingActionButton cadastrar_tarefaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        cadastrar_tarefaBtn = findViewById(R.id.btn_cadastrar_tarefa);

        initialize();
    }

    private void initialize() {
        cadastrar_tarefaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_cadastrar = new Intent(TaskActivity.this, RegisterTaskActivity.class);
                startActivity(janela_cadastrar);
            }
        });
    }
}