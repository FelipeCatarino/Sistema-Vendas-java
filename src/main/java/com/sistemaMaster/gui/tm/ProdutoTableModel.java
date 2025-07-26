package com.sistemaMaster.gui.tm;

import com.sistemaMaster.to.Produto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que define o modelo para tabela contendo dados do produto
 *
 * @author Juliano
 * 
 * Felipe incrementou o código para incluir o campo "codigo_produto" no modelo de tabela.
 */
public class ProdutoTableModel extends AbstractTableModel {

    private String colunas[] = {"Nome" , "Preço" , "Quantidade", "codigo"};
    private List<Produto> dados;

    @Override
    public int getRowCount() {
        if(dados == null){
            return 0;
        }
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = dados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return produto.getNome();
            case 1:
                return String.format("R$ %.2f", produto.getPrecoVenda()); 
            case 2:
                return produto.getQuantidade();
            case 3:
                return produto.getCodigo_produto();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int c) {
        return colunas[c];
    }

    @Override
    public boolean isCellEditable(int l, int c) {
        return false;
    }

    public void setDados(List<Produto> dados) {
        this.dados = dados;
        fireTableDataChanged();
    }

    public Produto getRowValue(int l) {
        return dados.get(l);
    }
}
