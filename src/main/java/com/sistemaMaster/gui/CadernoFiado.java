package com.sistemaMaster.gui;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class CadernoFiado extends JInternalFrame {

    public CadernoFiado() {
        super("Clientes Inadimplentes", true, true, true, true);
        setSize(900, 500);
        setLayout(new BorderLayout());

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        cardsPanel.setBackground(new Color(245, 245, 245));

        cardsPanel.add(criarCard("Total Clientes Devedores", "37", "#E53935"));
        cardsPanel.add(criarCard("Valor Total Devido", "R$ 14.230,75", "#D81B60"));
        cardsPanel.add(criarCard("Último Pagamento Recebido", "02/07/2025", "#8E24AA"));

        add(cardsPanel, BorderLayout.NORTH);

        ChartPanel graficoPanel = new ChartPanel(criarGraficoDevedores());
        graficoPanel.setPreferredSize(new Dimension(800, 300));
        add(graficoPanel, BorderLayout.CENTER);
    }

    private JLabel criarCard(String titulo, String valor, String corHex) {
        JLabel label = new JLabel("<html><center>" + titulo +
                "<br><span style='font-size:22px;'>" + valor + "</span></center></html>",
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

    private JFreeChart criarGraficoDevedores() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Dados fictícios
        dataset.addValue(3200.00, "Débito", "Carlos Silva");
        dataset.addValue(2700.00, "Débito", "Ana Souza");
        dataset.addValue(1800.00, "Débito", "João Lima");
        dataset.addValue(1350.50, "Débito", "Luciana Alves");
        dataset.addValue(990.75, "Débito", "Marcos Paulo");

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 5 Clientes Inadimplentes",
                "Cliente",
                "Valor Devido (R$)",
                dataset);
        return chart;
    }


}
