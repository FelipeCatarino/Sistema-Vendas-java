package com.sistemaMaster.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.sistemaMaster.dao.ClienteDAO;
import com.sistemaMaster.to.Cliente;

/**
 * Seletor de cliente para ordem de serviço
 */
public class SeletorCliente extends JDialog {
    
    private Cliente clienteSelecionado;
    private JTextField txtBusca;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private JButton btnSelecionar;
    private JButton btnCancelar;
    private ClienteDAO clienteDAO;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public SeletorCliente(JFrame parent) {
        super(parent, "Selecionar Cliente", true);
        this.clienteDAO = new ClienteDAO();
        initComponents();
        carregarClientes();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
        
        // Painel superior com busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelBusca.setBackground(new Color(245, 245, 245));
        
        JLabel lblBusca = new JLabel("🔍 Buscar por nome, modelo ou placa:");
        lblBusca.setFont(new Font("Arial", Font.BOLD, 14));
        painelBusca.add(lblBusca);
        
        txtBusca = new JTextField(25);
        txtBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        painelBusca.add(txtBusca);
        
        add(painelBusca, BorderLayout.NORTH);
        
        // Tabela de clientes
        String[] colunas = {"Código", "Nome", "Telefone", "Placa", "Modelo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaClientes.setRowHeight(25);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaClientes.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelaClientes.getTableHeader().setForeground(Color.WHITE);
        
        // Configurar larguras das colunas
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(60);  // Código
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(200); // Nome
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(120); // Telefone
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(100); // Placa
        tabelaClientes.getColumnModel().getColumn(4).setPreferredWidth(120); // Modelo
        
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Clientes Cadastrados",
            0, 0, new Font("Arial", Font.BOLD, 12)
        ));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(245, 245, 245));
        
        btnSelecionar = new JButton("✓ Selecionar Cliente");
        btnSelecionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSelecionar.setBackground(new Color(76, 175, 80));
        btnSelecionar.setForeground(Color.WHITE);
        btnSelecionar.setPreferredSize(new Dimension(160, 35));
        btnSelecionar.setFocusPainted(false);
        btnSelecionar.setBorderPainted(false);
        btnSelecionar.setEnabled(false);
        
        btnCancelar = new JButton("✖ Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        
        painelBotoes.add(btnSelecionar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        configurarEventos();
    }
    
    private void configurarEventos() {
        // Configurar filtro de busca
        sorter = new TableRowSorter<>(modeloTabela);
        tabelaClientes.setRowSorter(sorter);
        
        txtBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarTabela();
            }
        });
        
        // Seleção na tabela
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnSelecionar.setEnabled(tabelaClientes.getSelectedRow() != -1);
            }
        });
        
        // Duplo clique para selecionar
        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaClientes.getSelectedRow() != -1) {
                    selecionarCliente();
                }
            }
        });
        
        btnSelecionar.addActionListener(e -> selecionarCliente());
        btnCancelar.addActionListener(e -> {
            clienteSelecionado = null;
            dispose();
        });
        
        // Enter para buscar
        txtBusca.addActionListener(e -> filtrarTabela());
    }
    
    private void carregarClientes() {
        try {
            modeloTabela.setRowCount(0);
            ArrayList<Cliente> clientes = clienteDAO.listarTodos();
            
            for (Cliente cliente : clientes) {
                Object[] linha = {
                    cliente.getCodigo(),
                    cliente.getNome(),
                    cliente.getTelefone() != null ? cliente.getTelefone() : "",
                    cliente.getPlaca() != null ? cliente.getPlaca() : "",
                    cliente.getModeloMoto() != null ? cliente.getModeloMoto() : ""
                };
                modeloTabela.addRow(linha);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar clientes: " + ex.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filtrarTabela() {
        String texto = txtBusca.getText().trim();
        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            // Filtrar por nome, placa ou modelo (colunas 1, 3, 4)
            RowFilter<DefaultTableModel, Object> filtro = RowFilter.regexFilter(
                "(?i)" + texto, 1, 3, 4
            );
            sorter.setRowFilter(filtro);
        }
    }
    
    private void selecionarCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada != -1) {
            try {
                int modelRow = tabelaClientes.convertRowIndexToModel(linhaSelecionada);
                int codigo = (Integer) modeloTabela.getValueAt(modelRow, 0);
                clienteSelecionado = clienteDAO.recuperar(codigo);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao selecionar cliente: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }
}
