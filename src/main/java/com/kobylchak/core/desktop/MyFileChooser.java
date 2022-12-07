package com.kobylchak.core.desktop;

import javax.swing.*;
import java.awt.*;

public class MyFileChooser extends JFileChooser {

    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.setLocation(300, 200);
        dialog.setResizable(false);

        return dialog;
    }


}
