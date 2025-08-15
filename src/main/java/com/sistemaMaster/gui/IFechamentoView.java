package com.sistemaMaster.gui;

import com.sistemaMaster.to.FechamentoDia;
import java.util.ArrayList;

/**
 * Interface para a View do Fechamento
 * Define os métodos que a interface gráfica deve implementar para comunicação com o Controller
 */
public interface IFechamentoView {
    
    /**
     * Atualiza os dados exibidos na tela com o fechamento atual
     * @param fechamento dados do fechamento para exibir
     */
    void atualizarDadosFechamento(FechamentoDia fechamento);
    
    /**
     * Exibe uma mensagem de erro para o usuário
     * @param mensagem mensagem de erro
     */
    void exibirErro(String mensagem);
    
    /**
     * Exibe uma mensagem de sucesso para o usuário
     * @param mensagem mensagem de sucesso
     */
    void exibirSucesso(String mensagem);
    
    /**
     * Exibe uma mensagem de confirmação e retorna a resposta do usuário
     * @param mensagem mensagem de confirmação
     * @return true se o usuário confirmou, false caso contrário
     */
    boolean exibirConfirmacao(String mensagem);
    
    /**
     * Atualiza a tabela de histórico com os fechamentos
     * @param fechamentos lista de fechamentos para exibir
     */
    void atualizarHistorico(ArrayList<FechamentoDia> fechamentos);
    
    /**
     * Mostra/esconde o indicador de carregamento
     * @param carregando true para mostrar, false para esconder
     */
    void exibirCarregamento(boolean carregando);
    
    /**
     * Solicita confirmação ao usuário para abrir o PDF gerado
     * @param caminhoArquivo caminho do arquivo PDF
     * @param nomeArquivo nome do arquivo para exibição
     */
    void confirmarAberturaPDF(String caminhoArquivo, String nomeArquivo);
}
