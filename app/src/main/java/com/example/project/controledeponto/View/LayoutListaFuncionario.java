package com.example.project.controledeponto.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project.controledeponto.Model.ModelHistoricoFuncionario;
import com.example.project.controledeponto.R;

import java.util.List;

public class LayoutListaFuncionario extends ArrayAdapter<ModelHistoricoFuncionario> {


    private Context contexto;
    int resourceLayout;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String data = getItem(position).getData();
        String entrada = getItem(position).getEntrada();
        String saida = getItem(position).getSaida();

        LayoutInflater inflater = LayoutInflater.from(contexto);
        convertView = (inflater.inflate(resourceLayout,parent,false));


        TextView lt_data = (TextView) convertView.findViewById(R.id.lt_data);
        TextView lt_horario_entrada = (TextView) convertView.findViewById(R.id.lt_horario_entrada);
        TextView lt_horario_saida = (TextView) convertView.findViewById(R.id.lt_horario_saida);


        lt_data.setText("("+data+")");
        lt_horario_entrada.setText("(Entrada: "+entrada+")");
        lt_horario_saida.setText(" (saida: "+saida+")");
        return convertView;
    }

    public LayoutListaFuncionario(@NonNull Context context, int resource, @NonNull List<ModelHistoricoFuncionario> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.resourceLayout = resource;
    }


}
