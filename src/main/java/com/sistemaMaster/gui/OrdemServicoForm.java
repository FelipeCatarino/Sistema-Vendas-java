package com.sistemaMaster.gui;

import com.sistemaMaster.dao.ClienteDAO;
import com.sistemaMaster.dao.OrdemServicoDAO;
import com.sistemaMaster.to.Cliente;
import com.sistemaMaster.to.OrdemServico;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Janela de gestão de ordens de serviço - Sistema de Vendas para Oficina Mecânica
 * 
 * Interface gráfica para gestão completa de ordens de serviço.
 * Permite criar, editar, visualizar e controlar o status das ordens de serviço.
 *
 * @author Felipe da Costa Catarino - Implementação completa
 */
public class OrdemServicoForm extends JInternalFrame {
    
    private static OrdemServicoForm instance = null;
    
    // Componentes da interface
    private JPanel painelPrincipal;
    private JTabbedPane tabbedPane;
    
    // Aba Cadastro
    private JTextField txtCodigoOS;
    private JTextField txtCodigoCliente;
    private JTextField txtNomeCliente;
    private JTextField txtPlacaVeiculo;
    private JTextField txtModeloVeiculo;
    private JTextField txtQuilometragem;
    private JTextField txtTecnico;
    private JTextArea txtDescricaoProblema;
    private JTextArea txtDescricaoServico;
    private JTextArea txtObservacoes;
    private JComboBox<String> comboStatus;
    private JComboBox<String> comboPrioridade;
    private JTextField txtValorMaoObra;
    private JTextField txtValorPecas;
    private JTextField txtValorTotal;
    private JTextField txtDataAbertura;
    private JTextField txtDataPrevisao;
    private JTextField txtDataEntrega;
    private GerenciadorProdutosOS gerenciadorProdutos;
    
    // Botões
    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JButton btnBuscarCliente;
    private JButton btnAtualizarStatus;
    
    // Aba Listagem
    private JTable tabelaOS;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;
    private JComboBox<String> comboFiltroStatus;
    
    // DAOs
    private OrdemServicoDAO ordemServicoDAO;
    private ClienteDAO clienteDAO;
    
    // Controle de estado
    private boolean editando = false;
    private com.sistemaMaster.to.OrdemServico ordemAtual;
    
    // Formatadores
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public static OrdemServicoForm getInstance() {
        if (instance == null) {
            instance = new OrdemServicoForm();
        }
        return instance;
    }

