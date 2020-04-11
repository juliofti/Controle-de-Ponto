package com.example.project.controledeponto.Model;


public class Ponto {

    private int cod;
    private int tipo_ponto;
    private String data;
    private String hora;
    private String id_usuario_filtragem;
    private static int id_usuario;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getTipoPonto() {
        return tipo_ponto;
    }

    public void setTipoPonto(int tipo) {
        tipo_ponto = tipo;
    }

    public String getData() {
        return data;
    }

    public String getId_usuario_filtragem() {
        return id_usuario_filtragem;
    }

    public void setId_usuario_filtragem(String id_usuario_filtragem) {
        this.id_usuario_filtragem = id_usuario_filtragem;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public static void setId_usuario(int id_usuario) {
        Ponto.id_usuario = id_usuario;
    }

    public static int getId_usuario() {
        return id_usuario;
    }
}
