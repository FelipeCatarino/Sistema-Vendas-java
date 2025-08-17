package com.sistemaMaster.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.sistemaMaster.to.OrdemServico;

/**
 * Classe de acesso a dados da ordem de serviço
 *
 * @author Felipe da Costa Catarino - Implementação completa
 */
public class OrdemServicoDAO implements IDAO<OrdemServico> {

    @Override
    public void inserir(OrdemServico ordemServico) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "INSERT INTO TBORDEMSERVICO (CODIGOCLIENTE, PLACAVEICULO, MODELOVEICULO, " +
                        "DESCRICAOPROBLEMA, DESCRICAOSERVICO, OBSERVACOES, DATAABERTURA, " +
                        "DATAPREVISAOENTREGA, STATUS, PRIORIDADE, VALORMAOOBRA, VALORPECAS, " +
                        "VALORTOTAL, TECNICORESPONSAVEL, QUILOMETRAGEMVEICULO) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, ordemServico.getCodigoCliente());
            ps.setString(2, ordemServico.getPlacaVeiculo());
            ps.setString(3, ordemServico.getModeloVeiculo());
            ps.setString(4, ordemServico.getDescricaoProblema());
            ps.setString(5, ordemServico.getDescricaoServico());
            ps.setString(6, ordemServico.getObservacoes());
            ps.setDate(7, new Date(ordemServico.getDataAbertura().getTime()));
            
            if (ordemServico.getDataPrevisaoEntrega() != null) {
                ps.setDate(8, new Date(ordemServico.getDataPrevisaoEntrega().getTime()));
            } else {
                ps.setDate(8, null);
            }
            
            ps.setString(9, ordemServico.getStatus());
            ps.setString(10, ordemServico.getPrioridade());
            ps.setBigDecimal(11, ordemServico.getValorMaoDeObra());
            ps.setBigDecimal(12, ordemServico.getValorPecas());
            ps.setBigDecimal(13, ordemServico.getValorTotal());
            ps.setString(14, ordemServico.getTecnicoResponsavel());
            ps.setInt(15, ordemServico.getQuilometragemVeiculo());
            
