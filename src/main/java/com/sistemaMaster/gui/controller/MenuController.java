package com.sistemaMaster.gui.controller;

import java.awt.event.ActionEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import com.sistemaMaster.gui.CadastroCliente;
import com.sistemaMaster.gui.CadastroFornecedor;
import com.sistemaMaster.gui.CadastroProduto;
import com.sistemaMaster.gui.CadernoFiado;
import com.sistemaMaster.gui.Dashboard;
import com.sistemaMaster.gui.Estoque;
import com.sistemaMaster.gui.Fechamento;
import com.sistemaMaster.gui.LancamentoCompra;
import com.sistemaMaster.gui.LancamentoVenda;
import com.sistemaMaster.gui.Orcamento;
import com.sistemaMaster.gui.OrdemServicoForm;
import com.sistemaMaster.gui.Sobre;

public class MenuController {

    private JDesktopPane desktopPane;
    private JFrame menuFrame;
    private CadastroCliente cadastroClienteInstance = null;

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
        // Verifica se já existe uma instância da tela de cadastro de cliente
        if (cadastroClienteInstance != null && !cadastroClienteInstance.isClosed()) {
            // Se existe e não está fechada, apenas traz ela para frente
            try {
                cadastroClienteInstance.setSelected(true);
                cadastroClienteInstance.toFront();
            } catch (Exception e) {
                // Se houver erro ao trazer para frente, cria uma nova instância
                criarNovaInstanciaCliente();
            }
        } else {
            // Se não existe ou está fechada, cria uma nova instância
            criarNovaInstanciaCliente();
        }
    }

    private void criarNovaInstanciaCliente() {
        cadastroClienteInstance = new CadastroCliente();
        desktopPane.add(cadastroClienteInstance);
        cadastroClienteInstance.setVisible(true);
        try {
            cadastroClienteInstance.setSelected(true);
        } catch (Exception e) {
            // Ignora erro se não conseguir selecionar
        }
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

    public void miOrcamentoActionPerformed(ActionEvent evt) {
        Orcamento o = new Orcamento();
        desktopPane.add(o);
        o.setVisible(true);
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
        OrdemServicoForm ordemServico = OrdemServicoForm.getInstance();
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


