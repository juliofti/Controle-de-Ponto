package com.example.project.controledeponto.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.project.controledeponto.Model.UsuarioFuncionario;

public class FuncionarioDataBase extends SQLiteOpenHelper {

    private static final int version =  1;
    private static final String nomeBD ="funcionario";

    private static final String tb_funcionario = "funcionario";
    private static final String c_cod_funcionario = "cod_func";
    private static final String c_nome_func = "nome_func";
    private static final String c_cpf_func = "cpf_func";
    private static final String c_usuario_func = "usuario_func";
    private static final String c_senha_func = "senha_func";


    public FuncionarioDataBase(Context context) {
        super(context, nomeBD, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ tb_funcionario +"("+
                c_cod_funcionario + " INTEGER PRIMARY KEY, " + c_nome_func + " TEXT, "+
                c_cpf_func + " TEXT, " + c_usuario_func + " TEXT, "+
                c_senha_func +" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean cadastrarFuncionario(UsuarioFuncionario funcionario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(c_nome_func, funcionario.getNome());
            values.put(c_cpf_func, funcionario.getCpf());
            values.put(c_usuario_func, funcionario.getUsuario());
            values.put(c_senha_func, funcionario.getSenha());
            db.insert(tb_funcionario, null, values);
            db.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean loginFuncionario(String user, String password) {
        boolean retorno = false;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            cursor = db.query(tb_funcionario,
                    new String[]{c_usuario_func, c_senha_func},
                    c_senha_func+" = ?",
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
            values.put(c_senha_func, nova_senha);
            db.update(tb_funcionario, values, c_usuario_func + " = ?",
                    new String[]{String.valueOf(usuario+"")});
            db.close();
            return true;
        }  catch (SQLException e){
            return false;
        }
    }

    public String verificarIdUsuario(String user) {
        String codigo = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            cursor = db.query(tb_funcionario,
                    new String[]{c_cod_funcionario},
                    c_usuario_func+" = ?",
                    new String[]{user+""},
                    null, null, null);

            if(cursor != null) {
                if(cursor.moveToFirst()){
                    codigo = cursor.getString(0);
                    return codigo;
                }
            }
            cursor.close();
            db.close();
        } catch (SQLException e){
            codigo = null;
        }
        return codigo;
    }
}
