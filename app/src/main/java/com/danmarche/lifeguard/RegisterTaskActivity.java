package com.danmarche.lifeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.danmarche.lifeguard.dao.TarefaDAO;
import com.danmarche.lifeguard.dao.UsuarioDAO;
import com.danmarche.lifeguard.modelo.Tarefa;
import com.danmarche.lifeguard.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterTaskActivity extends AppCompatActivity {

    private Button voltar_tarefaBtn;
    private Button adicionar_tarefaBtn;
    private EditText nome_tarefaEditText;
    private EditText descricao_tarefaEditText;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_task);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.id = extras.getInt("id");
            Log.d("Usuario ID: TELA TAREFAS *****", String.valueOf(this.id));
            this.id = extras.getInt("id");
        }

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

//            Date dataAtual = new Date();
//                    String myFormat = "yyyy-MM-dd";
//                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
//                    String dataFormatada = sdf.format(dataAtual);
//            long idUser = getIdUsuario();
//            Tarefa tarefa = new Tarefa(nome, descricao, dataAtual.getTime(), idUser);
//            Log.d("Tarefa ", tarefa.toString());
//            salvarTarefa(tarefa);

            cadastrarTarefaServidor(nome, descricao);

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

    private void cadastrarTarefaServidor(String nome, String descricao) {
        Date dataAtual = new Date();
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        String dataFormatada = sdf.format(dataAtual);

        Log.d("******* VALOR DE DO ID NA HORA DE CRIAR NOVA TAREFA *******: ", String.valueOf(id));

        Map<String, String> dados = new HashMap<String, String>();
        dados.put("nome", nome);
        dados.put("data", dataFormatada);
        dados.put("descricao", descricao);
        dados.put("id_usuario", String.valueOf(id));

        JSONObject jsonBody = new JSONObject(dados);

        JsonObjectRequest requisicao = new JsonObjectRequest(Request.Method.POST, GlobalsVar.urlServidor + "usuario/", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse: ", response.toString());
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