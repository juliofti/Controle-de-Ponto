package com.example.project.controledeponto.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.project.controledeponto.Model.Ponto;
import java.util.ArrayList;
import java.util.List;


public class PontoDataBase  extends SQLiteOpenHelper {

    private static final int version =  1;
    private static final String nomeBD = "controle_ponto";


    private static final String tb_ponto = "ponto";
    private static final String c_cod = "cod";
    private static final String c_tipo = "tipo_func";
    private static final String c_data = "data_func";
    private static final String c_hora = "hora_func";
    private static final String c_id_func = "id_func";

    ArrayList<Ponto> lista = new ArrayList<>();

    public PontoDataBase(Context context) {
        super(context, nomeBD, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ tb_ponto +"("+
                c_cod + " INTEGER PRIMARY KEY, " + c_tipo + " INTEGER, "+
                c_data + " TEXT, " + c_hora + " TEXT, "+
                c_id_func +" TEXT)";
        db.execSQL(query);
    }


    public boolean cadastrarPontoFuncionario(Ponto ponto){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(c_data, ponto.getData());
            values.put(c_hora, ponto.getHora());
            values.put(c_tipo, ponto.getTipoPonto());
            values.put(c_id_func, ponto.getId_usuario());
            db.insert(tb_ponto, null, values);
            db.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Ponto> buscarPonto(Context context, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.query(tb_ponto,
                new String[]{c_cod, c_tipo,c_data,c_hora},
                c_id_func+" = ?",
                new String[]{id+""},
                null, null, null);
        return listaPonto(cursor);
    }

    public List<Ponto> listaPonto(Cursor cursor){
        lista = new ArrayList<Ponto>();
        if(cursor.moveToFirst()){
            do {
                Ponto ponto = new Ponto();
                ponto.setCod(Integer.parseInt(cursor.getString(0)));
                ponto.setTipoPonto(Integer.parseInt(cursor.getString(1)));
                ponto.setData(cursor.getString(2));
                ponto.setHora(cursor.getString(3));
                lista.add(ponto);
            } while (cursor.moveToNext());
        }
        return lista;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int verificaTipoDePonto(String data, int id_user){
        ArrayList<Ponto> listaPonto = new ArrayList<Ponto>();
        try{
            // BUSCA NO BANCO DE DADOS DE PONTOS REFERENTE A DATA ATUAL
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(tb_ponto,
                    new String[]{c_cod, c_tipo, c_id_func },
                    c_data+" = ?",
                    new String[]{data},
                    null, null, null);

            listaPonto = new ArrayList<>();

            // CRIA ARRAY LIST DE TODOS OS RESULTADOS
            if(cursor.moveToFirst()){
                do {
                    Ponto ponto = new Ponto();
                    ponto.setCod(Integer.parseInt(cursor.getString(0)));
                    ponto.setTipoPonto(Integer.parseInt(cursor.getString(1)));
                    ponto.setId_usuario_filtragem(cursor.getString(2));
                    listaPonto.add(ponto);
                } while (cursor.moveToNext());
            }
        } catch (Exception e){

        }
        return refinamentoTipoPonto(listaPonto, id_user);
    }

    public int refinamentoTipoPonto(ArrayList<Ponto> listaPonto, int id_user) {
        //REFINA NA LISTA SOMENTE OS PONTOS DE UM USUARIO ESPECIFICO
        ArrayList<Ponto> listafinal = new ArrayList<>();
        for (int i = 0; i < listaPonto.size(); i++) {
            int id_usuario = Integer.parseInt(listaPonto.get(i).getId_usuario_filtragem());
            if (id_usuario == id_user) {
                listafinal.add(listaPonto.get(i));
            }
        }

        //VERIFICA SE É ENTRADA OU SAIDA O ULTIMO PONTO UTILIZADO
        int maior_id = 0;
        int tipo_ponto = 0; // 1 - entrada / 2 - saida
        for (int i = 0; i < listafinal.size(); i++) {
            if (listafinal.get(i).getCod() > maior_id) {
                maior_id = listafinal.get(i).getCod();
                tipo_ponto = listafinal.get(i).getTipoPonto();
            }
        }

        int retorno = 4;

        Ponto po = new Ponto();
        if (tipo_ponto == 1 || tipo_ponto == 0) {
            retorno = 2; // entrada
        } else if (tipo_ponto == 2){
            retorno = 1; // saida
        }
        return retorno;
    }
}
