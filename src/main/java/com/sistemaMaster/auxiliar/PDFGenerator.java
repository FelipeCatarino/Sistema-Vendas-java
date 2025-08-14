package com.sistemaMaster.auxiliar;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Classe utilitária para geração de PDFs
 * 
 * @author Sistema
 */
public class PDFGenerator {
    
    private static final Logger LOGGER = Logger.getLogger(PDFGenerator.class.getName());
    
    /**
     * Gera PDF da Ordem de Serviço
     */
    public static void gerarOrdemServicoPDF(String nomeCliente, String telefone, String placaVeiculo, 
                                          String prioridade, String descricaoServico, String caminhoArquivo) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            
            document.open();
            
            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("ORDEM DE SERVIÇO - OFICINA MECÂNICA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            // Data e hora
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Font dataFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);
            Paragraph dataHora = new Paragraph("Gerado em: " + sdf.format(new Date()), dataFont);
            dataHora.setAlignment(Element.ALIGN_RIGHT);
            dataHora.setSpacingAfter(20);
            document.add(dataHora);
            
            // Informações do cliente
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            
            PdfPTable tabelaCliente = new PdfPTable(2);
            tabelaCliente.setWidthPercentage(100);
            tabelaCliente.setSpacingAfter(20);
            
            // Cabeçalho da tabela
            PdfPCell headerCell = new PdfPCell(new Phrase("DADOS DO CLIENTE", labelFont));
            headerCell.setColspan(2);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setPadding(10);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaCliente.addCell(headerCell);
            
            // Dados do cliente
            adicionarCelula(tabelaCliente, "Nome:", nomeCliente, labelFont, valueFont);
            adicionarCelula(tabelaCliente, "Telefone:", telefone, labelFont, valueFont);
            adicionarCelula(tabelaCliente, "Placa do Veículo:", placaVeiculo, labelFont, valueFont);
            adicionarCelula(tabelaCliente, "Prioridade:", prioridade, labelFont, valueFont);
            
            document.add(tabelaCliente);
            
            // Descrição do serviço
            Paragraph descricaoTitulo = new Paragraph("DESCRIÇÃO DO SERVIÇO:", labelFont);
            descricaoTitulo.setSpacingAfter(10);
            document.add(descricaoTitulo);
            
            // Cria uma tabela para a descrição com borda
            PdfPTable tabelaDescricao = new PdfPTable(1);
            tabelaDescricao.setWidthPercentage(100);
            tabelaDescricao.setSpacingAfter(30);
            
            PdfPCell celulaDescricao = new PdfPCell(new Phrase(descricaoServico, valueFont));
            celulaDescricao.setPadding(15);
            celulaDescricao.setBorder(Rectangle.BOX);
            celulaDescricao.setMinimumHeight(80);
            tabelaDescricao.addCell(celulaDescricao);
            
            document.add(tabelaDescricao);
            
            // Espaço para assinaturas
            PdfPTable tabelaAssinaturas = new PdfPTable(2);
            tabelaAssinaturas.setWidthPercentage(100);
            tabelaAssinaturas.setSpacingBefore(50);
            
            PdfPCell assinaturaCliente = new PdfPCell(new Phrase("_________________________\nAssinatura do Cliente", valueFont));
            assinaturaCliente.setBorder(Rectangle.NO_BORDER);
            assinaturaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
            assinaturaCliente.setPaddingTop(20);
            
            PdfPCell assinaturaMecanico = new PdfPCell(new Phrase("_________________________\nAssinatura do Mecânico", valueFont));
            assinaturaMecanico.setBorder(Rectangle.NO_BORDER);
            assinaturaMecanico.setHorizontalAlignment(Element.ALIGN_CENTER);
            assinaturaMecanico.setPaddingTop(20);
            
            tabelaAssinaturas.addCell(assinaturaCliente);
            tabelaAssinaturas.addCell(assinaturaMecanico);
            
            document.add(tabelaAssinaturas);
            
