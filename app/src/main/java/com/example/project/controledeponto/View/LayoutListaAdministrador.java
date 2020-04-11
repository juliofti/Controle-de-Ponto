package com.example.project.controledeponto.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project.controledeponto.Model.ModelHistoricoAdministrador;
import com.example.project.controledeponto.R;

import java.util.List;

public class LayoutListaAdministrador extends ArrayAdapter<ModelHistoricoAdministrador> {

    private Context contexto;
    int resourceLayout;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String nome_func = getItem(position).getNome_funcionario();
        String situacao_func = getItem(position).getSituacao();

        LayoutInflater inflater = LayoutInflater.from(contexto);
        convertView = (inflater.inflate(resourceLayout,parent,false));


        TextView nome_funcionario = (TextView) convertView.findViewById(R.id.nome_funcionario);
        TextView situacao_funcionario = (TextView) convertView.findViewById(R.id.situacao_funcionario);


        nome_funcionario.setText("Nome: "+nome_func);
        situacao_funcionario.setText("(Situação: "+situacao_func+")");
        return convertView;
    }


    public LayoutListaAdministrador(@NonNull Context context, int resource, @NonNull List<ModelHistoricoAdministrador> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.resourceLayout = resource;
    }
}
