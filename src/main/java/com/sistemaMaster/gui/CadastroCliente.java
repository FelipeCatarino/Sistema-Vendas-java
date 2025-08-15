package com.sistemaMaster.gui;

import com.sistemaMaster.gui.controller.CadastroClienteController;
import com.sistemaMaster.gui.tm.ClienteTableModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 * Janela de cadastro de cliente - Sistema de Vendas para Oficina Mecânica
 * 
 * Interface gráfica para cadastro completo de clientes.
 * Implementa o padrão MVC delegando a lógica para o CadastroClienteController.
 *
 * @author Felipe da Costa Catarino - Implementação completa e melhorias
 * @author Juliano Denner da Rocha - Interface base original
 */
public class CadastroCliente extends javax.swing.JInternalFrame implements CadastroClienteController.CadastroClienteView {

    private CadastroClienteController controller;

    public CadastroCliente() {
        controller = new CadastroClienteController(this);
        initComponents();
        controller.setComponentes(ftfNome, ftfTelefone, ftfPlaca, ftfModeloMoto, 
                                 ftfQuilometragemAtual, ftfObservacao, ftfPesquisa, tbGrade);
        habilitarFormulario(false);
        controller.carregarGrade();
    }

    // Implementação dos métodos da interface CadastroClienteView
    @Override
    public void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }

    @Override
    public void focarCampo(javax.swing.JFormattedTextField campo) {
        campo.requestFocus();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnBarraFerramentas = new javax.swing.JPanel();
        barraFerramentas = new javax.swing.JToolBar();
        btNovo = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        pnConteudo = new javax.swing.JPanel();
        pnPesquisa = new javax.swing.JPanel();
        lbPesquisar = new javax.swing.JLabel();
        ftfPesquisa = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        btLimparPesquisa = new javax.swing.JButton();
        pnForm = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbTelefone = new javax.swing.JLabel();
        lbPlaca = new javax.swing.JLabel();
        lbModeloMoto = new javax.swing.JLabel();
        lbQuilometragemAtual = new javax.swing.JLabel();
        lbObservacao = new javax.swing.JLabel();
        ftfNome = new javax.swing.JFormattedTextField();
        ftfTelefone = new javax.swing.JFormattedTextField();
        ftfPlaca = new javax.swing.JFormattedTextField();
        ftfModeloMoto = new javax.swing.JFormattedTextField();
        ftfQuilometragemAtual = new javax.swing.JFormattedTextField();
        ftfObservacao = new javax.swing.JTextArea();
        scrollObservacao = new javax.swing.JScrollPane();
        spGrade = new javax.swing.JScrollPane();
        tbGrade = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Cadastro de clientes");

        pnBarraFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 0, 10));
        pnBarraFerramentas.setOpaque(false);
        pnBarraFerramentas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        barraFerramentas.setFloatable(false);

        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/novo.png"))); // NOI18N
        btNovo.setText("Novo");
        btNovo.setFocusable(false);
        btNovo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btNovo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btNovo.setMargin(new java.awt.Insets(2, 8, 2, 8));
        btNovo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/novo-foco.png"))); // NOI18N
        btNovo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });
        barraFerramentas.add(btNovo);

        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/salvar.png"))); // NOI18N
        btSalvar.setText("Salvar");
        btSalvar.setFocusable(false);
        btSalvar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSalvar.setMargin(new java.awt.Insets(2, 8, 2, 8));
        btSalvar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/salvar-foco.png"))); // NOI18N
        btSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });
        barraFerramentas.add(btSalvar);

        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/excluir.png"))); // NOI18N
        btExcluir.setText("Excluir");
        btExcluir.setFocusable(false);
        btExcluir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btExcluir.setMargin(new java.awt.Insets(2, 8, 2, 8));
        btExcluir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/excluir-foco.png"))); // NOI18N
        btExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });
        barraFerramentas.add(btExcluir);

        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/cancelar.png"))); // NOI18N
        btCancelar.setText("Cancelar");
        btCancelar.setFocusable(false);
        btCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCancelar.setMargin(new java.awt.Insets(2, 8, 2, 8));
        btCancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sistemaMaster/gui/img/cancelar-foco.png"))); // NOI18N
        btCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });
        barraFerramentas.add(btCancelar);

        pnBarraFerramentas.add(barraFerramentas);

        getContentPane().add(pnBarraFerramentas, java.awt.BorderLayout.PAGE_START);

        // Configuração do painel de pesquisa
        pnPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 153, 255)));
        pnPesquisa.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lbPesquisar.setText("Pesquisar por nome, placa ou modelo:");
        pnPesquisa.add(lbPesquisar);

        ftfPesquisa.setColumns(20);
        ftfPesquisa.setToolTipText("Digite o nome do cliente, placa ou modelo da moto");
        ftfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftfPesquisaKeyReleased(evt);
            }
        });
        pnPesquisa.add(ftfPesquisa);

        btPesquisar.setText("Pesquisar");
        btPesquisar.setToolTipText("Clique para pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });
        pnPesquisa.add(btPesquisar);

        btLimparPesquisa.setText("Limpar");
        btLimparPesquisa.setToolTipText("Clique para limpar a pesquisa e mostrar todos os clientes");
        btLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparPesquisaActionPerformed(evt);
            }
        });
        pnPesquisa.add(btLimparPesquisa);

        pnConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnConteudo.setLayout(new java.awt.BorderLayout());

        pnConteudo.add(pnPesquisa, java.awt.BorderLayout.PAGE_START);

        pnForm.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0), javax.swing.BorderFactory.createTitledBorder(null, "Formulário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 153, 255)))); // NOI18N
        pnForm.setOpaque(false);
        pnForm.setLayout(new java.awt.GridBagLayout());

        lbNome.setText("Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(lbNome, gridBagConstraints);

        lbTelefone.setText("Telefone:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(lbTelefone, gridBagConstraints);

        lbPlaca.setText("Placa:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(lbPlaca, gridBagConstraints);

        lbModeloMoto.setText("Modelo Moto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(lbModeloMoto, gridBagConstraints);

        lbQuilometragemAtual.setText("Quilometragem:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(lbQuilometragemAtual, gridBagConstraints);

        lbObservacao.setText("Observação:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(lbObservacao, gridBagConstraints);

        ftfNome.setColumns(25);
        ftfNome.setPreferredSize(new java.awt.Dimension(200, 25));
        ftfNome.setMinimumSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(ftfNome, gridBagConstraints);

        ftfTelefone.setColumns(15);
        ftfTelefone.setPreferredSize(new java.awt.Dimension(200, 25));
        ftfTelefone.setMinimumSize(new java.awt.Dimension(200, 25));
        try {
            ftfTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfTelefone.setValue(new String());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(ftfTelefone, gridBagConstraints);

        ftfPlaca.setColumns(10);
        ftfPlaca.setPreferredSize(new java.awt.Dimension(200, 25));
        ftfPlaca.setMinimumSize(new java.awt.Dimension(200, 25));
        ftfPlaca.setValue(new String());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(ftfPlaca, gridBagConstraints);

        ftfModeloMoto.setColumns(20);
        ftfModeloMoto.setPreferredSize(new java.awt.Dimension(200, 25));
        ftfModeloMoto.setMinimumSize(new java.awt.Dimension(200, 25));
        ftfModeloMoto.setValue(new String());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(ftfModeloMoto, gridBagConstraints);

        ftfQuilometragemAtual.setColumns(10);
        ftfQuilometragemAtual.setPreferredSize(new java.awt.Dimension(200, 25));
        ftfQuilometragemAtual.setMinimumSize(new java.awt.Dimension(200, 25));
        ftfQuilometragemAtual.setValue(0);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(ftfQuilometragemAtual, gridBagConstraints);

        ftfObservacao.setColumns(30);
        ftfObservacao.setRows(3);
        ftfObservacao.setLineWrap(true);
        ftfObservacao.setWrapStyleWord(true);
        scrollObservacao.setViewportView(ftfObservacao);
        scrollObservacao.setPreferredSize(new java.awt.Dimension(250, 75));
        scrollObservacao.setMinimumSize(new java.awt.Dimension(250, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnForm.add(scrollObservacao, gridBagConstraints);

        pnConteudo.add(pnForm, java.awt.BorderLayout.WEST);

        tbGrade.setAutoCreateRowSorter(true);
        tbGrade.setModel(new ClienteTableModel());
        tbGrade.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbGrade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGradeMouseClicked(evt);
            }
        });
        spGrade.setViewportView(tbGrade);
        spGrade.setPreferredSize(new java.awt.Dimension(500, 300));

        pnConteudo.add(spGrade, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnConteudo, java.awt.BorderLayout.CENTER);

        setBounds(10, 10, 800, 600);
    }

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        controller.btNovoActionPerformed(evt);
    }//GEN-LAST:event_btNovoActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        controller.btSalvarActionPerformed(evt);
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        controller.btExcluirActionPerformed(evt);
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        controller.btCancelarActionPerformed(evt);
    }//GEN-LAST:event_btCancelarActionPerformed

    private void tbGradeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGradeMouseClicked
        controller.tbGradeMouseClicked(evt);
    }//GEN-LAST:event_tbGradeMouseClicked

    private void ftfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {
        controller.ftfPesquisaKeyReleased(evt);
    }

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {
        controller.btPesquisarActionPerformed(evt);
    }

    private void btLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {
        controller.btLimparPesquisaActionPerformed(evt);
    }

    public void habilitarFormulario(boolean ativo) {
        btNovo.setEnabled(!ativo);
        btSalvar.setEnabled(ativo);
        btExcluir.setEnabled(ativo);
        btCancelar.setEnabled(ativo);
        ftfNome.setEnabled(ativo);
        ftfTelefone.setEnabled(ativo);
        ftfPlaca.setEnabled(ativo);
        ftfModeloMoto.setEnabled(ativo);
        ftfQuilometragemAtual.setEnabled(ativo);
        ftfObservacao.setEnabled(ativo);
        tbGrade.setEnabled(!ativo);
        ftfPesquisa.setEnabled(!ativo);
        btPesquisar.setEnabled(!ativo);
        btLimparPesquisa.setEnabled(!ativo);

        if (!ativo) {
            limpaFormulario();
        }
    }

    public void limpaFormulario() {
        ftfNome.setValue("");
        ftfTelefone.setValue("");
        ftfPlaca.setValue("");
        ftfModeloMoto.setValue("");
        ftfQuilometragemAtual.setValue(0);
        ftfObservacao.setText("");
    }



    public void carregarGrade() {
        controller.carregarGrade();
    }

    public void pesquisarClientes() {
        controller.pesquisarClientes();
    }
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btLimparPesquisa;
    private javax.swing.JFormattedTextField ftfNome;
    private javax.swing.JFormattedTextField ftfTelefone;
    private javax.swing.JFormattedTextField ftfPlaca;
    private javax.swing.JFormattedTextField ftfModeloMoto;
    private javax.swing.JFormattedTextField ftfQuilometragemAtual;
    private javax.swing.JTextArea ftfObservacao;
    private javax.swing.JTextField ftfPesquisa;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbTelefone;
    private javax.swing.JLabel lbPlaca;
    private javax.swing.JLabel lbModeloMoto;
    private javax.swing.JLabel lbQuilometragemAtual;
    private javax.swing.JLabel lbObservacao;
    private javax.swing.JLabel lbPesquisar;
    private javax.swing.JPanel pnBarraFerramentas;
    private javax.swing.JPanel pnConteudo;
    private javax.swing.JPanel pnForm;
    private javax.swing.JPanel pnPesquisa;
    private javax.swing.JScrollPane spGrade;
    private javax.swing.JScrollPane scrollObservacao;
    private javax.swing.JTable tbGrade;

}
