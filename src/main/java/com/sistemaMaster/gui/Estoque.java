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

    private JDesktopPane desktop;

    private JTable tabela;
    private JTextField txtBusca;
    private DefaultTableModel modelo;

    public Estoque(JDesktopPane desktop) {
        super("Controle de Estoque", true, true, true, true);
        this.desktop = desktop;
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

        JPanel painelBotoes = new JPanel();
        painelTopo.add(painelBotoes, BorderLayout.SOUTH);
        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdicionar.setBackground(new Color(46, 204, 113));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.addActionListener(e -> {
            CadastroProduto telaCadastro = new CadastroProduto();
            desktop.add(telaCadastro); // adiciona no JDesktopPane principal
            telaCadastro.setVisible(true);
        });
        painelBotoes.add(btnAdicionar);

        JButton btnRemover = new JButton("Remover Produto");
        btnRemover.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRemover.setBackground(new Color(231, 76, 60));
        btnRemover.setForeground(Color.WHITE);
        btnRemover.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
                return;
            }

            int codigo = (int) modelo.getValueAt(linhaSelecionada, 0);
            String nome = (String) modelo.getValueAt(linhaSelecionada, 1);
            System.out.println("Removendo produto: " + nome + " (Código: " + codigo + ")");
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir o produto \"" + nome + "\"?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                ControleEstoque controle = new ControleEstoque();
                controle.excluirProduto(codigo);
                carregarProdutos(); // recarrega a tabela
            }
        });
        painelBotoes.add(btnRemover);

        JButton btnEditar = new JButton("Editar Produto");
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEditar.setBackground(new Color(241, 196, 15));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> {
            // Ação para editar produto
            JOptionPane.showMessageDialog(this, "Funcionalidade de edição de produto ainda não implementada.");
        });
        painelBotoes.add(btnEditar);

        JButton btnCompra = new JButton("Registrar Compra");
        btnCompra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCompra.setBackground(new Color(155, 89, 182));
        btnCompra.setForeground(Color.WHITE);
        btnCompra.addActionListener(e -> {
            LancamentoCompra telaCompra = new LancamentoCompra();
            desktop.add(telaCompra); // adiciona no JDesktopPane principal
            telaCompra.setVisible(true);

        });
        painelBotoes.add(btnCompra);

        JButton btAtualizar = new JButton("🔄");
        btAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btAtualizar.setBackground(new Color(155, 89, 182));
        btAtualizar.setForeground(Color.WHITE);
        btAtualizar.addActionListener(e -> {
            carregarProdutos(); // recarrega a tabela
            JOptionPane.showMessageDialog(this, "Tabela atualizada com sucesso!");

        });
        painelBotoes.add(btAtualizar);

        painelTopo.add(painelBusca, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = { "Código", "Nome", "Preço Compra", "Preço Venda", "Estoque", "Código Produto" };
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
