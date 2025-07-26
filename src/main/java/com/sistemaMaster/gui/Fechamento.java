package com.sistemaMaster.gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Fechamento extends JFrame {

    private JLabel lblTitulo, lblData, lblVendas, lblServicos, lblLucro, lblCaixa;
    private JLabel lblDinheiro, lblDebito, lblCredito, lblPix;
    private JButton btnGerarPDF, btnHistorico;
    private JPanel painelPrincipal, painelDetalhes, painelHistorico;
    private CardLayout cardLayout;
    private JTable tabelaHistorico;
    private JScrollPane scrollHistorico;

    // Simulação de dados
    private List<FechamentoDia> historicoFechamentos = new ArrayList<>();

    public Fechamento() {
        setTitle("Fechamento de Caixa - Oficina Mecânica");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        JPanel painelCards = new JPanel(cardLayout);

        painelPrincipal = criarPainelFechamento();
        painelHistorico = criarPainelHistorico();

        painelCards.add(painelPrincipal, "fechamento");
        painelCards.add(painelHistorico, "historico");

        add(painelCards);

        setVisible(true);
    }

    private JPanel criarPainelFechamento() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(40, 44, 52));
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        int vendasDinheiro = 0, vendasDebito = 0, vendasCredito = 0, vendasPix = 0;
        double totalDinheiro = 0, totalDebito = 0, totalCredito = 0, totalPix = 0;
        double totalVendas = 0, lucro = 0, caixa = 0;
        int totalServicos = 0;

        String dataHoje = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        String dataSql = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        try {
            com.sistemaMaster.dao.Conexao c = new com.sistemaMaster.dao.Conexao();
            String sql = "SELECT v.CODIGO, v.VALORTOTAL, v.FORMA_PAGAMENTO, iv.QUANTIDADE, iv.VALORUNITARIO, p.PRECOVENDA, p.PRECOCOMPRA " +
                         "FROM TBVENDA v " +
                         "JOIN TBITEMVENDA iv ON v.CODIGO = iv.CODIGOVENDA " +
                         "JOIN TBPRODUTO p ON iv.CODIGOPRODUTO = p.CODIGO " +
                         "WHERE v.DATAVENDA = ?";
            java.sql.PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setString(1, dataSql);
            java.sql.ResultSet rs = ps.executeQuery();

            java.util.Set<Integer> vendasContadas = new java.util.HashSet<>();
            while (rs.next()) {
                int forma = rs.getInt("FORMA_PAGAMENTO");
                double valorVenda = rs.getDouble("VALORTOTAL");
                double valorUnitario = rs.getDouble("VALORUNITARIO");
                int quantidade = rs.getInt("QUANTIDADE");
                double precoVenda = rs.getDouble("PRECOVENDA");
                double precoCompra = rs.getDouble("PRECOCOMPRA");
                int codigoVenda = rs.getInt("CODIGO");

                // Soma apenas uma vez por venda para os totais por forma de pagamento
                if (!vendasContadas.contains(codigoVenda)) {
                    totalVendas += valorVenda;
                    vendasContadas.add(codigoVenda);
                    switch (forma) {
                        case 1: totalDinheiro += valorVenda; vendasDinheiro++; break;
                        case 2: totalDebito += valorVenda; vendasDebito++; break;
                        case 3: totalCredito += valorVenda; vendasCredito++; break;
                        case 4: totalPix += valorVenda; vendasPix++; break;
                    }
                }
                // Lucro bruto por item vendido
                lucro += (precoVenda - precoCompra) * quantidade;
                totalServicos++; // Simulação: cada item é um serviço
            }
            caixa = totalDinheiro + totalDebito + totalCredito + totalPix;
            c.fechar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar vendas do dia: " + ex.getMessage());
        }

        lblTitulo = criarLabel("Fechamento do Dia", 28, Font.BOLD);
        lblData = criarLabel("Data: " + dataHoje, 16, Font.PLAIN);
        lblVendas = criarLabel("Total de Vendas: R$ " + String.format("%.2f", totalVendas), 16, Font.PLAIN);
        lblServicos = criarLabel("Serviços Realizados: " + totalServicos, 16, Font.PLAIN);
        lblLucro = criarLabel("Lucro Bruto: R$ " + String.format("%.2f", lucro), 16, Font.PLAIN);
        lblCaixa = criarLabel("Dinheiro em Caixa: R$ " + String.format("%.2f", caixa), 16, Font.PLAIN);

        painelDetalhes = new JPanel();
        painelDetalhes.setLayout(new GridLayout(2, 2, 20, 10));
        painelDetalhes.setBackground(new Color(40, 44, 52));
        painelDetalhes.setMaximumSize(new Dimension(600, 80));

        lblDinheiro = criarLabel("Dinheiro: R$ " + String.format("%.2f", totalDinheiro) + " (" + vendasDinheiro + " vendas)", 15, Font.PLAIN);
        lblDebito = criarLabel("Débito: R$ " + String.format("%.2f", totalDebito) + " (" + vendasDebito + " vendas)", 15, Font.PLAIN);
        lblCredito = criarLabel("Crédito: R$ " + String.format("%.2f", totalCredito) + " (" + vendasCredito + " vendas)", 15, Font.PLAIN);
        lblPix = criarLabel("Pix: R$ " + String.format("%.2f", totalPix) + " (" + vendasPix + " vendas)", 15, Font.PLAIN);

        painelDetalhes.add(lblDinheiro);
        painelDetalhes.add(lblDebito);
        painelDetalhes.add(lblCredito);
        painelDetalhes.add(lblPix);

        btnGerarPDF = new JButton("Gerar PDF");
        btnGerarPDF.setFocusPainted(false);
        btnGerarPDF.setBackground(new Color(30, 40, 55));
        btnGerarPDF.setForeground(new Color(220, 220, 220));
        btnGerarPDF.setFont(new Font("Arial", Font.BOLD, 16));
        btnGerarPDF.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGerarPDF.setPreferredSize(new Dimension(160, 40));
        btnGerarPDF.addActionListener(e -> gerarPDF());

        btnHistorico = new JButton("Histórico de Fechamentos");
        btnHistorico.setFocusPainted(false);
        btnHistorico.setBackground(new Color(25, 32, 45));
        btnHistorico.setForeground(new Color(220, 220, 220));
        btnHistorico.setFont(new Font("Arial", Font.BOLD, 16));
        btnHistorico.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHistorico.setPreferredSize(new Dimension(220, 40));
        btnHistorico.addActionListener(e -> cardLayout.show((Container) getContentPane().getComponent(0), "historico"));

        painel.add(lblTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(lblData);
        painel.add(lblVendas);
        painel.add(lblServicos);
        painel.add(lblLucro);
        painel.add(lblCaixa);
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        painel.add(painelDetalhes);
        painel.add(Box.createRigidArea(new Dimension(0, 30)));
        painel.add(btnGerarPDF);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(btnHistorico);

        // Simula histórico
        carregarHistoricoSimulado();

        return painel;
    }

    private JPanel criarPainelHistorico() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(40, 44, 52));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblHist = criarLabel("Histórico de Fechamentos", 24, Font.BOLD);
        lblHist.setHorizontalAlignment(SwingConstants.CENTER);
        lblHist.setForeground(new Color(76, 175, 80));
        painel.add(lblHist, BorderLayout.NORTH);

        String[] colunas = {"Data", "Vendas", "Dinheiro", "Débito", "Crédito", "Pix", "Lucro", "Caixa"};
        Object[][] dados = new Object[historicoFechamentos.size()][colunas.length];
        for (int i = 0; i < historicoFechamentos.size(); i++) {
            FechamentoDia f = historicoFechamentos.get(i);
            dados[i][0] = f.data;
            dados[i][1] = f.totalVendas;
            dados[i][2] = f.dinheiro;
            dados[i][3] = f.debito;
            dados[i][4] = f.credito;
            dados[i][5] = f.pix;
            dados[i][6] = f.lucro;
            dados[i][7] = f.caixa;
        }

        tabelaHistorico = new JTable(dados, colunas);
        tabelaHistorico.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaHistorico.setRowHeight(28);
        tabelaHistorico.setBackground(new Color(224, 224, 224));
        tabelaHistorico.setForeground(new Color(33, 33, 33));
        tabelaHistorico.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tabelaHistorico.getTableHeader().setBackground(new Color(76, 175, 80));
        tabelaHistorico.getTableHeader().setForeground(Color.WHITE);

        scrollHistorico = new JScrollPane(tabelaHistorico);
        painel.add(scrollHistorico, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBackground(new Color(33, 150, 243));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setPreferredSize(new Dimension(120, 40));
        btnVoltar.addActionListener(e -> cardLayout.show((Container) getContentPane().getComponent(0), "fechamento"));

        JPanel painelSul = new JPanel();
        painelSul.setBackground(new Color(40, 44, 52));
        painelSul.add(btnVoltar);

        painel.add(painelSul, BorderLayout.SOUTH);

        return painel;
    }

    private JLabel criarLabel(String texto, int tamanho, int estilo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", estilo, tamanho));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void gerarPDF() {
        JOptionPane.showMessageDialog(this, "PDF gerado com sucesso (simulação)!");
    }

    private void carregarHistoricoSimulado() {
        historicoFechamentos.clear();
        historicoFechamentos.add(new FechamentoDia("21/07/2025", "R$ 1.380,00", "R$ 800,00", "R$ 300,00", "R$ 180,00", "R$ 100,00", "R$ 900,00", "R$ 2.300,00"));
        historicoFechamentos.add(new FechamentoDia("20/07/2025", "R$ 1.120,00", "R$ 600,00", "R$ 250,00", "R$ 170,00", "R$ 100,00", "R$ 700,00", "R$ 1.800,00"));
        historicoFechamentos.add(new FechamentoDia("19/07/2025", "R$ 950,00", "R$ 500,00", "R$ 200,00", "R$ 150,00", "R$ 100,00", "R$ 600,00", "R$ 1.400,00"));
    }

    // Classe interna para simular dados de fechamento
    private static class FechamentoDia {
        String data, totalVendas, dinheiro, debito, credito, pix, lucro, caixa;
        FechamentoDia(String data, String totalVendas, String dinheiro, String debito, String credito, String pix, String lucro, String caixa) {
            this.data = data;
            this.totalVendas = totalVendas;
            this.dinheiro = dinheiro;
            this.debito = debito;
            this.credito = credito;
            this.pix = pix;
            this.lucro = lucro;
            this.caixa = caixa;
        }
    }

    public static void mostrarFechamento() {
        SwingUtilities.invokeLater(() -> {
            Fechamento janela = new Fechamento();
            janela.setVisible(true);
        });
    }
}
