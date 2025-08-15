package com.sistemaMaster.auxiliar;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

public class iniciaBanco {

    private static final Logger LOGGER = Logger.getLogger(iniciaBanco.class.getName());
    private static final String DB_FILE = "bancodedadosvendas.db";

    public static void inicializarBanco() {
        String dbPath = System.getProperty("user.dir") + File.separator + DB_FILE;
        String url = "jdbc:sqlite:" + dbPath;

        try {
            // Verifica se o banco de dados existe antes da conexão
            File banco = new File(dbPath);
            boolean bancoExistia = banco.exists();
            
            if (!bancoExistia) {
                LOGGER.info("Criando novo banco de dados: " + DB_FILE);
            } else {
                LOGGER.info("Banco de dados encontrado: " + DB_FILE);
            }

            // Garante que o driver SQLite está carregado
            Class.forName("org.sqlite.JDBC");
            
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Driver SQLite não encontrado: " + e.getMessage());
            throw new RuntimeException("Driver SQLite não disponível", e);
        }

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // Ativa foreign keys sempre (independente da existência do banco)
            stmt.execute("PRAGMA foreign_keys = ON");

            // Scripts de criação das tabelas (sempre executa)
            String[] sqls = {
                    "CREATE TABLE IF NOT EXISTS TBCLIENTE ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "Nome TEXT NOT NULL, "
                            + "Telefone TEXT, "
                            + "Placa TEXT, "
                            + "ModeloMoto TEXT, "
                            + "DataCadastro DATE, "
                            + "QuilometragemAtual INTEGER DEFAULT 0, "
                            + "Observacao TEXT"
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
                            + "CodigoProduto TEXT NOT NULL,"
                            + "CodigoFornecedor INTEGER NOT NULL, "
                            + "QuantidadeMinimaEstoque INTEGER DEFAULT 0, "
                            + "ativo INTEGER NOT NULL DEFAULT 1, "
                            + "FOREIGN KEY (CodigoFornecedor) REFERENCES TBFORNECEDOR (Codigo)"
                            + ");",

                    "CREATE TABLE IF NOT EXISTS TBVENDA ("
                            + "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "CodigoCliente INTEGER NOT NULL, "
                            + "DataVenda DATE NOT NULL, "
                            + "Situacao INTEGER NOT NULL, "
                            + "Forma_Pagamento INTEGER NOT NULL, "
                            + "ValorTotalProduto REAL NOT NULL, "
                            + "MaoDeObra REAL DEFAULT 0, "
                            + "ValorTotalVenda REAL NOT NULL, "
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
            LOGGER.info("Banco de dados e tabelas inicializados com sucesso");
        } catch (Exception e) {
            LOGGER.severe("Erro ao inicializar o banco de dados: " + e.getMessage());
            throw new RuntimeException("Falha na inicialização do banco de dados", e);
        }
    }
}