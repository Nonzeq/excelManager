package com.kobylchak.core.desktop.validators;

import com.kobylchak.core.desktop.MyFrame;

import javax.swing.*;

public class ChooseFileValidator {

    public static boolean validate(JLabel[] jLabels, MyFrame myFrame){

        for(JLabel jLabel : jLabels){
            if(jLabel.getText().equals("")){
                JOptionPane.showMessageDialog(myFrame,"Потрібно вибрати усі файли",
                        "Помилка",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;

    }
}
