package com.sistemaMaster.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sistemaMaster.to.FechamentoDia;

/**
 * Classe de acesso a dados do fechamento
 *
 * @author Sistema
 */
public class FechamentoDAO {
    
    private static final Logger LOGGER = Logger.getLogger(FechamentoDAO.class.getName());

    public void inserir(FechamentoDia fechamento) throws Exception {
        try (Conexao c = new Conexao()) {
            String sql = "INSERT INTO TBFECHAMENTO (DATA_FECHAMENTO, TOTAL_VENDAS, TOTAL_DINHEIRO, TOTAL_DEBITO, TOTAL_CREDITO, TOTAL_PIX, LUCRO_BRUTO, TOTAL_SERVICOS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = c.getConexao().prepareStatement(sql)) {
                ps.setDate(1, new Date(fechamento.getDataFechamento().getTime()));
                ps.setDouble(2, fechamento.getTotalVendas());
                ps.setDouble(3, fechamento.getTotalDinheiro());
                ps.setDouble(4, fechamento.getTotalDebito());
                ps.setDouble(5, fechamento.getTotalCredito());
                ps.setDouble(6, fechamento.getTotalPix());
                ps.setDouble(7, fechamento.getLucroBruto());
                ps.setInt(8, fechamento.getTotalServicos());
                ps.execute();
                c.confirmar();
                LOGGER.info("Fechamento inserido com sucesso para data: " + fechamento.getDataFechamento());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao inserir fechamento", e);
            throw e;
        }
    }

    public ArrayList<FechamentoDia> listarTodos() throws Exception {
        try (Conexao c = new Conexao()) {
            String sql = "SELECT * FROM TBFECHAMENTO ORDER BY DATA_FECHAMENTO DESC";
            try (PreparedStatement ps = c.getConexao().prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                ArrayList<FechamentoDia> listaFechamentos = new ArrayList<>();
                while (rs.next()) {
                    FechamentoDia fechamento = criarFechamentoFromResultSet(rs);
                    listaFechamentos.add(fechamento);
                }
                return listaFechamentos;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar fechamentos", e);
            throw e;
        }
    }

    public FechamentoDia recuperarPorData(java.util.Date data) throws Exception {
        try (Conexao c = new Conexao()) {
            String sql = "SELECT * FROM TBFECHAMENTO WHERE DATA_FECHAMENTO = ?";
            try (PreparedStatement ps = c.getConexao().prepareStatement(sql)) {
                ps.setDate(1, new Date(data.getTime()));
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return criarFechamentoFromResultSet(rs);
                    }
                    return null;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao recuperar fechamento por data", e);
            throw e;
        }
    }

    private FechamentoDia criarFechamentoFromResultSet(ResultSet rs) throws Exception {
        FechamentoDia fechamento = new FechamentoDia();
        fechamento.setCodigo(rs.getInt("CODIGO"));
        fechamento.setDataFechamento(rs.getDate("DATA_FECHAMENTO"));
        fechamento.setTotalVendas(rs.getDouble("TOTAL_VENDAS"));
        fechamento.setTotalDinheiro(rs.getDouble("TOTAL_DINHEIRO"));
        fechamento.setTotalDebito(rs.getDouble("TOTAL_DEBITO"));
        fechamento.setTotalCredito(rs.getDouble("TOTAL_CREDITO"));
        fechamento.setTotalPix(rs.getDouble("TOTAL_PIX"));
        fechamento.setLucroBruto(rs.getDouble("LUCRO_BRUTO"));
        fechamento.setTotalServicos(rs.getInt("TOTAL_SERVICOS"));
        return fechamento;
    }
}

