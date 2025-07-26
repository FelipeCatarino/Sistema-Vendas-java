package com.sistemaMaster.gui;

import javax.swing.*;
import java.awt.*;

public class DesktopPaneComImagem extends JDesktopPane {

    private Image imagem;

    public DesktopPaneComImagem(Image imagem) {
        this.imagem = imagem;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
