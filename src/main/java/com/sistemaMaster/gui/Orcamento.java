package com.sistemaMaster.gui;

import javax.swing.JInternalFrame;

/*@autor Felipe da Costa Catarino */

public class Orcamento extends JInternalFrame {

    public Orcamento() {
        setTitle("Orçamento");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        titulo = new javax.swing.JPanel();
        titulo.setBorder(javax.swing.BorderFactory.createTitledBorder("Título"));
        getContentPane().add(titulo, java.awt.BorderLayout.NORTH);
    }

    private javax.swing.JPanel titulo;

}
