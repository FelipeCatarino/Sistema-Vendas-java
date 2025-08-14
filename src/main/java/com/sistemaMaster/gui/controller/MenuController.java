package com.sistemaMaster.gui.controller;

import com.sistemaMaster.gui.DesktopPaneComImagem;
import com.sistemaMaster.gui.CadastroProduto;
import com.sistemaMaster.gui.CadastroCliente;
import com.sistemaMaster.gui.CadastroFornecedor;
import com.sistemaMaster.gui.LancamentoVenda;
import com.sistemaMaster.gui.LancamentoCompra;
import com.sistemaMaster.gui.Sobre;
import com.sistemaMaster.gui.OrdemServico;
import com.sistemaMaster.gui.Estoque;
import com.sistemaMaster.gui.Dashboard;
import com.sistemaMaster.gui.CadernoFiado;
import com.sistemaMaster.gui.Fechamento;

import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

public class MenuController {

    private JDesktopPane desktopPane;
    private JFrame menuFrame;

    public MenuController(JDesktopPane desktopPane, JFrame menuFrame) {
        this.desktopPane = desktopPane;
        this.menuFrame = menuFrame;
    }

    public void miProdutoActionPerformed(ActionEvent evt) {
        CadastroProduto c = new CadastroProduto();
        desktopPane.add(c);
        c.setVisible(true);
    }

    public void miClienteActionPerformed(ActionEvent evt) {
        CadastroCliente c = new CadastroCliente();
        desktopPane.add(c);
        c.setVisible(true);
    }

    public void miFornecedorActionPerformed(ActionEvent evt) {
        CadastroFornecedor c = new CadastroFornecedor();
        desktopPane.add(c);
        c.setVisible(true);
    }

    public void miVendaActionPerformed(ActionEvent evt) {
        LancamentoVenda v = new LancamentoVenda();
        desktopPane.add(v);
        v.setVisible(true);
    }

    public void miCompraActionPerformed(ActionEvent evt) {
        LancamentoCompra c = new LancamentoCompra();
        desktopPane.add(c);
        c.setVisible(true);
    }

    public void miSobreActionPerformed(ActionEvent evt) {
        Sobre s = new Sobre(menuFrame, true);
        s.setVisible(true);
    }

    public void miSairActionPerformed(ActionEvent evt) {
        menuFrame.dispose();
    }

    public void btOrdemServicoActionPerformed(ActionEvent evt) {
        OrdemServico ordemServico = new OrdemServico();
        desktopPane.add(ordemServico);
        ordemServico.setVisible(true);
    }

    public void btEstoqueActionPerformed(ActionEvent evt) {
        Estoque estoque = new Estoque(this.desktopPane);
        desktopPane.add(estoque);
        estoque.setVisible(true);
    }

    public void btDashboardActionPerformed(ActionEvent evt) {
        Dashboard dashboard = new Dashboard();
        desktopPane.add(dashboard);
        dashboard.setVisible(true);
    }

    public void btFiadoActionPerformed(ActionEvent evt) {
        CadernoFiado cadernoFiado = new CadernoFiado();
        desktopPane.add(cadernoFiado);
        cadernoFiado.setVisible(true);
    }

    public void btFechamentoActionPerformed(ActionEvent evt) {
        Fechamento.mostrarFechamento();
    }
}


