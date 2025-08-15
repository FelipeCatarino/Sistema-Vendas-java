package com.sistemaMaster.to;

import java.util.Date;

/**
 * Classe contendo os dados do cliente - Transfer Object
 * 
 * Representa a entidade Cliente com todos os atributos necessários
 * para o sistema de vendas da oficina mecânica.
 *
 * @author Felipe da Costa Catarino - Implementação completa e melhorias
 * @author Juliano Denner da Rocha - Estrutura base original
 */
public class Cliente {

    private int codigo;
    private String nome;
    private String telefone;
    private String placa;
    private String modeloMoto;
    private Date dataCadastro;
    private int quilometragemAtual;
    private String observacao;


    public Cliente() {
        this.codigo = 0;
        this.nome = "";
        this.telefone = "";
        this.placa = "";
        this.modeloMoto = "";
        this.dataCadastro = new Date();
        this.quilometragemAtual = 0;
        this.observacao = "";
    }

    public Cliente(int codigo) {
        this.codigo = codigo;
        this.nome = "";
        this.telefone = "";
        this.placa = "";
        this.modeloMoto = "";
        this.dataCadastro = new Date();
        this.quilometragemAtual = 0;
        this.observacao = "";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModeloMoto() {
        return modeloMoto;
    }

    public void setModeloMoto(String modeloMoto) {
        this.modeloMoto = modeloMoto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(int quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cliente) {
            Cliente c = (Cliente) o;
            if (c.getCodigo() == this.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
