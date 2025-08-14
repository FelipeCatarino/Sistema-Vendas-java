package com.sistemaMaster.dao;

import com.sistemaMaster.to.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe de acesso a dados do produto
 *
 * @author Juliano
 */
public class ProdutoDAO implements IDAO<Produto> {

    @Override
    public void inserir(Produto produto) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao(); // sempre inicia uma nova conexão
            String sql = "INSERT INTO TBPRODUTO (NOME, PRECOCOMPRA, PRECOVENDA, QUANTIDADEESTOQUE, CODIGOPRODUTO, CODIGOFORNECEDOR, QUANTIDADEESTOQUE, QUANTIDADEMINIMAESTOQUE) VALUES (?, ?, ?, 0, ?, ?, ?, ?)";
            PreparedStatement ps = c.getConexao().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPrecoCompra());
            ps.setDouble(3, produto.getPrecoVenda());
            ps.setString(4, produto.getCodigo_produto());
            ps.setInt(5, produto.getFornecedor().getCodigo());
            ps.setInt(6, produto.getQuantidade());
            ps.setInt(7, produto.getQuantidadeMinima());
            ps.execute();
            // Atualiza o campo 'codigo' do produto com o valor gerado pelo banco
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                produto.setCodigo(rs.getInt(1));
            }
            c.confirmar();
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    @Override
    public void alterar(Produto produto) throws Exception {
        Conexao c = new Conexao();
        String sql = "UPDATE TBPRODUTO SET NOME=?, PRECOCOMPRA=?, PRECOVENDA=?, CODIGO_PRODUTO WHERE CODIGO=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, produto.getNome());
        ps.setDouble(2, produto.getPrecoCompra());
        ps.setDouble(3, produto.getPrecoVenda());
        ps.setString(4, produto.getCodigo_produto());
        ps.setInt(5, produto.getCodigo());
        ps.execute();
        c.confirmar();
        c.close();
    }

    public void entradaEstoque(Conexao c, int codigo, int quantidade) throws Exception {
        String sql = "UPDATE TBPRODUTO SET QUANTIDADEESTOQUE= QUANTIDADEESTOQUE  + ? WHERE CODIGO=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, quantidade);
        ps.setInt(2, codigo);
        ps.execute();
    }

    public void saidaEstoque(Conexao c, int codigo, int quantidade) throws Exception {
        String sql = "UPDATE TBPRODUTO SET QUANTIDADEESTOQUE= QUANTIDADEESTOQUE  - ? WHERE CODIGO=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, quantidade);
        ps.setInt(2, codigo);
        ps.execute();
    }

    @Override
    public void excluir(Produto produto) throws Exception {
        Conexao c = new Conexao();
        String sql = "DELETE FROM TBPRODUTO WHERE CODIGO=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, produto.getCodigo());
        ps.execute();
        c.confirmar();
        c.close();
    }

    @Override
    public ArrayList<Produto> listarTodos() throws Exception {
        Conexao c = new Conexao();
        String sql = "SELECT * FROM TBPRODUTO ORDER BY NOME";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Produto> listaProdutos = new ArrayList<>();
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setCodigo(rs.getInt("CODIGO"));
            produto.setNome(rs.getString("NOME"));
            produto.setPrecoCompra(rs.getDouble("PRECOCOMPRA"));
            produto.setPrecoVenda(rs.getDouble("PRECOVENDA"));
            produto.setQuantidade(rs.getInt("QUANTIDADEESTOQUE"));
            produto.setCodigo_produto(rs.getString("CODIGOPRODUTO"));
            listaProdutos.add(produto);
        }
        c.close(); // <-- Fecha a conexão só depois do loop!
        return listaProdutos;
    }

    @Override
    public Produto recuperar(int codigo) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            return recuperar(c, codigo);
        } finally {
            if (c != null) c.close();
        }
    }

    public Produto recuperar(Conexao c, int codigo) throws Exception {
        String sql = "SELECT * FROM TBPRODUTO WHERE CODIGO=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();

        Produto produto = new Produto();
        if (rs.next()) {
            produto.setCodigo(rs.getInt("CODIGO"));
            produto.setNome(rs.getString("NOME"));
            produto.setPrecoCompra(rs.getDouble("PRECOCOMPRA"));
            produto.setPrecoVenda(rs.getDouble("PRECOVENDA"));
            produto.setQuantidade(rs.getInt("QUANTIDADEESTOQUE"));
            produto.setCodigo_produto(rs.getString("CODIGOPRODUTO"));
        }
        return produto;
    }
}
