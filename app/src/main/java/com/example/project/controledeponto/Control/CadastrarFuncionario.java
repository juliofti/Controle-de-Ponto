package com.example.project.controledeponto.Control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.controledeponto.Dao.AdministradorDataBase;
import com.example.project.controledeponto.Dao.FuncionarioDataBase;
import com.example.project.controledeponto.Model.UsuarioFuncionario;
import com.example.project.controledeponto.R;
import com.example.project.controledeponto.View.Mascaras;

public class CadastrarFuncionario extends Fragment implements  View.OnClickListener{

    public CadastrarFuncionario(){

    }

    EditText nome, cpf, usuario, senha;
    Button btn_cadastrar;
    AdministradorDataBase adb;
    FuncionarioDataBase fdb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tela_cadastrar_funcionario, container, false);
        return view;    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nome = view.findViewById(R.id.nome_cad_func);
        cpf = view.findViewById(R.id.cpf_cad_func);
        cpf.addTextChangedListener(Mascaras.mask(cpf, Mascaras.FORMAT_CPF));
        usuario = view.findViewById(R.id.usuario_cad_func);
        senha = view.findViewById(R.id.password_cad_func);
        btn_cadastrar = view.findViewById(R.id.btnCadastrar_func);
        btn_cadastrar.setOnClickListener(this);
        fdb = new FuncionarioDataBase(view.getContext());
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PrincipalAdministrador princ_adm = new PrincipalAdministrador();
        transaction.replace(R.id.content,princ_adm);

        if(v.getId() == btn_cadastrar.getId()){ // PROIBI CAMPOS VAZIOS
            if(nome.getText().toString().isEmpty() || cpf.getText().toString().isEmpty()
                    || usuario.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
                Toast.makeText(getActivity(),"Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
            } else {
                if(cpf.getText().toString().trim().length() == 14){ // VALIDA CPF
                    UsuarioFuncionario funcionario = new UsuarioFuncionario();
                    funcionario.setNome(nome.getText().toString().trim());
                    funcionario.setCpf(cpf.getText().toString().trim());
                    funcionario.setUsuario(usuario.getText().toString().trim());
                    funcionario.setSenha(senha.getText().toString().trim());
                    if(fdb.cadastrarFuncionario(funcionario)){ // CADASTRA FUNCIONARIO
                        Toast.makeText(getActivity(),"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        limpaCampos();
                    } else {
                        Toast.makeText(getActivity(),"Tente novamente!",Toast.LENGTH_SHORT).show();
                    }
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else{
                    Toast.makeText(getActivity(),"CPF inválido!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void limpaCampos(){
        nome.setText("");
        cpf.setText("");
        usuario.setText("");
        senha.setText("");
    }
}