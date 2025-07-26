package com.sistemaMaster.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sistemaMaster.gui.controller.ControleEstoque;

import java.awt.*;
import java.sql.*;
import java.util.List;

/**
 * Classe de acesso a dados da venda
 *
 * @author Felipe
 * @version 1.0
 */

public class Estoque extends JInternalFrame {

    private JTable tabela;
    private JTextField txtBusca;
    private DefaultTableModel modelo;

    public Estoque() {
        super("Controle de Estoque", true, true, true, true);
        setSize(850, 500);
        setLayout(new BorderLayout());

        // Painel superior - busca
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBorder(new EmptyBorder(10, 10, 10, 10));
        painelTopo.setBackground(Color.WHITE);

        JLabel lblBusca = new JLabel("🔍 Buscar Produto:");
        lblBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtBusca = new JTextField();
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setBackground(new Color(52, 152, 219));
        btnBuscar.setForeground(Color.DARK_GRAY);

        JPanel painelBusca = new JPanel(new BorderLayout(5, 5));
        painelBusca.add(lblBusca, BorderLayout.WEST);
        painelBusca.add(txtBusca, BorderLayout.CENTER);
        painelBusca.add(btnBuscar, BorderLayout.EAST);

        painelTopo.add(painelBusca, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = { "Código", "Nome", "Preço Compra", "Preço Venda", "Estoque", "Código Produto" };
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta na tabela
            }
        };

        tabela = new JTable(modelo);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setRowHeight(24);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.setSelectionBackground(new Color(230, 240, 255));

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(200, 220, 255)); // fundo da linha selecionada
                    c.setForeground(new Color(30, 30, 30)); // cor do texto na seleção (cinza escuro)
                } else {
                    c.setBackground(Color.WHITE); // fundo normal
                    c.setForeground(Color.BLACK); // texto normal
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // Ação de busca
        btnBuscar.addActionListener(e -> buscarProduto());

        // Carregar todos os produtos ao abrir
        carregarProdutos();
    }

    private void carregarProdutos() {
        modelo.setRowCount(0);
        ControleEstoque controle = new ControleEstoque();

        try {
            List<Object[]> produtos = controle.listarProdutos();
            for (Object[] linha : produtos) {
                modelo.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        } catch (Exception x) {
            JOptionPane.showMessageDialog(this, "Erro geral: " + x.getMessage());
        }
    }

private void buscarProduto() {
    String busca = txtBusca.getText().trim();
    modelo.setRowCount(0);

    ControleEstoque controle = new ControleEstoque();
    List<Object[]> produtos = controle.buscarProdutos(busca);

    for (Object[] linha : produtos) {
        modelo.addRow(linha);
    }

    if (produtos.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhum produto encontrado.");
    }
}

}
