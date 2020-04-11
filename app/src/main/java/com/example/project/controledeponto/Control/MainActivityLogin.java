package com.example.project.controledeponto.Control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.controledeponto.Dao.AdministradorDataBase;
import com.example.project.controledeponto.Dao.FuncionarioDataBase;
import com.example.project.controledeponto.Model.Ponto;
import com.example.project.controledeponto.Model.UsuarioFuncionario;
import com.example.project.controledeponto.R;

public class MainActivityLogin extends AppCompatActivity {

    AdministradorDataBase adb = new AdministradorDataBase(MainActivityLogin.this);
    FuncionarioDataBase fdb = new FuncionarioDataBase(MainActivityLogin.this);
    private Button bt_entrar;
    private Button bt_cadastrar;
    private Button bt_sair;
    private EditText edit_usuario;
    private EditText edit_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        edit_usuario = findViewById(R.id.edit_usuario);
        edit_senha = findViewById(R.id.edit_senha);
        bt_entrar = findViewById(R.id.bt_entrar);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
        bt_sair = findViewById(R.id.bt_sair);

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = edit_usuario.getText().toString().trim();
                String senha = edit_senha.getText().toString().trim();

                if(usuario.isEmpty() && senha.isEmpty()){
                    Toast.makeText(MainActivityLogin.this,"Usuário e senha obrigatório", Toast.LENGTH_LONG).show();
                } else {

                    if (fdb.loginFuncionario(usuario, senha)) {
                        int idFuncionario = Integer.parseInt(fdb.verificarIdUsuario(usuario));
                        Ponto ponto = new Ponto();
                        ponto.setId_usuario(idFuncionario);
                        Intent intent = new Intent(MainActivityLogin.this, DrawerFuncionario.class);
                        startActivity(intent);
                        finish();
                    } else if (adb.loginAdministrador(usuario, senha)){
                        Intent intent = new Intent(MainActivityLogin.this, DrawerAdministrador.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivityLogin.this,"Usuario ou senha incorretos!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityLogin.this, CadastrarAdministrador.class);
                startActivity(intent);
            }
        });

        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
