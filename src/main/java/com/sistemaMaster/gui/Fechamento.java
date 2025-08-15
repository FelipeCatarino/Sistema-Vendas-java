package com.sistemaMaster.gui;

import com.sistemaMaster.gui.controller.FechamentoController;
import com.sistemaMaster.to.FechamentoDia;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fechamento extends JFrame implements IFechamentoView {

    private JLabel lblTitulo, lblData, lblVendas, lblServicos, lblLucro, lblCaixa;
    private JLabel lblDinheiro, lblDebito, lblCredito, lblPix;
    private JLabel lblMaoDeObra, lblPecas, lblClientes, lblMotocicletas, lblTicketMedio;
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
        setSize(800, 625);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        fechamentoController = new FechamentoController();
        fechamentoController.setView(this);

        cardLayout = new CardLayout();
        JPanel painelCards = new JPanel(cardLayout);

        painelPrincipal = criarPainelFechamento();
        painelHistorico = criarPainelHistorico();

        painelCards.add(painelPrincipal, "fechamento");
        painelCards.add(painelHistorico, "historico");

        add(painelCards);
        
        // Inicializa o fechamento através do controller
        fechamentoController.inicializarFechamento();
        
        setVisible(true);
    }

    private JPanel criarPainelFechamento() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(245, 245, 245));
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Inicializa com fechamento vazio - será atualizado pelo controller
        if (fechamentoAtual == null) {
            fechamentoAtual = new FechamentoDia(new Date(), 0, 0, 0, 0, 0, 0, 0);
        }

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
        
        // Dados específicos da oficina de motocicletas
        lblMaoDeObra = criarLabel("🔧 Mão de Obra: R$ " + String.format("%.2f", fechamentoAtual.getTotalMaoDeObra()), 16,
                Font.PLAIN, new Color(128, 128, 128));
        lblPecas = criarLabel("⚙️ Peças: R$ " + String.format("%.2f", fechamentoAtual.getTotalPecas()), 16,
                Font.PLAIN, new Color(128, 128, 128));
        lblClientes = criarLabel("👥 Clientes Atendidos: " + fechamentoAtual.getQuantidadeClientes(), 16,
                Font.PLAIN, new Color(128, 128, 128));
        lblMotocicletas = criarLabel("🏍️ Motocicletas Atendidas: " + fechamentoAtual.getQuantidadeMotocicletasAtendidas(), 16,
                Font.PLAIN, new Color(128, 128, 128));
        lblTicketMedio = criarLabel("💰 Ticket Médio: R$ " + String.format("%.2f", fechamentoAtual.getTicketMedio()), 16,
                Font.PLAIN, new Color(128, 128, 128));

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
        btnSalvarFechamento.addActionListener(e -> fechamentoController.processarSalvarFechamento());

        btnGerarPDF = criarBotao("Gerar PDF", new Color(255, 152, 0), Color.WHITE);
        btnGerarPDF.addActionListener(e -> fechamentoController.processarGerarPDF());

        btnHistorico = criarBotao("Histórico", new Color(33, 150, 243), Color.WHITE);
        btnHistorico.addActionListener(e -> {
            fechamentoController.processarCarregarHistorico();
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
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        // Dados específicos da oficina
        painel.add(lblMaoDeObra);
        painel.add(lblPecas);
        painel.add(lblClientes);
        painel.add(lblMotocicletas);
        painel.add(lblTicketMedio);
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
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

    // Implementação dos métodos da interface IFechamentoView
    
    @Override
    public void atualizarDadosFechamento(FechamentoDia fechamento) {
        this.fechamentoAtual = fechamento;
        
        // Atualiza todos os labels com os novos dados
        lblVendas.setText("Total de Vendas: R$ " + String.format("%.2f", fechamento.getTotalVendas()));
        lblServicos.setText("Vendas Realizadas: " + fechamento.getTotalServicos());
        lblLucro.setText("Lucro Bruto: R$ " + String.format("%.2f", fechamento.getLucroBruto()));
        lblCaixa.setText("Total em Caixa: R$ " + String.format("%.2f", fechamento.getTotalCaixa()));
        
        // Dados específicos da oficina
        lblMaoDeObra.setText("🔧 Mão de Obra: R$ " + String.format("%.2f", fechamento.getTotalMaoDeObra()));
        lblPecas.setText("⚙️ Peças: R$ " + String.format("%.2f", fechamento.getTotalPecas()));
        lblClientes.setText("👥 Clientes Atendidos: " + fechamento.getQuantidadeClientes());
        lblMotocicletas.setText("🏍️ Motocicletas Atendidas: " + fechamento.getQuantidadeMotocicletasAtendidas());
        lblTicketMedio.setText("💰 Ticket Médio: R$ " + String.format("%.2f", fechamento.getTicketMedio()));
        
        // Atualiza os labels de detalhes das formas de pagamento
        atualizarLabelDetalhes(lblDinheiro, "💵 Dinheiro", fechamento.getTotalDinheiro());
        atualizarLabelDetalhes(lblDebito, "💳 Débito", fechamento.getTotalDebito());
        atualizarLabelDetalhes(lblCredito, "💳 Crédito", fechamento.getTotalCredito());
        atualizarLabelDetalhes(lblPix, "📱 PIX", fechamento.getTotalPix());
        
        repaint();
    }
    
    @Override
    public void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void exibirSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public boolean exibirConfirmacao(String mensagem) {
        int opcao = JOptionPane.showConfirmDialog(this, mensagem, "Confirmação", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return opcao == JOptionPane.YES_OPTION;
    }
    
    @Override
    public void atualizarHistorico(ArrayList<FechamentoDia> fechamentos) {
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
    }
    
    @Override
    public void exibirCarregamento(boolean carregando) {
        // Implementação simples - pode ser melhorada com um spinner
        setCursor(carregando ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : 
                              Cursor.getDefaultCursor());
        
        // Desabilita botões durante carregamento
        btnSalvarFechamento.setEnabled(!carregando);
        btnGerarPDF.setEnabled(!carregando);
        btnHistorico.setEnabled(!carregando);
    }
    
    @Override
    public void confirmarAberturaPDF(String caminhoArquivo, String nomeArquivo) {
        int opcao = JOptionPane.showConfirmDialog(this,
                "PDF gerado com sucesso!\n\n" +
                        "Arquivo: " + nomeArquivo + "\n" +
                        "Local: " + new File(caminhoArquivo).getParent() + "\n\n" +
                        "Deseja abrir o arquivo agora?",
                "PDF Gerado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (opcao == JOptionPane.YES_OPTION) {
            fechamentoController.abrirPDFArquivo(caminhoArquivo);
        }
    }
    
    /**
     * Método auxiliar para atualizar os labels de detalhes das formas de pagamento
     */
    private void atualizarLabelDetalhes(JLabel label, String texto, double valor) {
        label.setText("<html><div style='text-align: center;'>" + texto + "<br><b>R$ "
                + String.format("%.2f", valor) + "</b></div></html>");
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
}
