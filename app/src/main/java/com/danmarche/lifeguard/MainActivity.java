package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button ir_tarefasBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ir_tarefasBtn = findViewById(R.id.btn_tarefas);

        initialize();
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
}