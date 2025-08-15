package com.sistemaMaster.gui.controller;

import com.sistemaMaster.dao.FechamentoDAO;
import com.sistemaMaster.dao.Conexao;
import com.sistemaMaster.to.FechamentoDia;
import com.sistemaMaster.auxiliar.PDFGenerator;
import com.sistemaMaster.gui.IFechamentoView;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controller para o sistema de fechamento de caixa
 * Responsável pela lógica de negócio e comunicação entre a View e o Model
 */
public class FechamentoController {
    
    private FechamentoDAO fechamentoDAO;
    private IFechamentoView view;
    private FechamentoDia fechamentoAtual;
    
    public FechamentoController() {
        this.fechamentoDAO = new FechamentoDAO();
    }
    
    /**
     * Define a view que será controlada por este controller
     * @param view interface da view
     */
    public void setView(IFechamentoView view) {
        this.view = view;
    }
    
    /**
     * Inicializa o fechamento carregando os dados do dia
     */
    public void inicializarFechamento() {
        if (view != null) {
            view.exibirCarregamento(true);
        }
        
        try {
            fechamentoAtual = calcularDadosFechamento();
            if (view != null) {
                view.atualizarDadosFechamento(fechamentoAtual);
            }
        } catch (Exception e) {
            if (view != null) {
                view.exibirErro("Erro ao carregar dados do fechamento: " + e.getMessage());
                // Cria um fechamento vazio em caso de erro
                fechamentoAtual = new FechamentoDia(new Date(), 0, 0, 0, 0, 0, 0, 0);
                view.atualizarDadosFechamento(fechamentoAtual);
            }
        } finally {
            if (view != null) {
                view.exibirCarregamento(false);
            }
        }
    }
    
    /**
     * Processa a ação de salvar fechamento
     */
    public void processarSalvarFechamento() {
        if (fechamentoAtual == null) {
            if (view != null) {
                view.exibirErro("Nenhum fechamento para salvar.");
            }
            return;
        }
        
        try {
            String mensagemConfirmacao = String.format(
                "Confirma o salvamento do fechamento do dia?\n\n" +
                "Total de Vendas: R$ %.2f\n" +
                "Mão de Obra: R$ %.2f\n" +
                "Peças: R$ %.2f\n" +
                "Clientes Atendidos: %d",
                fechamentoAtual.getTotalVendas(),
                fechamentoAtual.getTotalMaoDeObra(),
                fechamentoAtual.getTotalPecas(),
                fechamentoAtual.getQuantidadeClientes()
            );
            
            if (view != null && !view.exibirConfirmacao(mensagemConfirmacao)) {
                return;
            }
            
            boolean salvou = salvarFechamento(fechamentoAtual);
            if (salvou && view != null) {
                view.exibirSucesso("Fechamento salvo com sucesso!");
            }
        } catch (Exception e) {
            if (view != null) {
                view.exibirErro("Erro ao salvar fechamento: " + e.getMessage());
            }
        }
    }
    
    /**
     * Processa a ação de gerar PDF
     */
    public void processarGerarPDF() {
        if (fechamentoAtual == null) {
            if (view != null) {
                view.exibirErro("Nenhum fechamento para gerar PDF.");
            }
            return;
        }
        
        try {
            String caminhoCompleto = gerarPDF(fechamentoAtual);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String nomeArquivo = "Fechamento_" + sdf.format(fechamentoAtual.getDataFechamento()) + ".pdf";
            
            if (view != null) {
                view.confirmarAberturaPDF(caminhoCompleto, nomeArquivo);
            }
        } catch (Exception e) {
            if (view != null) {
                view.exibirErro("Erro ao gerar PDF: " + e.getMessage());
            }
        }
    }
    
    /**
     * Processa a ação de carregar histórico
     */
    public void processarCarregarHistorico() {
        try {
            ArrayList<FechamentoDia> fechamentos = obterHistoricoFechamentos();
            if (view != null) {
                view.atualizarHistorico(fechamentos);
            }
        } catch (Exception e) {
            if (view != null) {
                view.exibirErro("Erro ao carregar histórico: " + e.getMessage());
            }
        }
    }
    
