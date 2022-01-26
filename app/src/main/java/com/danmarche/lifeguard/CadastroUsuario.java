package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.danmarche.lifeguard.config.GlobalsVar;
import com.danmarche.lifeguard.dao.UsuarioDAO;
import com.danmarche.lifeguard.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CadastroUsuario extends AppCompatActivity {
    private Button irHomeBtn;
    private Button cadastrarBtn;
    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText senhaEditText;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        irHomeBtn = findViewById(R.id.btn_ir_home);
        cadastrarBtn = findViewById(R.id.btn_cadastrar_novo_usuario);
        nomeEditText = findViewById(R.id.cadastro_nome);
        emailEditText = findViewById(R.id.cadastro_email);
        senhaEditText = findViewById(R.id.cadastro_senha);
        loading = findViewById(R.id.loading_cadastro_usuario);

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
//                    inserirUsuario(usuario);

                    cadastrarUsuario(usuario);

                }
            }
        });
    }

    private void inserirUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(CadastroUsuario.this);

        long id = usuarioDAO.inserir(usuario);


        Log.d("Usuario: ", String.valueOf(id));
    }

    private void cadastrarUsuario(Usuario usuario) {
        Map<String, String> dados = new HashMap<String, String>();
        dados.put("nome", usuario.getNome());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        JSONObject jsonBody = new JSONObject(dados);

        JsonObjectRequest requisicao = new JsonObjectRequest(Request.Method.POST, GlobalsVar.urlServidor + "usuario/", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("onResponse: ", response.toString());
                    Log.d("ID: ", String.valueOf(response.getInt("id")));

                    Intent janela_tarefas = new Intent(CadastroUsuario.this, TaskActivity.class);
                    janela_tarefas.putExtra("id", response.getInt("id"));
                    startActivity(janela_tarefas);


                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);

                                Log.d("onErrorResponse: ", obj.getString("message"));
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });
        Volley.newRequestQueue(this).add(requisicao);
    }
}