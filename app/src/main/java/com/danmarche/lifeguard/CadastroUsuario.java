package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.danmarche.lifeguard.dao.UsuarioDAO;
import com.danmarche.lifeguard.modelo.Usuario;

public class CadastroUsuario extends AppCompatActivity {
    private Button irHomeBtn;
    private Button cadastrarBtn;
    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        irHomeBtn = findViewById(R.id.btn_ir_home);
        cadastrarBtn = findViewById(R.id.btn_cadastrar_novo_usuario);
        nomeEditText = findViewById(R.id.cadastro_nome);
        emailEditText = findViewById(R.id.cadastro_email);
        senhaEditText = findViewById(R.id.cadastro_senha);

        initialize();
    }

    private void initialize() {
        irHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent janela_home = new Intent(CadastroUsuario.this, MainActivity.class);
                startActivity(janela_home);
            }
        });

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomeEditText.getText().length() == 0) {
                    nomeEditText.setError("O campo Nome não pode ser vazio");
                } else if (emailEditText.getText().length() == 0) {
                    emailEditText.setError("O campo Email não pode ser vazio");
                } else if (senhaEditText.getText().length() == 0) {
                    senhaEditText.setError("O campo Senha não pode ser vazio");

                } else {
                    String nome = String.valueOf(nomeEditText.getText());
                    String email = String.valueOf(emailEditText.getText());
                    String senha = String.valueOf(senhaEditText.getText());
                    Usuario usuario = new Usuario(nome, email, senha);
                    inserirUsuario(usuario);
                    Intent janela_tarefas = new Intent(CadastroUsuario.this, TaskActivity.class);
                    startActivity(janela_tarefas);
                }
            }
        });
    }

    private void inserirUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(CadastroUsuario.this);

        long id = usuarioDAO.inserir(usuario);


        Log.d("Usuario: ", String.valueOf(id));
    }
}