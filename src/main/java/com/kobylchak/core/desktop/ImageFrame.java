package com.kobylchak.core.desktop;

import javax.swing.*;
import java.util.Objects;

public class ImageFrame {

    public ImageFrame(){
        JFrame frame = new JFrame("Приклад для файлу збази данних");
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(ClassLoader
                .getSystemClassLoader()
                .getResource("images/priklad.png")));
        frame.add(new JLabel(imageIcon));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
