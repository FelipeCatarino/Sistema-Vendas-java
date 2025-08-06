package com.sistemaMaster.gui.controller;

import com.sistemaMaster.dao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControleEstoque {


    public List<Object[]> listarProdutos() throws Exception {
        List<Object[]> lista = new ArrayList<>();

        try (Connection conn = new Conexao().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tbproduto")) {

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


public List<Object[]> buscarProdutos(String busca) {
    List<Object[]> produtos = new ArrayList<>();

    String sql = "SELECT * FROM tbproduto WHERE Nome LIKE ? OR CodigoProduto LIKE ?";
    
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
}