    /**
     * Abre o arquivo PDF
     * @param caminhoArquivo caminho do arquivo
     */
    public void abrirPDFArquivo(String caminhoArquivo) {
        try {
            Desktop.getDesktop().open(new File(caminhoArquivo));
        } catch (Exception ex) {
            if (view != null) {
                view.exibirErro("Não foi possível abrir o arquivo automaticamente.\n" +
                        "Você pode encontrá-lo em: " + caminhoArquivo);
            }
        }
    }
    
    /**
     * Retorna o fechamento atual
     * @return fechamento atual ou null se não houver
     */
    public FechamentoDia getFechamentoAtual() {
        return fechamentoAtual;
    }
    
    /**
     * Calcula os dados do fechamento do dia atual
     * @return FechamentoDia com os dados calculados
     */
    public FechamentoDia calcularDadosFechamento() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long inicioDoDia = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long fimDoDia = calendar.getTimeInMillis();
        Date dataFechamento = new Date(fimDoDia);

        double totalVendas = 0, totalDinheiro = 0, totalDebito = 0, totalCredito = 0, totalPix = 0, lucro = 0;
        double totalMaoDeObra = 0, totalPecas = 0;
        int totalServicos = 0, quantidadeClientes = 0, quantidadeMotocicletasAtendidas = 0;

        try {
            Conexao c = new Conexao();
            
            // Consulta principal para vendas e produtos
            String sql = "SELECT v.CODIGO, v.VALORTOTALPRODUTO, v.MaoDeObra, v.FORMA_PAGAMENTO, iv.QUANTIDADE, iv.VALORUNITARIO, p.PRECOVENDA, p.PRECOCOMPRA "
                    +
                    "FROM TBVENDA v " +
                    "JOIN TBITEMVENDA iv ON v.CODIGO = iv.CODIGOVENDA " +
                    "JOIN TBPRODUTO p ON iv.CODIGOPRODUTO = p.CODIGO " +
                    "WHERE v.DATAVENDA BETWEEN ? AND ? AND v.SITUACAO = 2";

            PreparedStatement ps = c.getConexao().prepareStatement(sql);
            ps.setLong(1, inicioDoDia);
            ps.setLong(2, fimDoDia);
            ResultSet rs = ps.executeQuery();

            Set<Integer> vendasContadas = new HashSet<>();
            while (rs.next()) {
                int forma = rs.getInt("FORMA_PAGAMENTO");
                double valorProduto = rs.getDouble("VALORUNITARIO") * rs.getInt("QUANTIDADE");
                double maoDeObraVenda = rs.getDouble("MaoDeObra");
                double valorVenda = valorProduto + maoDeObraVenda;
                int quantidade = rs.getInt("QUANTIDADE");
                double precoVenda = rs.getDouble("PRECOVENDA");
                double precoCompra = rs.getDouble("PRECOCOMPRA");
                int codigoVenda = rs.getInt("CODIGO");

                // Soma apenas uma vez por venda para os totais por forma de pagamento
                if (!vendasContadas.contains(codigoVenda)) {
                    totalVendas += valorVenda;
                    totalMaoDeObra += maoDeObraVenda;
                    vendasContadas.add(codigoVenda);
                    switch (forma) {
                        case 1:
                            totalDinheiro += valorVenda;
                            break;
                        case 2:
                            totalDebito += valorVenda;
                            break;
                        case 3:
                            totalCredito += valorVenda;
                            break;
                        case 4:
                            totalPix += valorVenda;
                            break;
                    }
                }
                // Soma valor das peças e calcula lucro bruto por item vendido
                totalPecas += valorProduto;
                lucro += (precoVenda - precoCompra) * quantidade;
                totalServicos++;
            }
            
            // Consulta para quantidade de clientes únicos atendidos
            String sqlClientes = "SELECT COUNT(DISTINCT v.CODIGOCLIENTE) as qtd_clientes FROM TBVENDA v WHERE v.DATAVENDA BETWEEN ? AND ? AND v.SITUACAO = 2";
            PreparedStatement psClientes = c.getConexao().prepareStatement(sqlClientes);
            psClientes.setLong(1, inicioDoDia);
            psClientes.setLong(2, fimDoDia);
            ResultSet rsClientes = psClientes.executeQuery();
            if (rsClientes.next()) {
                quantidadeClientes = rsClientes.getInt("qtd_clientes");
            }
            
            // Para motocicletas atendidas, vamos usar a quantidade de clientes únicos
            // (cada cliente representa uma motocicleta atendida)
            quantidadeMotocicletasAtendidas = quantidadeClientes;
            
            c.close();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar vendas do dia: " + ex.getMessage(), ex);
        }

