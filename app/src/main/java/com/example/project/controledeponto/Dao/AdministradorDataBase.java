package com.example.project.controledeponto.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.controledeponto.Model.UsuarioAdministrador;

public class AdministradorDataBase extends SQLiteOpenHelper {

    private static final int version =  1;
    private static final String nomeBD = "administrador";

    private static final String tb_administrador = "administrador";
    private static final String c_cod_empresa = "cod_adm";
    private static final String c_nome_empresa = "nome_empresa";
    private static final String c_cnpj_empresa = "cnpj_empresa";
    private static final String c_usuario_adm = "usuario_adm";
    private static final String c_senha_adm = "senha_adm";


    public AdministradorDataBase(Context context) {
        super(context, nomeBD, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+tb_administrador+"("+
                c_cod_empresa+ " INTEGER PRIMARY KEY, " + c_nome_empresa + " TEXT, "+
                c_cnpj_empresa+ " TEXT, " + c_usuario_adm + " TEXT, "+
                c_senha_adm+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean cadastroAdministrador(UsuarioAdministrador admin){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(c_nome_empresa, admin.getNome_empresa());
            values.put(c_cnpj_empresa, admin.getCnpj());
            values.put(c_usuario_adm, admin.getUsuario());
            values.put(c_senha_adm, admin.getSenha());
            db.insert(tb_administrador, null, values);
            db.close();
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    public boolean loginAdministrador(String user, String password){
        boolean retorno = false;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            cursor = db.query(tb_administrador,
                    new String[]{c_usuario_adm, c_senha_adm},
                    c_senha_adm+" = ?",
                    new String[]{password+""},
                    null, null, null);

            if(cursor != null) {
                if(cursor.moveToFirst()){
                    if (cursor.getString(0).equals(user)) {
                        retorno = true;
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (SQLException e){
            retorno = false;
        }
        return retorno;
    }

    public boolean alterarSenha(String usuario, String nova_senha){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(c_senha_adm, nova_senha);
            db.update(tb_administrador, values, c_usuario_adm + " = ?",
                    new String[]{String.valueOf(usuario+"")});
            db.close();
            return true;
        }  catch (SQLException e){
            return false;
        }
    }
}
