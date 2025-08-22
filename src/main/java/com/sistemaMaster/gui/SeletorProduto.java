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

import com.sistemaMaster.dao.ProdutoDAO;
import com.sistemaMaster.to.Produto;

/**
 * Seletor de produto para ordem de serviço
 */
public class SeletorProduto extends JDialog {
    
    private Produto produtoSelecionado;
    private JTextField txtBusca;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JButton btnSelecionar;
    private JButton btnCancelar;
    private ProdutoDAO produtoDAO;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public SeletorProduto(JFrame parent) {
        super(parent, "Selecionar Produto", true);
        this.produtoDAO = new ProdutoDAO();
        initComponents();
        carregarProdutos();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setSize(700, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
        
        // Painel superior com busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelBusca.setBackground(new Color(245, 245, 245));
        
        JLabel lblBusca = new JLabel("🔍 Buscar produto:");
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
        
        // Tabela de produtos
        String[] colunas = {"Código", "Nome", "Estoque", "Preço Venda", "Fornecedor"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaProdutos.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelaProdutos.getTableHeader().setForeground(Color.WHITE);
        
        // Configurar larguras das colunas
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(60);  // Código
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(250); // Nome
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(80);  // Estoque
        tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(100); // Preço
        tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(100); // Fornecedor
        
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Produtos em Estoque",
            0, 0, new Font("Arial", Font.BOLD, 12)
        ));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(245, 245, 245));
        
        btnSelecionar = new JButton("✓ Selecionar Produto");
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
        tabelaProdutos.setRowSorter(sorter);
        
        txtBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarTabela();
            }
        });
        
        // Seleção na tabela
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnSelecionar.setEnabled(tabelaProdutos.getSelectedRow() != -1);
            }
        });
        
        // Duplo clique para selecionar
        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaProdutos.getSelectedRow() != -1) {
                    selecionarProduto();
                }
            }
        });
        
        btnSelecionar.addActionListener(e -> selecionarProduto());
        btnCancelar.addActionListener(e -> {
            produtoSelecionado = null;
            dispose();
        });
        
        // Enter para buscar
        txtBusca.addActionListener(e -> filtrarTabela());
    }
    
    private void carregarProdutos() {
        try {
            modeloTabela.setRowCount(0);
            ArrayList<Produto> produtos = produtoDAO.listarTodos();
            
            for (Produto produto : produtos) {
                Object[] linha = {
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getQuantidade(),
                    String.format("R$ %.2f", produto.getPrecoVenda()),
                    produto.getFornecedor() != null ? produto.getFornecedor().getNome() : ""
                };
                modeloTabela.addRow(linha);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar produtos: " + ex.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filtrarTabela() {
        String texto = txtBusca.getText().trim();
        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            // Filtrar por nome do produto (coluna 1)
            RowFilter<DefaultTableModel, Object> filtro = RowFilter.regexFilter(
                "(?i)" + texto, 1
            );
            sorter.setRowFilter(filtro);
        }
    }
    
    private void selecionarProduto() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada != -1) {
            try {
                int modelRow = tabelaProdutos.convertRowIndexToModel(linhaSelecionada);
                int codigo = (Integer) modeloTabela.getValueAt(modelRow, 0);
                produtoSelecionado = produtoDAO.recuperar(codigo);
                
                // Verificar se tem estoque
                if (produtoSelecionado.getQuantidade() <= 0) {
                    int confirmacao = JOptionPane.showConfirmDialog(this,
                        "Este produto está sem estoque. Deseja continuar?",
                        "Aviso", JOptionPane.YES_NO_OPTION);
                    
                    if (confirmacao != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao selecionar produto: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }
}
