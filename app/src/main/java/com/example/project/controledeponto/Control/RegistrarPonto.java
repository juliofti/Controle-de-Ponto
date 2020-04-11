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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.project.controledeponto.Dao.PontoDataBase;
import com.example.project.controledeponto.Model.Ponto;
import com.example.project.controledeponto.Model.UsuarioFuncionario;
import com.example.project.controledeponto.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegistrarPonto extends Fragment implements  View.OnClickListener{


    public RegistrarPonto() {
        // Required empty public constructor
    }

    private EditText type_pont;
    private EditText edit_horario;
    private EditText edit_data;
    private Button bt_voltar;
    private Button bt_inserir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.tela_registrar_ponto, container, false);
        return  view;
    }

    private String [] tipo = {"Entrada"};
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_data = view.findViewById(R.id.edit_data);
        edit_horario = view.findViewById(R.id.edit_horario);
        bt_inserir = view.findViewById(R.id.bt_inserir);
        bt_voltar = view.findViewById(R.id.bt_voltar);
        type_pont = view.findViewById(R.id.type_ponto);

        Ponto po = new Ponto();
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        PontoDataBase pdb = new PontoDataBase(getContext());
        int retorno = pdb.verificaTipoDePonto(data.trim(), po.getId_usuario());
        edit_data.setText(data);
        edit_horario.setText(hora);
        type_pont.setText(""+retorno);
        bt_voltar.setOnClickListener(this);
        bt_inserir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == bt_voltar.getId()){

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            PrincipalFuncionario princ = new PrincipalFuncionario();
            transaction.replace(R.id.content,princ);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(v.getId() == bt_inserir.getId()){
            Ponto po = new Ponto();
            po.setHora(edit_horario.getText().toString().trim());
            po.setData(edit_data.getText().toString().trim());
            po.setTipoPonto(Integer.parseInt(type_pont.getText().toString()));
            po.setId_usuario(po.getId_usuario());

            //VOLTA PARA A TELA PRINCIPAL
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            PrincipalFuncionario princ_func = new PrincipalFuncionario();
            transaction.replace(R.id.content, princ_func);

            // VERIFICA SE PONTO FOI REGISTRADO
            PontoDataBase pdb = new PontoDataBase(getActivity());
            if(pdb.cadastrarPontoFuncionario(po)){
                Toast.makeText(getActivity(),"Ponto registrado! ", Toast.LENGTH_LONG).show();
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                Toast.makeText(getActivity(),"Ponto não registrado!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
