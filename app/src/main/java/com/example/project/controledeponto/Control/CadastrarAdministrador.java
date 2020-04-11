package com.example.project.controledeponto.Control;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.project.controledeponto.Dao.AdministradorDataBase;
import com.example.project.controledeponto.Model.UsuarioAdministrador;
import com.example.project.controledeponto.R;
import com.example.project.controledeponto.View.Mascaras;

public class CadastrarAdministrador extends AppCompatActivity {

    EditText nome_empresa, cnpj, usuario, senha;
    Button cadastrar_administrador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastrar_administrador);

        nome_empresa = findViewById(R.id.cadastro_nome_empresa);
        cnpj = findViewById(R.id.cadastro_cnpj);
        cnpj.addTextChangedListener(Mascaras.mask(cnpj, Mascaras.FORMAT_CNPJ));
        usuario = findViewById(R.id.cadastro_usuario_administrador);
        senha = findViewById(R.id.cadastro_senha_administrador);
        cadastrar_administrador = findViewById(R.id.btn_cadastrar_administrador);

        cadastrar_administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PROIBI CAMPOS VAZIOS NA TELA CADASTRO
                if(nome_empresa.getText().toString().isEmpty() || cnpj.getText().toString().isEmpty()
                        || usuario.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
                    Toast.makeText(CadastrarAdministrador.this,"Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
                } else {
                    if(cnpj.getText().toString().trim().length() == 18){ // VALIDA CNPJ
                        UsuarioAdministrador adm = new UsuarioAdministrador();
                        adm.setNome_empresa(nome_empresa.getText().toString().trim());
                        adm.setCnpj(cnpj.getText().toString().trim());
                        adm.setUsuario(usuario.getText().toString().trim());
                        adm.setSenha(senha.getText().toString().trim());
                        AdministradorDataBase dtb = new AdministradorDataBase(CadastrarAdministrador.this);

                        if(dtb.cadastroAdministrador(adm)){ // CADASTRA ADMINISTRADOR
                            Toast.makeText(CadastrarAdministrador.this,"Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CadastrarAdministrador.this,"Tente novamente!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CadastrarAdministrador.this,"CNPJ inválido!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
