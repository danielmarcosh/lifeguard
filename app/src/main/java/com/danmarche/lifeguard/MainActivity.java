package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danmarche.lifeguard.dao.UsuarioDAO;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button cadastroBtn;
    private EditText emailEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.btn_login);
        cadastroBtn = findViewById(R.id.btn_ir_cadastro_usuario);
        emailEditText = findViewById(R.id.edit_text_email_login);
        senhaEditText = findViewById(R.id.edit_text_senha_login);

        initialize();

    }

    private void initialize() {
        cadastroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_tarefas = new Intent(MainActivity.this, CadastroUsuario.class);
                startActivity(janela_tarefas);
            }
        });

        login();
    }

    private void login() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                if (imm.isActive())
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);


                String email = String.valueOf(emailEditText.getText());
                String senha = String.valueOf(senhaEditText.getText());

                UsuarioDAO usuarioDAO = new UsuarioDAO(MainActivity.this);

                boolean resultado = usuarioDAO.autenticar(email, senha);

                if (!resultado) {
                    Toast.makeText(getApplicationContext(), "Usuario ou senha incorretos", Toast.LENGTH_SHORT).show();
                } else {
                    Intent janela_tarefas = new Intent(MainActivity.this, TaskActivity.class);
                    startActivity(janela_tarefas);

                }


            }
        });

    }
}