    public OrdemServicoForm() {
        super("Gestão de Ordens de Serviço", true, true, true, true);
        
        ordemServicoDAO = new OrdemServicoDAO();
        clienteDAO = new ClienteDAO();
        
        initComponents();
        configurarEventos();
        limparFormulario();
        carregarTabela();
        
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                instance = null;
            }
        });
    }

    private void initComponents() {
        painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(44, 44, 44));
        
        // Criar abas
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(44, 44, 44));
        tabbedPane.setForeground(Color.WHITE);
        
        // Aba Cadastro
        JPanel abaCadastro = criarAbaCadastro();
        tabbedPane.addTab("Cadastro", abaCadastro);
        
        // Aba Listagem
        JPanel abaListagem = criarAbaListagem();
        tabbedPane.addTab("Listagem", abaListagem);
        
        painelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarAbaCadastro() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(44, 44, 44));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        painel.add(painelBotoes, BorderLayout.NORTH);
        
        // Painel principal do formulário
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(new Color(44, 44, 44));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Primeira linha - Código OS, Cliente com seletor
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(criarLabel("Código OS:"), gbc);
        gbc.gridx = 1;
        txtCodigoOS = criarCampoTexto(false);
        txtCodigoOS.setPreferredSize(new Dimension(100, 25));
        painelFormulario.add(txtCodigoOS, gbc);
        
        gbc.gridx = 2;
        painelFormulario.add(criarLabel("Cliente:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 2;
        txtNomeCliente = criarCampoTexto(false);
        txtNomeCliente.setPreferredSize(new Dimension(300, 25));
        painelFormulario.add(txtNomeCliente, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 5;
        btnBuscarCliente = criarBotao("🔍", new Color(33, 150, 243));
        btnBuscarCliente.setPreferredSize(new Dimension(40, 25));
        btnBuscarCliente.setFont(new Font("Arial", Font.BOLD, 16));
        painelFormulario.add(btnBuscarCliente, gbc);
        
        // Campo oculto para código do cliente
        txtCodigoCliente = new JTextField();
        txtCodigoCliente.setVisible(false);
        
        // Segunda linha - Dados do veículo
        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(criarLabel("Placa:"), gbc);
        gbc.gridx = 1;
        txtPlacaVeiculo = criarCampoTexto(true);
        txtPlacaVeiculo.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtPlacaVeiculo, gbc);
        
        gbc.gridx = 2;
        painelFormulario.add(criarLabel("Modelo:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 3;
        txtModeloVeiculo = criarCampoTexto(true);
        txtModeloVeiculo.setPreferredSize(new Dimension(300, 25));
        painelFormulario.add(txtModeloVeiculo, gbc);
        gbc.gridwidth = 1;
        
        // Terceira linha - Quilometragem e Técnico
        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(criarLabel("Quilometragem:"), gbc);
        gbc.gridx = 1;
        txtQuilometragem = criarCampoTexto(true);
        txtQuilometragem.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtQuilometragem, gbc);
        
        gbc.gridx = 2;
        painelFormulario.add(criarLabel("Técnico:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 3;
        txtTecnico = criarCampoTexto(true);
        txtTecnico.setPreferredSize(new Dimension(300, 25));
        painelFormulario.add(txtTecnico, gbc);
        gbc.gridwidth = 1;
        
        // Quarta linha - Status e Prioridade
        gbc.gridx = 0; gbc.gridy = 3;
        painelFormulario.add(criarLabel("Status:"), gbc);
        gbc.gridx = 1;
        comboStatus = new JComboBox<>(new String[]{"ABERTA", "EM_ANDAMENTO", "CONCLUIDA", "ENTREGUE", "CANCELADA"});
        comboStatus.setPreferredSize(new Dimension(150, 25));
        painelFormulario.add(comboStatus, gbc);
        
        gbc.gridx = 2;
        painelFormulario.add(criarLabel("Prioridade:"), gbc);
        gbc.gridx = 3;
        comboPrioridade = new JComboBox<>(new String[]{"BAIXA", "NORMAL", "ALTA", "URGENTE"});
        comboPrioridade.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(comboPrioridade, gbc);
        
        // Quinta linha - Datas
        gbc.gridx = 0; gbc.gridy = 4;
        painelFormulario.add(criarLabel("Data Abertura:"), gbc);
        gbc.gridx = 1;
        txtDataAbertura = criarCampoTexto(false);
        txtDataAbertura.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtDataAbertura, gbc);
        
        gbc.gridx = 2;
        painelFormulario.add(criarLabel("Previsão Entrega:"), gbc);
        gbc.gridx = 3;
        txtDataPrevisao = criarCampoTexto(true);
        txtDataPrevisao.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtDataPrevisao, gbc);
        
        gbc.gridx = 4;
        painelFormulario.add(criarLabel("Data Entrega:"), gbc);
        gbc.gridx = 5;
        txtDataEntrega = criarCampoTexto(true);
        txtDataEntrega.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtDataEntrega, gbc);
        
        // Área de texto para descrição do problema
        gbc.gridx = 0; gbc.gridy = 5;
        painelFormulario.add(criarLabel("Descrição do Problema:"), gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 6; gbc.fill = GridBagConstraints.BOTH;
        txtDescricaoProblema = new JTextArea(3, 50);
        txtDescricaoProblema.setLineWrap(true);
        txtDescricaoProblema.setWrapStyleWord(true);
        JScrollPane scrollProblema = new JScrollPane(txtDescricaoProblema);
        scrollProblema.setPreferredSize(new Dimension(600, 60));
        painelFormulario.add(scrollProblema, gbc);
        
        // Gerenciador de produtos
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.BOTH;
        gerenciadorProdutos = new GerenciadorProdutosOS();
        gerenciadorProdutos.setPreferredSize(new Dimension(600, 180));
        painelFormulario.add(gerenciadorProdutos, gbc);
        
        // Área de texto para descrição do serviço
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE;
        painelFormulario.add(criarLabel("Descrição do Serviço:"), gbc);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 6; gbc.fill = GridBagConstraints.BOTH;
        txtDescricaoServico = new JTextArea(3, 50);
        txtDescricaoServico.setLineWrap(true);
        txtDescricaoServico.setWrapStyleWord(true);
        JScrollPane scrollServico = new JScrollPane(txtDescricaoServico);
        scrollServico.setPreferredSize(new Dimension(600, 60));
        painelFormulario.add(scrollServico, gbc);
        
        // Área de texto para observações
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.NONE;
        painelFormulario.add(criarLabel("Observações:"), gbc);
        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 6; gbc.fill = GridBagConstraints.BOTH;
        txtObservacoes = new JTextArea(2, 50);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        scrollObs.setPreferredSize(new Dimension(600, 50));
        painelFormulario.add(scrollObs, gbc);
        
        // Valores
        gbc.gridx = 0; gbc.gridy = 12; gbc.fill = GridBagConstraints.NONE; gbc.gridwidth = 1;
        painelFormulario.add(criarLabel("Valor Mão de Obra:"), gbc);
        gbc.gridx = 1;
        txtValorMaoObra = criarCampoTexto(true);
        txtValorMaoObra.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtValorMaoObra, gbc);
        
        gbc.gridx = 2;
        painelFormulario.add(criarLabel("Valor Peças:"), gbc);
        gbc.gridx = 3;
        txtValorPecas = criarCampoTexto(false); // Calculado automaticamente
        txtValorPecas.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtValorPecas, gbc);
        
        gbc.gridx = 4;
        painelFormulario.add(criarLabel("Valor Total:"), gbc);
        gbc.gridx = 5;
        txtValorTotal = criarCampoTexto(false);
        txtValorTotal.setPreferredSize(new Dimension(120, 25));
        painelFormulario.add(txtValorTotal, gbc);
        
        JScrollPane scrollFormulario = new JScrollPane(painelFormulario);
        scrollFormulario.setBackground(new Color(44, 44, 44));
        scrollFormulario.getViewport().setBackground(new Color(44, 44, 44));
        painel.add(scrollFormulario, BorderLayout.CENTER);
        
        return painel;
    }

    private JPanel criarAbaListagem() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(44, 44, 44));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de filtros
        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltros.setBackground(new Color(44, 44, 44));
        
        painelFiltros.add(criarLabel("Pesquisar:"));
        txtPesquisa = criarCampoTexto(true);
        txtPesquisa.setPreferredSize(new Dimension(200, 25));
        painelFiltros.add(txtPesquisa);
        
        painelFiltros.add(Box.createHorizontalStrut(10));
        painelFiltros.add(criarLabel("Status:"));
        comboFiltroStatus = new JComboBox<>(new String[]{"TODOS", "ABERTA", "EM_ANDAMENTO", "CONCLUIDA", "ENTREGUE", "CANCELADA"});
        comboFiltroStatus.setPreferredSize(new Dimension(120, 25));
        painelFiltros.add(comboFiltroStatus);
        
        JButton btnFiltrar = criarBotao("Filtrar", new Color(33, 150, 243));
        painelFiltros.add(btnFiltrar);
        
        painel.add(painelFiltros, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"Código", "Cliente", "Placa", "Status", "Prioridade", "Data Abertura", "Valor Total"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaOS = new JTable(modeloTabela);
        tabelaOS.setBackground(new Color(60, 60, 60));
        tabelaOS.setForeground(Color.WHITE);
        tabelaOS.setGridColor(new Color(80, 80, 80));
        tabelaOS.setSelectionBackground(new Color(33, 150, 243));
        tabelaOS.getTableHeader().setBackground(new Color(44, 44, 44));
        tabelaOS.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollTabela = new JScrollPane(tabelaOS);
        scrollTabela.setBackground(new Color(44, 44, 44));
        scrollTabela.getViewport().setBackground(new Color(60, 60, 60));
        painel.add(scrollTabela, BorderLayout.CENTER);
        
        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBackground(new Color(44, 44, 44));
        
        btnNovo = criarBotao("Novo", new Color(76, 175, 80));
        btnSalvar = criarBotao("Salvar", new Color(33, 150, 243));
        btnEditar = criarBotao("Editar", new Color(255, 152, 0));
        btnExcluir = criarBotao("Excluir", new Color(244, 67, 54));
        btnCancelar = criarBotao("Cancelar", new Color(96, 125, 139));
        btnAtualizarStatus = criarBotao("Atualizar Status", new Color(156, 39, 176));
        
        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        painel.add(btnCancelar);
        painel.add(Box.createHorizontalStrut(20));
        painel.add(btnAtualizarStatus);
        
        return painel;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    private JTextField criarCampoTexto(boolean editavel) {
        JTextField campo = new JTextField();
        campo.setEditable(editavel);
        campo.setBackground(editavel ? Color.WHITE : new Color(240, 240, 240));
        campo.setForeground(Color.BLACK);
        campo.setFont(new Font("Arial", Font.PLAIN, 12));
        return campo;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(100, 30));
        return botao;
    }

    private void configurarEventos() {
        btnNovo.addActionListener(e -> novaOrdem());
        btnSalvar.addActionListener(e -> salvarOrdem());
        btnEditar.addActionListener(e -> editarOrdem());
        btnExcluir.addActionListener(e -> excluirOrdem());
        btnCancelar.addActionListener(e -> cancelarEdicao());
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        btnAtualizarStatus.addActionListener(e -> atualizarStatus());
        
        // Eventos da tabela
        tabelaOS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarOrdemSelecionada();
                }
            }
        });
        
        // Calcular total automaticamente
        txtValorMaoObra.addActionListener(e -> calcularTotal());
        
        // Listener para atualizar valores quando produtos mudarem
        Timer timer = new Timer(1000, e -> {
            atualizarValoresProdutos();
            calcularTotal();
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void novaOrdem() {
        limparFormulario();
        habilitarFormulario(true);
        editando = false;
        ordemAtual = new com.sistemaMaster.to.OrdemServico();
        txtDataAbertura.setText(dateFormat.format(new Date()));
        txtCodigoCliente.requestFocus();
    }

    private void salvarOrdem() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            preencherOrdemAtual();
            
            if (editando) {
                ordemServicoDAO.alterar(ordemAtual);
                JOptionPane.showMessageDialog(this, "Ordem de serviço alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                ordemServicoDAO.inserir(ordemAtual);
                JOptionPane.showMessageDialog(this, "Ordem de serviço criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            
            limparFormulario();
            habilitarFormulario(false);
            carregarTabela();
            editando = false;
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar ordem de serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void editarOrdem() {
        if (ordemAtual == null || ordemAtual.getCodigo() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem de serviço para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        habilitarFormulario(true);
        editando = true;
        txtCodigoCliente.requestFocus();
    }

    private void excluirOrdem() {
        if (ordemAtual == null || ordemAtual.getCodigo() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem de serviço para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir a ordem de serviço " + ordemAtual.getCodigo() + "?", 
            "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ordemServicoDAO.excluir(ordemAtual);
                JOptionPane.showMessageDialog(this, "Ordem de serviço excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                carregarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir ordem de serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelarEdicao() {
        limparFormulario();
        habilitarFormulario(false);
        editando = false;
    }

    private void buscarCliente() {
        try {
            SeletorCliente seletor = new SeletorCliente((JFrame) SwingUtilities.getWindowAncestor(this));
            seletor.setVisible(true);
            
            Cliente cliente = seletor.getClienteSelecionado();
            if (cliente != null) {
                txtCodigoCliente.setText(String.valueOf(cliente.getCodigo()));
                
                // Mesclar todas as informações do cliente em um campo nome
                StringBuilder nomeCompleto = new StringBuilder();
                nomeCompleto.append(cliente.getNome());
                
                if (cliente.getTelefone() != null && !cliente.getTelefone().trim().isEmpty()) {
                    nomeCompleto.append(" - Tel: ").append(cliente.getTelefone());
                }
                
                if (cliente.getPlaca() != null && !cliente.getPlaca().trim().isEmpty()) {
                    nomeCompleto.append(" - Placa: ").append(cliente.getPlaca());
                }
                
                if (cliente.getModeloMoto() != null && !cliente.getModeloMoto().trim().isEmpty()) {
                    nomeCompleto.append(" - ").append(cliente.getModeloMoto());
                }
                
                txtNomeCliente.setText(nomeCompleto.toString());
                txtPlacaVeiculo.setText(cliente.getPlaca() != null ? cliente.getPlaca() : "");
                txtModeloVeiculo.setText(cliente.getModeloMoto() != null ? cliente.getModeloMoto() : "");
                txtQuilometragem.setText(String.valueOf(cliente.getQuilometragemAtual()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarStatus() {
        if (ordemAtual == null || ordemAtual.getCodigo() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem de serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String novoStatus = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o novo status:",
            "Atualizar Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"ABERTA", "EM_ANDAMENTO", "CONCLUIDA", "ENTREGUE", "CANCELADA"},
            ordemAtual.getStatus()
        );
        
        if (novoStatus != null) {
            try {
                ordemAtual.setStatus(novoStatus);
                if ("ENTREGUE".equals(novoStatus) && ordemAtual.getDataEntrega() == null) {
                    ordemAtual.setDataEntrega(new Date());
                }
                ordemServicoDAO.alterar(ordemAtual);
                comboStatus.setSelectedItem(novoStatus);
                if (ordemAtual.getDataEntrega() != null) {
                    txtDataEntrega.setText(dateFormat.format(ordemAtual.getDataEntrega()));
                }
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Status atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar status: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarOrdemSelecionada() {
        int linhaSelecionada = tabelaOS.getSelectedRow();
        if (linhaSelecionada != -1) {
            try {
                int codigo = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
                ordemAtual = ordemServicoDAO.recuperar(codigo);
                
                if (ordemAtual != null) {
                    preencherFormulario();
                    tabbedPane.setSelectedIndex(0); // Ir para aba cadastro
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar ordem de serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarTabela() {
        try {
            modeloTabela.setRowCount(0);
            ArrayList<OrdemServico> lista = ordemServicoDAO.listarTodos();
            
            for (OrdemServico os : lista) {
                Object[] linha = {
                    os.getCodigo(),
                    os.getNomeCliente(),
                    os.getPlacaVeiculo(),
                    os.getStatus(),
                    os.getPrioridade(),
                    os.getDataAbertura() != null ? dateFormat.format(os.getDataAbertura()) : "",
                    currencyFormat.format(os.getValorTotal())
                };
                modeloTabela.addRow(linha);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormulario() {
        if (ordemAtual != null) {
            txtCodigoOS.setText(String.valueOf(ordemAtual.getCodigo()));
            txtCodigoCliente.setText(String.valueOf(ordemAtual.getCodigoCliente()));
            txtNomeCliente.setText(ordemAtual.getNomeCliente() != null ? ordemAtual.getNomeCliente() : "");
            txtPlacaVeiculo.setText(ordemAtual.getPlacaVeiculo() != null ? ordemAtual.getPlacaVeiculo() : "");
            txtModeloVeiculo.setText(ordemAtual.getModeloVeiculo() != null ? ordemAtual.getModeloVeiculo() : "");
            txtQuilometragem.setText(String.valueOf(ordemAtual.getQuilometragemVeiculo()));
            txtTecnico.setText(ordemAtual.getTecnicoResponsavel() != null ? ordemAtual.getTecnicoResponsavel() : "");
            txtDescricaoProblema.setText(ordemAtual.getDescricaoProblema() != null ? ordemAtual.getDescricaoProblema() : "");
            txtDescricaoServico.setText(ordemAtual.getDescricaoServico() != null ? ordemAtual.getDescricaoServico() : "");
            txtObservacoes.setText(ordemAtual.getObservacoes() != null ? ordemAtual.getObservacoes() : "");
            comboStatus.setSelectedItem(ordemAtual.getStatus());
            comboPrioridade.setSelectedItem(ordemAtual.getPrioridade());
            txtValorMaoObra.setText(ordemAtual.getValorMaoDeObra().toString());
            txtValorPecas.setText(ordemAtual.getValorPecas().toString());
            txtValorTotal.setText(ordemAtual.getValorTotal().toString());
            
            if (ordemAtual.getDataAbertura() != null) {
                txtDataAbertura.setText(dateFormat.format(ordemAtual.getDataAbertura()));
            }
            if (ordemAtual.getDataPrevisaoEntrega() != null) {
                txtDataPrevisao.setText(dateFormat.format(ordemAtual.getDataPrevisaoEntrega()));
            }
            if (ordemAtual.getDataEntrega() != null) {
                txtDataEntrega.setText(dateFormat.format(ordemAtual.getDataEntrega()));
            }
        }
    }

    private void preencherOrdemAtual() throws Exception {
        ordemAtual.setCodigoCliente(Integer.parseInt(txtCodigoCliente.getText()));
        ordemAtual.setPlacaVeiculo(txtPlacaVeiculo.getText().trim());
        ordemAtual.setModeloVeiculo(txtModeloVeiculo.getText().trim());
        ordemAtual.setDescricaoProblema(txtDescricaoProblema.getText().trim());
        ordemAtual.setDescricaoServico(txtDescricaoServico.getText().trim());
        ordemAtual.setObservacoes(txtObservacoes.getText().trim());
        ordemAtual.setStatus((String) comboStatus.getSelectedItem());
        ordemAtual.setPrioridade((String) comboPrioridade.getSelectedItem());
        ordemAtual.setTecnicoResponsavel(txtTecnico.getText().trim());
        
        if (!txtQuilometragem.getText().trim().isEmpty()) {
            ordemAtual.setQuilometragemVeiculo(Integer.parseInt(txtQuilometragem.getText()));
        }
        
        if (!txtValorMaoObra.getText().trim().isEmpty()) {
            ordemAtual.setValorMaoDeObra(new BigDecimal(txtValorMaoObra.getText().replace(",", ".")));
        }
        
        if (!txtValorPecas.getText().trim().isEmpty()) {
            ordemAtual.setValorPecas(new BigDecimal(txtValorPecas.getText().replace(",", ".")));
        }
        
        if (!txtValorTotal.getText().trim().isEmpty()) {
            ordemAtual.setValorTotal(new BigDecimal(txtValorTotal.getText().replace(",", ".")));
        }
        
        if (!txtDataPrevisao.getText().trim().isEmpty()) {
            ordemAtual.setDataPrevisaoEntrega(dateFormat.parse(txtDataPrevisao.getText()));
        }
        
        if (!txtDataEntrega.getText().trim().isEmpty()) {
            ordemAtual.setDataEntrega(dateFormat.parse(txtDataEntrega.getText()));
        }
    }

    private boolean validarCampos() {
        if (txtCodigoCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o código do cliente.", "Validação", JOptionPane.WARNING_MESSAGE);
            txtCodigoCliente.requestFocus();
            return false;
        }
        
        if (txtDescricaoProblema.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a descrição do problema.", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDescricaoProblema.requestFocus();
            return false;
        }
        
        return true;
    }

    private void calcularTotal() {
        try {
            BigDecimal maoObra = BigDecimal.ZERO;
            BigDecimal pecas = BigDecimal.ZERO;
            
            if (!txtValorMaoObra.getText().trim().isEmpty()) {
                maoObra = new BigDecimal(txtValorMaoObra.getText().replace(",", "."));
            }
            
            if (!txtValorPecas.getText().trim().isEmpty()) {
                pecas = new BigDecimal(txtValorPecas.getText().replace(",", "."));
            }
            
            BigDecimal total = maoObra.add(pecas);
            txtValorTotal.setText(String.format("%.2f", total.doubleValue()));
            
        } catch (NumberFormatException ex) {
            // Ignorar erro de formato
        }
    }
    
    private void atualizarValoresProdutos() {
        if (gerenciadorProdutos != null) {
            BigDecimal valorPecas = gerenciadorProdutos.getValorTotalProdutos();
            txtValorPecas.setText(String.format("%.2f", valorPecas.doubleValue()));
        }
    }

    private void limparFormulario() {
        txtCodigoOS.setText("");
        txtCodigoCliente.setText("");
        txtNomeCliente.setText("");
        txtPlacaVeiculo.setText("");
        txtModeloVeiculo.setText("");
        txtQuilometragem.setText("");
        txtTecnico.setText("");
        txtDescricaoProblema.setText("");
        txtDescricaoServico.setText("");
        txtObservacoes.setText("");
        txtValorMaoObra.setText("0.00");
        txtValorPecas.setText("0.00");
        txtValorTotal.setText("0.00");
        txtDataAbertura.setText("");
        txtDataPrevisao.setText("");
        txtDataEntrega.setText("");
        comboStatus.setSelectedIndex(0);
        comboPrioridade.setSelectedIndex(1);
        
        if (gerenciadorProdutos != null) {
            gerenciadorProdutos.limpar();
        }
        
        ordemAtual = null;
    }

    private void habilitarFormulario(boolean habilitar) {
        txtCodigoCliente.setEditable(habilitar);
        txtPlacaVeiculo.setEditable(habilitar);
        txtModeloVeiculo.setEditable(habilitar);
        txtQuilometragem.setEditable(habilitar);
        txtTecnico.setEditable(habilitar);
        txtDescricaoProblema.setEditable(habilitar);
        txtDescricaoServico.setEditable(habilitar);
        txtObservacoes.setEditable(habilitar);
        txtValorMaoObra.setEditable(habilitar);
        txtValorPecas.setEditable(habilitar);
        txtDataPrevisao.setEditable(habilitar);
        txtDataEntrega.setEditable(habilitar);
        comboStatus.setEnabled(habilitar);
        comboPrioridade.setEnabled(habilitar);
        btnBuscarCliente.setEnabled(habilitar);
        
        btnSalvar.setEnabled(habilitar);
        btnCancelar.setEnabled(habilitar);
        btnNovo.setEnabled(!habilitar);
        btnEditar.setEnabled(!habilitar && ordemAtual != null && ordemAtual.getCodigo() > 0);
        btnExcluir.setEnabled(!habilitar && ordemAtual != null && ordemAtual.getCodigo() > 0);
    }
}