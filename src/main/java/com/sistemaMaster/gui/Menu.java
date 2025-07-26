package com.sistemaMaster.gui;

import com.sistemaMaster.gui.DesktopPaneComImagem;

/**
 * Janela menu da aplicação
 *
 * @author Juliano
 */
public class Menu extends javax.swing.JFrame {

    public Menu() {
        initComponents();
    }

    private void initComponents() {

        toolBar = new javax.swing.JToolBar();
        btVenda = new javax.swing.JButton();
        btOrdemServico = new javax.swing.JButton();
        btCompra = new javax.swing.JButton();
        btProduto = new javax.swing.JButton();
        btEstoque = new javax.swing.JButton();
        btCliente = new javax.swing.JButton();
        btDashboard = new javax.swing.JButton();
        btBusca = new javax.swing.JButton();
        btFiado = new javax.swing.JButton();
        btFornecedor = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        btFechamento = new javax.swing.JButton();
        desktopPane = new DesktopPaneComImagem(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fundo2.jpg")).getImage());

        menuBar = new javax.swing.JMenuBar();
        menuCadastros = new javax.swing.JMenu();
        miProduto = new javax.swing.JMenuItem();
        miCliente = new javax.swing.JMenuItem();
        miCliente = new javax.swing.JMenuItem();
        miFornecedor = new javax.swing.JMenuItem();
        menuMovimentos = new javax.swing.JMenu();
        miVenda = new javax.swing.JMenuItem();
        miCompra = new javax.swing.JMenuItem();
        menuSistema = new javax.swing.JMenu();
        miSobre = new javax.swing.JMenuItem();
        miSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Vendas");
        setUndecorated(true);
        toolBar.setFloatable(false);

        btVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/venda.png"))); // NOI18N
        btVenda.setText("Venda");
        btVenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btVenda.setFocusable(false);
        btVenda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btVenda.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btVenda.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/venda-foco.png"))); // NOI18N
        btVenda.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btVenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVendaActionPerformed(evt);
            }
        });
        toolBar.add(btVenda);

        btOrdemServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/ordem.png"))); // NOI18N
        btOrdemServico.setText("OS");
        btOrdemServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btOrdemServico.setFocusable(false);
        btOrdemServico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btOrdemServico.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btOrdemServico.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/ordem-foco.png"))); // NOI18N
        btOrdemServico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btOrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdemServico ordemServico = new OrdemServico();
                desktopPane.add(ordemServico);
                ordemServico.setVisible(true);
            }
        });
        toolBar.add(btOrdemServico);

        btCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/compra.png"))); // NOI18N
        btCompra.setText("Compra");
        btCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCompra.setFocusable(false);
        btCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCompra.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btCompra.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/compra-foco.png"))); // NOI18N
        btCompra.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btCompra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCompraActionPerformed(evt);
            }
        });
        toolBar.add(btCompra);

        btProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/produto.png"))); // NOI18N
        btProduto.setText("Produto");
        btProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btProduto.setFocusable(false);
        btProduto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btProduto.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btProduto.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/produto-foco.png"))); // NOI18N
        btProduto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miProdutoActionPerformed(evt);
            }
        });
        toolBar.add(btProduto);

        btEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/estoque.png"))); // NOI18N
        btEstoque.setText("Estoque");
        btEstoque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btEstoque.setFocusable(false);
        btEstoque.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btEstoque.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btEstoque.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/estoque-foco.png"))); // NOI18N
        btEstoque.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Estoque estoque = new Estoque();
                desktopPane.add(estoque);
                estoque.setVisible(true);
            }
        });
        toolBar.add(btEstoque);

        btCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/cliente.png"))); // NOI18N
        btCliente.setText("Cliente");
        btCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCliente.setFocusable(false);
        btCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCliente.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btCliente.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/cliente-foco.png"))); // NOI18N
        btCliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miClienteActionPerformed(evt);
            }
        });
        toolBar.add(btCliente);

        btFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fornecedor.png"))); // NOI18N
        btFornecedor.setText("Fornecedor");
        btFornecedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btFornecedor.setFocusable(false);
        btFornecedor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFornecedor.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btFornecedor.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fornecedor-foco.png"))); // NOI18N
        btFornecedor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFornecedorActionPerformed(evt);
            }
        });
        toolBar.add(btFornecedor);

        btDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/dashboard.png"))); // NOI18N
        btDashboard.setText("Dashboard");
        btDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btDashboard.setFocusable(false);
        btDashboard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btDashboard.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btDashboard.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/dashboard-foco.png"))); // NOI18N
        btDashboard.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dashboard dashboard = new Dashboard();
                desktopPane.add(dashboard);
                dashboard.setVisible(true);
            }
        });
        toolBar.add(btDashboard);

        btFiado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fiado.png")));
        btFiado.setText("Fiado");
        btFiado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btFiado.setFocusable(false);
        btFiado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFiado.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btFiado.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fiado-foco.png")));
        btFiado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btFiado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadernoFiado cadernoFiado = new CadernoFiado();
                desktopPane.add(cadernoFiado);
                cadernoFiado.setVisible(true);
            }
        });
        toolBar.add(btFiado);

        btFechamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fechamento.png"))); // NOI18N
        btFechamento.setText("Fechamento");
        btFechamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btFechamento.setFocusable(false);
        btFechamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFechamento.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btFechamento.setRolloverIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/fechamento-foco.png"))); // NOI18N
        btFechamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btFechamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Fechamento fechamento = new Fechamento();
                fechamento.mostrarFechamento();
            }
        });
        toolBar.add(btFechamento);

        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/sair.png"))); // NOI18N
        btSair.setText("Sair");
        btSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSair.setFocusable(false);
        btSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSair.setMargin(new java.awt.Insets(2, 12, 2, 12));
        btSair.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/sair-foco.png"))); // NOI18N
        btSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSairActionPerformed(evt);
            }
        });
        toolBar.add(btSair);

        getContentPane().add(toolBar, java.awt.BorderLayout.PAGE_START);

        desktopPane.setOpaque(false);
        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        menuCadastros.setText("Cadastros");

        miProduto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        miProduto.setText("Cadastrar Produto");
        miProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miProdutoActionPerformed(evt);
            }
        });
        menuCadastros.add(miProduto);

        miCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        miCliente.setText("Cadastrar Cliente");
        miCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miClienteActionPerformed(evt);
            }
        });
        menuCadastros.add(miCliente);

        miFornecedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        miFornecedor.setText("Cadastrar Fornecedor");
        miFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFornecedorActionPerformed(evt);
            }
        });
        menuCadastros.add(miFornecedor);

        menuBar.add(menuCadastros);

        menuMovimentos.setText("Movimentos");

        miVenda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        miVenda.setText("Registrar Venda");
        miVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVendaActionPerformed(evt);
            }
        });
        menuMovimentos.add(miVenda);

        miCompra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        miCompra.setText("Registrar Compra");
        miCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCompraActionPerformed(evt);
            }
        });
        menuMovimentos.add(miCompra);

        menuBar.add(menuMovimentos);

        menuSistema.setText("Sistema");

        miSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        miSobre.setText("Sobre");
        miSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSobreActionPerformed(evt);
            }
        });
        menuSistema.add(miSobre);

        miSair.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        miSair.setText("Sair");
        miSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSairActionPerformed(evt);
            }
        });
        menuSistema.add(miSair);

        menuBar.add(menuSistema);

        setJMenuBar(menuBar);

        setSize(new java.awt.Dimension(900, 700));
        setLocationRelativeTo(null);
    }

    private void miProdutoActionPerformed(java.awt.event.ActionEvent evt) {
        CadastroProduto c = new CadastroProduto();
        desktopPane.add(c);
        c.setVisible(true);
    }

    private void miClienteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miClienteActionPerformed
        CadastroCliente c = new CadastroCliente();
        desktopPane.add(c);
        c.setVisible(true);
    }

    private void miFornecedorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miFornecedorActionPerformed
        CadastroFornecedor c = new CadastroFornecedor();
        desktopPane.add(c);
        c.setVisible(true);
    }

    private void miVendaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miVendaActionPerformed
        LancamentoVenda v = new LancamentoVenda();
        desktopPane.add(v);
        v.setVisible(true);
    }

    private void miCompraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miCompraActionPerformed
        LancamentoCompra c = new LancamentoCompra();
        desktopPane.add(c);
        c.setVisible(true);
    }// GEN-LAST:event_miCompraActionPerformed

    private void miSobreActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miSobreActionPerformed
        Sobre s = new Sobre(this, true);
        s.setVisible(true);
    }// GEN-LAST:event_miSobreActionPerformed

    private void miSairActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miSairActionPerformed
        System.exit(0);
    }// GEN-LAST:event_miSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCliente;
    private javax.swing.JButton btCompra;
    private javax.swing.JButton btOrdemServico;
    private javax.swing.JButton btFornecedor;
    private javax.swing.JButton btDashboard;
    private javax.swing.JButton btBusca;
    private javax.swing.JButton btFiado;
    private javax.swing.JButton btProduto;
    private javax.swing.JButton btEstoque;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btFechamento;;
    private javax.swing.JButton btVenda;
    private com.sistemaMaster.gui.DesktopPaneComImagem desktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuCadastros;
    private javax.swing.JMenu menuMovimentos;
    private javax.swing.JMenu menuSistema;
    private javax.swing.JMenuItem miCliente;
    private javax.swing.JMenuItem miCompra;
    private javax.swing.JMenuItem miFornecedor;
    private javax.swing.JMenuItem miProduto;
    private javax.swing.JMenuItem miSair;
    private javax.swing.JMenuItem miSobre;
    private javax.swing.JMenuItem miVenda;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