        return new FechamentoDia(dataFechamento, totalVendas, totalDinheiro, totalDebito, totalCredito, totalPix,
                lucro, totalServicos, totalMaoDeObra, totalPecas, quantidadeClientes, quantidadeMotocicletasAtendidas);
    }
    
    /**
     * Salva o fechamento no banco de dados
     * @param fechamento dados do fechamento para salvar
     * @return true se salvou com sucesso, false se cancelado pelo usuário
     * @throws Exception em caso de erro ao salvar
     */
    public boolean salvarFechamento(FechamentoDia fechamento) throws Exception {
        // Verifica se já existe fechamento para hoje
        FechamentoDia existente = fechamentoDAO.recuperarPorData(fechamento.getDataFechamento());
        if (existente != null) {
            int opcao = JOptionPane.showConfirmDialog(null,
                    "Já existe um fechamento para hoje.\nDeseja substituí-lo?",
                    "Fechamento Existente",
                    JOptionPane.YES_NO_OPTION);
            if (opcao != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        fechamentoDAO.inserir(fechamento);
        JOptionPane.showMessageDialog(null,
                "Fechamento salvo com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    /**
     * Gera o PDF do fechamento
     * @param fechamento dados do fechamento
     * @return caminho completo do arquivo gerado
     * @throws Exception em caso de erro ao gerar PDF
     */
    public String gerarPDF(FechamentoDia fechamento) throws Exception {
        File diretorio = new File("relatorios");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nomeArquivo = "Fechamento_" + sdf.format(fechamento.getDataFechamento()) + ".pdf";
        String caminhoCompleto = diretorio.getAbsolutePath() + File.separator + nomeArquivo;

        SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yyyy");
        PDFGenerator.gerarFechamentoPDF(
                sdfBr.format(fechamento.getDataFechamento()),
                fechamento.getTotalVendas(),
                fechamento.getTotalDinheiro(),
                fechamento.getTotalDebito(),
                fechamento.getTotalCredito(),
                fechamento.getTotalPix(),
                fechamento.getLucroBruto(),
                fechamento.getTotalServicos(),
                fechamento.getTotalMaoDeObra(),
                fechamento.getTotalPecas(),
                fechamento.getQuantidadeClientes(),
                fechamento.getQuantidadeMotocicletasAtendidas(),
                caminhoCompleto);

        return caminhoCompleto;
    }
    
    /**
     * Abre o arquivo PDF gerado
     * @param caminhoArquivo caminho completo do arquivo
     * @param nomeArquivo nome do arquivo para exibição
     */
    public void abrirPDF(String caminhoArquivo, String nomeArquivo) {
        int opcao = JOptionPane.showConfirmDialog(null,
                "PDF gerado com sucesso!\n\n" +
                        "Arquivo: " + nomeArquivo + "\n" +
                        "Local: " + new File(caminhoArquivo).getParent() + "\n\n" +
                        "Deseja abrir o arquivo agora?",
                "PDF Gerado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().open(new File(caminhoArquivo));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível abrir o arquivo automaticamente.\n" +
                                "Você pode encontrá-lo em: " + caminhoArquivo,
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    /**
     * Recupera o histórico de todos os fechamentos
     * @return lista com todos os fechamentos salvos
     * @throws Exception em caso de erro ao recuperar dados
     */
    public ArrayList<FechamentoDia> obterHistoricoFechamentos() throws Exception {
        return fechamentoDAO.listarTodos();
    }
}
