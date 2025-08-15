package com.sistemaMaster.to;

import java.util.Date;

/**
 * Classe que representa um fechamento de caixa diário para oficina mecânica
 *
 * @author Sistema
 */
public class FechamentoDia {
    
    private int codigo;
    private Date dataFechamento;
    private double totalVendas;
    private double totalDinheiro;
    private double totalDebito;
    private double totalCredito;
    private double totalPix;
    private double lucroBruto;
    private int totalServicos;
    
    // Campos específicos para oficina mecânica
    private double totalMaoDeObra;
    private double totalPecas;
    private int quantidadeClientes;
    private int quantidadeMotocicletasAtendidas;
    private double ticketMedio;
    
    public FechamentoDia() {
    }
    
    public FechamentoDia(Date dataFechamento, double totalVendas, double totalDinheiro, 
                        double totalDebito, double totalCredito, double totalPix, 
                        double lucroBruto, int totalServicos) {
        this.dataFechamento = dataFechamento;
        this.totalVendas = totalVendas;
        this.totalDinheiro = totalDinheiro;
        this.totalDebito = totalDebito;
        this.totalCredito = totalCredito;
        this.totalPix = totalPix;
        this.lucroBruto = lucroBruto;
        this.totalServicos = totalServicos;
        
        // Inicializa campos específicos da oficina
        this.totalMaoDeObra = 0.0;
        this.totalPecas = 0.0;
        this.quantidadeClientes = 0;
        this.quantidadeMotocicletasAtendidas = 0;
        this.ticketMedio = 0.0;
    }
    
    public FechamentoDia(Date dataFechamento, double totalVendas, double totalDinheiro, 
                        double totalDebito, double totalCredito, double totalPix, 
                        double lucroBruto, int totalServicos, double totalMaoDeObra,
                        double totalPecas, int quantidadeClientes, int quantidadeMotocicletasAtendidas) {
        this.dataFechamento = dataFechamento;
        this.totalVendas = totalVendas;
        this.totalDinheiro = totalDinheiro;
        this.totalDebito = totalDebito;
        this.totalCredito = totalCredito;
        this.totalPix = totalPix;
        this.lucroBruto = lucroBruto;
        this.totalServicos = totalServicos;
        this.totalMaoDeObra = totalMaoDeObra;
        this.totalPecas = totalPecas;
        this.quantidadeClientes = quantidadeClientes;
        this.quantidadeMotocicletasAtendidas = quantidadeMotocicletasAtendidas;
        this.ticketMedio = quantidadeClientes > 0 ? totalVendas / quantidadeClientes : 0.0;
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(double totalVendas) {
        this.totalVendas = totalVendas;
    }

    public double getTotalDinheiro() {
        return totalDinheiro;
    }

    public void setTotalDinheiro(double totalDinheiro) {
        this.totalDinheiro = totalDinheiro;
    }

    public double getTotalDebito() {
        return totalDebito;
    }

    public void setTotalDebito(double totalDebito) {
        this.totalDebito = totalDebito;
    }

    public double getTotalCredito() {
        return totalCredito;
    }

    public void setTotalCredito(double totalCredito) {
        this.totalCredito = totalCredito;
    }

    public double getTotalPix() {
        return totalPix;
    }

    public void setTotalPix(double totalPix) {
        this.totalPix = totalPix;
    }

    public double getLucroBruto() {
        return lucroBruto;
    }

    public void setLucroBruto(double lucroBruto) {
        this.lucroBruto = lucroBruto;
    }

    public int getTotalServicos() {
        return totalServicos;
    }

    public void setTotalServicos(int totalServicos) {
        this.totalServicos = totalServicos;
    }

    public double getTotalCaixa() {
        return totalDinheiro + totalDebito + totalCredito + totalPix;
    }

    // Getters e Setters para campos específicos da oficina
    public double getTotalMaoDeObra() {
        return totalMaoDeObra;
    }

    public void setTotalMaoDeObra(double totalMaoDeObra) {
        this.totalMaoDeObra = totalMaoDeObra;
    }

    public double getTotalPecas() {
        return totalPecas;
    }

    public void setTotalPecas(double totalPecas) {
        this.totalPecas = totalPecas;
    }

    public int getQuantidadeClientes() {
        return quantidadeClientes;
    }

    public void setQuantidadeClientes(int quantidadeClientes) {
        this.quantidadeClientes = quantidadeClientes;
        // Recalcula o ticket médio automaticamente
        this.ticketMedio = quantidadeClientes > 0 ? totalVendas / quantidadeClientes : 0.0;
    }

    public int getQuantidadeMotocicletasAtendidas() {
        return quantidadeMotocicletasAtendidas;
    }

    public void setQuantidadeMotocicletasAtendidas(int quantidadeMotocicletasAtendidas) {
        this.quantidadeMotocicletasAtendidas = quantidadeMotocicletasAtendidas;
    }

    public double getTicketMedio() {
        return ticketMedio;
    }

    public void setTicketMedio(double ticketMedio) {
        this.ticketMedio = ticketMedio;
    }

    @Override
    public String toString() {
        return "FechamentoDia{" +
                "codigo=" + codigo +
                ", dataFechamento=" + dataFechamento +
                ", totalVendas=" + totalVendas +
                ", totalDinheiro=" + totalDinheiro +
                ", totalDebito=" + totalDebito +
                ", totalCredito=" + totalCredito +
                ", totalPix=" + totalPix +
                ", lucroBruto=" + lucroBruto +
                ", totalServicos=" + totalServicos +
                ", totalMaoDeObra=" + totalMaoDeObra +
                ", totalPecas=" + totalPecas +
                ", quantidadeClientes=" + quantidadeClientes +
                ", quantidadeMotocicletasAtendidas=" + quantidadeMotocicletasAtendidas +
                ", ticketMedio=" + ticketMedio +
                '}';
    }
}

