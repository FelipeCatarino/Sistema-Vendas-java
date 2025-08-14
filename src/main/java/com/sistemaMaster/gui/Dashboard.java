package com.sistemaMaster.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sistemaMaster.dao.Conexao;

/**
 * Classe de acesso a dados do dashboard
 *
 * @author Felipe
 * @version 1.0
 */

public class Dashboard extends JInternalFrame {
  private JLabel lblVendasDia, lblVendasMes, lblVendasAno, lblClientes, lblProdutos, lblFornecedores, lblTotalVendido;

  public Dashboard() {
    super("Dashboard", true, true, true, true);
    setSize(900, 500);
    setLayout(new BorderLayout());
   

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 4, 20, 20));
    panel.setBackground(new Color(245, 245, 245));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    lblVendasDia = criarCard("Vendas do Dia", "#4CAF50");
    lblVendasMes = criarCard("Vendas do Mês", "#2196F3");
    lblVendasAno = criarCard("Vendas do Ano", "#FF9800");
    lblTotalVendido = criarCard("Total Vendido", "#9C27B0");
    lblClientes = criarCard("Clientes", "#607D8B");
    lblProdutos = criarCard("Produtos", "#009688");
    lblFornecedores = criarCard("Fornecedores", "#E91E63");

    panel.add(lblVendasDia);
    panel.add(lblVendasMes);
    panel.add(lblVendasAno);
    panel.add(lblTotalVendido);
    panel.add(lblClientes);
    panel.add(lblProdutos);
    panel.add(lblFornecedores);

    add(panel, BorderLayout.NORTH);

    // Adiciona o gráfico de barras
    ChartPanel chartPanel = new ChartPanel(criarGraficoVendas());
    chartPanel.setPreferredSize(new Dimension(800, 250));
    add(chartPanel, BorderLayout.CENTER);

    atualizarDashboard();
  }

  private JLabel criarCard(String titulo, String corHex) {
    JLabel label = new JLabel("<html><center>" + titulo + "<br><span style='font-size:22px;'>0</span></center></html>",
        SwingConstants.CENTER);
    label.setOpaque(true);
    label.setBackground(Color.decode(corHex));
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Segoe UI", Font.BOLD, 16));
    label.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.WHITE, 2),
        BorderFactory.createEmptyBorder(20, 10, 20, 10)));
    return label;
  }

  private void atualizarDashboard() {
    try (Connection conn = new Conexao().getConexao();) {
      // Datas para filtro
      LocalDate hoje = LocalDate.now();
      String dataHoje = hoje.format(DateTimeFormatter.ISO_DATE);
      String dataMes = hoje.withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE);
      String dataAno = hoje.withDayOfYear(1).format(DateTimeFormatter.ISO_DATE);

      // Vendas do dia
      double vendasDia = consultaValor(conn, "SELECT IFNULL(SUM(ValorTotalVenda),0) FROM tbvenda WHERE DataVenda = ?",
          dataHoje);
      // Vendas do mês
      double vendasMes = consultaValor(conn,
          "SELECT IFNULL(SUM(ValorTotalVenda),0) FROM tbvenda WHERE DataVenda >= ? AND DataVenda <= ?", dataMes, dataHoje);
      // Vendas do ano
      double vendasAno = consultaValor(conn,
          "SELECT IFNULL(SUM(ValorTotalVenda),0) FROM tbvenda WHERE DataVenda >= ? AND DataVenda <= ?", dataAno, dataHoje);
      // Total vendido
      double totalVendido = consultaValor(conn, "SELECT IFNULL(SUM(ValorTotalVenda),0) FROM tbvenda", (String[]) null);

      // Clientes
      int clientes = consultaInteiro(conn, "SELECT COUNT(*) FROM tbcliente");
      // Produtos
      int produtos = consultaInteiro(conn, "SELECT COUNT(*) FROM tbproduto");
      // Fornecedores
      int fornecedores = consultaInteiro(conn, "SELECT COUNT(*) FROM tbfornecedor");

      lblVendasDia.setText(formatarCard("Vendas do Dia", vendasDia));
      lblVendasMes.setText(formatarCard("Vendas do Mês", vendasMes));
      lblVendasAno.setText(formatarCard("Vendas do Ano", vendasAno));
      lblTotalVendido.setText(formatarCard("Total Vendido", totalVendido));
      lblClientes.setText(formatarCard("Clientes", clientes));
      lblProdutos.setText(formatarCard("Produtos", produtos));
      lblFornecedores.setText(formatarCard("Fornecedores", fornecedores));
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Erro ao atualizar dashboard: " + ex.getMessage());
    }
  }

  private String formatarCard(String titulo, double valor) {
    return String.format("<html><center>%s<br><span style='font-size:22px;'>R$ %.2f</span></center></html>", titulo,
        valor);
  }

  private String formatarCard(String titulo, int valor) {
    return String.format("<html><center>%s<br><span style='font-size:22px;'>%d</span></center></html>", titulo, valor);
  }

  // Métodos utilitários para consulta
  private double consultaValor(Connection conn, String sql, String... params) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          ps.setString(i + 1, params[i]);
        }
      }
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next())
          return rs.getDouble(1);
      }
    }
    return 0;
  }

  private int consultaInteiro(Connection conn, String sql) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next())
          return rs.getInt(1);
      }
    }
    return 0;
  }



  private JFreeChart criarGraficoVendas() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    try (Connection conn = new Conexao().getConexao();) {
      LocalDate hoje = LocalDate.now();
      for (int i = 6; i >= 0; i--) {
        LocalDate data = hoje.minusDays(i);
        String dataStr = data.format(DateTimeFormatter.ISO_DATE);
        double valor = consultaValor(conn, "SELECT IFNULL(SUM(ValorTotalVenda),0) FROM tbvenda WHERE DataVenda = ?",
            dataStr);
        dataset.addValue(valor, "Vendas", data.format(DateTimeFormatter.ofPattern("dd/MM")));
      }
    } catch (Exception ex) {
      // Em caso de erro, mostra zeros
      for (int i = 6; i >= 0; i--) {
        LocalDate data = LocalDate.now().minusDays(i);
        dataset.addValue(0, "Vendas", data.format(DateTimeFormatter.ofPattern("dd/MM")));
      }
    }

    JFreeChart chart = ChartFactory.createBarChart(
        "Vendas dos Últimos 7 Dias", // Título
        "Data", // Eixo X
        "Valor (R$)", // Eixo Y
        dataset);
    return chart;
  }
}