package com.sistemaMaster.gui.auxiliar;

public class FormaPagamento {
    private int id;
    private String nome;

    public FormaPagamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }

    @Override
    public String toString() {
        return nome;
    }
}
