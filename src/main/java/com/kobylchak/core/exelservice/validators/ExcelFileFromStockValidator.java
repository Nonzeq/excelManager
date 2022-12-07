package com.kobylchak.core.exelservice.validators;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelFileFromStockValidator implements Validator{

    private boolean isValid;
    @Override
    public void validate(File file, JFrame frame) {

        try(Workbook workbook = new XSSFWorkbook(new FileInputStream(file))){
            Sheet sheet = workbook.getSheetAt(0);
            validateHeaders(sheet,frame);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void validateHeaders(Sheet sheet, JFrame frame ){
        Row headers = sheet.getRow(0);
        if(!headers.getCell(0).toString().equals("Код")){
            JOptionPane.showMessageDialog(
                    frame,
                    "Помилка в першому стовбці: " + headers.getCell(0).toString(),
                    "Помилка",JOptionPane.ERROR_MESSAGE);
        } else if (!headers.getCell(1).toString().equals("Найменування")) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Помилка в другому стовбці: " + headers.getCell(1).toString(),
                    "Помилка",JOptionPane.ERROR_MESSAGE);
        } else if (!headers.getCell(2).toString().equals("Ціна")) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Помилка в третьому стовбці: " + headers.getCell(2).toString(),
                    "Помилка",JOptionPane.ERROR_MESSAGE);
        } else if (!headers.getCell(3).toString().equals("Залишок")) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Помилка в четвертому стовбці: " + headers.getCell(3).toString(),
                    "Помилка",JOptionPane.ERROR_MESSAGE);
        }else {
            this.isValid = true;
        }

    }


    public boolean fileIsValid() {
        return isValid;
    }
}
