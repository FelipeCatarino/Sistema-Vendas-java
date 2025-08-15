package com.sistemaMaster.dao;

import com.sistemaMaster.to.Cliente;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe de acesso a dados do cliente
 *
 * @author Juliano
 */
public class ClienteDAO implements IDAO<Cliente> {

    @Override
    public void inserir(Cliente cliente) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "INSERT INTO TBCLIENTE (NOME, TELEFONE, PLACA, MODELOMOTO, DATACADASTRO, QUILOMETRAGEMATUAL, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getPlaca());
            ps.setString(4, cliente.getModeloMoto());
            ps.setDate(5, new Date(cliente.getDataCadastro().getTime()));
            ps.setInt(6, cliente.getQuilometragemAtual());
            ps.setString(7, cliente.getObservacao());
            ps.execute();
            c.confirmar();
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public void alterar(Cliente cliente) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "UPDATE TBCLIENTE SET NOME=?, TELEFONE=?, PLACA=?, MODELOMOTO=?, DATACADASTRO=?, QUILOMETRAGEMATUAL=?, OBSERVACAO=? WHERE CODIGO=?";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getPlaca());
            ps.setString(4, cliente.getModeloMoto());
            ps.setDate(5, new Date(cliente.getDataCadastro().getTime()));
            ps.setInt(6, cliente.getQuilometragemAtual());
            ps.setString(7, cliente.getObservacao());
            ps.setInt(8, cliente.getCodigo());
            ps.execute();
            c.confirmar();
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public void excluir(Cliente cliente) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "DELETE FROM TBCLIENTE WHERE CODIGO=?";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, cliente.getCodigo());
            ps.execute();
            c.confirmar();
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public ArrayList<Cliente> listarTodos() throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "SELECT * FROM TBCLIENTE ORDER BY NOME";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Cliente> listaClientes = new ArrayList<>();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getInt("CODIGO"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setTelefone(rs.getString("TELEFONE"));
                cliente.setPlaca(rs.getString("PLACA"));
                cliente.setModeloMoto(rs.getString("MODELOMOTO"));
                cliente.setDataCadastro(rs.getDate("DATACADASTRO"));
                cliente.setQuilometragemAtual(rs.getInt("QUILOMETRAGEMATUAL"));
                cliente.setObservacao(rs.getString("OBSERVACAO"));
                listaClientes.add(cliente);
            }
            return listaClientes;
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public Cliente recuperar(int codigo) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            return recuperar(c, codigo);
        } finally {
            if (c != null) c.close();
        }
    }

    public Cliente recuperar(Conexao c, int codigo) throws Exception {
        String sql = "SELECT * FROM TBCLIENTE WHERE CODIGO=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();

        Cliente cliente = new Cliente();
        if (rs.next()) {
            cliente.setCodigo(rs.getInt("CODIGO"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setTelefone(rs.getString("TELEFONE"));
            cliente.setPlaca(rs.getString("PLACA"));
            cliente.setModeloMoto(rs.getString("MODELOMOTO"));
            cliente.setDataCadastro(rs.getDate("DATACADASTRO"));
            cliente.setQuilometragemAtual(rs.getInt("QUILOMETRAGEMATUAL"));
            cliente.setObservacao(rs.getString("OBSERVACAO"));
        }
        return cliente;
    }

    public ArrayList<Cliente> pesquisarPorTexto(String texto) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "SELECT * FROM TBCLIENTE WHERE UPPER(NOME) LIKE ? OR UPPER(PLACA) LIKE ? OR UPPER(MODELOMOTO) LIKE ? ORDER BY NOME";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            String textoUpper = "%" + texto.toUpperCase() + "%";
            ps.setString(1, textoUpper);
            ps.setString(2, textoUpper);
            ps.setString(3, textoUpper);
            ResultSet rs = ps.executeQuery();

            ArrayList<Cliente> listaClientes = new ArrayList<>();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getInt("CODIGO"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setTelefone(rs.getString("TELEFONE"));
                cliente.setPlaca(rs.getString("PLACA"));
                cliente.setModeloMoto(rs.getString("MODELOMOTO"));
                cliente.setDataCadastro(rs.getDate("DATACADASTRO"));
                cliente.setQuilometragemAtual(rs.getInt("QUILOMETRAGEMATUAL"));
                cliente.setObservacao(rs.getString("OBSERVACAO"));
                listaClientes.add(cliente);
            }
            return listaClientes;
        } finally {
            if (c != null) c.close();
        }
    }
}
