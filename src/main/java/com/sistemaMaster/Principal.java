package com.sistemaMaster;


import com.sistemaMaster.auxiliar.iniciaBanco;
import com.sistemaMaster.gui.Menu;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


/**
 * Classe principal que inicia a aplicação
 *
 * @author Juliano
 */
public class Principal {

    /**
     * Método responsável por iniciar a aplicação
     *
     * @param args lista de parâmetros do método main
     */
    public static void main(String[] args) {
    // Fonte Dialog suporta acentuação e caracteres especiais em qualquer SO
    UIManager.put("Label.font", new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("TextField.font", new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("FormattedTextField.font", new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("Button.font", new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("Table.font", new Font("Dialog", Font.PLAIN, 12));
    UIManager.put("TableHeader.font", new Font("Dialog", Font.BOLD, 12));

    lookAndFeel();
    iniciaBanco.inicializarBanco();
    Menu menu = new Menu();
    menu.setVisible(true);
}

    private static void lookAndFeel() {
        try {
            boolean nimbusSet = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusSet = true;
                    break;
                }
            }
            if (!nimbusSet) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
