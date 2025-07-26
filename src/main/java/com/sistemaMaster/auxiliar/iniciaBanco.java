package com.sistemaMaster.auxiliar;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class iniciaBanco {

    private static final String DB_FILE = "dbsistemavendas.db";

    public static void inicializarBanco() {
        // Usa caminho absoluto para verificação do banco
        File banco = new File("dbsistemavendas");

        String dbPath = System.getProperty("user.dir") + File.separator + DB_FILE;
        String url = "jdbc:sqlite:" + dbPath;

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // Ativa foreign keys sempre (independente da existência do banco)
            stmt.execute("PRAGMA foreign_keys = ON");

            // Scripts de criação das tabelas (sempre executa)
            String[] sqls = {
                    "CREATE TABLE IF NOT EXISTS TBCLIENTE ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "Nome TEXT NOT NULL, "
                            + "CPF TEXT NOT NULL, "
                            + "DataNascimento DATE NOT NULL"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBFORNECEDOR ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "Nome TEXT NOT NULL, "
                            + "CNPJ TEXT NOT NULL"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBPRODUTO ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "Nome TEXT NOT NULL, "
                            + "PrecoCompra REAL NOT NULL, "
                            + "PrecoVenda REAL NOT NULL, "
                            + "QuantidadeEstoque INTEGER NOT NULL, "
                            + "CodigoProduto TEXT NOT NULL"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBVENDA ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "CodigoCliente INTEGER NOT NULL, "
                            + "DataVenda DATE NOT NULL, "
                            + "ValorTotal REAL NOT NULL, "
                            + "Situacao INTEGER NOT NULL, "
                            + "Forma_Pagamento INTEGER NOT NULL, "
                            + "MaoDeObra REAL DEFAULT 0, "
                            + "FOREIGN KEY (CodigoCliente) REFERENCES TBCLIENTE (Codigo)"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBCOMPRA ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "CodigoFornecedor INTEGER NOT NULL, "
                            + "DataCompra DATE NOT NULL, "
                            + "ValorTotal REAL NOT NULL, "
                            + "Situacao INTEGER NOT NULL, "
                            + "FOREIGN KEY (CodigoFornecedor) REFERENCES TBFORNECEDOR (Codigo)"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBITEMVENDA ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "CodigoProduto INTEGER NOT NULL, "
                            + "CodigoVenda INTEGER NOT NULL, "
                            + "Quantidade INTEGER NOT NULL, "
                            + "ValorUnitario REAL NOT NULL, "
                            + "FOREIGN KEY (CodigoProduto) REFERENCES TBPRODUTO (Codigo), "
                            + "FOREIGN KEY (CodigoVenda) REFERENCES TBVENDA (Codigo)"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBITEMCOMPRA ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "CodigoProduto INTEGER NOT NULL, "
                            + "CodigoCompra INTEGER NOT NULL, "
                            + "Quantidade INTEGER NOT NULL, "
                            + "ValorUnitario REAL NOT NULL, "
                            + "FOREIGN KEY (CodigoProduto) REFERENCES TBPRODUTO (Codigo), "
                            + "FOREIGN KEY (CodigoCompra) REFERENCES TBCOMPRA (Codigo)"
                            + ");"
            };

            for (String sql : sqls) {
                stmt.execute(sql);
            }
            System.out.println("Banco e tabelas garantidos.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o banco: " + e.getMessage());
            e.printStackTrace();
        }
    }
}