            ps.execute();
            c.confirmar();
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public void alterar(OrdemServico ordemServico) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "UPDATE TBORDEMSERVICO SET CODIGOCLIENTE=?, PLACAVEICULO=?, MODELOVEICULO=?, " +
                        "DESCRICAOPROBLEMA=?, DESCRICAOSERVICO=?, OBSERVACOES=?, DATAABERTURA=?, " +
                        "DATAPREVISAOENTREGA=?, DATAENTREGA=?, STATUS=?, PRIORIDADE=?, " +
                        "VALORMAOOBRA=?, VALORPECAS=?, VALORTOTAL=?, TECNICORESPONSAVEL=?, " +
                        "QUILOMETRAGEMVEICULO=? WHERE CODIGO=?";
            
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, ordemServico.getCodigoCliente());
            ps.setString(2, ordemServico.getPlacaVeiculo());
            ps.setString(3, ordemServico.getModeloVeiculo());
            ps.setString(4, ordemServico.getDescricaoProblema());
            ps.setString(5, ordemServico.getDescricaoServico());
            ps.setString(6, ordemServico.getObservacoes());
            ps.setDate(7, new Date(ordemServico.getDataAbertura().getTime()));
            
            if (ordemServico.getDataPrevisaoEntrega() != null) {
                ps.setDate(8, new Date(ordemServico.getDataPrevisaoEntrega().getTime()));
            } else {
                ps.setDate(8, null);
            }
            
            if (ordemServico.getDataEntrega() != null) {
                ps.setDate(9, new Date(ordemServico.getDataEntrega().getTime()));
            } else {
                ps.setDate(9, null);
            }
            
            ps.setString(10, ordemServico.getStatus());
            ps.setString(11, ordemServico.getPrioridade());
            ps.setBigDecimal(12, ordemServico.getValorMaoDeObra());
            ps.setBigDecimal(13, ordemServico.getValorPecas());
            ps.setBigDecimal(14, ordemServico.getValorTotal());
            ps.setString(15, ordemServico.getTecnicoResponsavel());
            ps.setInt(16, ordemServico.getQuilometragemVeiculo());
            ps.setInt(17, ordemServico.getCodigo());
            
            ps.execute();
            c.confirmar();
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public void excluir(OrdemServico ordemServico) throws Exception {
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "DELETE FROM TBORDEMSERVICO WHERE CODIGO=?";
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, ordemServico.getCodigo());
            ps.execute();
            c.confirmar();
        } finally {
            if (c != null) c.close();
        }
    }

    @Override
    public ArrayList<OrdemServico> listarTodos() throws Exception {
        ArrayList<OrdemServico> lista = new ArrayList<>();
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "SELECT os.*, cl.NOME as NOMECLIENTE " +
                        "FROM TBORDEMSERVICO os " +
                        "LEFT JOIN TBCLIENTE cl ON os.CODIGOCLIENTE = cl.CODIGO " +
                        "ORDER BY os.CODIGO DESC";
            
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setCodigo(rs.getInt("CODIGO"));
                os.setCodigoCliente(rs.getInt("CODIGOCLIENTE"));
                os.setNomeCliente(rs.getString("NOMECLIENTE"));
                os.setPlacaVeiculo(rs.getString("PLACAVEICULO"));
                os.setModeloVeiculo(rs.getString("MODELOVEICULO"));
                os.setDescricaoProblema(rs.getString("DESCRICAOPROBLEMA"));
                os.setDescricaoServico(rs.getString("DESCRICAOSERVICO"));
                os.setObservacoes(rs.getString("OBSERVACOES"));
                os.setDataAbertura(rs.getDate("DATAABERTURA"));
                os.setDataPrevisaoEntrega(rs.getDate("DATAPREVISAOENTREGA"));
                os.setDataEntrega(rs.getDate("DATAENTREGA"));
                os.setStatus(rs.getString("STATUS"));
                os.setPrioridade(rs.getString("PRIORIDADE"));
                os.setValorMaoDeObra(rs.getBigDecimal("VALORMAOOBRA"));
                os.setValorPecas(rs.getBigDecimal("VALORPECAS"));
                os.setValorTotal(rs.getBigDecimal("VALORTOTAL"));
                os.setTecnicoResponsavel(rs.getString("TECNICORESPONSAVEL"));
                os.setQuilometragemVeiculo(rs.getInt("QUILOMETRAGEMVEICULO"));
                
                lista.add(os);
            }
        } finally {
            if (c != null) c.close();
        }
        return lista;
    }

    @Override
    public OrdemServico recuperar(int codigo) throws Exception {
        OrdemServico os = null;
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "SELECT os.*, cl.NOME as NOMECLIENTE " +
                        "FROM TBORDEMSERVICO os " +
                        "LEFT JOIN TBCLIENTE cl ON os.CODIGOCLIENTE = cl.CODIGO " +
                        "WHERE os.CODIGO=?";
            
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                os = new OrdemServico();
                os.setCodigo(rs.getInt("CODIGO"));
                os.setCodigoCliente(rs.getInt("CODIGOCLIENTE"));
                os.setNomeCliente(rs.getString("NOMECLIENTE"));
                os.setPlacaVeiculo(rs.getString("PLACAVEICULO"));
                os.setModeloVeiculo(rs.getString("MODELOVEICULO"));
                os.setDescricaoProblema(rs.getString("DESCRICAOPROBLEMA"));
                os.setDescricaoServico(rs.getString("DESCRICAOSERVICO"));
                os.setObservacoes(rs.getString("OBSERVACOES"));
                os.setDataAbertura(rs.getDate("DATAABERTURA"));
                os.setDataPrevisaoEntrega(rs.getDate("DATAPREVISAOENTREGA"));
                os.setDataEntrega(rs.getDate("DATAENTREGA"));
                os.setStatus(rs.getString("STATUS"));
                os.setPrioridade(rs.getString("PRIORIDADE"));
                os.setValorMaoDeObra(rs.getBigDecimal("VALORMAOOBRA"));
                os.setValorPecas(rs.getBigDecimal("VALORPECAS"));
                os.setValorTotal(rs.getBigDecimal("VALORTOTAL"));
                os.setTecnicoResponsavel(rs.getString("TECNICORESPONSAVEL"));
                os.setQuilometragemVeiculo(rs.getInt("QUILOMETRAGEMVEICULO"));
            }
        } finally {
            if (c != null) c.close();
        }
        return os;
    }

    /**
     * Lista ordens de serviço por status
     */
    public ArrayList<OrdemServico> listarPorStatus(String status) throws Exception {
        ArrayList<OrdemServico> lista = new ArrayList<>();
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "SELECT os.*, cl.NOME as NOMECLIENTE " +
                        "FROM TBORDEMSERVICO os " +
                        "LEFT JOIN TBCLIENTE cl ON os.CODIGOCLIENTE = cl.CODIGO " +
                        "WHERE os.STATUS = ? " +
                        "ORDER BY os.PRIORIDADE DESC, os.DATAABERTURA ASC";
            
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setCodigo(rs.getInt("CODIGO"));
                os.setCodigoCliente(rs.getInt("CODIGOCLIENTE"));
                os.setNomeCliente(rs.getString("NOMECLIENTE"));
                os.setPlacaVeiculo(rs.getString("PLACAVEICULO"));
                os.setModeloVeiculo(rs.getString("MODELOVEICULO"));
                os.setDescricaoProblema(rs.getString("DESCRICAOPROBLEMA"));
                os.setDescricaoServico(rs.getString("DESCRICAOSERVICO"));
                os.setObservacoes(rs.getString("OBSERVACOES"));
                os.setDataAbertura(rs.getDate("DATAABERTURA"));
                os.setDataPrevisaoEntrega(rs.getDate("DATAPREVISAOENTREGA"));
                os.setDataEntrega(rs.getDate("DATAENTREGA"));
                os.setStatus(rs.getString("STATUS"));
                os.setPrioridade(rs.getString("PRIORIDADE"));
                os.setValorMaoDeObra(rs.getBigDecimal("VALORMAOOBRA"));
                os.setValorPecas(rs.getBigDecimal("VALORPECAS"));
                os.setValorTotal(rs.getBigDecimal("VALORTOTAL"));
                os.setTecnicoResponsavel(rs.getString("TECNICORESPONSAVEL"));
                os.setQuilometragemVeiculo(rs.getInt("QUILOMETRAGEMVEICULO"));
                
                lista.add(os);
            }
        } finally {
            if (c != null) c.close();
        }
        return lista;
    }

    /**
     * Lista ordens de serviço por cliente
     */
    public ArrayList<OrdemServico> listarPorCliente(int codigoCliente) throws Exception {
        ArrayList<OrdemServico> lista = new ArrayList<>();
        Conexao c = null;
        try {
            c = new Conexao();
            String sql = "SELECT os.*, cl.NOME as NOMECLIENTE " +
                        "FROM TBORDEMSERVICO os " +
                        "LEFT JOIN TBCLIENTE cl ON os.CODIGOCLIENTE = cl.CODIGO " +
                        "WHERE os.CODIGOCLIENTE = ? " +
                        "ORDER BY os.CODIGO DESC";
            
            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, codigoCliente);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setCodigo(rs.getInt("CODIGO"));
                os.setCodigoCliente(rs.getInt("CODIGOCLIENTE"));
                os.setNomeCliente(rs.getString("NOMECLIENTE"));
                os.setPlacaVeiculo(rs.getString("PLACAVEICULO"));
                os.setModeloVeiculo(rs.getString("MODELOVEICULO"));
                os.setDescricaoProblema(rs.getString("DESCRICAOPROBLEMA"));
                os.setDescricaoServico(rs.getString("DESCRICAOSERVICO"));
                os.setObservacoes(rs.getString("OBSERVACOES"));
                os.setDataAbertura(rs.getDate("DATAABERTURA"));
                os.setDataPrevisaoEntrega(rs.getDate("DATAPREVISAOENTREGA"));
                os.setDataEntrega(rs.getDate("DATAENTREGA"));
                os.setStatus(rs.getString("STATUS"));
                os.setPrioridade(rs.getString("PRIORIDADE"));
                os.setValorMaoDeObra(rs.getBigDecimal("VALORMAOOBRA"));
                os.setValorPecas(rs.getBigDecimal("VALORPECAS"));
                os.setValorTotal(rs.getBigDecimal("VALORTOTAL"));
                os.setTecnicoResponsavel(rs.getString("TECNICORESPONSAVEL"));
                os.setQuilometragemVeiculo(rs.getInt("QUILOMETRAGEMVEICULO"));
                
                lista.add(os);
            }
        } finally {
            if (c != null) c.close();
        }
        return lista;
    }
}
