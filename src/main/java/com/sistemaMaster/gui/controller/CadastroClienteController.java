package com.sistemaMaster.gui.controller;

import com.sistemaMaster.dao.ClienteDAO;
import com.sistemaMaster.gui.tm.ClienteTableModel;
import com.sistemaMaster.to.Cliente;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Controller para a tela de cadastro de cliente
 * 
 * Controla todas as operações de CRUD e lógica de negócio
 * relacionadas ao cadastro de clientes.
 *
 * @author Felipe da Costa Catarino
 */
public class CadastroClienteController {

    private ClienteDAO clienteDAO;
    private Cliente cliente;
    
    // Referências aos componentes da view
    private JFormattedTextField ftfNome;
    private JFormattedTextField ftfTelefone;
    private JFormattedTextField ftfPlaca;
    private JFormattedTextField ftfModeloMoto;
    private JFormattedTextField ftfQuilometragemAtual;
    private JTextArea ftfObservacao;
    private JTextField ftfPesquisa;
    private JTable tbGrade;
    
    // Interface para comunicação com a view
    public interface CadastroClienteView {
        void habilitarFormulario(boolean ativo);
        void limpaFormulario();
        void mostrarMensagem(String mensagem, String titulo, int tipo);
        void focarCampo(JFormattedTextField campo);
    }
    
    private CadastroClienteView view;

    public CadastroClienteController(CadastroClienteView view) {
        this.view = view;
        this.clienteDAO = new ClienteDAO();
        this.cliente = null;
    }

    public void setComponentes(JFormattedTextField ftfNome, JFormattedTextField ftfTelefone,
                              JFormattedTextField ftfPlaca, JFormattedTextField ftfModeloMoto,
                              JFormattedTextField ftfQuilometragemAtual, JTextArea ftfObservacao,
                              JTextField ftfPesquisa, JTable tbGrade) {
        this.ftfNome = ftfNome;
        this.ftfTelefone = ftfTelefone;
        this.ftfPlaca = ftfPlaca;
        this.ftfModeloMoto = ftfModeloMoto;
        this.ftfQuilometragemAtual = ftfQuilometragemAtual;
        this.ftfObservacao = ftfObservacao;
        this.ftfPesquisa = ftfPesquisa;
        this.tbGrade = tbGrade;
    }

    public void btNovoActionPerformed(ActionEvent evt) {
        cliente = new Cliente();
        view.habilitarFormulario(true);
    }

    public void btSalvarActionPerformed(ActionEvent evt) {
        if (validarFormulario()) {
            cliente.setNome(ftfNome.getText().trim());
            cliente.setTelefone((String) ftfTelefone.getValue());
            cliente.setPlaca(ftfPlaca.getText().trim());
            cliente.setModeloMoto(ftfModeloMoto.getText().trim());
            if (ftfQuilometragemAtual.getValue() != null) {
                cliente.setQuilometragemAtual(Integer.parseInt(ftfQuilometragemAtual.getValue().toString()));
            }
            cliente.setObservacao(ftfObservacao.getText().trim());
            cliente.setDataCadastro(new Date());

            if (cliente.getCodigo() == 0) {
                try {
                    clienteDAO.inserir(cliente);
                    view.mostrarMensagem("Cliente inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    view.mostrarMensagem("Erro ao inserir o cliente.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                try {
                    clienteDAO.alterar(cliente);
                    view.mostrarMensagem("Cliente alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    view.mostrarMensagem("Erro ao alterar o cliente.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            view.habilitarFormulario(false);
            carregarGrade();
        }
    }

    public void btExcluirActionPerformed(ActionEvent evt) {
        if (cliente == null) {
            view.mostrarMensagem("Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o cliente " + cliente.getNome() + "?", 
                                                 "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                clienteDAO.excluir(cliente);
                view.mostrarMensagem("Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                view.mostrarMensagem("Erro ao excluir o cliente.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            view.habilitarFormulario(false);
            carregarGrade();
        }
    }

    public void btCancelarActionPerformed(ActionEvent evt) {
        view.habilitarFormulario(false);
    }

    public void tbGradeMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2 && tbGrade.getSelectedRow() != -1) {
            ClienteTableModel tm = (ClienteTableModel) tbGrade.getModel();
            cliente = tm.getRowValue(tbGrade.getRowSorter().convertRowIndexToModel(tbGrade.getSelectedRow()));

            ftfNome.setValue(cliente.getNome());
            ftfTelefone.setValue(cliente.getTelefone());
            ftfPlaca.setValue(cliente.getPlaca());
            ftfModeloMoto.setValue(cliente.getModeloMoto());
            ftfQuilometragemAtual.setValue(cliente.getQuilometragemAtual());
            ftfObservacao.setText(cliente.getObservacao());

            view.habilitarFormulario(true);
        }
    }

    public void ftfPesquisaKeyReleased(KeyEvent evt) {
        pesquisarClientes();
    }

    public void btPesquisarActionPerformed(ActionEvent evt) {
        pesquisarClientes();
    }

    public void btLimparPesquisaActionPerformed(ActionEvent evt) {
        ftfPesquisa.setText("");
        carregarGrade();
    }

    private boolean validarFormulario() {
        if (ftfNome.getText().trim().length() < 2) {
            view.mostrarMensagem("Nome inválido. Deve ter pelo menos 2 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);
            view.focarCampo(ftfNome);
            return false;
        }
        if (ftfTelefone.getText().trim().length() < 10) {
            view.mostrarMensagem("Telefone inválido. Deve ter pelo menos 10 dígitos.", "Alerta", JOptionPane.WARNING_MESSAGE);
            view.focarCampo(ftfTelefone);
            return false;
        }
        if (ftfPlaca.getText().trim().length() < 7) {
            view.mostrarMensagem("Placa inválida. Deve ter pelo menos 7 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);
            view.focarCampo(ftfPlaca);
            return false;
        }
        if (ftfModeloMoto.getText().trim().length() < 2) {
            view.mostrarMensagem("Modelo da moto inválido. Deve ter pelo menos 2 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);
            view.focarCampo(ftfModeloMoto);
            return false;
        }
        return true;
    }

    public void carregarGrade() {
        ClienteTableModel tm = (ClienteTableModel) tbGrade.getModel();
        try {
            tm.setDados(clienteDAO.listarTodos());
        } catch (Exception ex) {
            view.mostrarMensagem("Erro ao carregar grade.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void pesquisarClientes() {
        String textoPesquisa = ftfPesquisa.getText().trim();
        ClienteTableModel tm = (ClienteTableModel) tbGrade.getModel();
        
        if (textoPesquisa.isEmpty()) {
            carregarGrade();
            return;
        }
        
        try {
            tm.setDados(clienteDAO.pesquisarPorTexto(textoPesquisa));
        } catch (Exception ex) {
            view.mostrarMensagem("Erro ao pesquisar clientes.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
