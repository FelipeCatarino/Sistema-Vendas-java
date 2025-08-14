package com.sistemaMaster.dao;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.sistemaMaster.auxiliar.iniciaBanco;

public class Conexao implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(Conexao.class.getName());
    private final String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + 
                              java.io.File.separator + "bancodedadosvendas.db"; 
    private Connection conexao;
    private volatile boolean fechada = false;
    private static volatile boolean bancoInicializado = false;

    public Conexao() throws SQLException {
        this(true);
    }
    
    public Conexao(boolean inicializarBanco) throws SQLException {
        if (inicializarBanco && !bancoInicializado) {
            synchronized (Conexao.class) {
                if (!bancoInicializado) {
                    iniciaBanco.inicializarBanco();
                    bancoInicializado = true;
                }
            }
        }
        
        try {
            Class.forName("org.sqlite.JDBC");
            conexao = DriverManager.getConnection(URL);
            conexao.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite não encontrado", e);
        }
    }

    public Connection getConexao() throws SQLException {
        validarConexao();
        return conexao;
    }

    private void validarConexao() throws SQLException {
        if (fechada) {
            throw new SQLException("Conexão já foi fechada");
        }
        if (conexao == null || conexao.isClosed()) {
            throw new SQLException("Conexão não está disponível");
        }
    }

    public boolean isConexaoValida() {
        try {
            return conexao != null && !conexao.isClosed() && !fechada;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Erro ao verificar estado da conexão", e);
            return false;
        }
    }

    public void confirmar() throws SQLException {
        validarConexao();
        try {
            conexao.commit();
        } catch (SQLException e) {
            try {
                rollback();
            } catch (Exception rollbackException) {
                // Adiciona a exceção do rollback como suprimida
                e.addSuppressed(rollbackException);
            }
            throw new SQLException("Erro ao confirmar transação", e);
        }
    }

    public void rollback() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.rollback();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao fazer rollback", e);
        }
    }

    @Override
    public void close() throws SQLException {
        if (fechada) {
            return; // Já foi fechada
        }
        
        if (conexao != null && !conexao.isClosed()) {
            try {
                if (!conexao.getAutoCommit()) {
                    conexao.commit(); // Confirma pendências antes de fechar
                }
                conexao.close();
            } catch (SQLException e) {
                throw new SQLException("Erro ao fechar conexão", e);
            } finally {
                fechada = true;
            }
        } else {
            fechada = true;
        }
    }
}
