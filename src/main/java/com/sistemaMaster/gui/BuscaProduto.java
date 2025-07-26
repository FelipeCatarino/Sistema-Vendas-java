package com.sistemaMaster.gui;

import com.sistemaMaster.dao.ProdutoDAO;
import com.sistemaMaster.gui.tm.ProdutoTableModel;
import com.sistemaMaster.to.Produto;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 * Janela de consulta a dados do produto
 *
 * @author Juliano
 */
public class BuscaProduto extends javax.swing.JDialog {

    Object parent;

    public BuscaProduto(JInternalFrame parent) {
        super();
        this.parent = parent;
        initComponents();
        carregarGrade();
    }

    public BuscaProduto(JFrame parent) {
        super(parent, true); // true = modal
        this.parent = parent;
        initComponents();
        carregarGrade();
    }

    private void carregarGrade() {
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            ProdutoTableModel ptm = (ProdutoTableModel) tbGrade.getModel();
            ptm.setDados(produtoDAO.listarTodos());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar grade.\n" + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {

        pnFiltro = new javax.swing.JPanel();
        lbFiltro = new javax.swing.JLabel();
        tfFiltro = new javax.swing.JTextField();
        spGrade = new javax.swing.JScrollPane();
        tbGrade = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Busca cliente");

        pnFiltro.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lbFiltro.setText("Filtro:");
        pnFiltro.add(lbFiltro);

        tfFiltro.setColumns(30);
        tfFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfFiltroKeyReleased(evt);
            }
        });
        pnFiltro.add(tfFiltro);

        getContentPane().add(pnFiltro, java.awt.BorderLayout.PAGE_START);

        tbGrade.setAutoCreateRowSorter(true);
        tbGrade.setModel(new ProdutoTableModel());
        tbGrade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGradeMouseClicked(evt);
            }
        });
        spGrade.setViewportView(tbGrade);

        getContentPane().add(spGrade, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(500, 350));
        setLocationRelativeTo(null);
    }

    private void tbGradeMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            ProdutoTableModel ptm = (ProdutoTableModel) tbGrade.getModel();
            int linhaSelecionada = tbGrade.getRowSorter().convertRowIndexToModel(tbGrade.getSelectedRow());
            Produto produto = ptm.getRowValue(linhaSelecionada);

            if (parent instanceof LancamentoVenda) {
                LancamentoVenda lv = (LancamentoVenda) parent;
                lv.setProduto(produto);
                dispose();
            } else if (parent instanceof LancamentoCompra) {
                LancamentoCompra lc = (LancamentoCompra) parent;
                lc.setProduto(produto);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Campo de destino inválido.", "Alerta",
                        JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    private void tfFiltroKeyReleased(java.awt.event.KeyEvent evt) {
        TableRowSorter rs = (TableRowSorter) tbGrade.getRowSorter();
        rs.setRowFilter(RowFilter.regexFilter("(?i)" + tfFiltro.getText(), 0, 1));
    }

    private javax.swing.JLabel lbFiltro;
    private javax.swing.JPanel pnFiltro;
    private javax.swing.JScrollPane spGrade;
    private javax.swing.JTable tbGrade;
    private javax.swing.JTextField tfFiltro;
    
}