            document.close();
            
            LOGGER.info("PDF da Ordem de Serviço gerado com sucesso: " + caminhoArquivo);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao gerar PDF da Ordem de Serviço", e);
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
    
    /**
     * Gera PDF do Fechamento de Caixa
     */
    public static void gerarFechamentoPDF(String dataFechamento, double totalVendas, double totalDinheiro, 
                                        double totalDebito, double totalCredito, double totalPix, 
                                        double lucro, int totalServicos, String caminhoArquivo) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            
            document.open();
            
            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("FECHAMENTO DE CAIXA - OFICINA MECÂNICA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            // Data do fechamento
            Font dataFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            Paragraph dataFech = new Paragraph("Data: " + dataFechamento, dataFont);
            dataFech.setAlignment(Element.ALIGN_CENTER);
            dataFech.setSpacingAfter(30);
            document.add(dataFech);
            
            // Resumo geral
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            
            PdfPTable tabelaResumo = new PdfPTable(2);
            tabelaResumo.setWidthPercentage(100);
            tabelaResumo.setSpacingAfter(20);
            
            // Cabeçalho
            PdfPCell headerCell = new PdfPCell(new Phrase("RESUMO DO DIA", labelFont));
            headerCell.setColspan(2);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setPadding(10);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaResumo.addCell(headerCell);
            
            // Dados do resumo
            adicionarCelula(tabelaResumo, "Total de Vendas:", String.format("R$ %.2f", totalVendas), labelFont, valueFont);
            adicionarCelula(tabelaResumo, "Serviços Realizados:", String.valueOf(totalServicos), labelFont, valueFont);
            adicionarCelula(tabelaResumo, "Lucro Bruto:", String.format("R$ %.2f", lucro), labelFont, valueFont);
            
            document.add(tabelaResumo);
            
            // Detalhamento por forma de pagamento
            PdfPTable tabelaPagamento = new PdfPTable(2);
            tabelaPagamento.setWidthPercentage(100);
            tabelaPagamento.setSpacingAfter(20);
            
            // Cabeçalho
            PdfPCell headerPagamento = new PdfPCell(new Phrase("DETALHAMENTO POR FORMA DE PAGAMENTO", labelFont));
            headerPagamento.setColspan(2);
            headerPagamento.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerPagamento.setPadding(10);
            headerPagamento.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaPagamento.addCell(headerPagamento);
            
            // Dados de pagamento
            adicionarCelula(tabelaPagamento, "Dinheiro:", String.format("R$ %.2f", totalDinheiro), labelFont, valueFont);
            adicionarCelula(tabelaPagamento, "Cartão de Débito:", String.format("R$ %.2f", totalDebito), labelFont, valueFont);
            adicionarCelula(tabelaPagamento, "Cartão de Crédito:", String.format("R$ %.2f", totalCredito), labelFont, valueFont);
            adicionarCelula(tabelaPagamento, "PIX:", String.format("R$ %.2f", totalPix), labelFont, valueFont);
            
            document.add(tabelaPagamento);
            
            // Rodapé com data de geração
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Font rodapeFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            Paragraph rodape = new Paragraph("Relatório gerado automaticamente em: " + sdf.format(new Date()), rodapeFont);
            rodape.setAlignment(Element.ALIGN_CENTER);
            rodape.setSpacingBefore(50);
            document.add(rodape);
            
            document.close();
            
            LOGGER.info("PDF do Fechamento gerado com sucesso: " + caminhoArquivo);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao gerar PDF do Fechamento", e);
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
    
    private static void adicionarCelula(PdfPTable tabela, String label, String valor, Font labelFont, Font valueFont) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, labelFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        cellLabel.setPadding(5);
        
        PdfPCell cellValue = new PdfPCell(new Phrase(valor, valueFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        cellValue.setPadding(5);
        
        tabela.addCell(cellLabel);
        tabela.addCell(cellValue);
    }
}

