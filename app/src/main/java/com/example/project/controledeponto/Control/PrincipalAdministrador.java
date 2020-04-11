package com.example.project.controledeponto.Control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.controledeponto.Model.ModelHistoricoAdministrador;
import com.example.project.controledeponto.R;
import com.example.project.controledeponto.View.LayoutListaAdministrador;

import java.util.ArrayList;

public class PrincipalAdministrador extends Fragment {

    public PrincipalAdministrador() {
        // Required empty public constructor
    }

    ListView historico;
    private TextView nome_funcionario;
    private TextView situacao;
    ArrayList<ModelHistoricoAdministrador> listahistorico = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tela_principal_administrador, container, false);
        nome_funcionario = view.findViewById(R.id.nome_funcionario);
        situacao = view.findViewById(R.id.situacao_funcionario);
        historico = view.findViewById(R.id.historico_administrador);

        LayoutListaAdministrador lista = new LayoutListaAdministrador(this.getContext(),R.layout.layout_lista_administrador, geradorHistorico());
        historico.setAdapter(lista);
        return view;
    }

    public ArrayList<ModelHistoricoAdministrador> geradorHistorico(){
        ModelHistoricoAdministrador adm_list;
        for(int i = 1; i<=10; i++){
            adm_list = new ModelHistoricoAdministrador();
            adm_list.setNome_funcionario("Nome Funcionario "+i);
            if(i<5){
                adm_list.setSituacao("Presente");
            } else{
                adm_list.setSituacao("Ausente");
            }
            listahistorico.add(adm_list);
        }

        return listahistorico;
    }
}
