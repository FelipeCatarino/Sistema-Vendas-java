package com.sistemaMaster.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Orcamento extends JInternalFrame {
    private JButton salvarButton;
    private JButton cancelarButton;
    private JButton gerarPDFButton;
    private JTextField valorField;
    private JTextArea descricaoArea;
    private JTextField clienteField;
    private JComboBox<String> categoriaCombo;

    public Orcamento() {
        super("Orçamento", true, true, true, true);
        initialize();
    }

    private void initialize() {
        setSize(500, 400);
        setLayout(new BorderLayout(5, 5)); // Layout principal com espaçamento
        
        // 1. PAINEL SUPERIOR (NORTE) - Informações básicas
        JPanel topPanel = criarTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // 2. PAINEL DO MEIO (CENTRO) - Descrição detalhada
        JPanel centerPanel = criarCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // 3. PAINEL INFERIOR (SUL) - Botões de ação
        JPanel bottomPanel = criarBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Adiciona um pouco de espaço ao redor dos painéis
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    private JPanel criarTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Informações do Orçamento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Linha 1 - Cliente
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cliente:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        clienteField = new JTextField(20);
        panel.add(clienteField, gbc);
        
        // Linha 2 - Valor
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Valor (R$):"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        valorField = new JTextField(10);
        panel.add(valorField, gbc);
        
        // Linha 3 - Categoria
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Categoria:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] categorias = {"Selecione", "Serviço", "Produto", "Projeto", "Manutenção"};
        categoriaCombo = new JComboBox<>(categorias);
        panel.add(categoriaCombo, gbc);
        
        return panel;
    }
    
    private JPanel criarCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Descrição Detalhada"));
        
        descricaoArea = new JTextArea(8, 30);
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(descricaoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel criarBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");
        gerarPDFButton = new JButton("Gerar PDF");

        // Estilizando os botões
        salvarButton.setPreferredSize(new Dimension(100, 30));
        cancelarButton.setPreferredSize(new Dimension(100, 30));
        gerarPDFButton.setPreferredSize(new Dimension(100, 30));

        panel.add(salvarButton);
        panel.add(cancelarButton);
        panel.add(gerarPDFButton);

        return panel;
    }

    // Métodos para adicionar listeners (Controller irá configurar)
    public void addSalvarListener(ActionListener listener) {
        salvarButton.addActionListener(listener);
    }
    
    public void addCancelarListener(ActionListener listener) {
        cancelarButton.addActionListener(listener);
    }
    
    // Métodos para obter dados da view
    public String getValor() {
        return valorField.getText();
    }
    
    public String getDescricao() {
        return descricaoArea.getText();
    }
    
    public String getCliente() {
        return clienteField.getText();
    }
    
    public String getCategoria() {
        return (String) categoriaCombo.getSelectedItem();
    }
    
    // Métodos para definir dados na view
    public void setValor(String valor) {
        valorField.setText(valor);
    }
    
    public void setDescricao(String descricao) {
        descricaoArea.setText(descricao);
    }
    
    public void setCliente(String cliente) {
        clienteField.setText(cliente);
    }
    
    public void setCategoria(String categoria) {
        categoriaCombo.setSelectedItem(categoria);
    }
    
    // Método para limpar o formulário
    public void limparFormulario() {
        clienteField.setText("");
        valorField.setText("");
        descricaoArea.setText("");
        categoriaCombo.setSelectedIndex(0);
    }
}