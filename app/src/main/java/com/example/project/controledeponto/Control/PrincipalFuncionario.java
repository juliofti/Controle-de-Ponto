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
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.controledeponto.Dao.PontoDataBase;
import com.example.project.controledeponto.Model.ModelHistoricoFuncionario;
import com.example.project.controledeponto.Model.Ponto;
import com.example.project.controledeponto.Model.UsuarioAdministrador;
import com.example.project.controledeponto.R;
import com.example.project.controledeponto.View.LayoutListaFuncionario;

import java.util.ArrayList;
import java.util.List;

public class PrincipalFuncionario extends Fragment implements  View.OnClickListener{

    Ponto ponto = new Ponto();

    public PrincipalFuncionario() {
        // Required empty public constructor
    }

    private Button bt_adicionar;
    ListView historico;
    private TextView  lt_data;
    private TextView lt_horario_entrada;
    private TextView lt_horario_saida;
    private TextView txt_usuario;
    ArrayList<ModelHistoricoFuncionario> listahistorico = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tela_principal_funcionario, container, false);
        lt_data = view.findViewById(R.id.lt_data);
        lt_horario_entrada = view.findViewById(R.id.lt_horario_entrada);
        lt_horario_saida = view.findViewById(R.id.lt_horario_saida);
        historico = view.findViewById(R.id.historico_funcionario);

        LayoutListaFuncionario lista = new LayoutListaFuncionario(this.getContext(),R.layout.layout_lista_funcionario,geradorDatas());
        historico.setAdapter(lista);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_usuario = view.findViewById(R.id.textView4);
        bt_adicionar = view.findViewById(R.id.bt_adicionar);
        bt_adicionar.setOnClickListener(this);
        txt_usuario.setText(ponto.getId_usuario()+"");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bt_adicionar.getId()) {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            RegistrarPonto registrar = new RegistrarPonto();

            transaction.replace(R.id.content, registrar);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public ArrayList<ModelHistoricoFuncionario> geradorDatas(){
        PontoDataBase pontoDataBase = new PontoDataBase(getActivity());
        int id_funcionario = ponto.getId_usuario();
        List<Ponto> listaPonto = pontoDataBase.buscarPonto(getActivity(), id_funcionario);

        for(int i =0; i < listaPonto.size(); i++){
            ModelHistoricoFuncionario hist = new ModelHistoricoFuncionario();
            hist.setData(listaPonto.get(i).getData());
            hist.setEntrada(listaPonto.get(i).getHora());
            hist.setSaida(listaPonto.get(i).getHora());
            listahistorico.add(hist);
        }
        return  listahistorico;
    }
}
