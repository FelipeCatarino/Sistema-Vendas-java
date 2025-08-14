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
            String sql = "INSERT INTO TBCLIENTE (NOME, CPF, DATANASCIMENTO) VALUES (?, ?, ?)";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setDate(3, new Date(cliente.getDataNascimento().getTime()));
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
            String sql = "UPDATE TBCLIENTE SET NOME=?, CPF=?, DATANASCIMENTO=? WHERE CODIGO=?";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setDate(3, new Date(cliente.getDataNascimento().getTime()));
            ps.setInt(4, cliente.getCodigo());
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
                cliente.setCpf(rs.getString("CPF"));
                cliente.setDataNascimento(rs.getDate("DATANASCIMENTO"));
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
            cliente.setCpf(rs.getString("CPF"));
            cliente.setDataNascimento(rs.getDate("DATANASCIMENTO"));
        }
        return cliente;
    }
}
