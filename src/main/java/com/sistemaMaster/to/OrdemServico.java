package com.sistemaMaster.to;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe contendo os dados da ordem de serviço - Transfer Object
 * 
 * Representa a entidade Ordem de Serviço com todos os atributos necessários
 * para o sistema de vendas da oficina mecânica.
 *
 * @author Felipe da Costa Catarino - Implementação completa
 */
public class OrdemServico {

    private int codigo;
    private int codigoCliente;
    private String nomeCliente; // Campo auxiliar para exibição
    private String placaVeiculo;
    private String modeloVeiculo;
    private String descricaoProblema;
    private String descricaoServico;
    private String observacoes;
    private Date dataAbertura;
    private Date dataPrevisaoEntrega;
    private Date dataEntrega;
    private String status; // ABERTA, EM_ANDAMENTO, CONCLUIDA, ENTREGUE, CANCELADA
    private String prioridade; // BAIXA, NORMAL, ALTA, URGENTE
    private BigDecimal valorMaoDeObra;
    private BigDecimal valorPecas;
    private BigDecimal valorTotal;
    private String tecnicoResponsavel;
    private int quilometragemVeiculo;

    public OrdemServico() {
        this.codigo = 0;
        this.codigoCliente = 0;
        this.nomeCliente = "";
        this.placaVeiculo = "";
        this.modeloVeiculo = "";
        this.descricaoProblema = "";
        this.descricaoServico = "";
        this.observacoes = "";
        this.dataAbertura = new Date();
        this.dataPrevisaoEntrega = null;
        this.dataEntrega = null;
        this.status = "ABERTA";
        this.prioridade = "NORMAL";
        this.valorMaoDeObra = BigDecimal.ZERO;
        this.valorPecas = BigDecimal.ZERO;
        this.valorTotal = BigDecimal.ZERO;
        this.tecnicoResponsavel = "";
        this.quilometragemVeiculo = 0;
    }

    public OrdemServico(int codigo) {
        this();
        this.codigo = codigo;
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataPrevisaoEntrega() {
        return dataPrevisaoEntrega;
    }

    public void setDataPrevisaoEntrega(Date dataPrevisaoEntrega) {
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public BigDecimal getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(BigDecimal valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(String tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public int getQuilometragemVeiculo() {
        return quilometragemVeiculo;
    }

    public void setQuilometragemVeiculo(int quilometragemVeiculo) {
        this.quilometragemVeiculo = quilometragemVeiculo;
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "codigo=" + codigo +
                ", codigoCliente=" + codigoCliente +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", placaVeiculo='" + placaVeiculo + '\'' +
                ", status='" + status + '\'' +
                ", prioridade='" + prioridade + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
