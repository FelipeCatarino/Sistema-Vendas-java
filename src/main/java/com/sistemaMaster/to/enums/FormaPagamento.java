package com.sistemaMaster.to.enums;

public enum FormaPagamento {
    DINHEIRO(1, "Dinheiro"),

    PIX(2, "Pix"),

    DEBITO(3, "Debito"),

    CREDITO(4, "Crédito");

    private final int ID;
    private final String DESCRICAO;

    private FormaPagamento(int id, String descricao) {
        this.ID = id;
        this.DESCRICAO = descricao;
    }

    /**
     * Retorna o código da situação
     *
     * @return int id
     */
    public int getId() {
        return ID;
    }

    @Override
    public String toString() {
        return this.DESCRICAO;
    }
}
