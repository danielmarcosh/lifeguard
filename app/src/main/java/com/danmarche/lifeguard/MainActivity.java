package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.danmarche.lifeguard.config.GlobalsVar;
import com.danmarche.lifeguard.dao.UsuarioDAO;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button cadastroBtn;
    private EditText emailEditText;
    private EditText senhaEditText;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.btn_login);
        cadastroBtn = findViewById(R.id.btn_ir_cadastro_usuario);
        emailEditText = findViewById(R.id.edit_text_email_login);
        senhaEditText = findViewById(R.id.edit_text_senha_login);

        loading = findViewById(R.id.loadingLogin);

        initialize();
        pedirPermissao();

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
//                UsuarioDAO usuarioDAO = new UsuarioDAO(MainActivity.this);
//
//                boolean resultado = usuarioDAO.autenticar(email, senha);
//                if (!resultado) {
//                    Toast.makeText(getApplicationContext(), "Usuario ou senha incorretos", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent janela_tarefas = new Intent(MainActivity.this, TaskActivity.class);
//                    startActivity(janela_tarefas);
//
//                }


                autenticar(email, senha);

            }
        });

    }

    private void pedirPermissao() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
        }, 1);
    }

    private void autenticar(String email, String senha) {
        loading.setVisibility(View.VISIBLE);
        Map<String, String> dados = new HashMap<String, String>();
        dados.put("email", email);
        dados.put("senha", senha);
        JSONObject jsonBody = new JSONObject(dados);

        JsonObjectRequest requisicao = new JsonObjectRequest(Request.Method.POST, GlobalsVar.urlServidor + "login", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Response:%n %s", response.toString(4));
                    Log.d("ID USUARIO ", String.valueOf(response.getInt("id")));

                    Intent janela_tarefas = new Intent(MainActivity.this, TaskActivity.class);
                    janela_tarefas.putExtra("id", response.getInt("id"));
                    startActivity(janela_tarefas);

                } catch (JSONException e) {
                    e.printStackTrace();
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
                                Toast.makeText(getApplicationContext(), "Usuario ou senha incorretos", Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });
        Volley.newRequestQueue(this).add(requisicao);
        loading.setVisibility(View.INVISIBLE);
    }
}