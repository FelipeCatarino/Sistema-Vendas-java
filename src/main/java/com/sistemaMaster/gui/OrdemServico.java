package com.sistemaMaster.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdemServico extends JFrame {

    private JTextField txtNomeCliente, txtPlacaVeiculo, txtTelefone;
    private JTextArea txtDescricaoServico;
    private JComboBox<String> comboPrioridade;
    private JButton btnGerarOS;

    public OrdemServico() {
        setTitle("Ordem de Serviço - Oficina Mecânica");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(new Color(44, 44, 44));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = criarLabel("Nova Ordem de Serviço", 20, true);
        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        txtNomeCliente = criarCampoTexto("Nome do Cliente:", painelPrincipal);
        txtTelefone = criarCampoTexto("Telefone de Contato:", painelPrincipal);
        txtPlacaVeiculo = criarCampoTexto("Placa do Veículo:", painelPrincipal);

        painelPrincipal.add(criarLabel("Prioridade:", 14, false));
        comboPrioridade = new JComboBox<>(new String[]{"Normal", "Alta", "Urgente"});
        comboPrioridade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboPrioridade.setFont(new Font("Arial", Font.PLAIN, 14));
        painelPrincipal.add(comboPrioridade);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        painelPrincipal.add(criarLabel("Descrição do Serviço:", 14, false));
        txtDescricaoServico = new JTextArea(6, 20);
        txtDescricaoServico.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDescricaoServico.setLineWrap(true);
        txtDescricaoServico.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescricaoServico);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        painelPrincipal.add(scroll);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        btnGerarOS = new JButton("Gerar Ordem de Serviço");
        btnGerarOS.setFocusPainted(false);
        btnGerarOS.setBackground(new Color(33, 150, 243));
        btnGerarOS.setForeground(Color.WHITE);
        btnGerarOS.setFont(new Font("Arial", Font.BOLD, 14));
        btnGerarOS.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGerarOS.setPreferredSize(new Dimension(200, 40));

        btnGerarOS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarOrdemServico();
            }
        });

        painelPrincipal.add(btnGerarOS);

        add(painelPrincipal);
    }

    private JLabel criarLabel(String texto, int tamanho, boolean negrito) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", negrito ? Font.BOLD : Font.PLAIN, tamanho));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField criarCampoTexto(String label, JPanel painel) {
        painel.add(criarLabel(label, 14, false));
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(campo);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        return campo;
    }

    private void gerarOrdemServico() {
        String cliente = txtNomeCliente.getText();
        String telefone = txtTelefone.getText();
        String placa = txtPlacaVeiculo.getText();
        String prioridade = (String) comboPrioridade.getSelectedItem();
        String descricao = txtDescricaoServico.getText();

        // Aqui é só simulação para demonstração
        JOptionPane.showMessageDialog(this,
                "Ordem de Serviço Gerada com Sucesso!\n\nCliente: " + cliente +
                        "\nPlaca: " + placa +
                        "\nPrioridade: " + prioridade +
                        "\nDescrição:\n" + descricao);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrdemServico());
    }
}
