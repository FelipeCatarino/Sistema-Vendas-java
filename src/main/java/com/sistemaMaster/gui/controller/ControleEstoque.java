package com.sistemaMaster.gui.controller;

import com.sistemaMaster.dao.Conexao;
import com.sistemaMaster.gui.CadastroProduto;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;

public class ControleEstoque {

    private JInternalFrame desktopPane;

    public List<Object[]> listarProdutos() throws Exception {
        List<Object[]> lista = new ArrayList<>();

        try (Connection conn = new Conexao().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tbproduto where ativo = 1")) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("Codigo"),
                        rs.getString("Nome"),
                        rs.getDouble("PrecoCompra"),
                        rs.getDouble("PrecoVenda"),
                        rs.getInt("QuantidadeEstoque"),
                        rs.getString("CodigoProduto")
                });
            }
        }

        return lista;
    }
    public void miProdutoActionPerformed(ActionEvent evt) {
        CadastroProduto c = new CadastroProduto();
        desktopPane.add(c);
        c.setVisible(true);
    }

public List<Object[]> buscarProdutos(String busca) {
    List<Object[]> produtos = new ArrayList<>();

    String sql = "SELECT * FROM tbproduto WHERE ativo = 1 AND (Nome LIKE ? OR CodigoProduto LIKE ?)";
    
    try (Connection conn = new Conexao().getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + busca + "%");
        stmt.setString(2, "%" + busca + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            produtos.add(new Object[]{
                rs.getInt("Codigo"),
                rs.getString("Nome"),
                rs.getDouble("PrecoCompra"),
                rs.getDouble("PrecoVenda"),
                rs.getInt("QuantidadeEstoque"),
                rs.getString("CodigoProduto")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return produtos;
}

public void excluirProduto(int codigo){
    String sql = "UPDATE TBPRODUTO SET ativo = 0 WHERE CODIGO = ?";

    try (Connection conn = new Conexao().getConexao()){
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, codigo);
        ps.executeUpdate();
        conn.commit();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception x) {
        x.printStackTrace();

    }
}

}


