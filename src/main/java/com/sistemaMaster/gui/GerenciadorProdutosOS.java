package com.sistemaMaster.gui;

import com.sistemaMaster.to.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Componente para gerenciar produtos/peças em uma ordem de serviço
 * Interface simples para usuários leigos
 */
public class GerenciadorProdutosOS extends JPanel {
    
    private DefaultTableModel modeloProdutos;
    private JTable tabelaProdutos;
    private JButton btnAdicionarProduto;
    private JButton btnAdicionarExterno;
    private JButton btnRemoverProduto;
    private ArrayList<ItemProdutoOS> itens;
    
    public GerenciadorProdutosOS() {
        this.itens = new ArrayList<>();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(44, 44, 44));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Produtos/Peças Utilizados",
            0, 0, new Font("Arial", Font.BOLD, 12), Color.WHITE
        ));
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        painelBotoes.setBackground(new Color(44, 44, 44));
        
        btnAdicionarProduto = criarBotao("+ Produto do Estoque", new Color(33, 150, 243));
        btnAdicionarExterno = criarBotao("+ Produto Externo", new Color(76, 175, 80));
        btnRemoverProduto = criarBotao("- Remover", new Color(244, 67, 54));
        btnRemoverProduto.setEnabled(false);
        
        painelBotoes.add(btnAdicionarProduto);
        painelBotoes.add(btnAdicionarExterno);
        painelBotoes.add(btnRemoverProduto);
        
        add(painelBotoes, BorderLayout.NORTH);
        
        // Tabela de produtos
        String[] colunas = {"Código", "Descrição", "Qtd", "Valor Unit.", "Total", "Origem"};
        modeloProdutos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaProdutos = new JTable(modeloProdutos);
        tabelaProdutos.setBackground(new Color(60, 60, 60));
        tabelaProdutos.setForeground(Color.WHITE);
        tabelaProdutos.setGridColor(new Color(80, 80, 80));
        tabelaProdutos.setSelectionBackground(new Color(33, 150, 243));
        tabelaProdutos.getTableHeader().setBackground(new Color(44, 44, 44));
        tabelaProdutos.getTableHeader().setForeground(Color.WHITE);
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Configurar larguras das colunas
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(60);  // Código
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(200); // Descrição
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(50);  // Qtd
        tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(80);  // Valor Unit
        tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(80);  // Total
        tabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(80);  // Origem
        
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setPreferredSize(new Dimension(550, 150));
        scrollPane.setBackground(new Color(44, 44, 44));
        scrollPane.getViewport().setBackground(new Color(60, 60, 60));
        add(scrollPane, BorderLayout.CENTER);
        
        configurarEventos();
    }
    
    private void configurarEventos() {
        btnAdicionarProduto.addActionListener(e -> adicionarProdutoEstoque());
        btnAdicionarExterno.addActionListener(e -> adicionarProdutoExterno());
        btnRemoverProduto.addActionListener(e -> removerProduto());
        
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnRemoverProduto.setEnabled(tabelaProdutos.getSelectedRow() != -1);
            }
        });
        
        // Duplo clique para editar quantidade
        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaProdutos.getSelectedRow() != -1) {
                    editarQuantidade();
                }
            }
        });
    }
    
    private void adicionarProdutoEstoque() {
        SeletorProduto seletor = new SeletorProduto((JFrame) SwingUtilities.getWindowAncestor(this));
        seletor.setVisible(true);
        
        Produto produto = seletor.getProdutoSelecionado();
        if (produto != null) {
            String qtdStr = JOptionPane.showInputDialog(this, 
                "Quantidade a utilizar:", "1");
            
            if (qtdStr != null && !qtdStr.trim().isEmpty()) {
                try {
                    int quantidade = Integer.parseInt(qtdStr);
                    if (quantidade > 0) {
                        ItemProdutoOS item = new ItemProdutoOS();
                        item.codigoProduto = produto.getCodigo();
                        item.descricao = produto.getNome();
                        item.quantidade = quantidade;
                        item.valorUnitario = new BigDecimal(produto.getPrecoVenda());
                        item.valorTotal = item.valorUnitario.multiply(new BigDecimal(quantidade));
                        item.origem = "ESTOQUE";
                        
                        itens.add(item);
                        atualizarTabela();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void adicionarProdutoExterno() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));
        painel.add(new JLabel("Descrição:"));
        JTextField txtDescricao = new JTextField();
        painel.add(txtDescricao);
        
        painel.add(new JLabel("Quantidade:"));
        JTextField txtQuantidade = new JTextField("1");
        painel.add(txtQuantidade);
        
        painel.add(new JLabel("Valor:"));
        JTextField txtValor = new JTextField("0.00");
        painel.add(txtValor);
        
        int resultado = JOptionPane.showConfirmDialog(this, painel, 
            "Adicionar Produto Externo", JOptionPane.OK_CANCEL_OPTION);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String descricao = txtDescricao.getText().trim();
                int quantidade = Integer.parseInt(txtQuantidade.getText());
                BigDecimal valor = new BigDecimal(txtValor.getText().replace(",", "."));
                
                if (!descricao.isEmpty() && quantidade > 0 && valor.compareTo(BigDecimal.ZERO) >= 0) {
                    ItemProdutoOS item = new ItemProdutoOS();
                    item.codigoProduto = 0; // Produto externo
                    item.descricao = descricao;
                    item.quantidade = quantidade;
                    item.valorUnitario = valor;
                    item.valorTotal = valor.multiply(new BigDecimal(quantidade));
                    item.origem = "EXTERNO";
                    
                    itens.add(item);
                    atualizarTabela();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void removerProduto() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada != -1) {
            int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja remover este produto?", "Confirmação", JOptionPane.YES_NO_OPTION);
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                itens.remove(linhaSelecionada);
                atualizarTabela();
            }
        }
    }
    
    private void editarQuantidade() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada != -1) {
            ItemProdutoOS item = itens.get(linhaSelecionada);
            String novaQtdStr = JOptionPane.showInputDialog(this, 
                "Nova quantidade:", String.valueOf(item.quantidade));
            
            if (novaQtdStr != null && !novaQtdStr.trim().isEmpty()) {
                try {
                    int novaQtd = Integer.parseInt(novaQtdStr);
                    if (novaQtd > 0) {
                        item.quantidade = novaQtd;
                        item.valorTotal = item.valorUnitario.multiply(new BigDecimal(novaQtd));
                        atualizarTabela();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void atualizarTabela() {
        modeloProdutos.setRowCount(0);
        
        for (ItemProdutoOS item : itens) {
            Object[] linha = {
                item.codigoProduto > 0 ? item.codigoProduto : "-",
                item.descricao,
                item.quantidade,
                String.format("R$ %.2f", item.valorUnitario.doubleValue()),
                String.format("R$ %.2f", item.valorTotal.doubleValue()),
                item.origem
            };
            modeloProdutos.addRow(linha);
        }
    }
    
    public BigDecimal getValorTotalProdutos() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemProdutoOS item : itens) {
            total = total.add(item.valorTotal);
        }
        return total;
    }
    
    public ArrayList<ItemProdutoOS> getItens() {
        return new ArrayList<>(itens);
    }
    
    public void limpar() {
        itens.clear();
        atualizarTabela();
    }
    
    public void setItens(ArrayList<ItemProdutoOS> itens) {
        this.itens = new ArrayList<>(itens);
        atualizarTabela();
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 10));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(140, 25));
        return botao;
    }
    
    // Classe interna para representar um item de produto na OS
    public static class ItemProdutoOS {
        public int codigoProduto;
        public String descricao;
        public int quantidade;
        public BigDecimal valorUnitario;
        public BigDecimal valorTotal;
        public String origem; // "ESTOQUE" ou "EXTERNO"
    }
}
