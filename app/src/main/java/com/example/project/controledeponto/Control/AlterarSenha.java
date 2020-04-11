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
import com.example.project.controledeponto.R;

public class AlterarSenha extends Fragment implements  View.OnClickListener{


    public AlterarSenha() {

    }

    private String usuario;
    private String senha_atual;
    private String senha_nova;
    private String senha_confirma;

    AdministradorDataBase adb;
    FuncionarioDataBase fdb;
    EditText user;
    EditText s_atual;
    EditText s_nova;
    EditText s_confirmar;
    Button btn_alterar_s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tela_alterar_senha, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = view.findViewById(R.id.edit_user);
        s_atual = view.findViewById(R.id.edit_s_atual);
        s_nova = view.findViewById(R.id.edit_s_nova);
        s_confirmar = view.findViewById(R.id.edit_s_confirmar);
        btn_alterar_s = view.findViewById(R.id.bt_alterarSenha);
        btn_alterar_s.setOnClickListener(this);
        adb = new AdministradorDataBase(view.getContext());
        fdb = new FuncionarioDataBase(view.getContext());
    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PrincipalFuncionario princ_func = new PrincipalFuncionario();
        PrincipalAdministrador princ_adm = new PrincipalAdministrador();

        if(v.getId() == btn_alterar_s.getId()){
            usuario = user.getText().toString().trim();
            senha_atual = s_atual.getText().toString().trim();
            senha_nova = s_nova.getText().toString().trim();
            senha_confirma = s_confirmar.getText().toString().trim();

            if(senha_nova.equals(senha_confirma)){
                if(adb.loginAdministrador(usuario,senha_atual)){
                    if(adb.alterarSenha(usuario,senha_nova)){
                        Toast.makeText(getActivity(),"Senha alterada",Toast.LENGTH_SHORT).show();
                        limpaCampos();
                    } else {
                        Toast.makeText(getActivity(),"Tente novamente",Toast.LENGTH_SHORT).show();
                    }
                    transaction.replace(R.id.content,princ_adm);
                } if (fdb.loginFuncionario(usuario, senha_atual)){
                    if(fdb.alterarSenha(usuario,senha_nova)){
                        Toast.makeText(getActivity(),"Senha alterada",Toast.LENGTH_SHORT).show();
                        limpaCampos();
                    } else {
                        Toast.makeText(getActivity(),"Tente novamente",Toast.LENGTH_SHORT).show();
                    }
                    transaction.replace(R.id.content,princ_func);
                }
            } else {
                Toast.makeText(getActivity(),"Nova senha não confere!",Toast.LENGTH_SHORT).show();
            }


        }
    }
    public void limpaCampos(){
        user.setText("");
        s_atual.setText("");
        s_nova.setText("");
        s_confirmar.setText("");
    }
}
