package com.sistemaMaster.gui;

import com.sistemaMaster.gui.controller.FechamentoController;
import com.sistemaMaster.to.FechamentoDia;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fechamento extends JFrame {

    private JLabel lblTitulo, lblData, lblVendas, lblServicos, lblLucro, lblCaixa;
    private JLabel lblDinheiro, lblDebito, lblCredito, lblPix;
    private JButton btnGerarPDF, btnHistorico, btnSalvarFechamento;
    private JPanel painelPrincipal, painelDetalhes, painelHistorico;
    private CardLayout cardLayout;
    private JTable tabelaHistorico;
    private JScrollPane scrollHistorico;

    // Dados do fechamento atual
    private FechamentoDia fechamentoAtual;
    private FechamentoController fechamentoController;

    public Fechamento() {
        setTitle("Fechamento de Caixa - Oficina Mecânica");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        fechamentoController = new FechamentoController();

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
        painel.setBackground(new Color(245, 245, 245));
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        calcularDadosFechamento();

        String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        lblTitulo = criarLabel("Fechamento do Dia", 28, Font.BOLD, new Color(33, 150, 243));
        lblData = criarLabel("Data: " + dataHoje, 18, Font.BOLD, Color.DARK_GRAY);
        lblVendas = criarLabel("Total de Vendas: R$ " + String.format("%.2f", fechamentoAtual.getTotalVendas()), 16,
                Font.PLAIN, Color.BLACK);
        lblServicos = criarLabel("Vendas Realizadas: " + fechamentoAtual.getTotalServicos(), 16, Font.PLAIN,
                Color.BLACK);
        lblLucro = criarLabel("Lucro Bruto: R$ " + String.format("%.2f", fechamentoAtual.getLucroBruto()), 16,
                Font.PLAIN, new Color(76, 175, 80));
        lblCaixa = criarLabel("Total em Caixa: R$ " + String.format("%.2f", fechamentoAtual.getTotalCaixa()), 18,
                Font.BOLD, new Color(255, 152, 0));

        painelDetalhes = new JPanel();
        painelDetalhes.setLayout(new GridLayout(2, 2, 20, 15));
        painelDetalhes.setBackground(new Color(245, 245, 245));
        painelDetalhes.setMaximumSize(new Dimension(700, 100));

        lblDinheiro = criarLabelDetalhes("💵 Dinheiro", fechamentoAtual.getTotalDinheiro(), new Color(76, 175, 80));
        lblDebito = criarLabelDetalhes("💳 Débito", fechamentoAtual.getTotalDebito(), new Color(33, 150, 243));
        lblCredito = criarLabelDetalhes("💳 Crédito", fechamentoAtual.getTotalCredito(), new Color(255, 152, 0));
        lblPix = criarLabelDetalhes("📱 PIX", fechamentoAtual.getTotalPix(), new Color(156, 39, 176));

        painelDetalhes.add(lblDinheiro);
        painelDetalhes.add(lblDebito);
        painelDetalhes.add(lblCredito);
        painelDetalhes.add(lblPix);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setBackground(new Color(245, 245, 245));

        btnSalvarFechamento = criarBotao("Salvar Fechamento", new Color(76, 175, 80), Color.WHITE);
        btnSalvarFechamento.addActionListener(e -> salvarFechamento());

        btnGerarPDF = criarBotao("Gerar PDF", new Color(255, 152, 0), Color.WHITE);
        btnGerarPDF.addActionListener(e -> gerarPDF());

        btnHistorico = criarBotao("Histórico", new Color(33, 150, 243), Color.WHITE);
        btnHistorico.addActionListener(e -> {
            atualizarHistorico();
            cardLayout.show((Container) getContentPane().getComponent(0), "historico");
        });

        painelBotoes.add(btnSalvarFechamento);
        painelBotoes.add(btnGerarPDF);
        painelBotoes.add(btnHistorico);

        painel.add(lblTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(lblData);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        painel.add(lblVendas);
        painel.add(lblServicos);
        painel.add(lblLucro);
        painel.add(lblCaixa);
        painel.add(Box.createRigidArea(new Dimension(0, 25)));
        painel.add(painelDetalhes);
        painel.add(Box.createRigidArea(new Dimension(0, 30)));
        painel.add(painelBotoes);

        return painel;
    }

    private JPanel criarPainelHistorico() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(245, 245, 245));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblHist = criarLabel("Histórico de Fechamentos", 24, Font.BOLD, new Color(33, 150, 243));
        lblHist.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblHist, BorderLayout.NORTH);

        // Tabela
        String[] colunas = { "Data", "Vendas (R$)", "Dinheiro (R$)", "Débito (R$)", "Crédito (R$)", "PIX (R$)",
                "Lucro (R$)", "Serviços" };
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaHistorico = new JTable(modelo);
        tabelaHistorico.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaHistorico.setRowHeight(30);
        tabelaHistorico.setBackground(Color.WHITE);
        tabelaHistorico.setSelectionBackground(new Color(33, 150, 243, 50));
        tabelaHistorico.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabelaHistorico.getTableHeader().setBackground(new Color(33, 150, 243));
        tabelaHistorico.getTableHeader().setForeground(Color.WHITE);

        scrollHistorico = new JScrollPane(tabelaHistorico);
        scrollHistorico.setBackground(Color.WHITE);
        painel.add(scrollHistorico, BorderLayout.CENTER);

        JButton btnVoltar = criarBotao("Voltar", new Color(108, 117, 125), Color.WHITE);
        btnVoltar.addActionListener(e -> cardLayout.show((Container) getContentPane().getComponent(0), "fechamento"));

        JPanel painelSul = new JPanel();
        painelSul.setBackground(new Color(245, 245, 245));
        painelSul.add(btnVoltar);

        painel.add(painelSul, BorderLayout.SOUTH);

        return painel;
    }

    private void calcularDadosFechamento() {
        try {
            fechamentoAtual = fechamentoController.calcularDadosFechamento();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar vendas do dia: " + ex.getMessage());
            // Cria um fechamento vazio em caso de erro
            fechamentoAtual = new FechamentoDia(new Date(), 0, 0, 0, 0, 0, 0, 0);
        }
    }

    private void salvarFechamento() {
        try {
            fechamentoController.salvarFechamento(fechamentoAtual);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar fechamento: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarPDF() {
        try {
            String caminhoCompleto = fechamentoController.gerarPDF(fechamentoAtual);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String nomeArquivo = "Fechamento_" + sdf.format(fechamentoAtual.getDataFechamento()) + ".pdf";
            fechamentoController.abrirPDF(caminhoCompleto, nomeArquivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar PDF: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarHistorico() {
        try {
            ArrayList<FechamentoDia> fechamentos = fechamentoController.obterHistoricoFechamentos();
            DefaultTableModel modelo = (DefaultTableModel) tabelaHistorico.getModel();
            modelo.setRowCount(0);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (FechamentoDia f : fechamentos) {
                Object[] linha = {
                        sdf.format(f.getDataFechamento()),
                        String.format("%.2f", f.getTotalVendas()),
                        String.format("%.2f", f.getTotalDinheiro()),
                        String.format("%.2f", f.getTotalDebito()),
                        String.format("%.2f", f.getTotalCredito()),
                        String.format("%.2f", f.getTotalPix()),
                        String.format("%.2f", f.getLucroBruto()),
                        f.getTotalServicos()
                };
                modelo.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar histórico: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel criarLabel(String texto, int tamanho, int estilo, Color cor) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", estilo, tamanho));
        label.setForeground(cor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel criarLabelDetalhes(String texto, double valor, Color cor) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + texto + "<br><b>R$ "
                + String.format("%.2f", valor) + "</b></div></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(cor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cor, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }

    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(corFundo);
        botao.setForeground(corTexto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setPreferredSize(new Dimension(150, 40));
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private static Fechamento instanciaUnica = null;

    public static void mostrarFechamento() {
        SwingUtilities.invokeLater(() -> {
            if (instanciaUnica == null || !instanciaUnica.isDisplayable()) {
                instanciaUnica = new Fechamento();
                instanciaUnica.setVisible(true);
                instanciaUnica.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        instanciaUnica = null;
                    }
                });
            } else {
                instanciaUnica.toFront();
                instanciaUnica.requestFocus();
            }
        });
    }
}
