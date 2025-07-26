package com.sistemaMaster.dao;

import java.sql.*;

/**
 * Classe responsável por abrir a conexão com o banco de dados SQLite
 *
 * @author Felipe
 * @version 1.0
 * 
 * @Note Autor original: Juliano
 * @Note Esta classe foi adaptada para uso com SQLite, mantendo a estrutura original.
 */
public class Conexao {

   
    private final String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + java.io.File.separator + "dbsistemavendas.db"; 

    private Connection conexao;

    public Conexao() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            conexao = DriverManager.getConnection(URL);
            conexao.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Driver SQLite não encontrado.\n" + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar com o banco SQLite.\n" + e.getMessage());
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    public void confirmar() throws SQLException {
        try {
            conexao.commit();
        } catch (SQLException e) {
            throw new SQLException("Erro ao confirmar alterações no banco de dados.\n" + e.getMessage());
        }
    }

    public void fechar() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }
}
